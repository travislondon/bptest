//=====================================================================
//
//File:      $RCSfile: GraphicalAnchorTests.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/05/10 05:52:03 $
//
// NOTE: This file was generated, but is maintained by hand.
// Generated by: UnitTestGenerator.pl
// Version:      1.10
// Matrix:       graphical_anchors.txt
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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

package org.xtuml.bp.ui.canvas.test.anchors;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PrecisionPointList;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.util.WorkspaceUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.canvas.test.CanvasTestUtilities;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.parts.ConnectorEditPart;
import org.xtuml.bp.ui.graphics.parts.DiagramEditPart;
import org.xtuml.bp.ui.graphics.parts.ShapeEditPart;
import org.xtuml.bp.utilities.ui.CanvasUtilities;

public class GraphicalAnchorTests extends CanvasTest {
	public static boolean generateResults = false;
	public static boolean useDrawResults = false;

	String test_id = "";

	protected String getResultName() {
		return getClass().getSimpleName() + "_" + test_id;
	}

	protected GraphicalEditor fActiveEditor;

	private Point resultStartPoint;

	private Point resultEndPoint;

	private static boolean initialized;

	private ConnectorEditPart testPart;

	protected GraphicalEditor getActiveEditor() {
		IEditorPart activeEditor = UITestingUtilities.getActiveEditor();
		if (activeEditor == null || !(activeEditor instanceof GraphicalEditor)) {
			return null;
		}
		return (GraphicalEditor) activeEditor;
	}

	public GraphicalAnchorTests(String subTypeClassName, String subTypeArg0) {
		super(null, subTypeArg0);
	}

	/**
	 * Test id must contain the C variant to help with creation, where the
	 * action is different.
	 * 
	 * @param src
	 * @param dest
	 * @param count
	 * @return
	 */
	protected String getTestId(String src, String dest, String count) {
		return "test_" + src + "_" + dest + "_" + count;
	}

	protected void setUp() throws Exception {
		super.setUp();
		// import the test model for the first
		// setUp
		if (!initialized) {
			// set the core plugin to debugging as
			// there are some benign consistency errors
			// causing failures
			// These errors will be resolved when full
			// generic package support is complete
			CorePlugin.getDefault().setDebugging(true);
			
			WorkspaceUtil.setAutobuilding(false);

			loadProject("GraphicalAnchorTests");
			PersistableModelComponent sys_comp = m_sys
					.getPersistableComponent();
			sys_comp.loadComponentAndChildren(new NullProgressMonitor());
			initialized = true;
		} else {
			// undo the last change
			if (m_sys.getTransactionManager().getUndoAction().isEnabled()) {
				m_sys.getTransactionManager().getUndoAction().run();
			}
		}
		BaseTest.dispatchEvents(0);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.closeAllEditors(false);
	}

	/**
	 * "A" is one of the degrees of freedom as specified in this issues test
	 * matrix. This routine gets the "A" instance from the given name.
	 * 
	 * @param element
	 *            The degree of freedom instance to retrieve
	 * @return A model element used in the test as specified by the test matrix
	 */
	NonRootModelElement selectA(String element) {
		if (element.equalsIgnoreCase("A5")) {
			return getModelForTest();
		}
		GraphicalEditPart elementByName = getElementByName(element);
		NonRootModelElement nrme = (NonRootModelElement) elementByName
				.getModel();
		assertTrue(
				"An instance with degree of freedom type \"A\" was not found.  Instance Name: "
						+ element + ".", nrme != null);
		return nrme;
	}

