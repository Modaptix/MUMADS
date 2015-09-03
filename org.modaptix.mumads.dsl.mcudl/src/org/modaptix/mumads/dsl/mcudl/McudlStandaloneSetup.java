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

package org.modaptix.mumads.dsl.mcudl;

/**
 * Initialization support for running Xtext languages without equinox extension
 * registry
 */
public class McudlStandaloneSetup extends McudlStandaloneSetupGenerated
{

	public static void doSetup()
	{
		new McudlStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}
