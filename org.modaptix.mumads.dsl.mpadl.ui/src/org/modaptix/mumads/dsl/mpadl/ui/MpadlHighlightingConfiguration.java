package org.modaptix.mumads.dsl.mpadl.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.*;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.modaptix.xtext.expressions.ui.ArithmeticExpressionHighlightingConfiguration;

public class MpadlHighlightingConfiguration extends ArithmeticExpressionHighlightingConfiguration
{
	// Provide ID strings for the highlighting calculator
	public static final String REGISTER_REAL_ID = "reg_real";
	public static final String REGISTER_MMAP_ID = "reg_mmap";
	public static final String REGISTER_INDX_ID = "reg_indx"; 
	public static final String REGISTER_FLAG_ID = "reg_flag";
	public static final String ADDRMODE_ID = "addrmode";
	public static final String INSTRUCTION_ID = "instruction";
		
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor)
	{
		super.configure(acceptor);
		
		acceptor.acceptDefaultHighlighting(REGISTER_REAL_ID, "Register name (real)", registerRealTextStyle());
		acceptor.acceptDefaultHighlighting(REGISTER_MMAP_ID, "Register name (memory mapped)", registerMmapTextStyle());
		acceptor.acceptDefaultHighlighting(REGISTER_INDX_ID, "Register name (index)", registerIndxTextStyle());
		acceptor.acceptDefaultHighlighting(REGISTER_FLAG_ID, "Register name (flag)", registerFlagTextStyle());
		acceptor.acceptDefaultHighlighting(ADDRMODE_ID, "Addressing mode", addressingModeTextStyle());
		acceptor.acceptDefaultHighlighting(INSTRUCTION_ID, "Instruction mnemonic", instructionMnemonicTextStyle());
	}

	protected TextStyle instructionMnemonicTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(100, 100, 0));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	protected TextStyle registerRealTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(100, 0, 0));
		return textStyle;
	}

	protected TextStyle registerMmapTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 100, 0));
		return textStyle;
	}

	protected TextStyle registerIndxTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 0, 100));
		return textStyle;
	}

	protected TextStyle registerFlagTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 100, 100));
		return textStyle;
	}

	protected TextStyle addressingModeTextStyle()
	{
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 175, 130));
		return textStyle;
	}
}
