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
		
		URI mpadlURI = ml.getDefaultMpadlURIForResource(resource);
		if (mpadlURI != null)
			importedURIs.add(mpadlURI);
		
		return importedURIs;
	}
}
