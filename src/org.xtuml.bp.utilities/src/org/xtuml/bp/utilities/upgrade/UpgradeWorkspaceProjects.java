package org.xtuml.bp.utilities.upgrade;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.XtUMLNature;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.io.mdl.upgrade.UpgradeUtil;

public class UpgradeWorkspaceProjects implements IWorkbenchWindowActionDelegate {

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public void init(IWorkbenchWindow window) {
		// nothing to do
	}

	@Override
	public void run(IAction action) {
		// locate all projects that require upgrade
		List<IProject> toUpgrade = new ArrayList<IProject>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		for (IProject project : projects) {
			try {
				if(project.exists() && project.isOpen()) {
					if (XtUMLNature.hasNature(project)) {
						PersistableModelComponent rootComponent = PersistenceManager
								.getRootComponent(project);
						if(rootComponent == null) {
							PersistenceManager.getDefaultInstance().traverseProject(project);
						}
						rootComponent = PersistenceManager.getRootComponent(project);
						if (PersistenceManager
								.requiresUpgradeBeforeUse(rootComponent)) {
							toUpgrade.add(project);
						}
					}
				}
			} catch (CoreException e) {
				CorePlugin.logError(
						"Unable to determine if project has xtUML nature.", e);
			}
		}
		if(toUpgrade.isEmpty()) {
			UIUtil
					.openInformation(PlatformUI.getWorkbench().getDisplay()
							.getActiveShell(), "Model Upgrade",
							"There are no projects in the workspace that require an upgrade.");
			return;
		}
		UpgradeUtil.addProjectsForPEUpgrade(toUpgrade
				.toArray(new IProject[toUpgrade.size()]), true);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// nothing to do
	}

}
