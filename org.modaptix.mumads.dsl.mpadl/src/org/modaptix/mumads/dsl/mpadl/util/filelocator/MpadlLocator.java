package org.modaptix.mumads.dsl.mpadl.util.filelocator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.ui.resource.XtextResourceSetProvider;
import org.modaptix.mumads.dsl.mpadl.util.interfaces.IMpadlLocator;
import org.modaptix.mumads.dsl.mpadl.util.interfaces.IMpadlLocatorVisitor;
import org.modaptix.mumads.dsl.mpadl.util.interfaces.IMumadsProjectPreferences;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MpadlLocator implements IMpadlLocator
{
	@Inject
	protected IMumadsProjectPreferences projectPrefs;
	
	@Inject
	protected XtextResourceSetProvider resourceSetProvider;
	
	// Map keyed on arch name containing a map keyed on project name containing
	// a list of the path(s) to the .mpadl file(s) contained in the workspace.
	protected Map<String, Map<String, List<String>>> fileResources;
	
	// Map keyed on arch name containing a map keyed on plug-in name containing
	// a list of the path(s) to the .mpadl file(s) provided by plug-ins.
	protected Map<String, Map<String, List<String>>> pluginResources;
	
	public MpadlLocator()
	{
		System.out.println("MpadlLocator::MpadlLocator() @" + this);
		
		try
		{
			fileResources = Collections.synchronizedMap(new HashMap<String, Map<String, List<String>>>());
			pluginResources = Collections.synchronizedMap(new HashMap<String, Map<String, List<String>>>());
	
			// Register an IResourceChangeListener for POST_CHANGE events
			// so we can track new and deleted files.
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IResourceChangeListener listener = new MpadlLocatorWorkspaceListener(this);
			workspace.addResourceChangeListener(listener, IResourceChangeEvent.POST_CHANGE);
			
			// We only get notified of change events so we need to scan for
			// already existing files in the workspace.
			processContainer(workspace.getRoot());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	protected void processContainer(IContainer container)
	{
		IResource[] members;
		try
		{
			members = container.members();
			for (IResource member : members)
			{
				if (member instanceof IContainer)
					processContainer((IContainer) member);
				else if (member instanceof IFile)
					processFile((IFile) member);
			}
		}
		catch (CoreException e)
		{
			e.printStackTrace();
		}
	}
	
	protected void processFile(IFile member)
	{
		String fe = member.getFileExtension();
		
		if ((fe != null) && (fe.equals("mpadl")))
		{
			addResource(member);
		}
	}

	public void addResource(IResource res)
	{
		// Check that we got a resource and that it at least has the
		// correct file extension.
		if ((res == null) || (res.getFileExtension() == null) || 
				(!res.getFileExtension().equalsIgnoreCase("mpadl")))
			return;
		
		// Get the arch name associated with this resource
		// and lookup or create the arch level HashMap. 
		String archName = res.getName();
		archName = archName.substring(0, archName.length()-6);
		Map<String, List<String>> alm = fileResources.get(archName);
		if (alm == null)
		{
			alm = Collections.synchronizedMap(new HashMap<String, List<String>>());
			fileResources.put(archName,  alm);
		}
		
		// Get the name of the project associated with this resource
		// and lookup or create the project level Vector. 
		String projectName = res.getProject().getName();
		List<String> plv = alm.get(projectName);
		if (plv == null)
		{
			plv = Collections.synchronizedList(new Vector<String>());
			alm.put(projectName, plv);
		}

		// Finally we can add the path to this resource to that vector.
		plv.add(res.getProjectRelativePath().toOSString());		
	}

	public void removeResource(IResource res)
	{
		// Check that we got a resource and that it at least has the
		// correct file extension.
		if ((res == null) || (!res.getFileExtension().equals("mpadl")))
			return;

		// Get the arch name associated with this resource
		// and lookup the arch level HashMap. If we can't find
		// it then we don't have this resource in our list so
		// we're done.
		String archName = res.getName();
		archName = archName.substring(0, archName.length()-6);
		Map<String, List<String>> alm = fileResources.get(archName);
		if (alm == null)
			return;
		
		// Get the name of the project associated with this resource
		// and lookup the project level Vector. If we can't find
		// it then we don't have this resource in our list so
		// we're done.
		String projectName = res.getProject().getName();
		List<String> plv = alm.get(projectName);
		if (plv == null)
			return;
		
		plv.remove(res.getProjectRelativePath().toOSString());
	}
	
	public void iterateFileResourcesByProject(final String projectName, final IMpadlLocatorVisitor visitor)
	{
		// Iterate through all the available architectures
		for (Entry<String, Map<String, List<String>>> archEntry : fileResources.entrySet())
		{
			// Iterate through all the available projects
			for (Entry<String, List<String>> projectEntry : archEntry.getValue().entrySet())
			{
				// If the project entry name equals the supplied project name then
				// just add any files in the list.
				if (projectEntry.getKey().equals(projectName))
				{
					for (String filePath : projectEntry.getValue())
					{
						visitor.visit("Project", archEntry.getKey(), projectEntry.getKey(), filePath);
					}
				}
			}
		};
	}

	public URI getDefaultMpadlURIForResource(Resource resource)
	{
		String pURI = resource.getURI().toPlatformString(true);
		String proj = pURI.substring(1, pURI.indexOf("/", 1));
		
		// Get the path to the default mpadl file for this project.
		return projectPrefs.getDefaultMpadlPath(proj);
	}
}
