package org.modaptix.mumads.project;

import java.net.URI;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.xtext.ui.XtextProjectHelper;

public class Support
{
	public static IProject createProject(String projectName, URI location)
	{
		Assert.isNotNull(projectName);
		Assert.isTrue(projectName.trim().length() > 0);

		IProject project = createBaseProject(projectName, location);
		try
		{
			addNature(project);

			String[] paths = { "src", "obj", "bin" };
			addToProjectStructure(project, paths);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			project = null;
		}
		
		return project;
	}

	private static IProject createBaseProject(String projectName, URI location)
	{
		IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		if (newProject.exists())
			return newProject;

		URI projectLocation = location;
		IProjectDescription desc = newProject.getWorkspace().newProjectDescription(newProject.getName());
		if (location != null && ResourcesPlugin.getWorkspace().getRoot().getLocationURI().equals(location))
		{
			projectLocation = null;
		}
		desc.setLocationURI(projectLocation);

		try
		{
			newProject.create(desc, null);
			if (!newProject.isOpen())
			{
				newProject.open(null);
			}
		}
		catch (CoreException e)
		{
			e.printStackTrace();
		}

		return newProject;
	}

	private static void createFolder(IFolder folder) throws CoreException
	{
		IContainer parent = folder.getParent();
		if (parent instanceof IFolder)
		{
			createFolder((IFolder) parent);
		}
		if (!folder.exists())
		{
			folder.create(false, true, null);
		}
	}

	private static void addToProjectStructure(IProject newProject, String[] paths) throws CoreException
	{
		for (String path : paths)
		{
			IFolder folder = newProject.getFolder(path);
			createFolder(folder);
		}
	}

	public static IStatus addNature(IProject project) throws ExecutionException
	{
		try
		{
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			String[] newNatures = new String[natures.length + 2];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);

			newNatures[natures.length] = Nature.NATURE_ID;
			newNatures[natures.length+1] = XtextProjectHelper.NATURE_ID;

			// validate the natures
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IStatus status = workspace.validateNatureSet(newNatures);

			// only apply new nature, if the status is ok
			if (status.getCode() == IStatus.OK)
			{
				description.setNatureIds(newNatures);
				project.setDescription(description, null);
			}

			return status;
		}
		catch (CoreException e)
		{
			throw new ExecutionException(e.getMessage(), e);
		}
	}

	public static IStatus removeNature(IProject project) throws ExecutionException
	{
		try
		{
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			String[] newNatures = ArrayUtils.removeElement(natures, Nature.NATURE_ID);
	
			// validate the natures
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IStatus status = workspace.validateNatureSet(newNatures);
	
			// only apply new nature, if the status is ok
			if (status.getCode() == IStatus.OK)
			{
				description.setNatureIds(newNatures);
				project.setDescription(description, null);
			}
	
			return status;
		}
		catch (CoreException e)
		{
			throw new ExecutionException(e.getMessage(), e);
		}
	}
	
	
}
