package org.modaptix.mumads.dsl.asm.validation

import org.eclipse.emf.common.util.EList
import org.eclipse.xtext.validation.Check
import org.modaptix.mumads.dsl.asm.asm.ArchInstructionOrMacro
import org.modaptix.mumads.dsl.asm.asm.AsmPackage
import org.modaptix.mumads.dsl.asm.asm.NamedReference
import org.modaptix.mumads.dsl.asm.asm.Operand
import org.modaptix.mumads.dsl.mpadl.mpadl.OperandTypeAddress
import org.modaptix.mumads.dsl.mpadl.mpadl.OperandTypeImmediate
import org.modaptix.mumads.dsl.mpadl.mpadl.ComplexInstruction
import org.modaptix.mumads.dsl.mpadl.mpadl.InstructionVariant
import org.modaptix.mumads.dsl.mpadl.mpadl.SimpleInstruction
import org.modaptix.mumads.dsl.asm.asm.MacroDefinition

/**
 * Custom validation rules. 
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
class AsmValidator extends AbstractAsmValidator
{
	public static val SIMPLE_INSTRUCTION_WITH_OPERANDS = 'siwo'
	public static val COMPLEX_INSTRUCTION_TOO_FEW_OPERANDS = 'citfo'
	public static val COMPLEX_INSTRUCTION_TOO_MANY_OPERANDS = 'citmo'
	public static val COMPLEX_INSTRUCTION_WRONG_OPERAND_TYPE = 'ciwot'
	public static val NAMED_REFERENCE_IS_MACRO = 'nrim'
	
	@Check
	def checkNamedReference(NamedReference namedReference)
	{
		if (namedReference.target instanceof MacroDefinition)
			error("A macro name can never be used as an operand!", namedReference,
				 AsmPackage.Literals.NAMED_REFERENCE__TARGET, NAMED_REFERENCE_IS_MACRO)
	}
	
	@Check
	def checkArchInstruction(ArchInstructionOrMacro instruction)
	{
		try
		{
			if (instruction.mnemonic instanceof ComplexInstruction)
			{
				subcheckComplexInstruction(instruction, instruction.mnemonic as ComplexInstruction)
			}
			else if (instruction.mnemonic instanceof SimpleInstruction)
			{
				subcheckSimpleInstruction(instruction, instruction.mnemonic as SimpleInstruction)
			}
		}
		catch (Exception e)
		{
			e.printStackTrace()
		}
	}
	
	def subcheckSimpleInstruction(ArchInstructionOrMacro archInst, SimpleInstruction simpInst)
	{
		// If there are no operands then all is well.
		if ((archInst.operands == null) || (archInst.operands.length == 0))
			return
		
		// There are some operands so flag each of them as erroneous.		
		val String msg = 'The instruction ' + simpInst.name + ' does not accept any operands.' 
		archInst.operands.forEach
		[
			error(msg, it,	AsmPackage.Literals.OPERAND__VALUE, SIMPLE_INSTRUCTION_WITH_OPERANDS)
		]
	}
	
	def subcheckComplexInstruction(ArchInstructionOrMacro archInst, ComplexInstruction compInst)
	{
		// Create an Instruction Variant Iterable (ivi) containing all possible instruction variants
		// and a Candidate Instruction Variant Iterable (civi) containing all the current candidates for
		// this instruction.  We'll also need a temporary holder for the new civi and a list of
		// the operands which have been supplied.
		var Iterable<InstructionVariant> ivi = compInst.variants		
		var Iterable<InstructionVariant> civi = ivi
		var Iterable<InstructionVariant> ncivi
		val EList<Operand> operands = archInst.operands
		val int operandCount = operands.length

		// Check that there are some instruction variants which take the
		// supplied number of operands or less.  If not then that is our
		// first error.
		ncivi = civi.filter[it.operands.length <= operandCount]
		if (ncivi.empty)
		{
			error('The instruction ' + compInst.name + ' always requires more than ' + operandCount + ' operands.',
				AsmPackage.Literals.ARCH_INSTRUCTION_OR_MACRO__OPERANDS, COMPLEX_INSTRUCTION_TOO_FEW_OPERANDS)
		}

		// Were we supplied with any operands?  If not then we're done already.
		if (operandCount < 1)
			return;

		// Loop through the operands we were supplied with...		
		for (i : 0 ..< operandCount)
		{
			var String msg
			val operand = operands.get(i)
			
			// Filter the available instruction variants to those which take at least this
			// number of parameters (remember, i is zero based).
			ncivi = civi.filter[it.operands.length > i]
			
			// If the new civi is empty then this operand can never be valid so
			// we need to raise an error.
			if (ncivi.empty)
			{
				msg = "Unexpected operand! The " + compInst.name + 
									" instruction never accepts this many operands."
						
				error(msg, operand,	AsmPackage.Literals.OPERAND__VALUE, COMPLEX_INSTRUCTION_TOO_MANY_OPERANDS)
			}
			else
			{
				// Filter the available instruction variants by this parameter.   
				if (operand.immediate)
					ncivi = civi.filter[it.operands.get(i).operandType instanceof OperandTypeImmediate]
				else 
					ncivi = civi.filter[it.operands.get(i).operandType instanceof OperandTypeAddress]
					
				// If the new civi is empty then there was a problem with this operand so
				// we need to raise an error.
				if (ncivi.empty)
				{
					var InstructionVariant iv
					
					// Could it be right if it was a different instruction variant?
					if (operand.immediate)
						iv = ivi.findFirst[it.operands.get(i).operandType instanceof OperandTypeImmediate]
					else
						iv = ivi.findFirst[it.operands.get(i).operandType instanceof OperandTypeAddress]
						
					if (iv == null)
					{
						// It seems not.
						msg = "The " + compInst.name + " instruction never accepts an "
						if (operand.immediate)
							msg += "immediate value"
						else
							msg += "address"
							
						msg += " as parameter " + (i+1)
					}
					else
					{
						// Presumably, if we got this far, then it should have been the other type.
						msg = "The " + compInst.name + " instruction ("
						msg += "...) expects an "
						if (operand.immediate)
							msg += "immediate value"
						else
							msg += "address"
						msg += " parameter " + (i+1)
					}
					error(msg, operand, AsmPackage.Literals.OPERAND__VALUE, COMPLEX_INSTRUCTION_WRONG_OPERAND_TYPE)
				}
				
				// For the next pass only the new candidates need to be considered.
				civi = ncivi
			}
		}
	}
}
