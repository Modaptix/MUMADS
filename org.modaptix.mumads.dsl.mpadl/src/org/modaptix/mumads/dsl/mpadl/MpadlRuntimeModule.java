/*
 * generated by Xtext
 */
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
