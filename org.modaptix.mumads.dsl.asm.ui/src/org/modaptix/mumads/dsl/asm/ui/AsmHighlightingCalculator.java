package org.modaptix.mumads.dsl.asm.ui;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;

public class AsmHighlightingCalculator implements ISemanticHighlightingCalculator
{

	@Override
	public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor)
	{
		// Get the node model and loop through it...
		INode root = resource.getParseResult().getRootNode();
		for (INode node : root.getAsTreeIterable())
		{
			// For each node get the current grammar element and, if it is an instance of RuleCall...
			EObject grammarElement = node.getGrammarElement();
			if (grammarElement instanceof RuleCall)
			{
				// determine which rule is represents...
				RuleCall ruleCall = (RuleCall) grammarElement;
				AbstractRule abstractRule = ruleCall.getRule();

				// see if we can highlight it based on the token type alone and, if not...
				if (!tokenHighlight(abstractRule.getName(), acceptor, node))
				{
					// If this grammar element is an assignment then...
					EObject grammarElementContainer = grammarElement.eContainer();
					if (grammarElementContainer instanceof Assignment)
					{
						Assignment assignment = ((Assignment) grammarElementContainer);
						
						INode p = node.getParent();
						if (p != null && p.getGrammarElement() instanceof RuleCall)
						{
							RuleCall parentRuleCall = (RuleCall) p.getGrammarElement();
							AbstractRule parentAbstractRule = parentRuleCall.getRule();
	
							ruleElementHighlight(parentAbstractRule.getName(), assignment.getFeature(), abstractRule.getName(), acceptor, node);
						}
					}
				}
			}
		}
	}
	
	private boolean tokenHighlight(final String tokenName, IHighlightedPositionAcceptor acceptor, INode node)
	{
		switch (tokenName)
		{
			case "HEX":
				acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.HEXNUM_ID);
				return true;
			case "INT":
				acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.DECNUM_ID);
				return true;
		}
		return false;
	}
	
	private boolean ruleElementHighlight(String ruleName, String featureName, String tokenName, IHighlightedPositionAcceptor acceptor, INode node)
	{
		if (ruleName.equals("Instruction"))
		{
			if (featureName.equals("name"))
			{
				acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.KEYWORD_ID);
				return true;
			}
			if (featureName.equals("label"))
			{
				acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.LABEL_ID);
				return true;
			}
		}
		
		return false;
	}
	
/*	public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor)
	{
		INode root = resource.getParseResult().getRootNode();
		for (INode node : root.getAsTreeIterable())
		{
			EObject grammarElement = node.getGrammarElement();
			if (grammarElement instanceof RuleCall)
			{
				RuleCall ruleCall = (RuleCall) grammarElement;
				AbstractRule abstractRule = ruleCall.getRule();

				switch (abstractRule.getName())
				{
					case "HEX":
						acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.HEXNUM_ID);
						break;
					case "INT":
						acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.DECNUM_ID);
						break;
					case "ID":
						INode p = node.getParent();
						if (p != null && p.getGrammarElement() instanceof RuleCall)
						{
							RuleCall parentRuleCall = (RuleCall) p.getGrammarElement();
							AbstractRule r2 = parentRuleCall.getRule();

							// It tests whether the parent node represents a method.
							switch (r2.getName())
							{
								case "Instruction":
									acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.KEYWORD_ID);
									break;
								default:
									acceptor.addPosition(node.getOffset(), node.getLength(), AsmHighlightingConfiguration.REGISTER_ID);
									break;
							}
						}
						break;
				}
			}
		}
	}
*/
	

}
