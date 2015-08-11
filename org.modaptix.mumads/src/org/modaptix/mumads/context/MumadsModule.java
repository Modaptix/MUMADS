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
