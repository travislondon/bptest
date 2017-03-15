//========================================================================
//
//File:      $RCSfile: RootAdapter.java,v $
//Version:   $Revision: 1.16 $
//Modified:  $Date: 2013/01/10 23:14:08 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
package org.xtuml.bp.ui.session.adapters;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;

public class RootAdapter implements ITreeContentProvider {
    static RootAdapter root = null;
    /**
     * Returns the adapters singleton instance. If this
     * is the first time, the instance is created.
     */
    public static RootAdapter getInstance() {
        if (root == null) {
            root = new RootAdapter();
        }
        return root;
    }
    /**
     * @see IContentProvider#inputChanged(Viewer, Object, Object)
     * Called when the tree's input has been changed
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // Nothing to do
    }
    /**
     * @see IContentProvider#dispose()
     * Called when this viewer is no longer needed
     */
    public void dispose() {
        // Nothing to dispose
    }
    /**
     * @see ITreeContentProvider#getParent(Object)
     * Returns the parent of this node
     */
    public Object getParent(Object arg) {
        return null;
    }
    /**
     * @see IStructuredContentProvider#getElements(Object)
     * Returns the elements below this node
     */
    public Object[] getElements(Object arg) {
        return getChildren(arg);
    }
    /**
     * @see ITreeContentProvider#getChildren(Object)
     * Returns the children of this node
     */
    public Object[] getChildren(Object arg) {
          ArrayList<SystemModel_c> result = new ArrayList<SystemModel_c>();
          SystemModel_c[] systems = SystemModel_c.SystemModelInstances(Ooaofooa
                .getDefaultInstance());
          for (int i =0; i < systems.length; i++) {
            Package_c [] packages = Package_c.getManyEP_PKGsOnR1401(systems[i]);
            for (int j=0; j < packages.length; j++) {
            	if (packages[j].Isexecutingorischildexecuting()) {
            		if(!result.contains(systems[i]))
            			result.add(systems[i]);
            		break;
            	}
            }
          }
          return result.toArray();
    }
    /**
     * @see ITreeContentProvider#hasChildren(Object)
     * Returns true if this node has any children
     */
    public boolean hasChildren(Object arg) {
        return getChildren(arg).length > 0;
    }
}