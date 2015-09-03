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

import org.modaptix.mumads.dsl.mpadl.util.filelocator.MumadsProjectPreferences;
import org.modaptix.mumads.dsl.mpadl.util.interfaces.IMumadsProjectPreferences;

import com.google.inject.AbstractModule;

public class MumadsModule extends AbstractModule
{
	
	@Override
	protected void configure()
	{
	}

	public Class<? extends IMumadsProjectPreferences> bindIMumadsProjectPreferences()
	{
		return MumadsProjectPreferences.class;
	}
	
	//public Class<? extends IMpadlLocator> bindIMpadlLocator() {
	//	return MpadlLocator.class;
	//}
}
