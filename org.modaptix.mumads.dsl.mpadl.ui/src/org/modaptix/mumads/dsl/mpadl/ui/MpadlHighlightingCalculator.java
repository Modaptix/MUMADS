package org.modaptix.mumads.dsl.mpadl.ui;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;

public class MpadlHighlightingCalculator implements	ISemanticHighlightingCalculator
{

	@Override
	public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor)
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
						acceptor.addPosition(node.getOffset(), node.getLength(), MpadlHighlightingConfiguration.HEXNUM_ID);
						break;
					case "INT":
						acceptor.addPosition(node.getOffset(), node.getLength(), MpadlHighlightingConfiguration.DECNUM_ID);
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
								case "AddressingMode":
									acceptor.addPosition(node.getOffset(), node.getLength(), MpadlHighlightingConfiguration.ADDRMODE_ID);
									break;
								default:
									acceptor.addPosition(node.getOffset(), node.getLength(), MpadlHighlightingConfiguration.REGISTER_ID);
									break;
							}
						}
						break;
				}
			}
		}
	}

}
