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

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.modaptix.mumads.dsl.mpadl.ui.MpadlHighlightingCalculator;
import org.modaptix.mumads.dsl.mpadl.ui.MpadlHighlightingConfiguration;

/**
 * Use this class to register components to be used within the IDE.
 */
public class MpadlUiModule extends org.modaptix.mumads.dsl.mpadl.ui.AbstractMpadlUiModule
{
	public MpadlUiModule(AbstractUIPlugin plugin)
	{
		super(plugin);
	}

	public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration()
	{
		return MpadlHighlightingConfiguration.class;
	}

	public Class<? extends AbstractAntlrTokenToAttributeIdMapper> bindAbstractAntlrTokenToAttributeIdMapper()
	{
		return MpadlAntlrTokenToAttributeIdMapper.class ;
	}

	public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator()
	{
		return MpadlHighlightingCalculator.class;
	}
}
