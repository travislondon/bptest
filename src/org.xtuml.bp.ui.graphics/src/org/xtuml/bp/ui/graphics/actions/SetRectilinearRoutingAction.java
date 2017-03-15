//========================================================================
//
//File:      $RCSfile: SetRectilinearRoutingAction.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:57 $
//
//Copyright (c) 2005-2014 Mentor Graphics Corporation.  All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//======================================================================== 
package org.xtuml.bp.ui.graphics.actions;

import java.util.Iterator;

import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import org.xtuml.bp.ui.graphics.figures.DecoratedPolylineConnection;
import org.xtuml.bp.ui.graphics.parts.ConnectorEditPart;
import org.xtuml.bp.ui.graphics.parts.DiagramEditPart;
import org.xtuml.bp.ui.graphics.policies.GraphicsConnectionLineSegPolicy;

public class SetRectilinearRoutingAction implements IActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			for (Iterator<?> iterator = ss.iterator(); iterator.hasNext();) {
				ConnectorEditPart selected = (ConnectorEditPart) iterator
						.next();
				DiagramEditPart diagramPart = (DiagramEditPart) selected
						.getViewer().getContents();
				((DecoratedPolylineConnection) selected.getFigure())
						.internalSetConnectionRouter(diagramPart
								.getRectilinearConnectionRouter());
				selected
						.removeEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE);
				selected.installEditPolicy(
						EditPolicy.CONNECTION_BENDPOINTS_ROLE,
						new GraphicsConnectionLineSegPolicy());
				selected.removeEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
				selected.storeRouterSetting();
				selected.refresh();
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
