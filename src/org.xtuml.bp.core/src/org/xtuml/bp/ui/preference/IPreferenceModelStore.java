//========================================================================
//
// File:      $RCSfile: IPreferenceModelStore.java,v $
// Version:   $Revision: 1.7 $
// Modified:  $Date: 2012/01/23 21:28:08 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================

package org.xtuml.bp.ui.preference;

import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @author babar_ali
 */
public interface IPreferenceModelStore {
    public Class getModelClass(); 
    public void saveModel(IPreferenceStore store, IPreferenceModel model);
    public IPreferenceModel loadModel(IPreferenceStore store, BasePlugin plugin, IPreferenceModel model);
    public void restoreModelDefaults(IPreferenceModel model);
}
