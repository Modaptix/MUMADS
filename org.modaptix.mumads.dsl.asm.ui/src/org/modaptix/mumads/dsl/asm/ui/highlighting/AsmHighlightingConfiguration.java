package org.modaptix.mumads.dsl.asm.ui.highlighting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.*;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.modaptix.xtext.expressions.ui.ArithmeticExpressionHighlightingConfiguration;

public class AsmHighlightingConfiguration extends ArithmeticExpressionHighlightingConfiguration
{
	// Provide ID strings for the highlighting calculator
	public static final String REGISTER_ID = "register";
	public static final String LABEL_ID = "label";
	public static final String MACRO_NAME_ID = "macro_name";
	public static final String MACRO_CALL_ID = "macro_call";
	public static final String EQUATE_ID = "equate";
	public static final String DS_ID = "ds";
		
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor)
	{
		super.configure(acceptor);
		
		acceptor.acceptDefaultHighlighting(LABEL_ID, "Instruction label", labelModeTextStyle());
		acceptor.acceptDefaultHighlighting(REGISTER_ID, "Register name", registerNameTextStyle());
		acceptor.acceptDefaultHighlighting(MACRO_NAME_ID, "Macro (declaration) name", macroNameTextStyle());
		acceptor.acceptDefaultHighlighting(MACRO_CALL_ID, "Macro (instantiation) name", macroCallTextStyle());
		acceptor.acceptDefaultHighlighting(EQUATE_ID, "Equate name", equateTextStyle());
		acceptor.acceptDefaultHighlighting(DS_ID, "Data Storage name", dsTextStyle());
	}

	@Override
	public TextStyle commentTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(125, 125, 125));
		return textStyle;
	}

	protected TextStyle labelModeTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 175, 130));
		return textStyle;
	}
	
	protected TextStyle registerNameTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 115, 175));
		return textStyle;
	}

	protected TextStyle macroNameTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(130, 0, 175));
		return textStyle;
	}

	protected TextStyle macroCallTextStyle()
	{
		TextStyle textStyle = keywordTextStyle().copy();
		textStyle.setStyle(textStyle.getStyle() | SWT.ITALIC);
		return textStyle;
	}

	protected TextStyle equateTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(255, 165, 0));
		return textStyle;
	}

	protected TextStyle dsTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 128, 0));
		return textStyle;
	}
}
