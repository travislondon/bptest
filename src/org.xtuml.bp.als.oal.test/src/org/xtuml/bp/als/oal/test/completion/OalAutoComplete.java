//=====================================================================
//
// NOTE: This file was generated, but is maintained by hand.
// Generated by: UnitTestGenerator.pl
// Version:      1.15
// Matrix:       oal_autocomplete_matrix.txt
//
//=====================================================================

package org.xtuml.bp.als.oal.test.completion;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.IEditorPart;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.xtuml.bp.als.oal.ParseRunnable;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Body_c;
import org.xtuml.bp.core.BridgeBody_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.DerivedAttributeBody_c;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.FunctionBody_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.OperationBody_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.ProvidedOperationBody_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignalBody_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.RequiredOperationBody_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignalBody_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.StateActionBody_c;
import org.xtuml.bp.core.TransitionActionBody_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.util.ActionLanguageDescriptionUtil;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.text.activity.ParseAllActivitiesAction;

@RunWith(OrderedRunner.class)
public class OalAutoComplete extends CanvasTest {
    public static boolean generateResults = false;
    public static boolean useDrawResults = false;
    public static boolean implementationExists = true;
    String[] results = null;

    String test_id = "";

    protected String getResultName() {
        return getClass().getSimpleName() + "_" + test_id;
    }

    protected IEditorPart fActiveEditor;

    protected IEditorPart getActiveEditor() {
        return fActiveEditor;
    }

    public OalAutoComplete(String subTypeClassName, String subTypeArg0) {
        super(null, subTypeArg0);
    }

    protected String getTestId(String src, String dest, String count) {
        return "test_" + count;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        Ooaofooa.disableChangeNotification();
    }
    
