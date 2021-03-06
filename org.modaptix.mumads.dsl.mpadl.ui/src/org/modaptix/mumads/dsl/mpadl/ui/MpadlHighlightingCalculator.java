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

package org.modaptix.mumads.dsl.mpadl.ui;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.modaptix.mumads.dsl.mpadl.mpadl.OperandType;
import org.modaptix.mumads.dsl.mpadl.mpadl.Flag;
import org.modaptix.mumads.dsl.mpadl.mpadl.Instruction;
import org.modaptix.mumads.dsl.mpadl.mpadl.Operand;
import org.modaptix.mumads.dsl.mpadl.mpadl.RegisterIndexable;
import org.modaptix.mumads.dsl.mpadl.mpadl.RegisterMmap;
import org.modaptix.mumads.dsl.mpadl.mpadl.RegisterReal;
import org.modaptix.xtext.util.PolymorphicSemanticHighlightingCalculator;

public class MpadlHighlightingCalculator extends PolymorphicSemanticHighlightingCalculator
{
	protected void highlight(RegisterReal semanticElement, RuleCall ruleCall, Assignment assignment, INode node, IHighlightedPositionAcceptor acceptor)
	{
		final String featureName = assignment.getFeature();
		
		if (featureName.equals("name"))
		{
			acceptor.addPosition(node.getOffset(), node.getLength(), MpadlHighlightingConfiguration.REGISTER_REAL_ID);
			return;
		}
		if (featureName.equals("index"))
		{
			acceptor.addPosition(node.getOffset(), node.getLength(), MpadlHighlightingConfiguration.REGISTER_INDX_ID);
			return;
		}
	}
	
	protected void highlight(RegisterMmap semanticElement, RuleCall ruleCall, Assignment assignment, INode node, IHighlightedPositionAcceptor acceptor)
	{
		final String featureName = assignment.getFeature();
		
		if (featureName.equals("name"))
		{
			acceptor.addPosition(node.getOffset(), node.getLength(), MpadlHighlightingConfiguration.REGISTER_MMAP_ID);
			return;
		}
		if (featureName.equals("index"))
		{
			acceptor.addPosition(node.getOffset(), node.getLength(), MpadlHighlightingConfiguration.REGISTER_INDX_ID);
			return;
		}
	}
	
	protected void highlight(RegisterIndexable semanticElement, CrossReference grammarElement, EObject grammarElementContainer, INode node, IHighlightedPositionAcceptor acceptor)
	{
		acceptor.addPosition(node.getOffset(), node.getLength(), MpadlHighlightingConfiguration.REGISTER_INDX_ID);
	}
	
	protected void highlight(Flag semanticElement, RuleCall ruleCall, Assignment assignment, INode node, IHighlightedPositionAcceptor acceptor)
	{
		final String featureName = assignment.getFeature();
		
		if (featureName.equals("name"))
		{
			acceptor.addPosition(node.getOffset(), node.getLength(), MpadlHighlightingConfiguration.REGISTER_FLAG_ID);
			return;
		}
	}
	
	protected void highlight(OperandType semanticElement, RuleCall ruleCall, Assignment assignment, INode node, IHighlightedPositionAcceptor acceptor)
	{
		final String featureName = assignment.getFeature();
		
		if (featureName.equals("name"))
		{
			acceptor.addPosition(node.getOffset(), node.getLength(), MpadlHighlightingConfiguration.ADDRMODE_ID);
		}
	}
	
	protected void highlight(Instruction semanticElement, RuleCall ruleCall, Assignment assignment, INode node, IHighlightedPositionAcceptor acceptor)
	{
		final String featureName = assignment.getFeature();
		
		if (featureName.equals("name"))
		{
			acceptor.addPosition(node.getOffset(), node.getLength(), MpadlHighlightingConfiguration.INSTRUCTION_ID);
		}
	}
	
	protected void highlight(Operand semanticElement, CrossReference grammarElement, EObject grammarElementContainer, INode node, IHighlightedPositionAcceptor acceptor)
	{
		acceptor.addPosition(node.getOffset(), node.getLength(), MpadlHighlightingConfiguration.ADDRMODE_ID);
	}
	
	/*protected void highlight(RegisterMmap semanticElement, CrossReference grammarElement, EObject grammarElementContainer, INode node, IHighlightedPositionAcceptor acceptor)
	{
		System.out.println("Missing highlight() method for: " + semanticElement.getClass().getSimpleName()
		  											  + " : " + grammarElement.getClass().getSimpleName()
		  											  + " : " + grammarElementContainer.getClass().getSimpleName());
	}*/
}
