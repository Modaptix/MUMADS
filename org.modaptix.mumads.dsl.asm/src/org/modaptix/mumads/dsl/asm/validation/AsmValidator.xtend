package org.modaptix.mumads.dsl.asm.validation

import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.validation.Check
import org.modaptix.mumads.dsl.asm.asm.ArchInstructionOrMacro
import org.modaptix.mumads.dsl.asm.asm.AsmPackage
import org.modaptix.mumads.dsl.asm.asm.Operand
import org.modaptix.mumads.dsl.mpadl.mpadl.AddressingModeAddress
import org.modaptix.mumads.dsl.mpadl.mpadl.AddressingModeImmediate
import org.modaptix.mumads.dsl.mpadl.mpadl.ComplexInstruction
import org.modaptix.mumads.dsl.mpadl.mpadl.InstructionVariant
import org.modaptix.mumads.dsl.mpadl.mpadl.SimpleInstruction

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
	
	@Check
	def checkOperand(Operand operand)
	{
		val EObject instruction = operand.eContainer
		if (instruction instanceof ArchInstructionOrMacro)
		{
			// If this operand is being supplied to a SimpleInstruction then it shouldn't be!
			if (instruction.mnemonic instanceof SimpleInstruction)
			{
				val SimpleInstruction simpleInstruction = instruction.mnemonic as SimpleInstruction
				error('The instruction ' + simpleInstruction.name + ' does not accept any operands.',
					AsmPackage.Literals.OPERAND__VALUE, SIMPLE_INSTRUCTION_WITH_OPERANDS)
				return
			}
		}
	}
	
	def boolean instVarOpLe(EList<InstructionVariant> instructionVariants, int operandCount)
	{
		for (InstructionVariant iv : instructionVariants)
			if (iv.operands.length <= operandCount)
				return true
		
		return false
	}
	
	@Check
	def checkArchInstruction(ArchInstructionOrMacro instruction)
	{
		try
		{
			if (instruction.mnemonic instanceof ComplexInstruction)
			{
				// Make an iterable which represents the instruction variants for this
				// mnemonic.
				val ComplexInstruction complexInstruction = instruction.mnemonic as ComplexInstruction
				val EList<InstructionVariant> instructionVariants = complexInstruction.variants
				//var Iterable<InstructionVariant> civi

				val int operandCount = instruction.operands.length
				
				// Check that there are some instruction variants which take the
				// supplied number of operands or less.
				if (!instVarOpLe(instructionVariants, operandCount))
				{
					error('The instruction ' + complexInstruction.name + ' requires more than ' + operandCount + ' operands.',
						AsmPackage.Literals.ARCH_INSTRUCTION_OR_MACRO__OPERANDS, COMPLEX_INSTRUCTION_TOO_FEW_OPERANDS)
					return
				}
				
				// Mark any operands which must be too many as erroneous.
				var int maxOperandCount = instructionVariants.maxBy[it.operands.length].operands.length
				if (operandCount > maxOperandCount)
				{
					for (i : maxOperandCount ..< operandCount)
					{
						error('Unexpected operand!', instruction.operands.get(i),
							AsmPackage.Literals.OPERAND__VALUE, COMPLEX_INSTRUCTION_TOO_MANY_OPERANDS)
					}
					return
				}
				
				// Filter the remaining candidate instruction variants.
				val List<InstructionVariant> civ = instructionVariants.filter[it.operands.length == operandCount].toList
				
				// Loop through the operands supplied with this instruction...				
				for (i : 0 ..< instruction.operands.length)
				{
					var Operand operand 
					// If the candidate iterable is not empty then...
					if (!civ.empty)
					{
						// Filter the possible variants   
						operand = instruction.operands.get(i)
						if (operand.immediate)
							civ.removeAll(civ.filter[it.operands.get(i).addressingMode instanceof AddressingModeAddress])
						else 
							civ.removeAll(civ.filter[it.operands.get(i).addressingMode instanceof AddressingModeImmediate])
					}
					
					// If the iterable is now empty then the last operand that we encountered must
					// be the wrong type.
					if (civ.empty)
					{					
						error('Incorrect operand type!', operand, 
							AsmPackage.Literals.OPERAND__VALUE, COMPLEX_INSTRUCTION_WRONG_OPERAND_TYPE)
						return
					}					
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace()
		}
	}
}
