/*
 * generated by Xtext
 */
package org.modaptix.mumads.dsl.asm.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.modaptix.mumads.dsl.asm.ui.highlighting.AsmAntlrTokenToAttributeIdMapper;
import org.modaptix.mumads.dsl.asm.ui.highlighting.AsmHighlightingCalculator;
import org.modaptix.mumads.dsl.asm.ui.highlighting.AsmHighlightingConfiguration;

/**
 * Use this class to register components to be used within the IDE.
 */
public class AsmUiModule extends org.modaptix.mumads.dsl.asm.ui.AbstractAsmUiModule
{
	public AsmUiModule(AbstractUIPlugin plugin)
	{
		super(plugin);
	}
	
	public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration()
	{
		return AsmHighlightingConfiguration.class;
	}

	public Class<? extends AbstractAntlrTokenToAttributeIdMapper> bindAbstractAntlrTokenToAttributeIdMapper()
	{
		return AsmAntlrTokenToAttributeIdMapper.class ;
	}

	public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator()
	{
		return AsmHighlightingCalculator.class;
	}
}
