package org.modaptix.mumads.dsl.asm.scoping;

import java.util.LinkedHashSet;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.scoping.impl.ImportUriGlobalScopeProvider;

public class AsmImportURIGlobalScopeProvider extends ImportUriGlobalScopeProvider
{
	public static final URI MPADL_URI = URI.createURI("platform:/plugin/org.modaptix.mumads/test/model.mpadl");
	
	@Override
	protected LinkedHashSet<URI> getImportedUris(Resource resource)
	{
		LinkedHashSet<URI> importedURIs = super.getImportedUris(resource);
		//importedURIs.add(MPADL_URI);
		//System.out.println("AsmImportURIGlobalScopeProvider::getImportedUris()");
		return importedURIs;
	}
}
