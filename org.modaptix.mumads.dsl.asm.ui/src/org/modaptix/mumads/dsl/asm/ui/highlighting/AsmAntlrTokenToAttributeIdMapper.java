package org.modaptix.mumads.dsl.asm.ui.highlighting;

import org.modaptix.xtext.expressions.ui.ArithmeticExpressionAntlrTokenToAttributeIdMapper;

public class AsmAntlrTokenToAttributeIdMapper extends ArithmeticExpressionAntlrTokenToAttributeIdMapper
{
	@Override
	protected String calculateId(String tokenName, int tokenType)
	{
		String temp = super.calculateId(tokenName, tokenType);
		if (temp != AsmHighlightingConfiguration.DEFAULT_ID)
			return temp;
		
		if (tokenName.equals("RULE_SL_COMMENT"))
		{
			return AsmHighlightingConfiguration.COMMENT_ID;
		}

		return AsmHighlightingConfiguration.DEFAULT_ID;
	}
}
