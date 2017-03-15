package org.xtuml.bp.model.compare.actions;

import org.eclipse.jface.action.Action;

import org.xtuml.bp.model.compare.contentmergeviewer.SynchronizedTreeViewer;

public class ExpandAllAction extends Action {

	private SynchronizedTreeViewer viewer;

	public ExpandAllAction(SynchronizedTreeViewer viewer) {
		this.viewer = viewer;
	}
	
	@Override
	public void run() {
		viewer.expandAllViewers();
	}
}
