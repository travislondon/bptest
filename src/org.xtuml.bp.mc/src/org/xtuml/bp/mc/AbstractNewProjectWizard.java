package org.xtuml.bp.mc;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbench;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.XtUMLNature;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.ui.DelegatingWizard;
import org.xtuml.bp.core.ui.NewSystemWizard;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.WizardDelegate;
import org.xtuml.bp.core.ui.WizardDelegateChooserPage;

public abstract class AbstractNewProjectWizard extends DelegatingWizard {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.projtech.bp.core.ui.DelegatingWizard#getExtensionPoint()
	 */
	@Override
	public String getExtensionPoint() {
		return "org.xtuml.bp.core.code-builders"; //NON-NLS-1
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		String[] mcis = null;
		String builderName = "";
		WizardDelegate gdw = getDelegatingWizard();
		if (gdw != null) {
			mcis = gdw.getChoices();
			if (mcis.length > 1) {
				addPage(new WizardDelegateChooserPage(
						"newxtUMLCodeBuilderChooser",
						"Multiple Code Builders found",
						"Select the code builder to use with this xtUML project",
						"Available code builders:"));
			} else if (mcis.length == 1) {
				setDelegate(builderName);
			}
		}
	}

	@Override
	public boolean performFinish(IProject newProject) {
		
		boolean result = super.performFinish(newProject);
		
		final String projectName = newProject.getName();
		
		// The call to add the xtUML nature was added to support the Model Compiler
        // "Switcher" utility.  In the New Project Wizard this does nothing 
        // (because the nature has already been added earlier in the NPW flow). If
		// we are switching MCs on a project that already has the nature, this does 
		// nothing.
        try {
            if (!newProject.hasNature(XtUMLNature.getNatureId())) {
                XtUMLNature.addNature(newProject);
                NewSystemWizard.createSystemModel(newProject, newProject.getName());
            }
        } catch (CoreException ce) {
            CorePlugin.logError("Unable to add xtUML nature", ce);
        }
		
		ClassQueryInterface_c query = new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((SystemModel_c)candidate).getName().equals(projectName);
			}
		};
		
		// get the associated instance
		SystemModel_c newSys =  SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), query);
		Selection.getInstance().setSelection(
				new StructuredSelection(newSys));
		
		return result;
	}
}
