//====================================================================
//
// File:      $RCSfile: CleanseForMCWorker.java,v $
// Version:   $Revision: 1.6 $
// Modified:  $Date: 2013/01/10 23:15:01 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
package org.xtuml.bp.internal.tools.cleaner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import org.xtuml.bp.core.Body_c;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.FunctionBody_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.OperationBody_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.util.UIUtil;

public class CleanseForMCWorker extends ActionDelegate {
    
    private final String CONSOLE_NAME = "Console"; //$NON-NLS-1$

    private MessageConsole myConsole;
    private MessageConsoleStream msgbuf;
    
    public CleanseForMCWorker() {
        myConsole = findConsole(CONSOLE_NAME);
        msgbuf = myConsole.newMessageStream();
    }
    
	public void run() {
		myConsole.clearConsole();
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		if(selection instanceof StructuredSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			Iterator iter = structuredSelection.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof SystemModel_c) {
		            boolean userOKsUpdate = false;
	                String msg =
		                        "This action will remove lots of model data (Functions, " +
		                        "Operations, OAL, etc...) in the \"" + ((SystemModel_c)obj).getName() +
		                        "\" model.  These deletions cannot be undone.\n\nAre you sure you " +
		                        "want to cleanse the model and remove this data? ";
	                userOKsUpdate = UIUtil.displayYesNoQuestion(msg);

	                if (!userOKsUpdate) {
	                	logMsg("Model cleanse aborted.");
	                	return;
	                }
					
					cleanModel((SystemModel_c) obj);
					try {
		                NonRootModelElement nrme = (NonRootModelElement)obj;
						PersistableModelComponent pmc = nrme.getPersistableComponent();
						pmc.persistSelfAndChildren();
					} catch (CoreException ce) {
						logMsg("Unable to persist the file." + ce.getMessage());
					}
				}
			}
		}
	}

	public void run(IAction action) {
		run();
	}

    private void cleanModel(final SystemModel_c sys) {

        final IProject project = org.xtuml.bp.io.image.generator.Generator
                .getProject(sys);
        boolean failed = false;
        
        if (project != null) {
            ProgressMonitorDialog pmd = new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
            pmd.setCancelable(true);
            pmd.create();
            IProgressMonitor monitor = pmd.getProgressMonitor();
            
            try {
                IWorkbenchPage page = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage();
                String id = IConsoleConstants.ID_CONSOLE_VIEW;
                IConsoleView view = (IConsoleView) page.showView(id);
                view.display(myConsole);

                pmd.run(true, true, new IRunnableWithProgress() {

                    public void run(IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {

                        int steps = 3;
                        int curStep = 1;

                        monitor.beginTask("Cleanse for Model Compiler", steps);

                        while (curStep <= steps) {
                            if (monitor.isCanceled()) {
                                InterruptedException ie = new InterruptedException("User cancelled the operation");
                                throw ie;
                            }

                            try {
                                switch (curStep) {
                                case 1:
                                    monitor.subTask("Loading model");
                                    PersistableModelComponent pmc = sys.getPersistableComponent();
                                    pmc.loadComponentAndChildren(new NullProgressMonitor());
                                    monitor.worked(1);
                                    break;
                                case 2:
                                    monitor.subTask("Cleaning");
                                    removeUnwantedElements(sys);
                                    monitor.worked(1);
                                    break;
                                case 3:
                                    monitor.subTask("Refreshing");
                                    project.refreshLocal(IResource.DEPTH_INFINITE, null);
                                    monitor.worked(1);
                                    break;
                                }
                            } catch (CoreException e) {
                                RuntimeException re = new RuntimeException(e.getMessage());
                                throw re;
                            } 
                            curStep++;
                        }
                    }
                });

            } catch (CoreException ce) {
                String errMsg = ce.getMessage();
                if ( (errMsg == null) || errMsg.isEmpty() ) {
                    errMsg = "CoreException";
                }
                logMsg("Error.  Model cleansing failed: " + errMsg);
                failed = true;
            } catch (InvocationTargetException ite) {
                String errMsg = ite.getMessage();
                if ( (errMsg == null) || errMsg.isEmpty() ) {
                    errMsg = "InvocationTargetException";
                }
                logMsg("Error.  Model cleansing failed: " + errMsg);
                failed = true;
            } catch (RuntimeException re) {
                String errMsg = re.getMessage();
                if ( (errMsg == null) || errMsg.isEmpty() ) {
                    errMsg = "RuntimeException";
                }
                logMsg("Error.  Model cleansing failed: " + errMsg);
                failed = true;
            } catch (InterruptedException ie) {
                String errMsg = ie.getMessage();
                if ( (errMsg == null) || errMsg.isEmpty() ) {
                    errMsg = "InterruptedException";
                }
                logMsg("Error.  Model cleansing failed: " + errMsg);
                failed = true;
            } finally {
                if (failed) {
                    logMsg("Error.  Model cleansing failed.");
                } else {
                    logMsg("Model cleansing finished successfully.");
                }
                monitor.done();
            }
        }
    }
 
    private void removeUnwantedElements(SystemModel_c sys) {
    	ModelRoot root = Ooaofooa.getInstance("/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/ooaofooa.xtuml");
    	
    	if (root == null) {
    		logMsg("Could not find the ooaofooa model root.  Exiting.");
    		return;
    	}
    	
    	Operation_c[] v_operation = Operation_c.OperationInstances(root);

		for (int i = 0; i < v_operation.length; ++i) {
			logMsg("Removing operation: " + v_operation[i].getName());
			v_operation[i].Dispose();
		}

    	Function_c[] v_function = Function_c.FunctionInstances(root);

		for (int i = 0; i < v_function.length; ++i) {
		    logMsg("Removing function: " + v_function[i].getName());
		    v_function[i].Dispose();
		}

    	DerivedBaseAttribute_c[] v_dba = DerivedBaseAttribute_c.DerivedBaseAttributeInstances(root);

		for (int i = 0; i < v_dba.length; ++i) {
			logMsg("Removing derived base attribute: " + v_dba[i].getName());
			v_dba[i].Dispose();
		}

    	Body_c[] v_body = Body_c.BodyInstances(root);

		for (int i = 0; i < v_body.length; ++i) {
			if (i == 0) {
				logMsg("Removing OAL bodies.");				
			}
			v_body[i].Dispose();
		}

    }

    private MessageConsole findConsole(String name) {
        ConsolePlugin plugin = ConsolePlugin.getDefault();
        IConsoleManager conMan = plugin.getConsoleManager();
        IConsole[] existing = conMan.getConsoles();
        for (int i = 0; i < existing.length; i++) {
            if (name.equals(existing[i].getName())) {
                return (MessageConsole) existing[i];
            }
        }
        // no console found, so create a new one
        MessageConsole myConsole = new MessageConsole(name, null);
        conMan.addConsoles(new IConsole[] { myConsole });
        return myConsole;
    }

    private void logMsg(String msg) {
        try {
            msgbuf.println(msg);
            msgbuf.flush();
        } catch (IOException ioe) {
        }
    }

}