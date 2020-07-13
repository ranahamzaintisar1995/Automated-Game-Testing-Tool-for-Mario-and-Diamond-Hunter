package Driver;

public class StateMachineState {

	private String name;
	private String parentStateName;
	
	public StateMachineState(String name, String parentStateName) {
		this.name = name;
		this.parentStateName=parentStateName;
	}

	public void print() {
		System.out.println("StateName: " + name+ " Parent: "+ parentStateName);
	}
	
	public String getStateName() {
		return this.name;
	}
	
	public String getParentStateName() {
		return this.parentStateName;
	}
}
