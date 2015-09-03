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

package org.modaptix.mumads.dsl.mpadl.ui.labeling

import com.google.inject.Inject
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.modaptix.mumads.dsl.mpadl.mpadl.InstructionVariant
import org.modaptix.mumads.dsl.mpadl.mpadl.Operand
import org.modaptix.mumads.dsl.mpadl.mpadl.RegisterIndexable
import org.modaptix.mumads.dsl.mpadl.mpadl.OperandType
import org.modaptix.mumads.dsl.mpadl.mpadl.OperandTypeWithWidth
import org.modaptix.mumads.dsl.mpadl.mpadl.RegisterIndexed

/**
 * Provides labels for a EObjects.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#labelProvider
 */
class MpadlLabelProvider extends DefaultEObjectLabelProvider
{

	@Inject
	new(AdapterFactoryLabelProvider delegate)
	{
		super(delegate);
	}

	def text(RegisterIndexable register)
	{
		var String name = register.getName()

		name += " (" + register.width + " bits)";

		return name;
	}

	def text(RegisterIndexed register)
	{
		var String name = register.getName()

		if(register.getIndex() != null)
			name += "[" + register.getIndex().getName() + "]";

		name += " (" + register.width + " bits)";

		return name;
	}

	def text(OperandType addressingMode)
	{
		return addressingMode.longName;
	}

	def text(OperandTypeWithWidth addressingMode)
	{
		return addressingMode.longName + " (" + addressingMode.width + " bits)";
	}

	def text(InstructionVariant instructionVariant)
	{
		var String name = ""
		var boolean first = true
		for (Operand operand : instructionVariant.operands)
		{
			if(!first)
				name += " "
			name += operand.operandType.name
			first = false
		}
		return name
	}

// Labels and icons can be computed like this:
//	def text(Greeting ele) {
//		'A greeting to ' + ele.name
//	}
//
//	def image(Greeting ele) {
//		'Greeting.gif'
//	}
}
