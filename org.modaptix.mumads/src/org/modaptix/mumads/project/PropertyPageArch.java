package org.modaptix.mumads.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
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
	public static final String PLUGIN_ID = "org.modaptix.mumads";
	public static final String PROPERTY_SOURCE_TYPE = PLUGIN_ID + ".arch.default.source.type";
	public static final String PROPERTY_SOURCE_PLOC = PLUGIN_ID + ".arch.default.source.pop";
	public static final String PROPERTY_SOURCE_PATH = PLUGIN_ID + ".arch.default.source.path";
	
	@Inject
    protected MpadlLocator ml;

	protected IEclipsePreferences node;
	protected Table mpadlTable;
	protected String sourceType, sourcePloc, sourcePath;

	
	@Override
	protected Control createContents(Composite parent)
	{
		
		System.out.println("PropertyPageArch::IMpadlLocator ml = " + ml);
		
		// There will be no default, therefore remove default button
	    noDefaultButton();
	    
	    mpadlTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION);
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
			// Get the properties from the project.
			IProject project = (IProject) getElement().getAdapter(IProject.class);
			node = new ProjectScope(project).getNode(PLUGIN_ID);
		    sourceType = node.get(PROPERTY_SOURCE_TYPE, null);
		    sourcePloc = node.get(PROPERTY_SOURCE_PLOC, null);
		    sourcePath = node.get(PROPERTY_SOURCE_PATH, null);
		    
		    System.out.println("Stored properties = " + sourceType + ", " + sourcePloc + ", " + sourcePath);
		    
		    // Add the list of the .mpadl files contained in any plugins.
		    ml.iterateFileResourcesFromPlugins(this);
		    
		    // Add the list of the .mpadl files contained in this project or any
		    // dependent projects to the table control.
		    ml.iterateFileResourcesByProject(project.getName(), this);
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

	public void visit(final String source, final String arch, final String ploc, final String path)
	{
		TableItem item = new TableItem (mpadlTable, SWT.NONE);
		item.setText(0, source);
		item.setText(1, arch);
		item.setText(2, ploc);
		item.setText(3, path);
		
		if (source.equals(sourceType) && ploc.equals(sourcePloc) && path.equals(sourcePath))
			mpadlTable.select(mpadlTable.getItemCount()-1);
	}
	
	@Override
	public boolean performOk()
	{
		System.out.println("PropertyPageArch::performOk()");
		
		try
		{
			TableItem[] selection = mpadlTable.getSelection();
			if (selection.length > 0)
			{
				node.put(PROPERTY_SOURCE_TYPE, selection[0].getText(0));
				node.put(PROPERTY_SOURCE_PLOC, selection[0].getText(2));
				node.put(PROPERTY_SOURCE_PATH, selection[0].getText(3));
				node.flush();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return super.performOk();
	}
}
