//========================================================================
//
//File:      $RCSfile: SetCustomFillColorAction.java,v $
//Version:   $Revision: 1.4 $
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

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.Elementstyle_c;
import org.xtuml.bp.ui.canvas.Fillcolorstyle_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Shape_c;

public class SetCustomFillColorAction implements IActionDelegate {

	private ISelection selection;
	
	@Override
	public void run(IAction action) {
		if(selection instanceof IStructuredSelection) {
			// first have the user choose the color
			ColorDialog colorChooser = new ColorDialog(PlatformUI
					.getWorkbench().getDisplay().getActiveShell());
			RGB color = colorChooser.open();
			
			if(color == null) {
				// user canceled
				return;
			}
			IStructuredSelection ss = (IStructuredSelection) selection;
			// UI guarantees selection type
			// do the following in a transaction for undo/redo support
			TransactionManager manager = TransactionManager.getSingleton();
			Transaction transaction = null;
			try {
				transaction = manager.startTransaction("Set Fill Color", new ModelRoot[] {Ooaofooa.getDefaultInstance(), Ooaofgraphics.getDefaultInstance()});
				for(Iterator<?> iterator = ss.iterator(); iterator.hasNext();) {
					GraphicalEditPart part = (GraphicalEditPart) iterator.next();
					// fill is supported for diagrams, shapes and connectors
					GraphicalElement_c element = null;
					if(part.getModel() instanceof Shape_c) {
						element = GraphicalElement_c.getOneGD_GEOnR2((Shape_c) part.getModel());
					}
					if(part.getModel() instanceof Connector_c) {
						element = GraphicalElement_c.getOneGD_GEOnR2((Connector_c) part.getModel());
					}
					Fillcolorstyle_c fcs = null;
					if(element != null) {
						fcs = Fillcolorstyle_c
								.getOneSTY_FCSOnR400(Elementstyle_c
										.getManySTY_SsOnR401(element));
					} else { 
						fcs = Fillcolorstyle_c
								.getOneSTY_FCSOnR400(Elementstyle_c
										.getManySTY_SsOnR402((Model_c) part
												.getModel()));
					}
					if(fcs == null) {
						// create fill style and associate with graphic
						fcs = new Fillcolorstyle_c(((NonRootModelElement) part
								.getModel()).getModelRoot());
						fcs.setRed(color.red);
						fcs.setBlue(color.blue);
						fcs.setGreen(color.green);
						Elementstyle_c style = new Elementstyle_c(
								((NonRootModelElement) part.getModel())
										.getModelRoot());
						style.relateAcrossR400To(fcs);
						if(part.getModel() instanceof Model_c) {
							style.relateAcrossR402To((Model_c) part.getModel());
						} else {
							style.relateAcrossR401To(element);
						}
					} else {
						fcs.setRed(color.red);
						fcs.setBlue(color.blue);
						fcs.setGreen(color.green);
					}
				}
				manager.endTransaction(transaction);
			} catch (Exception e) {
				if(transaction != null) {
					manager.cancelTransaction(transaction);
				}
				CorePlugin.logError("Unable to process fill color transaction.", e);
			}
		}
	}
	  
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
