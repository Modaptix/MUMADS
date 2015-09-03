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

package org.modaptix.mumads.dsl.mpadl;

import java.util.List;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.modaptix.mumads.dsl.mpadl.mpadl.CpuFeature;
import org.modaptix.mumads.dsl.mpadl.mpadl.Instruction;
import org.modaptix.mumads.dsl.mpadl.mpadl.MpadlPackage;
import org.modaptix.mumads.dsl.mpadl.mpadl.OperandType;
import org.modaptix.mumads.dsl.mpadl.mpadl.RegisterIndexed;

public class MpadlQualifiedNameProvider extends	DefaultDeclarativeQualifiedNameProvider
{
	protected QualifiedName qualifiedName(Instruction instruction)
	{
		return QualifiedName.create(instruction.getName());
	}
	
	protected QualifiedName qualifiedName(CpuFeature feature)
	{
		return QualifiedName.create(feature.getName());
	}

	protected QualifiedName qualifiedName(OperandType operandType)
	{
		return QualifiedName.create(operandType.getName());
	}

	protected QualifiedName qualifiedName(RegisterIndexed register)
	{
		// We can't just use register.getIndex().getName() here as that will cause a
		// name resolution loop so we have to resolve it from the node model.
		List<INode> nodeList = NodeModelUtils.findNodesForFeature(register, MpadlPackage.Literals.REGISTER_INDEXED__INDEX);
		String index = "";
		if (!nodeList.isEmpty())
			index = nodeList.get(0).getText();
		return QualifiedName.create(register.getName()+"["+index+"]");
	}
}
