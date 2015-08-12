package org.modaptix.mumads.dsl.asm.scoping;

import java.util.LinkedHashSet;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.scoping.impl.ImportUriGlobalScopeProvider;
import org.modaptix.mumads.dsl.mpadl.util.filelocator.MpadlLocator;

import com.google.inject.Inject;

public class AsmImportURIGlobalScopeProvider extends ImportUriGlobalScopeProvider
{
	@Inject	protected MpadlLocator ml;
	
	@Override
	protected LinkedHashSet<URI> getImportedUris(Resource resource)
	{
		LinkedHashSet<URI> importedURIs = super.getImportedUris(resource);
		importedURIs.add(ml.getDefaultMpadlURIForResource(resource));
		return importedURIs;
	}
}
