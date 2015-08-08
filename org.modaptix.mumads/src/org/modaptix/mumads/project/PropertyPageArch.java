package org.modaptix.mumads.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;
import org.modaptix.mumads.dsl.mpadl.util.filelocator.MpadlLocator;
import org.modaptix.mumads.dsl.mpadl.util.interfaces.IMpadlLocatorVisitor;

import com.google.inject.Inject;

public class PropertyPageArch extends PropertyPage implements IWorkbenchPropertyPage, IMpadlLocatorVisitor
{
	@Inject
    protected MpadlLocator ml;

	protected Table mpadlTable;
	
	@Override
	protected Control createContents(Composite parent)
	{
		System.out.println("PropertyPageArch::IMpadlLocator ml = " + ml);
		
		// There will be no default, therefore remove default button
	    noDefaultButton();
	    
	    mpadlTable = new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
	    mpadlTable.setLinesVisible (true);
		mpadlTable.setHeaderVisible (true);
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		mpadlTable.setLayoutData(data);
		
		String[] titles = {"Source", "Architecture", "Project / Plug-in", "Path"};
		for (int i=0; i<titles.length; i++)
		{
			TableColumn column = new TableColumn (mpadlTable, SWT.NONE);
			column.setText(titles [i]);
		}

		try
		{
			IProject project = (IProject) getElement().getAdapter(IProject.class);
			
			for (IProject referencedProject : project.getReferencedProjects())
			{
				ml.iterateFileResourcesByProject(referencedProject.getName(), this);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		for (int i=0; i<titles.length; i++)
		{
			mpadlTable.getColumn(i).pack();
		}	
	    
	    return mpadlTable;
	}

	public void visit(final String source, final String arch, final String project, final String path)
	{
		TableItem item = new TableItem (mpadlTable, SWT.NONE);
		item.setText(0, source);
		item.setText(1, arch);
		item.setText(2, project);
		item.setText(3, path);
	}
}
