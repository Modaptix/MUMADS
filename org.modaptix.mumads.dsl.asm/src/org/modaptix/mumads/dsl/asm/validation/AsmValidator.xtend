package org.modaptix.mumads.dsl.asm.validation

//import org.eclipse.xtext.validation.Check
//import org.modaptix.mumads.dsl.asm.asm.ArchInstructionOrMacro

/**
 * Custom validation rules. 
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
class AsmValidator extends AbstractAsmValidator
{
	public static val INVALID_INSTRUCTION = 'invalidInstruction'
	
	/*@Check
	def checkArchInstruction(ArchInstructionOrMacro archInstruction)
	{
		try
		{
			// First we need to get the default Mpadl object associated with this project.
			val String pURI = archInstruction.eResource.URI.toPlatformString(true)
			val String proj = pURI.substring(1, pURI.indexOf("/", 1))
			val Mpadl mpadl = ml.getDefaultMpadlForProject(proj)

			// If we didn't have one then we have a small problem.
			// TODO: Implement error.			
			if (mpadl == null)
			{
				System.out.println("AsmValidator::checkArchInstruction() mpadl == null")
				return
			}
			
			// Second we can check that this instruction at least exists.
			val instruction = mpadl.instructionSet.findFirst[name.equals(archInstruction.mnemonic)] == null
			if (!instruction)
			{
				// It could still be a macro!
				//val macro =  
				
				error('Not a recognised instruction or macro name',
					AsmPackage.Literals.ARCH_INSTRUCTION_OR_MACRO__MNEMONIC, INVALID_INSTRUCTION)
				return
			}
			
			// TODO: Third we can validate the parameters.
		}
		catch (Exception e)
		{
			e.printStackTrace()
		}
	}*/
}
