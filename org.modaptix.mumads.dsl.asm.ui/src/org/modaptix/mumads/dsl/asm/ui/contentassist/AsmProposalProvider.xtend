/*******************************************************************************
 * Copyright (c) 2015 Modaptix Limited.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Max Hacking - initial implementation
 *     
 *******************************************************************************/

package org.modaptix.mumads.dsl.asm.ui.contentassist

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.Assignment
import org.eclipse.xtext.CrossReference
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.RuleCall
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor
import org.modaptix.mumads.dsl.asm.asm.ArchInstructionOrMacro
import org.modaptix.mumads.dsl.asm.asm.AsmPackage
import org.modaptix.mumads.dsl.mpadl.mpadl.ComplexInstruction
import org.modaptix.mumads.dsl.mpadl.mpadl.ConcreteInstruction
import org.modaptix.mumads.dsl.mpadl.mpadl.SimpleInstruction
import org.modaptix.xtext.expressions.arithmeticExpression.IntegerExpression

/**
 * see http://www.eclipse.org/Xtext/documentation.html#contentAssist on how to customize content assistant
 */
class AsmProposalProvider extends AbstractAsmProposalProvider
{

	override completeArchInstructionOrMacro_Operands(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completeArchInstructionOrMacro_Operands - " + model.class)

		// This function will be called with some random AST node in model, usually a MacroDefinition
		// or the top level Asm container, never an ArchInstructionOrMacro as that is handled in the
		// function below.
		//
		// Whatever the node we are going to be in the "operand space" of an instruction so we
		// need to find that instruction using the context.

		var ArchInstructionOrMacro aiom = null
		var INode currentNode = context.lastCompleteNode

		do
		{
			var EObject grammarElement = currentNode.grammarElement
			var RuleCall ruleCall = null
			
			if (grammarElement instanceof RuleCall)
			{
				ruleCall = grammarElement as RuleCall
				
				if ("Instruction".equals(ruleCall.rule.name))
				{
					val aiomNode = currentNode.asTreeIterable.findFirst[it.grammarElement instanceof CrossReference]
					val EObject eObject = NodeModelUtils.findActualSemanticObjectFor(aiomNode);
					
					if (eObject instanceof ArchInstructionOrMacro)
						aiom = eObject as ArchInstructionOrMacro
				}
			}
			
			if (currentNode.hasPreviousSibling)
				currentNode = currentNode.previousSibling
			else
				currentNode = currentNode.parent			

		} while (currentNode != null && aiom == null)
		
		if (aiom.mnemonic instanceof ComplexInstruction)
			doCompleteArchInstructionOrMacro_Operands(aiom, assignment, 0, context, acceptor)
	}

	def completeArchInstructionOrMacro_Operands(ArchInstructionOrMacro aiom, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completeArchInstructionOrMacro_Operands")

		// This should probably never happen so if it does we just do nothing.
		if (!(aiom.mnemonic instanceof ComplexInstruction))
			return;
		
		// Whenever this function is called we know there are already some operands so		
		// all we need to do now is work out which operand we are on, which is easier said than done.
		//
		// We can either be on an operand already (in which case we need to traverse the INode tree
		// upwards until we come to an INode created by an Operand RuleCall) or we are in the space
		// after a COMMA (in which case one of our previous siblings will be an INode created by an
		// Operand RuleCall) or there is no COMMA (in which case the previous INode will have been
		// created by an ArchInstructionOrMacro RuleCall and we need to traverse downwards from it
		// counting the existing INodes created by Operand RuleCalls).    		 
		
		// The space after an operand with NO COMMA is the easiest place to detect so let's try that.
		var boolean foundOne = false
		var int operandCount = 0
		var INode currentNode = context.lastCompleteNode.previousSibling
		
		if (currentNode instanceof RuleCall)
		{
			var RuleCall ruleCall = currentNode.grammarElement as RuleCall
			if ("ArchInstructionOrMacro".equals(ruleCall.rule.name))
			{
				foundOne = true
				operandCount = currentNode.asTreeIterable.filter[
						it.grammarElement instanceof RuleCall && "Operand".equals((it.grammarElement as RuleCall).rule.name)
					].length 
			}			
		}

		if (foundOne)
		{
			if (debug) System.out.println("AsmProposalProvider::completeArchInstructionOrMacro_Operands - found a match using method 1")
			doCompleteArchInstructionOrMacro_Operands(aiom, assignment, operandCount, context, acceptor)
			return
		}
		
		// The space after a COMMA is the second easiest place to be so we'll try that now. 
		currentNode = context.lastCompleteNode
		operandCount = 0
		
		while (currentNode != null)
		{
			var EObject grammarElement = currentNode.grammarElement
			var RuleCall ruleCall = null
			
			if (grammarElement instanceof RuleCall)
			{
				ruleCall = grammarElement as RuleCall
				
				if ("Operand".equals(ruleCall.rule.name))
				{
					operandCount++
					foundOne = true
				}
			}
			
			currentNode = currentNode.previousSibling
		} 
		
		if (foundOne)		
		{
			if (debug) System.out.println("AsmProposalProvider::completeArchInstructionOrMacro_Operands - found a match using method 2")
			doCompleteArchInstructionOrMacro_Operands(aiom, assignment, operandCount, context, acceptor)
			return
		}
			
		// If we got this far then presumably we are on an operand already.
		currentNode = context.lastCompleteNode
		operandCount = 1

		while (currentNode != null)
		{
			var EObject grammarElement = currentNode.grammarElement
			var RuleCall ruleCall = null
			
			if (grammarElement instanceof RuleCall)
			{
				ruleCall = grammarElement as RuleCall
				
				if ("Operand".equals(ruleCall.rule.name))
				{
					do
					{
						currentNode = currentNode.previousSibling
						
						grammarElement = currentNode.grammarElement
						ruleCall = grammarElement as RuleCall
						
						if (grammarElement instanceof RuleCall)
						{
							ruleCall = grammarElement as RuleCall
							
							if ("Operand".equals(ruleCall.rule.name))
								operandCount++
						}
						
					} while (currentNode != null)
					
					if (debug) System.out.println("AsmProposalProvider::completeArchInstructionOrMacro_Operands - found a match using method 3")
					doCompleteArchInstructionOrMacro_Operands(aiom, assignment, operandCount, context, acceptor)
					return
				}
			}
			
			currentNode = currentNode.parent
		}
		
		// If we fell through then we never worked out where we were.  Lame.	
		if (debug) System.out.println("AsmProposalProvider::completeArchInstructionOrMacro_Operands - never found a match!")	
	}

