package **PROJECT**;
//=====================================================================
//
// File:      $RCSfile$
// Version:   $Revision$
// Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp.  All rights reserved.
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
// This class is responsible for providing the help ids for the
// **PLUGIN_NAME** plugin.
//
import org.eclipse.ui.PlatformUI;

/**
 * Help context ids for the **VIEW_NAME**.
 * <p>
 * This interface contains constants only; it is not intended to be implemented
 * or extended.
 * </p>
 * 
 */
public interface I**HELPCONTEXTIDS_PREFIX**HelpContextIds {
  public static final String PREFIX = "**PROJECT**."; //$NON-NLS-1$

  // Actions
  public static final String FILTER_SELECTION_ACTION = PREFIX + "filter_selection_action_context"; //$NON-NLS-1$
  public static final String GOTO_RESOURCE_ACTION = PREFIX + "goto_resource_action_context"; //$NON-NLS-1$
  public static final String **PREFIX**_MOVE_ACTION = PREFIX + "**PREFIX**_move_action_context"; //$NON-NLS-1$
  public static final String **PREFIX**_RENAME_ACTION = PREFIX + "**PREFIX**_rename_action_context"; //$NON-NLS-1$
  public static final String SHOW_IN_EXPLORER_ACTION = PREFIX + "show_in_explorer_action_context"; //$NON-NLS-1$
  public static final String SORT_VIEW_ACTION = PREFIX + "sort_view_action_context"; //$NON-NLS-1$
  public static final String COPY_ACTION = PREFIX + "**PREFIX**_copy_action_context"; //$NON-NLS-1$
  public static final String PASTE_ACTION = PREFIX + "**PREFIX**_paste_action_context"; //$NON-NLS-1$
  public static final String COLLAPSE_ALL_ACTION = PREFIX + "collapse_all_action_context"; //$NON-NLS-1$

  // Dialogs
  public static final String GOTO_RESOURCE_DIALOG = PREFIX + "goto_resource_dialog_context"; //$NON-NLS-1$
  
  // Views
  public static final String **VIEW_NAME**Id = PREFIX + "**VIEW_NAME**Id"; //$NON-NLS-1$
}