package org.modaptix.mumads.dsl.mpadl;

import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractDeclarativeValueConverterService;
import org.eclipse.xtext.nodemodel.INode;

import com.google.inject.Inject;

public class MpadlTerminalConverters extends AbstractDeclarativeValueConverterService
{
	private Grammar grammar;
 	
	@Inject
	public void setGrammar(IGrammarAccess grammarAccess)
	{
		this.grammar = grammarAccess.getGrammar();
	}

	protected Grammar getGrammar()
	{
		return grammar;
	}

	private static final String[] VALID_PREFIXES = { "0x", "-0x", "0X", "-0x", "$", "-$" };

	private String removeValidPrefix(String string)
	{
		boolean negated = false;
		
		if (string.startsWith("-"))
			negated = true;
		
		for (String prefix : VALID_PREFIXES)
		{
			if (string.startsWith(prefix))
			{
				return (negated ? "-" : "") + string.replaceFirst(prefix, "");
			}
		}
		return null;
	}
	
	@ValueConverter(rule = "HEX")
	public IValueConverter<Integer> HEX()
	{
		return new IValueConverter<Integer>()
		{
			
			@Override
			public String toString(Integer value) throws ValueConverterException
			{
				try
				{
					return Integer.toHexString(value);
				}
				catch (Exception e)
				{
					throw new ValueConverterException("Unable to convert Integer to hex", null, e);
				}
			}
			
			@Override
			public Integer toValue(String string, INode node) throws ValueConverterException
			{
				if (string == null)
					throw new ValueConverterException("Unable to convert null (hexadecimal) string to Integer", node, null);
				if (string.isEmpty())
					throw new ValueConverterException("Unable to convert empty (hexadecimal) string to Integer", node, null);
				
				String tmp = removeValidPrefix(string);
				
				if (tmp == null)
					throw new ValueConverterException("Unable to convert hexadecimal string \"" + string + "\" to Integer", node, null);
				
				try
				{
					return Integer.valueOf(tmp, 16);
				}
				catch (NumberFormatException e)
				{
					throw new ValueConverterException("Unable to convert hexadecimal string \"" + string + "\" to Integer", node, e);
				}
			}
		};
	}
}
