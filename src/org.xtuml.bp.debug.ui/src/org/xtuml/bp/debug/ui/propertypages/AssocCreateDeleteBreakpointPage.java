//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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
//
package org.xtuml.bp.debug.ui.propertypages;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import org.xtuml.bp.debug.ui.model.BPAssocCreateDeleteBreakpoint;

/**
 * Property page for editing breakpoints of type
 * <code>org.eclipse.jdt.debug.core.IJavaLineBreakpoint</code>.
 */
public class AssocCreateDeleteBreakpointPage extends BPBreakpointPage {

	protected Button fCreateButton;
	protected Button fDeleteButton;
	
	/**
	 * @see org.xtuml.bp.debug.ui.propertypages.BPBreakpointPage#doStore()
	 */
	protected void doStore() throws CoreException {
		BPAssocCreateDeleteBreakpoint breakpoint= (BPAssocCreateDeleteBreakpoint) getBreakpoint();
		super.doStore();
		boolean access= fCreateButton.getSelection();
		breakpoint.setCreate(access);
		boolean modification = fDeleteButton.getSelection();
		breakpoint.setDelete(modification);
	}

	/**
	 * @see org.xtuml.bp.debug.ui.propertypages.BPBreakpointPage#createTypeSpecificLabels(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificLabels(Composite parent) {
		// nothing
	}
	
	/**
	 * Create the condition editor and associated editors.
	 * @see org.xtuml.bp.debug.ui.propertypages.BPBreakpointPage#createTypeSpecificEditors(org.eclipse.swt.widgets.Composite)
	 */
	protected void createTypeSpecificEditors(Composite parent) throws CoreException {
		BPAssocCreateDeleteBreakpoint breakpoint= (BPAssocCreateDeleteBreakpoint) getBreakpoint();
		fCreateButton = createCheckButton(parent, "Creation");
		fCreateButton.setSelection(breakpoint.isCreate());
		fDeleteButton = createCheckButton(parent, "Deletion");
		fDeleteButton.setSelection(breakpoint.isDelete());
		
		if (breakpoint.supportsCondition()) {
			createConditionEditor(parent);
		}
	}
	
}
