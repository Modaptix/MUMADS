package org.modaptix.mumads.dsl.mpadl;

import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;
import org.modaptix.xtext.expressions.TerminalConverters;

public class MpadlTerminalConverters extends TerminalConverters
{
	@ValueConverter(rule = "STRING")
	public IValueConverter<String> STRING()
	{
		return new IValueConverter<String>()
		{
			
			@Override
			public String toString(String value) throws ValueConverterException
			{
				if (value == null)
					throw new ValueConverterException("", null, null);

				try
				{
					return "\"" + value + "\"";
				}
				catch (Exception e)
				{
					throw new ValueConverterException("", null, e);
				}
			}
			
			@Override
			public String toValue(String string, INode node) throws ValueConverterException
			{
				if (string == null)
					throw new ValueConverterException("Unable to convert null to string", node, null);
				if (string.length() < 2)
					throw new ValueConverterException("Unable to convert too short quoted string to string", node, null);

				try
				{
					return string.substring(1, string.length()-1);
				}
				catch (Exception e)
				{
					throw new ValueConverterException("Unable to convert string \"" + string + "\" to string", node, e);
				}
			}
		};
	}
}
