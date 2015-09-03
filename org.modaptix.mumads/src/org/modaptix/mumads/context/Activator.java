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

package org.modaptix.mumads.context;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.modaptix.mumads.dsl.asm.ui.internal.AsmActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;

public class Activator extends AbstractUIPlugin
{
	private static Activator plugin;

	private Injector injector;

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault()
	{
		return plugin;
	}

	@Override
	public void start(BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;
		injector = AsmActivator.getInstance().getInjector(AsmActivator.ORG_MODAPTIX_MUMADS_DSL_ASM_ASM).createChildInjector();
	}

	public Injector getInjector()
	{
		return injector;
	}
}
