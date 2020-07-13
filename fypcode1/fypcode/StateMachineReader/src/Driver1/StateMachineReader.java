/*This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/package Driver1;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.xmi.UnresolvedReferenceException;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.Vertex;

/***
 * Reads State Machine and Put it into proper java code Structure
 * 
 * @author
 * 
 * 
 * @ClassesStructure is as: ParentClass (Board) BaseLass (BoardState)
 *                   DerivedClass (All Classes extended to BaseClaas)
 * */

public class StateMachineReader {
	ArrayList<StateMachineState> states = new ArrayList<StateMachineState>();
	ArrayList<StateMachineTransition> transitions = new ArrayList<StateMachineTransition>();
	State parentState = null;

	protected JavaCodeStructure javaCodeStructure = new JavaCodeStructure();

	// * Temporary Lists and attributes that are passed to JavaCodeStructure
	// class
	// * for populating proper Code struture
	ClassStructure parentClass;
	ClassStructure baseClass;
	ArrayList<ClassStructure> derClassList;

	public ArrayList<StateMachineState> getStates() {
		return this.states;
	}

	public ArrayList<StateMachineTransition> getTransitions() {
		return this.transitions;
	}

	/***
	 * @param Model
	 * @return proper java code structure object containing all classes and
	 *         hierarichy
	 * @throws UnresolvedReferenceException
	 */
	public JavaCodeStructure processTheModel(Model model)
			throws UnresolvedReferenceException {

		EList<PackageableElement> members = null;
		members = model.getPackagedElements();
		for (PackageableElement element : members) {
			if (element.eClass() == UMLPackage.Literals.CLASS) {
				Class c = (Class) element;
				String parentClassName = c.getLabel();
				parentClass = new ClassStructure(parentClassName, "public",
						new ArrayList<AttributesStructure>(),
						new ArrayList<MethodsStructure>());
				baseClass = new ClassStructure(parentClassName + "State",
						"public", new ArrayList<AttributesStructure>(),
						new ArrayList<MethodsStructure>());
				derClassList = new ArrayList<ClassStructure>();

				EList<Behavior> ownedBehaviors = c.getOwnedBehaviors();
				for (Behavior beh : ownedBehaviors) {
					if (beh.eClass() == UMLPackage.Literals.STATE_MACHINE) {
						// print(beh.getName());
						readBehaviours(beh);
					}
				}
				/*
				 * this.javaCodeStructure.addClassStructures(derClassList,
				 * baseClass,parentClass);
				 * this.javaCodeStructure.addConstructorsToDerivedClass
				 * (parentClass.getClassName());
				 * this.javaCodeStructure.addAttributeToParentClass
				 * (parentClass.getClassName());
				 */

			}
		}
		// System.out.println("Print this.javaCodeStructure.printClasses()");
		// print(this.javaCodeStructure.printClasses());
		return this.javaCodeStructure;
	}

	/**
	 * @param Behavior
	 *            behavior
	 * @functionality reads behavior of state machine region wise
	 */
	private void readBehaviours(Behavior behavior) {

		StateMachine stateMachine = (StateMachine) behavior;
		EList<Region> regions = stateMachine.getRegions();
		for (Region reg : regions) {
			readRegoins(reg);
		}

	}

	/**
	 * 
	 * @param Region
	 *            region
	 * @functionality Explore region vertex by vertex
	 */

	private void readRegoins(Region region) {

		EList<Vertex> vertices = region.getSubvertices();

		for (Vertex vertex : vertices) {
			if (vertex.eClass() == UMLPackage.Literals.STATE) {
				readVertices(vertex);
			}
		}
	}

	private void readSubRegoins(Region region) {

		EList<Vertex> vertices = region.getSubvertices();

		for (Vertex vertex : vertices) {
			if (vertex.eClass() == UMLPackage.Literals.STATE) {
				readSubVertices(vertex);
			}
		}
	}

	private void readSubVertices(Vertex vertex) {

		/** States (Derive Classes) */
		State state = (State) vertex;
		String derivedClassName = state.getLabel(); // Derived class name
		StateMachineState stateLabel = null;
		if (this.parentState != null)
			stateLabel = new StateMachineState(derivedClassName,
					this.parentState.getLabel());
		else
			stateLabel = new StateMachineState(derivedClassName, null);

		// to check for duplicate
		boolean contains = containsState(derivedClassName,
				stateLabel.getParentStateName());
		if (contains == false)
			states.add(stateLabel);

		// -----------------------------------------------------------------------------

		ArrayList<MethodsStructure> methodStruct; // class methods list
		ArrayList<AttributesStructure> paramList; // metods Parameter list

		EList<Transition> outgoingTransitions = state.getOutgoings();

		methodStruct = new ArrayList<MethodsStructure>();
		paramList = new ArrayList<AttributesStructure>();

		for (Transition trans : outgoingTransitions) {
			if (state.eClass() != UMLPackage.Literals.PSEUDOSTATE)
				getTransitions(trans, paramList, methodStruct, derivedClassName);
		}
		derClassList.add(new ClassStructure(derivedClassName, "public", null,
				methodStruct));

	}

