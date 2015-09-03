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

package org.modaptix.mumads.dsl.asm;

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.modaptix.mumads.dsl.asm.scoping.AsmImportURIGlobalScopeProvider;
import org.modaptix.mumads.dsl.mpadl.MpadlTerminalConverters;
import org.modaptix.mumads.dsl.mpadl.util.filelocator.MumadsProjectPreferences;
import org.modaptix.mumads.dsl.mpadl.util.interfaces.IMumadsProjectPreferences;

/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
public class AsmRuntimeModule extends org.modaptix.mumads.dsl.asm.AbstractAsmRuntimeModule
{
	//public Class<? extends IMpadlLocator> bindIMpadlLocator() {
	//	return MpadlLocator.class;
	//}
	
	public Class<? extends IMumadsProjectPreferences> bindIMumadsProjectPreferences()
	{
		return MumadsProjectPreferences.class;
	}
	
	@Override
	public Class<? extends IGlobalScopeProvider> bindIGlobalScopeProvider()
	{
	    return AsmImportURIGlobalScopeProvider.class;
	}
	
	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService()
	{
		return MpadlTerminalConverters.class;
	}
}
