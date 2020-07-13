package Driver;

import java.util.ArrayList;

public class StateMachineRegion {

	ArrayList<StateMachineTransition> transitionsInRegion= new ArrayList<>();
	public void addTransition(StateMachineTransition transition)
	{
		transitionsInRegion.add(transition);
	}
	public ArrayList<StateMachineTransition> getTransitions() {
		return this.transitionsInRegion;
	}
	public void print() {
		for (StateMachineTransition transition : transitionsInRegion) {
			transition.print();
		}System.out.println("");
	}
}
