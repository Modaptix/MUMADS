package org.modaptix.mumads.project;

import java.net.URI;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class NewProjectWizard extends Wizard implements INewWizard
{
	private static final String WIZARD_NAME = "New MUMADS project";
	
	private WizardNewProjectCreationPage _pageOne;
	
	public NewProjectWizard()
	{
		setWindowTitle(WIZARD_NAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void addPages()
	{
		super.addPages();
		
		_pageOne = new WizardNewProjectCreationPage(WIZARD_NAME);
	    _pageOne.setTitle(WIZARD_NAME);
	    _pageOne.setDescription("Create a new MUMADS project");
	 
	    addPage(_pageOne);
	}
	
	@Override
	public boolean performFinish()
	{
		String name = _pageOne.getProjectName();
	    URI location = null;
	    if (!_pageOne.useDefaults())
	    {
	        location = _pageOne.getLocationURI();
	    }
	 
    	return (Support.createProject(name, location) != null);
	}

}