	private Model_c getModelForTest() {
		String packageName = "Test Container";
		if (test_id.contains("C1")) {
			// open the editor for creation
			packageName = packageName + " Creation";
		}
		final String finalPackageName = packageName;
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object arg0) {
						return ((Package_c) arg0).getName().equals(
								finalPackageName);
					}
				});
		if (getActiveEditor() == null
				|| getActiveEditor().getModel().getRepresents() != pkg) {
			CanvasUtilities.openCanvasEditor(pkg);
			while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
				;
			// fill the available space with the editor
			// as nothing else is interesting to this
			// test
			if(!PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().isPageZoomed()) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().toggleZoom(
								PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow()
										.getActivePage()
										.getActivePartReference());
			}
			// also set the zoom of the editor to 100%
			// for easier position calculations
			ZoomManager zoomManager = (ZoomManager) getActiveEditor()
					.getAdapter(ZoomManager.class);
			getActiveEditor().zoomAll();
			zoomManager.setZoom(1);
			while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
				;
			// disable grid snapping to allow exact
			// positions
			CorePlugin.getDefault().getPreferenceStore().setValue(
					BridgePointPreferencesStore.SNAP_TO_GRID, false);
			getActiveEditor().configureGridOptions();
		} else {
			// restore 100% zoom
			ZoomManager zoomManager = (ZoomManager) getActiveEditor()
					.getAdapter(ZoomManager.class);
			zoomManager.setZoom(1);			
		}
		return getActiveEditor().getModel();
	}

	/**
	 * "BC" is one of the degrees of freedom as specified in this issues test
	 * matrix. This routine gets the "BC" instance from the given name.
	 * 
	 * @param element
	 *            The degree of freedom instance to retrieve
	 * @return A model element used in the test as specified by the test matrix
	 */
	NonRootModelElement selectBC(String element) {
		// bc elements are not necessary
		return null;
	}

	/**
	 * This routine performs the action associated with a matrix cell. The
	 * parameters represent model instances acquired based on the specified column
	 * instance and row instance.
	 * 
	 * @param columnInstance
	 *            Model instance from the column
	 * @param rowInstance
	 *            Model instance from the row
	 */
	void A_BC_Action(NonRootModelElement columnInstance,
			NonRootModelElement rowInstance) {
		// zoom the diagram before performing the test
		GraphicalElement_c ge = null;
		GraphicalEditor graphicalEditorFor = null;
		if(columnInstance instanceof Shape_c) {
			ge = GraphicalElement_c.getOneGD_GEOnR2((Shape_c) columnInstance);
		}
		if(columnInstance instanceof Connector_c) {
			ge = GraphicalElement_c.getOneGD_GEOnR2((Connector_c) columnInstance);
		}
		if(ge != null) {
			graphicalEditorFor = UITestingUtilities.getGraphicalEditorFor((NonRootModelElement) ge.getRepresents(),
					false);
		}
		if(columnInstance instanceof Model_c) {
			graphicalEditorFor = UITestingUtilities.getGraphicalEditorFor(
					(NonRootModelElement) ((Model_c) columnInstance).getRepresents(), true, false);
		}
		if(graphicalEditorFor != null) {
			graphicalEditorFor.zoomAll();
			graphicalEditorFor.refresh();
			BaseTest.dispatchEvents(0);
			// check the test id for which action to run
			if (test_id.contains("C1")) {
				GraphicalEditPart sourcePart = getSourceForCreation(columnInstance);
				GraphicalEditPart targetPart = getTargetForCreation(columnInstance);
				performCreationAction(sourcePart, targetPart);
			} else if (test_id.contains("C2")) {
				performUpdateEndAction(columnInstance, rowInstance);
			} else {
				performMoveSegmentAction(columnInstance, rowInstance);
			}
		}
	}

	private void performMoveSegmentAction(NonRootModelElement columnInstance,
			NonRootModelElement rowInstance) {
		UITestingUtilities.clearGraphicalSelection();
		ConnectorEditPart testPart = getTestPart(columnInstance);
		GraphicalEditPart hostPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(columnInstance);
		// locate the midpoint between the segments midpoint and start point
		Point midPoint = testPart.getConnectionFigure().getPoints()
				.getMidpoint();
		Point startPoint = testPart.getConnectionFigure().getPoints()
				.getFirstPoint();
		// cache start and end points for comparison during
		// result checking
		resultStartPoint = testPart.getConnectionFigure().getPoints()
				.getFirstPoint();
		resultEndPoint = testPart.getConnectionFigure().getPoints()
				.getLastPoint();
		Point mousePoint = new PointList(new int[] { midPoint.x, midPoint.y,
				startPoint.x, startPoint.y }).getMidpoint();
		testPart.getFigure().translateToAbsolute(mousePoint);
		CanvasTestUtilities.doMouseMove(mousePoint.x, mousePoint.y);
		CanvasTestUtilities.doMousePress(mousePoint.x, mousePoint.y);
		Point moveDelta = getMoveDelta(hostPart, testPart, mousePoint);
		Point deltaCopy = moveDelta.getCopy();
		// apply the delta to the cached values
		resultStartPoint.translate(deltaCopy);
		resultEndPoint.translate(deltaCopy);
		CanvasTestUtilities.doMouseMove(mousePoint.x + moveDelta.x,
				mousePoint.y + moveDelta.y);
		CanvasTestUtilities.doMouseRelease(mousePoint.x + moveDelta.x,
				mousePoint.y + moveDelta.y);
	}

	private void openEditorFor(NonRootModelElement columnInstance) {
		Model_c model = null;
		if(columnInstance instanceof Model_c) {
			model = (Model_c) columnInstance;
		} else {
			GraphicalElement_c element = getGraphicalElementFromInstance(columnInstance);
			model = Model_c.getOneGD_MDOnR1(element);
		}
		CanvasTestUtils.openCanvasEditor(model.getRepresents());
	}

	private void performUpdateEndAction(NonRootModelElement columnInstance,
			NonRootModelElement rowInstance) {
		UITestingUtilities.clearGraphicalSelection();
		ConnectorEditPart testPart = getTestPart(columnInstance);
		// add the test part to the selection so that selection
		// handles are created
		UITestingUtilities.addElementToGraphicalSelection(testPart);
		GraphicalEditPart hostPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(columnInstance);
		// get the start point for mouse events
		Point mousePoint = testPart.getConnectionFigure().getPoints()
				.getFirstPoint();
		if(test_id.contains("A3") || test_id.contains("A4")) {
			// for A3 we are dealing with a requirement which
			// ends on the connector, A4 is dealing with a subtype
			// which also ends on the connector
			mousePoint = testPart.getConnectionFigure().getPoints()
					.getLastPoint();
		}
		resultStartPoint = mousePoint.getCopy();
		testPart.getFigure().translateToAbsolute(mousePoint);
		CanvasTestUtilities.doMouseMove(mousePoint.x, mousePoint.y);
		CanvasTestUtilities.doMousePress(mousePoint.x, mousePoint.y);
		Point moveDelta = getMoveDelta(hostPart, testPart, mousePoint);
		// apply the delta to the start point
		resultStartPoint.translate(moveDelta);
		CanvasTestUtilities.doMouseMove(mousePoint.x + moveDelta.x,
				mousePoint.y + moveDelta.y);
		CanvasTestUtilities.doMouseRelease(mousePoint.x + moveDelta.x,
				mousePoint.y + moveDelta.y);
	}

	private Point getMoveDelta(GraphicalEditPart hostPart,
			GraphicalEditPart testPart, Point mousePoint) {
		Point moveDelta = new Point(24, 24);
		if (test_id.contains("B1")) {
			// just move the connector right
			moveDelta.y = 0;
		} else if (test_id.contains("B3")) {
			if(test_id.contains("A5")) {
				// special test as the host is the entire canvas
				// so treat as if within bounds, use the default
				return moveDelta;
			}
			// move such that the anchor point
			// would be out of the anchor host
			Rectangle bounds = hostPart.getFigure().getBounds();
			Point newLocation = new Point(bounds.x - 24, bounds.y);
			Point mouseCopy = mousePoint.getCopy();
			hostPart.getFigure().translateToRelative(mouseCopy);
			Dimension diff = newLocation.getDifference(mouseCopy);
			moveDelta.x = diff.width;
			moveDelta.y = 0;
		} else {
			if(test_id.contains("A2")) {
				// this is a connector middle, we cannot use
				// 24 pts on the y axis as that is an invalid drop
				moveDelta.y = 12;
			}
		}
		return moveDelta;
	}

	private ConnectorEditPart getTestPart(NonRootModelElement columnInstance) {
		if (testPart == null) {
			openEditorFor(columnInstance);
			GraphicalEditPart hostPart = (GraphicalEditPart) UITestingUtilities
					.getEditorPartFor(columnInstance);
			if (test_id.contains("A2")) {
				// we need to find the timing mark
				testPart = (ConnectorEditPart) ((GraphicalEditPart) hostPart
						.getSourceConnections().get(0)).getSourceConnections()
						.get(0);
			} else {
				testPart = (ConnectorEditPart) hostPart.getSourceConnections()
						.get(0);
			}
		}
		return testPart;
	}

	private void performCreationAction(GraphicalEditPart sourcePart,
			GraphicalEditPart targetPart) {
		Point startPoint = getStartPointForCreation(sourcePart);
		Point endPoint = getEndPointForCreation(targetPart, startPoint);
		resultStartPoint = startPoint.getCopy();
		resultEndPoint = endPoint.getCopy();
		sourcePart.getFigure().translateToAbsolute(startPoint);
		sourcePart.getFigure().translateToAbsolute(endPoint);
		String[] toolAndDrawer = getToolFromColumn(getColumnFromTestId());
		UITestingUtilities.activateTool(UITestingUtilities.getTool(
				toolAndDrawer[1], toolAndDrawer[0]));
		CanvasTestUtilities.doMouseMove(startPoint.x, startPoint.y);
		CanvasTestUtilities.doMousePress(startPoint.x, startPoint.y);
		CanvasTestUtilities.doMouseMove(endPoint.x, endPoint.y);
		CanvasTestUtilities.doMouseRelease(endPoint.x, endPoint.y);
		// cache the new connector for location tests
		testPart = getNewConnectorPart();
	}

	private ConnectorEditPart getNewConnectorPart() {
		GraphicalEditor activeEditor = getActiveEditor();
		Model_c model = activeEditor.getModel();
		Connector_c[] connectors = Connector_c
				.getManyGD_CONsOnR2(GraphicalElement_c.getManyGD_GEsOnR1(model));
		return (ConnectorEditPart) UITestingUtilities
				.getEditorPartFor(connectors[connectors.length - 1]);
	}

	private Point getEndPointForCreation(GraphicalEditPart targetPart, Point start) {
		Point point = targetPart.getFigure().getBounds().getCenter();
		if (test_id.contains("B3")) {
			Rectangle extent = getCurrentExtent();
			point.y = extent.y + 200;
			point.x = ((IFigure) targetPart.getFigure().getChildren().get(0))
					.getBounds().x;
			if(test_id.contains("A5")) {
				// use the start point and extend downward
				point.x = start.x;
				point.y = start.y + 100;
			}
		}
		if (test_id.contains("B1")) {
			if(test_id.equals("test_A2_B1C1_8")) {
				// this test draws to whitespace, we need to use the
				// start point to determine a good end point
				point = new Point(start.x, start.y - 100);
				return point;
			}
			if(test_id.contains("A3")) {
				// use the start point for the connection
				point = ((Connection) targetPart.getFigure()).getPoints().getFirstPoint();
				return point;
			}
			if(test_id.contains("A4")) {
				// use the end point for the connection
				point = ((Connection) targetPart.getFigure()).getPoints().getLastPoint();
				return point;
			}
			point.y = targetPart.getFigure().getBounds().getBottom().y;
		}
		if(test_id.equals("test_A2_B2C1_9")) {
			// this test draws to whitespace, we need to use the
			// start point to determine a good end point
			point = new Point(start.x, start.y - 100);
			return point;			
		}
		if(test_id.contains("B2") && targetPart instanceof ConnectorEditPart) {
			// return the midpoint of the connection
			ConnectorEditPart target = (ConnectorEditPart) targetPart;
			point = target.getConnectionFigure().getPoints().getMidpoint();
		}
		return point;
	}

	private Point getStartPointForCreation(GraphicalEditPart sourcePart) {
		Point point = sourcePart.getFigure().getBounds().getCenter();
		if (test_id.contains("B3")) {
			Rectangle extent = getCurrentExtent();
			point.y = extent.y + 100;
			point.x = ((IFigure) sourcePart.getFigure().getChildren().get(0))
					.getBounds().x;
			if(test_id.contains("A5")) {
				// use a clear area
				point.x = extent.getCenter().x;
				point.y = extent.getCenter().y;
				return point;
			}
		}
		if (test_id.contains("B1")) {
			if(test_id.contains("A2")) {
				// use the midpoint for the connection
				point = ((Connection) sourcePart.getFigure()).getPoints().getMidpoint();
				return point;
			}
			point.y = sourcePart.getFigure().getBounds().getTop().y;
		}
		if(test_id.contains("B2") && test_id.contains("A2")) {
			// for a connection we want the point to be
			// just off of the line
			point = ((Connection) sourcePart.getFigure()).getPoints().getMidpoint();
			point.y = point.y - 10;
		}
		return point;
	}

	private Rectangle getCurrentExtent() {
		List<GraphicalEditPart> allSymbols = GraphicalEditor.getAllSymbols(
				(GraphicalViewer) getActiveEditor().getAdapter(
						GraphicalViewer.class), false);
		Rectangle extent = null;
		for (GraphicalEditPart part : allSymbols) {
			if (extent == null) {
				extent = part.getFigure().getBounds().getCopy();
			}
			extent.union(part.getFigure().getBounds().getCopy());
		}
		return extent;
	}

	private String[] getToolFromColumn(String column) {
		String[] result = new String[2];
		if (column.equals("A1")) {
			result[0] = "Association";
			result[1] = "Classes";
		} else if (column.equals("A2")) {
			result[0] = "Timing Mark";
			result[1] = "Sequence";
		} else if (column.equals("A3")) {
			result[0] = "Required Interface";
			result[1] = "Components";
		} else if (column.equals("A4")) {
			result[0] = "Subtype";
			result[1] = "Classes";
		} else if (column.equals("A5")) {
			result[0] = "Synchronous Message";
			result[1] = "Interaction";
		}
		return result;
	}

	private GraphicalEditPart getTargetForCreation(
			NonRootModelElement columnInstance) {
		String column = getColumnFromTestId();
		String destinationName = column + "Destination";
		GraphicalEditPart editPart = getElementByName(destinationName);
		if (column.equals("A2") || column.equals("A4")) {
			// get lifeline, or the supertype
			if(column.equals("A2")) {
				editPart = (GraphicalEditPart) UITestingUtilities
					.getEditorPartFor(getModelForTest());
			} else {
				editPart = (GraphicalEditPart) editPart.getSourceConnections().get(
						0);
			}
		}
		if (column.equals("A5")) {
			// get diagram
			editPart = (GraphicalEditPart) UITestingUtilities
					.getEditorPartFor(columnInstance);
		}
		return editPart;
	}

	private GraphicalEditPart getElementByName(String name) {
		GraphicalElement_c[] others = GraphicalElement_c
				.getManyGD_GEsOnR1(getModelForTest());
		Object object = null;
		for (int i = 0; i < others.length; i++) {
			NonRootModelElement represents = (NonRootModelElement) others[i]
					.getRepresents();
			if (represents.getName().equals(name)) {
				object = Shape_c.getOneGD_SHPOnR2(others[i]);
				if (object == null) {
					object = Connector_c.getOneGD_CONOnR2(others[i]);
				}
				break;
			}
		}
		return (GraphicalEditPart) UITestingUtilities.getEditorPartFor(object);
	}

	private GraphicalEditPart getSourceForCreation(
			NonRootModelElement columnInstance) {
		String column = getColumnFromTestId();
		GraphicalEditPart part = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(columnInstance);
		if (column.equals("A2")) {
			// get the lifeline
			part = (GraphicalEditPart) part.getSourceConnections().get(0);
		}
		return part;
	}

	private GraphicalElement_c getGraphicalElementFromInstance(
			NonRootModelElement columnInstance) {
		if (columnInstance instanceof Shape_c) {
			return GraphicalElement_c.getOneGD_GEOnR2((Shape_c) columnInstance);
		}
		if (columnInstance instanceof Connector_c) {
			return GraphicalElement_c
					.getOneGD_GEOnR2((Connector_c) columnInstance);
		}
		return null;
	}

	private String getColumnFromTestId() {
		String[] split = test_id.split("_");
		return split[1];
	}

	/**
	 * This function verifies an expected result.
	 * 
	 * @param source
	 *            A model element instance acquired through a action taken on a
	 *            column of the matrix.
	 * @param destination
	 *            A model element instance acquired through a action taken taken
	 *            on a row of the matrix.
	 * @return true if the test succeeds, false if it fails
	 */
	boolean checkResult_dropPointMovedToConnectorStart(
			NonRootModelElement source, NonRootModelElement destination) {
		ConnectorEditPart testPart = getTestPart(source);
		Point destPoint = new Point();
		Point testPoint = testPart.getConnectionFigure().getPoints()
				.getFirstPoint();
		if (testPart.getSource() instanceof ConnectorEditPart) {
			destPoint = ((AbstractConnectionEditPart) testPart.getSource())
					.getConnectionFigure().getPoints().getFirstPoint();
		} else {
			destPoint = ((AbstractConnectionEditPart) testPart.getTarget())
					.getConnectionFigure().getPoints().getLastPoint();
			testPoint = testPart.getConnectionFigure().getPoints()
					.getLastPoint();
		}
		if (!destPoint.equals(testPoint)) {
			return false;
		}
		return true;
	}

	/**
	 * This function verifies an expected result.
	 * 
	 * @param source
	 *            A model element instance acquired through a action taken on a
	 *            column of the matrix.
	 * @param destination
	 *            A model element instance acquired through a action taken taken
	 *            on a row of the matrix.
	 * @return true if the test succeeds, false if it fails
	 */
	boolean checkResult_dropPointUnchanged(NonRootModelElement source,
			NonRootModelElement destination) {
		ConnectorEditPart testPart = getTestPart(source);
		PointList points = testPart.getConnectionFigure().getPoints();
		assertTrue("Expected result point was not present.",
				resultStartPoint != null);
		if(!resultStartPoint.equals(points.getFirstPoint())) {
			return false;
		}
		if (resultEndPoint != null) {
			if (!resultEndPoint.equals(points.getLastPoint())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This function verifies an expected result.
	 * 
	 * @param source
	 *            A model element instance acquired through a action taken on a
	 *            column of the matrix.
	 * @param destination
	 *            A model element instance acquired through a action taken taken
	 *            on a row of the matrix.
	 * @return true if the test succeeds, false if it fails
	 */
	boolean checkResult_closestPointUsed(NonRootModelElement source,
			NonRootModelElement destination) {
		ConnectorEditPart testPart = getTestPart(source);
		boolean result = true;
		// the point should be the bottom left corner of the host
		Point firstPoint = testPart.getConnectionFigure().getPoints()
				.getFirstPoint();
		GraphicalEditPart sourcePart = (GraphicalEditPart) testPart.getSource();
		if (sourcePart instanceof ShapeEditPart) {
			if (!firstPoint.equals(sourcePart.getFigure().getBounds()
					.getLocation())) {
				return false;
			}
		}
		if (sourcePart instanceof ConnectorEditPart) {
			ConnectorEditPart conPart = (ConnectorEditPart) sourcePart;
			if (!firstPoint.equals(conPart.getConnectionFigure().getPoints()
					.getFirstPoint())) {
				return false;
			}
		}
		// also assert that the points are on the shapes edge
		result = arePointsOnEdge(testPart);
		return result;
	}

	/**
	 * This function verifies an expected result.
	 * 
	 * @param source
	 *            A model element instance acquired through a action taken on a
	 *            column of the matrix.
	 * @param destination
	 *            A model element instance acquired through a action taken taken
	 *            on a row of the matrix.
	 * @return true if the test succeeds, false if it fails
	 */
	boolean checkResult_dropPointCroppedToEdge(NonRootModelElement source,
			NonRootModelElement destination) {
		ConnectorEditPart testPart = getTestPart(source);
		PointList points = testPart.getConnectionFigure().getPoints();
		assertTrue("Expected result point was not present.",
				resultStartPoint != null);
		boolean result = true;
		if (points.getFirstPoint().equals(resultStartPoint)) {
			result = false;
		}
		if (resultEndPoint != null) {
			if (points.getLastPoint().equals(resultEndPoint)) {
				result = false;
			}
		}
		// also assert that the points are on the shapes edge
		result = arePointsOnEdge(testPart);
		return result;
	}

	private boolean arePointsOnEdge(ConnectorEditPart testPart) {
		GraphicalEditPart source = (GraphicalEditPart) testPart.getSource();
		GraphicalEditPart target = (GraphicalEditPart) testPart.getTarget();
		testPart.getConnectionFigure().getConnectionRouter().route(testPart.getConnectionFigure());
		Point startPoint = testPart.getConnectionFigure().getPoints().getFirstPoint();
		Point endPoint = testPart.getConnectionFigure().getPoints()
				.getLastPoint().getCopy();
		boolean targetValid = false;
		boolean sourceValid = false;
		if (source instanceof ShapeEditPart) {
			PointList shapePoints = getPolygonPoints(source.getFigure()
					.getBounds());
			sourceValid = isPointOnPointList(shapePoints, startPoint);
		}
		if (target instanceof ShapeEditPart) {
			PointList shapePoints = getPolygonPoints(target.getFigure()
					.getBounds());
			targetValid = isPointOnPointList(shapePoints, endPoint);
		}
		if (source instanceof ConnectorEditPart) {
			sourceValid = isPointOnPointList(((AbstractConnectionEditPart) source)
					.getConnectionFigure().getPoints(), startPoint);
		}
		if (target instanceof ConnectorEditPart) {
			targetValid = isPointOnPointList(((AbstractConnectionEditPart) target)
					.getConnectionFigure().getPoints(), endPoint);
		}
		if (source instanceof DiagramEditPart) {
			sourceValid = true;
		}
		if (target instanceof DiagramEditPart) {
			targetValid = true;
		}
		return sourceValid && targetValid;
	}

	public static boolean isPointOnPointList(PointList shapePoints, Point point) {
		List<?> lineSegments = PointListUtilities.getLineSegments(shapePoints);
		boolean internalResult = false;
		for (Object segObj : lineSegments) {
			LineSeg seg = (LineSeg) segObj;
			if (seg.containsPoint(point, 1)) {
				internalResult = true;
				break;
			}
		}
		return internalResult;
	}

	protected PointList getPolygonPoints(Rectangle bounds) {
		PrecisionRectangle r = new PrecisionRectangle(bounds);
		PrecisionPointList ptList = new PrecisionPointList(5);
		ptList.addPoint(new PrecisionPoint(r.preciseX, r.preciseY));
		ptList.addPoint(new PrecisionPoint(r.preciseX + r.preciseWidth,
				r.preciseY));
		ptList.addPoint(new PrecisionPoint(r.preciseX + r.preciseWidth,
				r.preciseY + r.preciseHeight));
		ptList.addPoint(new PrecisionPoint(r.preciseX, r.preciseY
				+ r.preciseHeight));
		ptList.addPoint(new PrecisionPoint(r.preciseX, r.preciseY));
		return ptList;
	}

	/**
	 * This function verifies an expected result.
	 * 
	 * @param source
	 *            A model element instance acquired through a action taken on a
	 *            column of the matrix.
	 * @param destination
	 *            A model element instance acquired through a action taken taken
	 *            on a row of the matrix.
	 * @return true if the test succeeds, false if it fails
	 */
	boolean checkResult_dropPointMovedToConnectorEnd(
			NonRootModelElement source, NonRootModelElement destination) {
		ConnectorEditPart testPart = getTestPart(source);
		Point conPoint = new Point();
		Point testPoint = testPart.getConnectionFigure().getPoints()
			.getLastPoint();
		if(testPart.getSource() instanceof ConnectorEditPart) {
			conPoint = ((AbstractConnectionEditPart) testPart.getSource())
				.getConnectionFigure().getPoints().getLastPoint();
		} else {
			conPoint = ((AbstractConnectionEditPart) testPart.getTarget())
				.getConnectionFigure().getPoints().getLastPoint();			
		}
		if (!conPoint.equals(testPoint)) {
			return false;
		}
		return true;
	}

}
