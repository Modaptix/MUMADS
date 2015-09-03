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

package org.modaptix.mumads.dsl.mpadl.ui.outline

import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider
import org.modaptix.mumads.dsl.mpadl.mpadl.InstructionVariant
import org.modaptix.mumads.dsl.mpadl.mpadl.Mpadl
import org.eclipse.xtext.ui.editor.outline.IOutlineNode

/**
 * Customization of the default outline structure.
 *
 * see http://www.eclipse.org/Xtext/documentation.html#outline
 */
class MpadlOutlineTreeProvider extends DefaultOutlineTreeProvider
{
	def _createChildren(IOutlineNode parentNode, Mpadl rootElement)
	{
		var temp = createEObjectNode(parentNode, rootElement, null, "Registers", true)
		for (node : rootElement.cpuFeatures)
			createNode(temp, node)
		
		temp = createEObjectNode(parentNode, rootElement, null, "Operand Types", true)
		for (node : rootElement.operandTypes)
			createNode(temp, node)
			
		temp = createEObjectNode(parentNode, rootElement, null, "Interrupt Modes", true)
		for (node : rootElement.interruptModes)
			createNode(temp, node)
			
		temp = createEObjectNode(parentNode, rootElement, null, "Instruction Set", true)
		for (node : rootElement.instructionSet)
			createNode(temp, node)
			
	}
	
	def _isLeaf(InstructionVariant instructionVariant)
	{
		true
	}
}
