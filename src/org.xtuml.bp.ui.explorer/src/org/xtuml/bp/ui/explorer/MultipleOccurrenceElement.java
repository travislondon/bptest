//========================================================================
//
//File:      $RCSfile: MultipleOccurrenceElement.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:15:44 $
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
package org.xtuml.bp.ui.explorer;

import org.eclipse.core.runtime.IAdaptable;

import org.xtuml.bp.core.common.NonRootModelElement;

public class MultipleOccurrenceElement implements IAdaptable {

	private NonRootModelElement element;

	public MultipleOccurrenceElement(NonRootModelElement element) {
		this.element = element;
	}
	
	@Override
	public Object getAdapter(Class adapter) {
		if(adapter == NonRootModelElement.class) {
			return element;
		}
		return null;
	}

	public NonRootModelElement getElement() {
		return element;
	}
	
}
