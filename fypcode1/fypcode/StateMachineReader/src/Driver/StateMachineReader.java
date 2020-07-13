package Driver;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.xmi.UnresolvedReferenceException;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.Package;

/***
 * Reads State Machine and Put it into proper java code Structure
 * 
 * @author Osama
 * 
 * 
 * @ClassesStructure is as: ParentClass (Board) BaseLass (BoardState)
 *                   DerivedClass (All Classes extended to BaseClaas)
 * */

public class StateMachineReader {
	ArrayList<StateMachineState> states = new ArrayList<StateMachineState>();
	ArrayList<StateMachineTransition> transitions = new ArrayList<StateMachineTransition>();
	State parentState = null;
	int nestedRegNo=0;
	
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
	public JavaCodeStructure processTheModel(Object objModel)
			throws UnresolvedReferenceException {
		EList<PackageableElement> members = null;
		  if (objModel instanceof Model) {
		   Model sourceModel = (Model) objModel;
		   members = sourceModel.getPackagedElements();
		  } else if (objModel instanceof Package) {
			  Package sourcePackage = (Package) objModel;
		   members = sourcePackage.getPackagedElements();
		  }
		
		
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
						readBehaviours(beh);
					}
				}
			}
			else if (element.eClass() == UMLPackage.Literals.STATE_MACHINE) {
				StateMachine sm = (StateMachine) element;
			//	System.err.println("StateMachine"+sm.getName());
				derClassList = new ArrayList<ClassStructure>();
				readBehaviours(sm);
//				EList<Region>regions = sm.getRegions();
//				for(Region r: regions){
//					System.err.println("Region: "+r.getName());
//					EList<Transition> transitions = r.getTransitions();
//					for(Transition t: transitions){
//						System.out.println(t);
//					}
//				}
			}
			
		}
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
	private void readBehaviours(StateMachine sm) {

		EList<Region> regions = sm.getRegions();
		for (Region reg : regions) {
			//			readRegoins(reg);
			for(Vertex v: reg.getSubvertices()){
				readSubVertices(v);
			}
//			EList<Transition> transitions = reg.getTransitions();
//			for(Transition t: transitions){
//				System.out.println(t);
//			}
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
			readVertices(vertex);
		}
	}

	private void readSubRegoins(Region region) {

		EList<Vertex> vertices = region.getSubvertices();
		for (Vertex vertex : vertices) {
			readSubVertices(vertex);
		}
	}

	private void readSubVertices(Vertex vertex) {

		/** States (Derive Classes) */
		State state=null;
		Boolean isPseudostate=false;
		Pseudostate pseudostate=null;
		String derivedClassName=null;
		if(vertex.eClass() == UMLPackage.Literals.STATE)
		{
			isPseudostate=false;
			state = (State) vertex;
			derivedClassName= state.getLabel(); // Derived class name
		}
		else if (vertex.eClass() == UMLPackage.Literals.PSEUDOSTATE)
		{
			isPseudostate=true;
			pseudostate= (Pseudostate) vertex;
			if (pseudostate.getKind().getLiteral().equalsIgnoreCase("choice"))
			{
				derivedClassName= pseudostate.getKind().getLiteral(); // Derived class name
			}
			else return;
		}
		else
			return;
		//------------- Add in States Array List-----------
		StateMachineState stateLabel = null;
		stateLabel = new StateMachineState(derivedClassName, this.parentState != null ? this.parentState.getLabel() : null);

		boolean contains = containsStateAlready(derivedClassName,
				stateLabel.getParentStateName());
		if (contains == false)
			states.add(stateLabel);

		ArrayList<MethodsStructure> methodStruct; // class methods list
		ArrayList<AttributesStructure> paramList; // methods Parameter list
		
		EList<Transition> outgoingTransitions;
		outgoingTransitions = getOutgoingTransitions(state, isPseudostate, pseudostate);
		methodStruct = new ArrayList<MethodsStructure>();
		paramList = new ArrayList<AttributesStructure>();

		for (Transition trans : outgoingTransitions) {
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
		State state=null;
		Boolean isPseudostate=false;
		Pseudostate pseudostate=null;
		String derivedClassName=null;
		if(vertex.eClass() == UMLPackage.Literals.STATE)
		{
			isPseudostate=false;
			state = (State) vertex;
			derivedClassName= state.getLabel();
		}
		else if (vertex.eClass() == UMLPackage.Literals.PSEUDOSTATE)
		{
			isPseudostate=true;
			pseudostate= (Pseudostate) vertex;
			if (pseudostate.getKind().getLiteral().equalsIgnoreCase("choice"))
			{
				derivedClassName= pseudostate.getKind().getLiteral();
			}
			else return;
		}
		else
			return;
		//------------- Add in States Array List-----------
		StateMachineState stateLabel = null;
		stateLabel = new StateMachineState(derivedClassName, this.parentState != null ? this.parentState.getLabel() : null);

		boolean contains = containsStateAlready(derivedClassName,
				stateLabel.getParentStateName());
		if (contains == false)
			states.add(stateLabel);
		//--------------------------------------------------
		
		// Nested Regions
		if (isPseudostate==false && state.getRegions() != null) {
			this.parentState = state;
			EList<Region> regions = state.getRegions();
			for (Region reg : regions) { // parallel regions
				this.nestedRegNo++;
				readSubRegoins(reg);
			}this.nestedRegNo=0;
		}
		//return from Nested <region/>
		this.parentState = null;
		// -----------------------------------------------------------------------------

		ArrayList<MethodsStructure> methodStruct; // class methods list
		ArrayList<AttributesStructure> paramList; // methods Parameter list

		EList<Transition> outgoingTransitions;
		outgoingTransitions = getOutgoingTransitions(state, isPseudostate, pseudostate);
		methodStruct = new ArrayList<MethodsStructure>();
		paramList = new ArrayList<AttributesStructure>();

		for (Transition trans : outgoingTransitions) {
				getTransitions(trans, paramList, methodStruct, derivedClassName);
		}
		
		derClassList.add(new ClassStructure(derivedClassName, "public", null,
				methodStruct));

	}

	public EList<Transition> getOutgoingTransitions(State state,
			Boolean isPseudostate, Pseudostate pseudostate) {
		EList<Transition> outgoingTransitions;
		if(isPseudostate==false)
			outgoingTransitions= state.getOutgoings();
		else
			outgoingTransitions = pseudostate.getOutgoings();
		return outgoingTransitions;
	}

	public boolean containsStateAlready(String derivedClassName, String parentStateName) {
		boolean contains = false;
		for (int i = 0; i < states.size(); i++) {
			StateMachineState element = states.get(i);
			if (derivedClassName.equalsIgnoreCase(element.getStateName()) && ((parentStateName==null && element.getParentStateName()==null) || parentStateName.equalsIgnoreCase(element.getParentStateName())))
			{
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
		
		/****** guard condition **********/
		String condition = "";
		EList<Constraint> ownedRules = transition.getOwnedRules();
		for (Constraint Rule : ownedRules) {
			ValueSpecification Specifications = Rule.getSpecification();
			OpaqueExpression expr = (OpaqueExpression) Specifications;

			condition += expr.getBodies().toString();

		}

		StateMachineTransition trans = null;
		String targetState,sourceState;
		targetState = getStateName((Vertex)transition.getTarget());
		sourceState = getStateName((Vertex)transition.getSource());
		//-------------------
		if (transition.getLabel() != null) {
			
				trans = new StateMachineTransition(transition.getLabel(),
						sourceState, targetState,
						this.parentState != null ? this.parentState.getLabel() : null , condition!=null ? condition : null, "Region"+this.nestedRegNo);
			
			transitions.add(trans);
		}
		else if (transition.getEffect() != null) {
			
				trans = new StateMachineTransition(transition.getEffect()
						.getName(), sourceState,
						targetState,
						this.parentState != null ? this.parentState.getLabel() : null, condition!=null ? condition : null, "Region"+this.nestedRegNo);
			
			transitions.add(trans);
		}
	}

	public String getStateName(Vertex vertex) {
		String stateName;
		if( vertex.eClass() == UMLPackage.Literals.PSEUDOSTATE)
		{
			stateName=((Pseudostate)vertex).getKind().getLiteral();
		}
		else
			stateName=((State)vertex).getName();
		return stateName;
	}

	// **************** Other Useful methods ******************

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