	/**
	 * 
	 * @param vertex
	 * @functionality Explore each vertex state by state and extract
	 */
	private void readVertices(Vertex vertex) {

		/** States (Derive Classes) */
		State state = (State) vertex;
		String derivedClassName = state.getLabel(); // Derived class name
		StateMachineState stateLabel = null;
		if (this.parentState != null)
			stateLabel = new StateMachineState(derivedClassName,
					this.parentState.getLabel());
		else
			stateLabel = new StateMachineState(derivedClassName, null);

		// to check for duplicate
		boolean contains = containsState(derivedClassName,
				stateLabel.getParentStateName());
		if (contains == false)
			states.add(stateLabel);

		// Nested Regions
		if (state.getRegions() != null) {
			this.parentState = state;
			EList<Region> regions = state.getRegions();
			for (Region reg : regions) { // parallel regions
				readSubRegoins(reg);
			}
		}
		// when return from <region/>
		// System.out.println("Returned from Nested...");
		this.parentState = null;
		// -----------------------------------------------------------------------------

		ArrayList<MethodsStructure> methodStruct; // class methods list
		ArrayList<AttributesStructure> paramList; // metods Parameter list

		EList<Transition> outgoingTransitions = state.getOutgoings();

		methodStruct = new ArrayList<MethodsStructure>();
		paramList = new ArrayList<AttributesStructure>();

		for (Transition trans : outgoingTransitions) {
			if (state.eClass() != UMLPackage.Literals.PSEUDOSTATE)
				getTransitions(trans, paramList, methodStruct, derivedClassName);
		}
		derClassList.add(new ClassStructure(derivedClassName, "public", null,
				methodStruct));

	}

	public boolean containsState(String derivedClassName, String parentStateName) {
		boolean contains = false;
		for (int i = 0; i < states.size(); i++) {
			StateMachineState element = states.get(i);
			if (element.getStateName().equalsIgnoreCase(derivedClassName)
					&& element.getParentStateName().equalsIgnoreCase(
							parentStateName)) {
				contains = true;
			}
		}
		return contains;
	}

