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

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class MpadlRuntimeModule extends org.modaptix.mumads.dsl.mpadl.AbstractMpadlRuntimeModule
{
	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService()
	{
		return MpadlTerminalConverters.class;
	}
	
	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider()
	{
		return MpadlQualifiedNameProvider.class;
	}
}
