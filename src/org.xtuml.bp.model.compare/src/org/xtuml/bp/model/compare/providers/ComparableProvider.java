package org.xtuml.bp.model.compare.providers;
//=====================================================================
//
//File:      $RCSfile: ComparableProvider.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/05/10 13:26:04 $
//
//(c) Copyright 2013-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BridgeMessage_c;
import org.xtuml.bp.core.CantHappen_c;
import org.xtuml.bp.core.ClassAsAssociatedOneSide_c;
import org.xtuml.bp.core.ClassAsAssociatedOtherSide_c;
import org.xtuml.bp.core.ClassAsSimpleFormalizer_c;
import org.xtuml.bp.core.ClassAsSimpleParticipant_c;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.EventIgnored_c;
import org.xtuml.bp.core.EventMessage_c;
import org.xtuml.bp.core.FunctionMessage_c;
import org.xtuml.bp.core.InformalAsynchronousMessage_c;
import org.xtuml.bp.core.InformalSynchronousMessage_c;
import org.xtuml.bp.core.InterfaceOperationMessage_c;
import org.xtuml.bp.core.NewBaseAttribute_c;
import org.xtuml.bp.core.OperationMessage_c;
import org.xtuml.bp.core.PolymorphicEvent_c;
import org.xtuml.bp.core.SemEvent_c;
import org.xtuml.bp.core.SignalMessage_c;
import org.xtuml.bp.core.StateEventMatrixEntry_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.inspector.ObjectElement;
import org.xtuml.bp.model.compare.ComparableTreeObject;
import org.xtuml.bp.model.compare.providers.custom.ActionComparable;
import org.xtuml.bp.model.compare.providers.custom.AssignedEventComparable;
import org.xtuml.bp.model.compare.providers.custom.AssociationComparable;
import org.xtuml.bp.model.compare.providers.custom.AssociationSubtypeComparable;
import org.xtuml.bp.model.compare.providers.custom.AttributeComparable;
import org.xtuml.bp.model.compare.providers.custom.DerivedBaseAttributeComparable;
import org.xtuml.bp.model.compare.providers.custom.EventMatrixComparable;
import org.xtuml.bp.model.compare.providers.custom.GraphicalDataComparable;
import org.xtuml.bp.model.compare.providers.custom.MessageComparable;
import org.xtuml.bp.model.compare.providers.custom.NewBaseAttributeComparable;
import org.xtuml.bp.model.compare.providers.custom.PolymorphicEventComparable;
import org.xtuml.bp.model.compare.providers.custom.SemEventComparable;
import org.xtuml.bp.model.compare.providers.custom.SemeComparable;
import org.xtuml.bp.model.compare.providers.custom.TransitionComparable;
import org.xtuml.bp.ui.canvas.Model_c;

public class ComparableProvider {

	public static ComparableTreeObject getComparableTreeObject(Object element) {
		if(element instanceof NonRootModelElement) {
			return getNonRootModelElementComparable(element);
		} else if(element instanceof ObjectElement) {
			return getObjectElementComparable(element);
		} else if(element instanceof ComparableTreeObject) {
			return (ComparableTreeObject) element;
		}
		return null;
	}

	private static ComparableTreeObject getObjectElementComparable(
			Object element) {
		ObjectElement objEle = (ObjectElement) element;
		if(objEle.getParent() instanceof Transition_c) {
			if (objEle.getName().startsWith("referential_Assigned")) { //$NON-NLS-1$
				return new AssignedEventComparable(objEle);
			}
		}
		return new ObjectElementComparable((ObjectElement) element);
	}

	private static ComparableTreeObject getNonRootModelElementComparable(
			Object element) {
		if (element instanceof Association_c) {
			return new AssociationComparable((NonRootModelElement) element);
		}
		if (element instanceof Attribute_c) {
			return new AttributeComparable((NonRootModelElement) element);
		}
		if (element instanceof Model_c) {
			return new GraphicalDataComparable((Model_c) element);
		}
		if (element instanceof SemEvent_c) {
			return new SemEventComparable((SemEvent_c) element);
		}
		if (element instanceof PolymorphicEvent_c) {
			return new PolymorphicEventComparable((PolymorphicEvent_c) element);
		}
		if (element instanceof DerivedBaseAttribute_c) {
			return new DerivedBaseAttributeComparable(
					(DerivedBaseAttribute_c) element);
		}
		if (element instanceof NewBaseAttribute_c) {
			return new NewBaseAttributeComparable((NewBaseAttribute_c) element);
		}
		if (element instanceof FunctionMessage_c
				|| element instanceof OperationMessage_c
				|| element instanceof EventMessage_c
				|| element instanceof SignalMessage_c
				|| element instanceof InterfaceOperationMessage_c
				|| element instanceof BridgeMessage_c
				|| element instanceof InformalSynchronousMessage_c
				|| element instanceof InformalAsynchronousMessage_c) {
			return new MessageComparable((NonRootModelElement) element);
		}
		if (element instanceof ClassAsSimpleParticipant_c
				|| element instanceof ClassAsSimpleFormalizer_c
				|| element instanceof ClassAsAssociatedOneSide_c
				|| element instanceof ClassAsAssociatedOtherSide_c) {
			return new AssociationSubtypeComparable((NonRootModelElement) element);
		}
		if (element instanceof CantHappen_c
				|| element instanceof EventIgnored_c) {
			return new EventMatrixComparable((NonRootModelElement) element);
		}
		if(element instanceof Transition_c) {
			return new TransitionComparable((NonRootModelElement) element);
		}
		if(element instanceof Action_c) {
			return new ActionComparable((NonRootModelElement) element);
		}
		if(element instanceof StateEventMatrixEntry_c) {
			return new SemeComparable((NonRootModelElement) element);
		}
		return new NonRootModelElementComparable((NonRootModelElement) element);
	}
	
}