	def doCompleteArchInstructionOrMacro_Operands(ArchInstructionOrMacro aiom, Assignment assignment, int operandNum, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::doCompleteArchInstructionOrMacro_Operands - mnemonic = " + aiom.mnemonic.name + " operandNum = " + operandNum)
		
		var ConcreteInstruction concInst = aiom.mnemonic
		
		// This should never happen but just in case.
		if (concInst instanceof SimpleInstruction)
			return;
		
		var scope = getScopeProvider().getScope(aiom, AsmPackage.eINSTANCE.namedReference_Target)
		scope.getAllElements.forEach[
			if (it.EObjectOrProxy instanceof IntegerExpression)
				acceptor.accept(createCompletionProposal(it.name.toString, context))
		]
	}
	
	/* All methods below are blank and have been overridden to suppress completion.  
	 * 
	 */
	override completeKeyword(Keyword keyword, ContentAssistContext contentAssistContext, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completeKeyword")
	}
	
	override completeArchInstructionOrMacro_Mnemonic(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		super.completeArchInstructionOrMacro_Mnemonic(model, assignment, context, acceptor)
		if (debug) System.out.println("AsmProposalProvider::completeArchInstructionOrMacro_Mnemonic")
	}

	override complete_NamedReference(EObject model, RuleCall ruleCall, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::complete_NamedReference")
	}
	
	override complete_Operand(EObject model, RuleCall ruleCall, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::complete_Operand")
	}

	override completeOperand_Value(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completeOperand_Value")
	}

	override completeOperand_Immediate(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completeOperand_Immediate")
	}

	/*override completeAsm_Imports(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completeAsm_Imports")
	}*/

	override completeAsm_Code(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completeAsm_Code")
	}

	/*override completeImport_ImportURI(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completeImport_ImportURI")
	}*/

	override completeMacroDefinition_Name(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completeMacroDefinition_Name")
	}

	override completeMacroDefinition_Code(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completeMacroDefinition_Code")
	}

	/*override completePseudoInstructionEqu_Name(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completePseudoInstructionEqu_Name")
	}

	override completePseudoInstructionEqu_IntegerExpression(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completePseudoInstructionEqu_IntegerExpression")
	}*/

	override completePseudoInstructionOrg_Address(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completePseudoInstructionOrg_Address")
	}

	override completePseudoInstructionDs_Name(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completePseudoInstructionDs_Name")
	}

	override completePseudoInstructionDs_Variant(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completePseudoInstructionDs_Variant")
	}

	override completePseudoInstructionDs_SizeIntExpr(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completePseudoInstructionDs_SizeIntExpr")
	}

	override completeArchInstructionOrMacro_Name(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completeArchInstructionOrMacro_Name")
	}
	
	override completeNamedReference_Target(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		if (debug) System.out.println("AsmProposalProvider::completeNamedReference_Target")
	}
	
}
