package org.modaptix.mumads.dsl.asm.ui;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.*;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.modaptix.xtext.expressions.ui.ArithmeticExpressionHighlightingConfiguration;

public class AsmHighlightingConfiguration extends ArithmeticExpressionHighlightingConfiguration
{
	// Provide ID strings for the highlighting calculator
	public static final String REGISTER_ID = "register";
	public static final String LABEL_ID = "label";
	public static final String MACRO_ID = "macro";
		
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor)
	{
		super.configure(acceptor);
		
		acceptor.acceptDefaultHighlighting(LABEL_ID, "Label", labelModeTextStyle());
		acceptor.acceptDefaultHighlighting(REGISTER_ID, "Register name", registerNameTextStyle());
		acceptor.acceptDefaultHighlighting(MACRO_ID, "Macro name", macroNameTextStyle());
//		acceptor.acceptDefaultHighlighting(_ID, "", TextStyle());
//		acceptor.acceptDefaultHighlighting(_ID, "", TextStyle());
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
		textStyle.setColor(new RGB(0, 130, 175));
		return textStyle;
	}

	protected TextStyle macroNameTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(130, 0, 175));
		return textStyle;
	}

}
