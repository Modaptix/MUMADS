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
		
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor)
	{
		super.configure(acceptor);
		
		acceptor.acceptDefaultHighlighting(REGISTER_ID, "Register name", registerNameTextStyle());
		acceptor.acceptDefaultHighlighting(LABEL_ID, "Label", labelModeTextStyle());
	}

	protected TextStyle registerNameTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 130, 175));
		return textStyle;
	}

	protected TextStyle labelModeTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 175, 130));
		return textStyle;
	}
	
	public TextStyle commentTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(125, 125, 125));
		return textStyle;
	}
}
