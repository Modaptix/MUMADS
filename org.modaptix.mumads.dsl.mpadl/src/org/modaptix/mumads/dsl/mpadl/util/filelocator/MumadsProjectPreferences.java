package org.modaptix.mumads.dsl.mpadl.util.filelocator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.emf.common.util.URI;
import org.modaptix.mumads.dsl.mpadl.util.interfaces.IMumadsProjectPreferences;

import com.google.inject.Singleton;

@Singleton
public class MumadsProjectPreferences implements IMumadsProjectPreferences
{
	public static final String PLUGIN_ID = "org.modaptix.mumads";
	public static final String PROPERTY_SOURCE_TYPE = PLUGIN_ID + ".arch.default.source.type";
	public static final String PROPERTY_SOURCE_PLOC = PLUGIN_ID + ".arch.default.source.pop";
	public static final String PROPERTY_SOURCE_PATH = PLUGIN_ID + ".arch.default.source.path";
	
	// Map keyed on project name containing the path to the Mpadl object representing
	// the selected default architecture. 
	//protected Map<String, URI> projectDefaultMpadl;

	public MumadsProjectPreferences()
	{
		//projectDefaultMpadl = Collections.synchronizedMap(new HashMap<String, URI>());
	}
	
	public URI getDefaultMpadlPath(final String projectName)
	{
		// Try to find the path to the default mpadl file in the map.
		//String mpadlPath = projectDefaultMpadl.get(projectName);
		//if (mpadlPath != null)
		//	return null;
		
		// We didn't find it in the map so we need to get it from the project preferences.
		// Get the properties from the project.
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		IEclipsePreferences node = new ProjectScope(project).getNode(PLUGIN_ID);
	    String sourceType = node.get(PROPERTY_SOURCE_TYPE, null);
	    String sourcePloc = node.get(PROPERTY_SOURCE_PLOC, null);
	    String sourcePath = node.get(PROPERTY_SOURCE_PATH, null);

	    // Construct a new resource URI
	    if ("Project".equals(sourceType))
	    	return URI.createURI("platform:/resource/" + sourcePloc + "/" + sourcePath);
	    else if ("Plug-in".equals(sourceType))
	    	return URI.createURI("platform:/plugin/" + sourcePloc + "/" + sourcePath);

	    // We still didn't find it!
		return null;
	}
}
