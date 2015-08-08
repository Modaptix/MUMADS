package org.modaptix.mumads.dsl.asm.validation

import com.google.inject.Inject
import org.eclipse.xtext.validation.Check
import org.modaptix.mumads.dsl.asm.asm.ArchInstructionOrMacro
import org.modaptix.mumads.dsl.asm.asm.AsmPackage
import org.modaptix.mumads.dsl.mpadl.util.filelocator.MpadlLocator

/**
 * Custom validation rules. 
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
class AsmValidator extends AbstractAsmValidator
{
	public static val INVALID_INSTRUCTION = 'invalidInstruction'
	
	@Inject protected MpadlLocator ml;
	
	@Check
	def checkArchInstruction(ArchInstructionOrMacro archInstruction)
	{
		System.out.println("IMpadlLocator ml = " + ml)
		
		error('Not a recognised instruction or macro name',
			AsmPackage.Literals.ARCH_INSTRUCTION_OR_MACRO__MNEMONIC, INVALID_INSTRUCTION)
	}
}
