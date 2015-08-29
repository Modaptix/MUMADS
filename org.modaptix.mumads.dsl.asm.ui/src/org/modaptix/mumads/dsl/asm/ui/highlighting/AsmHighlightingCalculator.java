package org.modaptix.mumads.dsl.asm.ui.highlighting;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.impl.KeywordImpl;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.modaptix.mumads.dsl.asm.asm.ArchInstructionOrMacro;
import org.modaptix.mumads.dsl.asm.asm.MacroDefinition;
import org.modaptix.mumads.dsl.asm.asm.NamedReference;
import org.modaptix.mumads.dsl.asm.asm.PseudoInstructionDs;
import org.modaptix.mumads.dsl.asm.asm.PseudoInstructionEqu;
import org.modaptix.mumads.dsl.mpadl.mpadl.NamedEntity;
import org.modaptix.mumads.dsl.mpadl.mpadl.RegisterIndexable;
import org.modaptix.mumads.dsl.mpadl.mpadl.RegisterIndexed;
import org.modaptix.xtext.util.PolymorphicSemanticHighlightingCalculator;

public class AsmHighlightingCalculator extends PolymorphicSemanticHighlightingCalculator
{
	protected void highlight(NamedEntity semanticElement, RuleCall ruleCall, Assignment assignment, INode node, IHighlightedPositionAcceptor acceptor)
	{
		final String featureName = assignment.getFeature();
		
		if (featureName.equals("name"))
		{
			acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.LABEL_ID);
			return;
		}
	}
	
	protected void highlight(ArchInstructionOrMacro aiomr, CrossReference crossReference, Assignment assignment, INode node, IHighlightedPositionAcceptor acceptor)
	{
		final String featureName = assignment.getFeature();
		
		if (featureName.equals("mnemonic"))
		{
			if (aiomr.getMnemonic() instanceof MacroDefinition)
				acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.MACRO_CALL_ID);
			else
				acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.KEYWORD_ID);
		}
	}
			
	protected void highlight(NamedReference namedReference, CrossReference crossReference, Assignment assignment, INode node, IHighlightedPositionAcceptor acceptor)
	{
		if ((namedReference.getTarget() instanceof RegisterIndexable) ||
			(namedReference.getTarget() instanceof RegisterIndexed))
			acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.REGISTER_ID);
		else if (namedReference.getTarget() instanceof PseudoInstructionEqu)
			acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.EQUATE_ID);
		else if (namedReference.getTarget() instanceof PseudoInstructionDs)
			acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.DS_ID);
		else if (namedReference.getTarget() instanceof NamedEntity)
			acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.LABEL_ID);
	}
	
	protected void highlight(MacroDefinition semanticElement, KeywordImpl grammarElement, EObject grammarElementContainer, INode node, IHighlightedPositionAcceptor acceptor)
	{
		acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.KEYWORD_ID);
	}
	
	protected void highlight(MacroDefinition semanticElement, RuleCall grammarElement, Assignment assignment, INode node, IHighlightedPositionAcceptor acceptor)
	{
		if (assignment == null)
			return;
		
		final String featureName = assignment.getFeature();
		
		if (featureName.equals("name"))
		{
			acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.MACRO_NAME_ID);
			return;
		}
	}
	
	protected void highlight(PseudoInstructionEqu semanticElement, RuleCall grammarElement, Assignment assignment, INode node, IHighlightedPositionAcceptor acceptor)
	{
		if (assignment == null)
			return;
		
		final String featureName = assignment.getFeature();
		
		if (featureName.equals("name"))
		{
			acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.EQUATE_ID);
			return;
		}
	}
	
	protected void highlight(PseudoInstructionDs semanticElement, RuleCall grammarElement, Assignment assignment, INode node, IHighlightedPositionAcceptor acceptor)
	{
		if (assignment == null)
			return;
		
		final String featureName = assignment.getFeature();
		
		if (featureName.equals("name"))
		{
			acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.DS_ID);
			return;
		}
	}
	
	/*protected void highlight(EObject semanticElement, EObject grammarElement, EObject grammarElementContainer, INode node, IHighlightedPositionAcceptor acceptor)
	{
		System.out.println("Missing highlight() method for: " + semanticElement.getClass().getSimpleName()
		  											  + " : " + grammarElement.getClass().getSimpleName()
		  											  + " : " + grammarElementContainer.getClass().getSimpleName());
	}*/
}