    @Override
    protected void initialSetup() throws Exception {
    	loadProject("oal_autocomplete");
    	modelRoot = Ooaofooa.getInstance("/oal_autocomplete/models/oal_autocomplete/Container/Container.xtuml");
    	// create the initial OAL instances
    	Package_c container = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Container");
			}
		});
    	Selection.getInstance().clear(); Selection.getInstance().addToSelection(container);
    	ParseAllActivitiesAction action = new ParseAllActivitiesAction();
    	action.run(null);
    	// configure flag for existance of implementation of auto complete
    	try {
			if(Class.forName("org.xtuml.bp.core.Proposal_c") == null) {
				implementationExists = false;
			}
		} catch (ClassNotFoundException e) {
			implementationExists = false;
		}
    };

    @After
    public void tearDown() throws Exception {
        Ooaofooa.enableChangeNotification();
    }

    /**
     * "LPAH" is one of the degrees of freedom as specified in this issues
     * test matrix.
     * This routine gets the "LPAH" instance from the given name.
     * 
     * @param element The degree of freedom instance to retrieve
     * @return A model element used in the test as specified by the test matrix
     */
    NonRootModelElement selectLPAH(String element) {
        return selectLPAH(element, null);
    }

    /**
     * "LPAH" is one of the degrees of freedom as specified in this issues
     * test matrix.
     * This routine gets the "LPAH" instance from the given name.
     * 
     * @param element The degree of freedom instance to retrieve
     * @param extraData Extra data needed for selection
     * @return A model element used in the test as specified by the test matrix
     */
    NonRootModelElement selectLPAH(String element, Object extraData) {
    	return findElementForDof(element);
    }

	private int getLineNumber(String element) {
		if(element.contains("S2")) {
			return 135;
		} else if(element.contains("S3")) {
			return 52;
		}
		return 26;
	}

	private String getLocationText() {
		String element = getEntryFromString(getName(), false);
		if(element.equals("L2")) {
			return "l2_var.";
		} else if(element.equals("L3")) {
			return "::";
		} else if(element.equals("L4")) {
			return "send";
		} else if(element.equals("L5")) {
			return "send Port2::";
		} else if(element.equals("L6")) {
			return "send Port2::operation(parameter: 1) to";
		} else if(element.equals("L7")) {
			return "select one l7_var_one";
		} else if(element.equals("L8")) {
			return "select one l8_var_one related by l8_var_two->";
		} else if(element.equals("L9")) {
			return "select one l9_var from instances of ";
		} else if(element.equals("L10")) {
			return "generate";
		} else if(element.equals("L11")) {
			return "generate L11Class1:event to";
		} else if(element.equals("L12")) {
			return "p12_var =";
		} else if(element.equals("L13")) {
			return "for each l13_var in";
		} else if(element.equals("L14")) {
			return "return";
		} else if(element.equals("L15")) {
			return "relate";
		} else if(element.equals("L16")) {
			return "relate l16_var to";
		} else if(element.equals("L17")) {
			return "relate l17_var to l17_var_2 across";
		} else if(element.equals("L18")) {
			return "relate l18_var to l18_var_2 across R1.";
		} else if(element.equals("L19")) {
			return "relate l19_var to l19_var_other across R2 using";
		} else if(element.equals("L20")) {
			return "unrelate l20_var from";
		} else if(element.equals("L21")) {
			return "unrelate l21_var from l21_var_2 across";
		} else if(element.equals("L22")) {
			return "unrelate l22_var from l22_var_2 across R1.";
		} else if(element.equals("L23")) {
			return "unrelate l23_var from l23_other across R2 using";
		} else if(element.equals("L24")) {
			return "self.";
		} else if(element.equals("L25")) {
			return "select one l9_var from instances of L19 ";
		} else if(element.equals("L26")) {
			return "card_var = cardinality ";
		} else if(element.equals("L27")) {
			return "param.";
		} else if(element.equals("L28")) {
			return "create object instance l28_var of";
		} else if(element.equals("L29")) {
			return "delete object instance";
		} else if(element.equals("L30")) {
			return "if(";
		} else if(element.equals("L31")) {
			return "create event instance l31_var of";
		} else if(element.equals("L32")) {
			return "create event instance l32_var of L11Class1 to";
		} else if(element.equals("L33")) {
			return "L33::";
		}
		return " ";
	}
	
	// example name: testS1V1_L1P21AH3
	private String getEntryFromString(String string, boolean p_result) {
		int l_index = string.indexOf("L");
		int p_index = string.indexOf("P");
		int ah_index = string.indexOf("AH");
		CharSequence l_sequence = string.subSequence(l_index, p_index);
		CharSequence p_sequence = string.subSequence(p_index, ah_index);
		if(p_result) {
			return p_sequence.toString();
		} else {
			return l_sequence.toString();
		}
	}

	private String[] getPossibilities(String element) {
		element = getEntryFromString(element, true);
		if(element.equals("P1")) {
			return new String[] {"control stop"};
		} else if (element.equals("P2")) {
			return new String[] {"create event instance"};
		} else if (element.equals("P3")) {
			return new String[] {"create object instance"};
		} else if (element.equals("P4")) {
			return new String[] {"delete object instance"};
		} else if (element.equals("P5")) {
			return new String[] {"for each"};
		} else if (element.equals("P6")) {
			return new String[] {"generate"};
		} else if (element.equals("P7")) {
			return new String[] {"if"};
		} else if (element.equals("P8")) {
			return new String[] {"param"};
		} else if (element.equals("P9")) {
			return new String[] {"relate"};
		} else if (element.equals("P10")) {
			return new String[] {"return"};
		} else if (element.equals("P11")) {
			return new String[] {"select any"};
		} else if (element.equals("P12")) {
			return new String[] {"select many"};
		} else if (element.equals("P13")) {
			return new String[] {"select one"};
		} else if (element.equals("P14")) {
			return new String[] {"send"};
		} else if (element.equals("P15")) {
			return new String[] {"unrelate"};
		} else if (element.equals("P16")) {
			return new String[] {"while"};
		} else if (element.equals("P17")) {
			return new String[] {"break"};
		} else if (element.equals("P18")) {
			return new String[] {"continue"};
		} else if (element.equals("P19")) {
			return new String[] {"end while"};
		} else if (element.equals("P20")) {
			return new String[] {"end for"};
		} else if (element.equals("P21")) {
			return new String[] {"elif"};
		} else if (element.equals("P22")) {
			return new String[] {"else"};
		} else if (element.equals("P23")) {
			return new String[] {"end if"};
		} else if (element.equals("P24")) {
			return new String[] {"self"};
		}  else if (element.equals("P25")) {
			return new String[] { "x" };
		} else if (element.equals("P26")) {
			return new String[] {"L33::"};
		} else if (element.equals("P27")) {
			return new String[] {"L33::"};
		} else if (element.equals("P28")) {
			return new String[] {"Port1::", "Port2::"};
		} else if (element.equals("P29")) {
			return new String[] {"attribute"};
		} else if (element.equals("P30")) {
			return new String[] {"operation( parameter: )"};
		} else if (element.equals("P31")) {
			return new String[] {"cb_operation"};
		} else if (element.equals("P32")) {
			return new String[0]; // not tested yet
		} else if (element.equals("P33")) {
			return new String[] {"function( parameter: )", "FunctionOne()", "FunctionOne-Parameters( ParameterOne:, ParameterTwo:, ParameterThree: )", "FunctionTwo()", "FunctionTwo-Parameters( ParameterOne:, ParameterTwo:, ParameterThree: )"};
		} else if (element.equals("P34")) {
			return new String[0]; // not tested yet
		} else if (element.equals("P35")) {
			return new String[] {"R1.'formalizer'"};
		} else if (element.equals("P36")) {
			return new String[] {"'formalizer'"};
		} else if (element.equals("P37")) {
			return new String[0]; // creator is only added for creation events
		} else if (element.equals("P38")) {
			return new String[0]; // 'class' is only added for class based state machine events
		} else if (element.equals("P39")) {
			return new String[] {"l11_inst_event"};
		} else if (element.equals("P40")) {
			return new String[] {"where"};
		} else if (element.equals("P41")) {
			return new String[] {"cardinality"};
		} else if (element.equals("P42")) {
			return new String[] {"empty"};
		} else if (element.equals("P43")) {
			return new String[] {"not"};
		} else if (element.equals("P44")) {
			return new String[] { "l2_var", "l8_var_one", "l8_var_two", "l11_var",
					"l16_var", "l16_var_2", "l17_var", "l17_var_2", "l18_var", "l18_var_2", "l19_var", "l19_var_other",
					"l19_var_link", "l20_var", "l20_var_2", "l21_var", "l21_var_2", "l22_var", "l22_var_2", "l23_var",
					"l23_other", "l23_link" };
		} else if (element.equals("P45")) {
			return new String[] {"l13_vars"};
		} else if (element.equals("P46")) {
			return new String[] {"not"};
		} else if (element.equals("P47")) {
			return new String[] {"true"};
		} else if (element.equals("P48")) {
			return new String[] {"false"};
		} else if (element.equals("P49")) {
			return new String[] {"operation( op_parameter: )", "signal( sig_parameter: )"};
		} else if (element.equals("P50")) {
			return new String[0]; // not tested yet
		} else if (element.equals("P51")) {
			return new String[] {"related by"};
		} else if (element.equals("P52")) {
			return new String[] {"from instances of"};
		} else if (element.equals("P54")) {
			return new String[] {"Class11:event( parameter: )", "Class12:creation()", "Class21:event( parameter: )", "L11Class1:event()", "L11Class_A1:event()"};
		} else if (element.equals("P55")) {
			return new String[0]; // Untested for now;
		} else if (element.equals("P56")) {
			return new String[0]; // Untested for now;
		} else if (element.equals("P57")) {
			return new String[0]; // Untested for now;
		} else if (element.equals("P58")) {
			return new String[0]; // Untested for now;
		} else if (element.equals("P59")) {
			return new String[] {"bridge( parameter: )"};
		} else if (element.equals("P60")) {
			return new String[] {"->L8Class[R1.'simple']"};
		}
		return new String[0];
	}

	private String[] populateAutoComplete(String element) throws BadLocationException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		NonRootModelElement rootElement = (NonRootModelElement) getRootElementForBody(testBody)[0];
		int lineNumber = getLineNumber(element);
		String locationText = getLocationText();
		String action = ActionLanguageDescriptionUtil.getActionLanguageAttributeValue(rootElement);
		Document doc = new Document(action);
		IRegion region = doc.getLineInformation(lineNumber);
		doc.replace(region.getOffset(), region.getLength(), locationText);
		String documentContents = doc.get();
        ParseRunnable parseRunner = new ParseRunnable(rootElement, documentContents,
                lineNumber + 1, locationText.length() + 1);
        parseRunner.run();
        Class<?> proposalClass = Class.forName("org.xtuml.bp.core.Proposal_c");
        Class<?> proposalListClass = Class.forName("org.xtuml.bp.core.ProposalList_c");
		Method proposalMethod = proposalClass.getMethod("getManyP_PsOnR1601", new Class[] {proposalListClass}); 
		Method getReplacementTextMethod = proposalClass.getMethod("getReplacement_text", new Class[0]);
		Method proposalListsMethod = proposalListClass.getMethod("getOneP_PLOnR1603", new Class[] {Body_c.class});
		Object proposalList = proposalListsMethod.invoke(null, new Object[] {testBody});
		Object[] proposals = (Object[]) proposalMethod.invoke(null, new Object[] {proposalList});
		String[] results = new String[proposals.length];
		for(int i = 0; i < results.length; i++) {
			results[i] = (String) getReplacementTextMethod.invoke(proposals[i]);
		}
		return results;
	}

	public static Object[] getRootElementForBody(NonRootModelElement columnInstance) {
		NonRootModelElement element = Bridge_c.getOneS_BRGOnR697(BridgeBody_c.getManyACT_BRBsOnR698((Body_c) columnInstance));
		if (element == null) {
			element = Operation_c.getOneO_TFROnR696((OperationBody_c.getManyACT_OPBsOnR698((Body_c) columnInstance)));
		}
		if(element == null) {
			element = Action_c.getOneSM_ACTOnR691(StateActionBody_c.getManyACT_SABsOnR698((Body_c) columnInstance));
		} 
		if(element == null) {
			element = Function_c.getOneS_SYNCOnR695(FunctionBody_c.getManyACT_FNBsOnR698((Body_c) columnInstance));
		} 
		if(element == null) {
			element = DerivedBaseAttribute_c.getOneO_DBATTROnR693(DerivedAttributeBody_c.getManyACT_DABsOnR698((Body_c) columnInstance));
		} 
		if(element == null) {
			element = ProvidedOperation_c.getOneSPR_POOnR687(ProvidedOperationBody_c.getManyACT_POBsOnR698((Body_c) columnInstance));
		} 
		if (element == null) {
			element = ProvidedSignal_c.getOneSPR_PSOnR686(ProvidedSignalBody_c.getManyACT_PSBsOnR698((Body_c) columnInstance));
		} 
		if (element == null) {
			element = RequiredOperation_c.getOneSPR_ROOnR685(RequiredOperationBody_c.getManyACT_ROBsOnR698((Body_c) columnInstance));
		} 
		if (element == null) {
			element = RequiredSignal_c.getOneSPR_RSOnR684(RequiredSignalBody_c.getManyACT_RSBsOnR698((Body_c) columnInstance));
		} 
		if (element == null) {
			element = Action_c.getOneSM_ACTOnR688(TransitionActionBody_c.getManyACT_TABsOnR698((Body_c) columnInstance));
		}
		String action = ActionLanguageDescriptionUtil.getActionLanguageAttributeValue(element);
		return new Object[] { element, action };
	}

	Body_c testBody = null;
	private String[] actualProposals;
	NonRootModelElement findElementForDof(String element) {
		ClassQueryInterface_c classQuery = new ClassQueryInterface_c() {

			@Override
			public boolean evaluate(Object candidate) {
				// ignore empty bodies
				Object[] detail = getBodyDetail((Body_c) candidate);
				NonRootModelElement pkg = (Package_c) detail[0];
				String action = (String) detail[1];
				if(action.length() == 0) {
					return false;
				}
				int index = getName().indexOf("AH");
				int length = getName().length();
				CharSequence testSequence = getName().subSequence(index, length);
				index = pkg.getName().indexOf("AH");
				if(index != -1) {
					length = pkg.getName().length();
					CharSequence pkgSequence = pkg.getName().subSequence(index, length);			
					// For AH6-9 there are multiple bodies in the test package, so more
					// filtering is required
					if ( pkgSequence.toString().contains("AH6") ) {
						ProvidedOperation_c spr_po = ProvidedOperation_c.getOneSPR_POOnR687(ProvidedOperationBody_c.getOneACT_POBOnR698((Body_c)candidate));
					    return testSequence.equals(pkgSequence) && null != spr_po;
					}
					else if ( pkgSequence.toString().contains("AH7") ) {
						ProvidedSignal_c spr_ps = ProvidedSignal_c.getOneSPR_PSOnR686(ProvidedSignalBody_c.getOneACT_PSBOnR698((Body_c)candidate));
					    return testSequence.equals(pkgSequence) && null != spr_ps;
					}
					else if ( pkgSequence.toString().contains("AH8") ) {
						RequiredOperation_c spr_ro = RequiredOperation_c.getOneSPR_ROOnR685(RequiredOperationBody_c.getOneACT_ROBOnR698((Body_c)candidate));
					    return testSequence.equals(pkgSequence) && null != spr_ro;
					}
					else if ( pkgSequence.toString().contains("AH9") ) {
						RequiredSignal_c spr_rs = RequiredSignal_c.getOneSPR_RSOnR684(RequiredSignalBody_c.getOneACT_RSBOnR698((Body_c)candidate));
					    return testSequence.equals(pkgSequence) && null != spr_rs;
					}
					else return testSequence.equals(pkgSequence);
				}
				return false;
			}
		};
		testBody = Body_c.BodyInstance(modelRoot, classQuery);
		return testBody;
	}
    protected Object[] getBodyDetail(Body_c body) {
    	Object[] bodyDetail = getRootElementForBody(body);
		NonRootModelElement root = (NonRootModelElement) bodyDetail[0];
		Package_c parentPackage = root.getFirstParentPackage();
		if(parentPackage == null) {
			Component_c parentComponent = root.getFirstParentComponent();
			if(parentComponent != null) {
				parentPackage = parentComponent.getFirstParentPackage();
			}
		}
		bodyDetail[0] = parentPackage;
		return bodyDetail;
	}

	/**
     * "SV" is one of the degrees of freedom as specified in this issues
     * test matrix.
     * This routine gets the "SV" instance from the given name.
     * 
     * @param element The degree of freedom instance to retrieve
     * @return A model element used in the test as specified by the test matrix
     */
    NonRootModelElement selectSV(String element) {
        return selectSV(element, null);
    }

    /**
     * "SV" is one of the degrees of freedom as specified in this issues
     * test matrix.
     * This routine gets the "SV" instance from the given name.
     * 
     * @param element The degree of freedom instance to retrieve
     * @param extraData Extra data needed for selection
     * @return A model element used in the test as specified by the test matrix
     */
    NonRootModelElement selectSV(String element, Object extraData) {
    	// everything will be selected in the PAH selection, and test run
        return null;
    }

    /**
     * This routine performs the action associated with a matrix cell.
     * The parameters represent model instances aquired based on the specifed
     * column instance and row instance.
     * 
     * @param columnInstance Model instance from the column
     * @param rowInstance Model instance from the row
     * @throws BadPositionCategoryException 
     * @throws BadLocationException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws ClassNotFoundException 
     */
    void SV_LPAH_Action(NonRootModelElement columnInstance, NonRootModelElement rowInstance) throws BadLocationException, BadPositionCategoryException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        actualProposals = populateAutoComplete(getName());
    }

    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_doesNotExist(NonRootModelElement source, NonRootModelElement destination) {
    	// for non-existence of auto-complete we want these
    	// to fail, as they will be incorrectly true in some
    	// cases 
    	if(!implementationExists) {
    		return false;
    	}
    	String[] possibilities = getPossibilities(getName());
        // make sure no possibility is present
        for(String possibility : possibilities) {
    	    for(String actual : actualProposals) {
	        	if(actual.equals(possibility)) {
	        		return false;
	        	}
    		}
        }
        return true;
    }

	/**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_doesExist(NonRootModelElement source, NonRootModelElement destination) {
    	String[] possibilities = getPossibilities(getName());
        // make sure every possibility is present
    	int num_possibilities_present = 0;
        for(String possibility : possibilities) {
    	    for(String actual : actualProposals) {
	    		if(actual.equals(possibility)) {
	    			num_possibilities_present++;
	    			break;
		        }
    		}
        }
        return ( num_possibilities_present == possibilities.length );
    }


}
