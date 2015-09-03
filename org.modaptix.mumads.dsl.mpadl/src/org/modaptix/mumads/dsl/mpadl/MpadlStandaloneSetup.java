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

import org.eclipse.emf.ecore.EPackage;

import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages without equinox extension
 * registry
 */
public class MpadlStandaloneSetup extends MpadlStandaloneSetupGenerated
{

	public static void doSetup()
	{
		new MpadlStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	@Override
	public void register(Injector injector)
	{
		if (!EPackage.Registry.INSTANCE.containsKey("http://www.modaptix.org/xtext/expressions/ArithmeticExpression"))
		{
			EPackage.Registry.INSTANCE.put("http://www.modaptix.org/xtext/expressions/ArithmeticExpression",
					org.modaptix.xtext.expressions.arithmeticExpression.ArithmeticExpressionPackage.eINSTANCE);
		}
		
		if (!EPackage.Registry.INSTANCE.containsKey("http://www.modaptix.org/mumads/dsl/mpadl/Mpadl"))
		{
			EPackage.Registry.INSTANCE.put("http://www.modaptix.org/mumads/dsl/mpadl/Mpadl",
					org.modaptix.mumads.dsl.mpadl.mpadl.MpadlPackage.eINSTANCE);
		}
		
		super.register(injector);
	}
}
