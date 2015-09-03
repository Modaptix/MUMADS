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

package org.modaptix.mumads.dsl.asm.ui.outline

import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider
import org.modaptix.mumads.dsl.asm.asm.Asm
import org.modaptix.mumads.dsl.mpadl.mpadl.Instruction

/**
 * Customization of the default outline structure.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#outline
 */
class AsmOutlineTreeProvider extends DefaultOutlineTreeProvider
{
	def protected _createChildren(IOutlineNode parentNode, Asm rootElement)
	{
		for (Instruction instruction : rootElement.code)
		{
			if (instruction.name != null && instruction.name.length > 0)
			{
				createEObjectNode(parentNode, instruction, null, instruction.name, true)
			}
		}
	}
}