	/**
	 * 
	 * @param transition
	 * @param paramList
	 * @param methodStruct
	 * @param derivedClassName
	 * 
	 * @functionality handles guards condition ( conditional expressions ) and
	 *                bodies
	 */
	private void getTransitions(Transition transition,
			ArrayList<AttributesStructure> paramList,
			ArrayList<MethodsStructure> methodStruct, String derivedClassName) {
		String methodBody = null;
		Operation operation = null;

		/****** guard condition **********/
		String condition = "";
		EList<Constraint> ownedRules = transition.getOwnedRules();
		for (Constraint Rule : ownedRules) {
			ValueSpecification Specifications = Rule.getSpecification();
			OpaqueExpression expr = (OpaqueExpression) Specifications;

			condition += expr.getBodies().toString();

		}
		// System.out.println("StateMachineReader.getTransitions() : condition: "+
		// condition );
		/*********** conditional body ***********/
		if ((OpaqueBehavior) transition.getEffect() != null) {
			methodBody = "";
			methodBody += ("\nif ( " + removeSquareBrackets(condition) + " ){\n");
			methodBody += removeSquareBrackets(((OpaqueBehavior) transition
					.getEffect()).getBodies().toString()) + "}\n";

		}

		// System.out.println("StateMachineReader.getTransitions() : methodBody :"+methodBody);

		CallEvent callEvent = null;
		/**
		 * TimeEvent timeEvent=null; ChangeEvent changeEvent = null;
		 */
		EList<Trigger> trigger = transition.getTriggers();

		StateMachineTransition trans = null;
		/*if (transition.getEffect() != null) {
			if (this.parentState != null)
				trans = new StateMachineTransition(transition.getEffect()
						.getName(), transition.getSource().getName(),
						transition.getTarget().getName(),
						this.parentState.getLabel());
			else
				trans = new StateMachineTransition(transition.getEffect()
						.getName(), transition.getSource().getName(),
						transition.getTarget().getName(), null);
			transitions.add(trans);
		} else*/ if (transition.getLabel() != null) {
			if (this.parentState != null)
			{
				trans = new StateMachineTransition(transition.getLabel(),
						transition.getSource().getName(), transition
								.getTarget().getName(),
						this.parentState.getLabel());
			}
			else
			{
				trans = new StateMachineTransition(transition.getLabel(),
						transition.getSource().getName(), transition
								.getTarget().getName(), null);
			}
			transitions.add(trans);
		}
		else if (transition.getEffect() != null) {
			if (this.parentState != null)
			{
				trans = new StateMachineTransition(transition.getEffect()
						.getName(), transition.getSource().getName(),
						transition.getTarget().getName(),
						this.parentState.getLabel());
			}
			else
			{
				trans = new StateMachineTransition(transition.getEffect()
						.getName(), transition.getSource().getName(),
						transition.getTarget().getName(), null);
			}
			transitions.add(trans);
		}
		// for (Trigger triger : trigger) {
		// paramList = new ArrayList<AttributesStructure>();
		// System.out.println("transition.getTriggers(): paramList");
		// /**** handles class method and bodies****/
		// if(triger.getEvent().getName().contains("CallEvent")){
		// callEvent = (CallEvent) (triger.getEvent());
		// operation = callEvent.getOperation();
		// if(operation != null){
		//
		// /** method parameters */
		// EList<Parameter> parameters = operation.getOwnedParameters();
		//
		// for (Parameter param : parameters){
		// if(!paramList.contains(param))
		// paramList.add(new AttributesStructure(param.getName(), "double",
		// ""));
		// }
		//
		// String objectName = this.baseClass.getClassName().toLowerCase();
		// String parentMethodBody = objectName +
		// "."+operation.getName()+"();\n"
		// + ((transition.getTarget().getName() == null) ? "":objectName +
		// " = new "+transition.getTarget().getName().replaceAll(" ",
		// "")+"(this "+/*paramList+*/");");
		//
		// /** add method to class*/
		// MethodsStructure parentMethodToAdd = new
		// MethodsStructure(operation.getName(),"void", "public",
		// paramList,parentMethodBody);
		//
		//
		// if(transition.getEffect()!=null)
		// {
		// StateMachineTransition trans=new
		// StateMachineTransition(transition.getEffect().getName(),
		// transition.getSource().getName(), transition.getTarget().getName());
		// //
		// // System.out.println("transition.getName() :"+
		// transition.getEffect().getName());
		// //
		// System.out.println("transition.getTarget().getName() :"+transition.getTarget().getName());
		// //
		// System.out.println("transition.getSource().getName() :"+transition.getSource().getName());
		//
		// transitions.add(trans);
		// }
		//
		// if(methodBody != null){
		// MethodsStructure baseMethodToAdd = new
		// MethodsStructure(operation.getName(),"void", "public",
		// paramList,methodBody);
		// if(notAlreadyExist(operation)){
		// baseClass.addMethods(baseMethodToAdd);
		// }
		// if(notAlreadyExist(methodStruct,baseMethodToAdd))
		// methodStruct.add(baseMethodToAdd);
		// /** handles when multiple conditional expressions if else, if else
		// ...*
		// * It appends their body with another if else
		// **/
		// else{
		// this.javaCodeStructure.appendBody(derivedClassName,
		// baseMethodToAdd.getmethodName(), methodBody);
		// }
		// }
		// else{
		// MethodsStructure baseMethodToAdd = new
		// MethodsStructure(operation.getName(),"void", "public",
		// paramList," ");
		//
		// /**
		// * if method same name method already added to class then skip it
		// */
		// if(notAlreadyExist(operation)){
		// baseClass.addMethods(baseMethodToAdd);
		// }
		// methodStruct.add(baseMethodToAdd);
		// }
		// /**
		// * add methods to parent class
		// */
		// parentClass.addMethods(parentMethodToAdd);
		//
		// }
		//
		// else if(triger.getEvent().getName().contains("TimeEvent")){
		// /** not handled **/
		// }
		//
		// else if(triger.getEvent().getName().contains("ChangeEvent")){
		// /** not handled **/
		// }
		// else{}
		// }
		// }
	}

	// **************** Other Useful methods ******************
	/**
	 * 
	 * @param String
	 *            string
	 * @return remove square brackets [] and return
	 */
	public String removeSquareBrackets(String myString) {
		// print("remove braces : "+myString);
		if (myString.equals(""))
			return myString;
		return myString.substring(1, myString.length() - 1);
	}

	/**
	 * 
	 * @param operation
	 * @return booleab
	 * @functionality if operation ()method already added to class return false
	 *                else return true
	 */
	public boolean notAlreadyExist(Operation operation) {
		for (MethodsStructure ms : baseClass.getClassMethods())
			if (ms.getmethodName().equals(operation.getName()))
				return false;
		return true;
	}

	/**
	 * 
	 * @param operation
	 * @return boolean
	 * @functionality if operation ()method already added to class return false
	 *                else return true
	 */
	public boolean notAlreadyExist(ArrayList<MethodsStructure> methodStruct,
			MethodsStructure var) {
		for (MethodsStructure ms : methodStruct)
			if (ms.getmethodName().equals(var.getmethodName()))
				return false;

		return true;

	}

	/**
	 * 
	 * @param String
	 * @functionality print string on console
	 */
	public void print(String data) {
		System.out.println(data);
	}
	// /*********************************************/
}
