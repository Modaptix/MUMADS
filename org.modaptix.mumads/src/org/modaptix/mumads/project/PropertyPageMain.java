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

package org.modaptix.mumads.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

public class PropertyPageMain extends PropertyPage implements IWorkbenchPropertyPage
{
	@Override
	protected Control createContents(Composite parent)
	{
		// There will be no default, therefore remove default button
	    noDefaultButton();
	    
	    Label label = new Label(parent, SWT.NONE);
	    label.setText("MUMADS properties");
	    
	    return label;
	}

}
