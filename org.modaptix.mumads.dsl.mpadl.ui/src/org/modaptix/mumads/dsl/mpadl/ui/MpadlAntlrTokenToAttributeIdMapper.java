/*******************************************************************************
 * Copyright (c) 2015 Modaptix Limited.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Max Hacking - initial implementation
 *     
 *******************************************************************************/

package org.modaptix.mumads.dsl.mpadl.ui;

import org.modaptix.xtext.expressions.ui.ArithmeticExpressionAntlrTokenToAttributeIdMapper;

public class MpadlAntlrTokenToAttributeIdMapper extends ArithmeticExpressionAntlrTokenToAttributeIdMapper
{
	@Override
	protected String calculateId(String tokenName, int tokenType)
	{
		String temp = super.calculateId(tokenName, tokenType);
		if (temp != MpadlHighlightingConfiguration.DEFAULT_ID)
			return temp;
		
		if (tokenName.equals("RULE_SL_COMMENT"))
		{
			return MpadlHighlightingConfiguration.COMMENT_ID;
		}

		if (tokenName.equals("RULE_STRING"))
		{
			return MpadlHighlightingConfiguration.STRING_ID;
		}

		return MpadlHighlightingConfiguration.DEFAULT_ID;
	}
}
