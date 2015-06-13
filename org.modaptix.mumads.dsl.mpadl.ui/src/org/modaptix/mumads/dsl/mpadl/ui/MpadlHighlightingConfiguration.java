package org.modaptix.mumads.dsl.mpadl.ui;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.*;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class MpadlHighlightingConfiguration extends DefaultHighlightingConfiguration
{
	// Provide ID strings for the highlighting calculator
	public static final String BINNUM_ID = "binnum";
	public static final String DECNUM_ID = "decnum";
	public static final String HEXNUM_ID = "hexnum";
	public static final String REGISTER_ID = "register";
	public static final String ADDRMODE_ID = "addrmode";
		
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor)
	{
		acceptor.acceptDefaultHighlighting(DEFAULT_ID, "Default text", defaultTextStyle());
		acceptor.acceptDefaultHighlighting(COMMENT_ID, "Comments", commentTextStyle());
		acceptor.acceptDefaultHighlighting(INVALID_TOKEN_ID, "Invalid token", invalidTokenTextStyle());
		acceptor.acceptDefaultHighlighting(KEYWORD_ID, "Keyword", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(NUMBER_ID, "Number", numberTextStyle());
		acceptor.acceptDefaultHighlighting(PUNCTUATION_ID, "Punctuation", punctuationTextStyle());
		acceptor.acceptDefaultHighlighting(STRING_ID, "Text String", stringTextStyle());
		
		acceptor.acceptDefaultHighlighting(BINNUM_ID, "Binary number", binaryNumberTextStyle());
		acceptor.acceptDefaultHighlighting(DECNUM_ID, "Decimal number", decimalNumberTextStyle());
		acceptor.acceptDefaultHighlighting(HEXNUM_ID, "Hexadecimal number", hexadecimalNumberTextStyle());
		acceptor.acceptDefaultHighlighting(REGISTER_ID, "Register name", registerNameTextStyle());
		acceptor.acceptDefaultHighlighting(ADDRMODE_ID, "Addressing mode", addressingModeTextStyle());
	}

	protected TextStyle binaryNumberTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(250, 0, 0));
		return textStyle;
	}

	protected TextStyle decimalNumberTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 130, 0));
		return textStyle;
	}

	protected TextStyle hexadecimalNumberTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 0, 255));
		return textStyle;
	}

	protected TextStyle registerNameTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 130, 175));
		return textStyle;
	}

	protected TextStyle addressingModeTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 175, 130));
		return textStyle;
	}

	protected TextStyle invalidTokenTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setBackgroundColor(new RGB(255, 0, 0));
		return textStyle;
	}
}
