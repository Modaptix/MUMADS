package org.modaptix.mumads.dsl.mpadl.util.filelocator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

public class MpadlLocatorWorkspaceListener implements IResourceChangeListener, IResourceDeltaVisitor
{
	protected MpadlLocator mpadlLocator;
	
	protected MpadlLocatorWorkspaceListener(final MpadlLocator mpadlLocator)
	{
		this.mpadlLocator = mpadlLocator;
	}
	
	@Override
	public void resourceChanged(IResourceChangeEvent event)
	{
		try
		{
			if (event.getDelta() != null)
				event.getDelta().accept(this);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean visit(IResourceDelta delta) throws CoreException
	{
		// Check that the resource has the correct file extension.
		IResource res = delta.getResource();
		if ((res == null) || (res.getFileExtension() == null) || 
				(!res.getFileExtension().equalsIgnoreCase("mpadl")))
			return true;

		switch (delta.getKind()) {
            case IResourceDelta.ADDED:
            	mpadlLocator.addFileResource(res);
            	return false;
            case IResourceDelta.REMOVED:
            	mpadlLocator.removeResource(res);
            	return false;
		}
		return true;
	}

}
