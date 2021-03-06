//=====================================================================
//
//File:      ExplorerGlobalsTestSuite.java
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package org.xtuml.bp.ui.explorer.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.TestSuite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	ExplorerGlobalsTest.class,
	BuildProjectMenuTest.class,
	ExplorerTest.class,
	ProjectManipulationTests.class,
	I634ExplorerEmptySelectionTest.class,
	FunctionKeyActivationTest.class,
	//this test should run at the end of all others as it removes
	// any existing projects before its run
	AlphaSortingTest.class
})
public class ExplorerGlobalsTestSuite extends TestSuite {
    
}