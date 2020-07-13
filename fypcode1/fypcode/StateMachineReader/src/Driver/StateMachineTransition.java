package Driver;

public class StateMachineTransition {

	private String funcName;
	private String sourceState, targetState;
	private Integer priorityScore;
	private String parentStateName;
	private String gaurdCondition;
	private String regNo;
	public StateMachineTransition(){}
	public StateMachineTransition(String funcName, String sourceState,
			String targetState, String parentStateName, String gaurdCondition, String regNo) {
		this.funcName = funcName;
		this.sourceState = sourceState;
		this.targetState = targetState;
		this.parentStateName=parentStateName;
		this.regNo=regNo;
		this.gaurdCondition=gaurdCondition;
	}
	
	
	
	public String getGaurdCondition() {
		return this.gaurdCondition;
	}
	public Integer getPriorityScore() {
		return this.priorityScore;
	}
	
	public String getParentStateName()
	{
		return this.parentStateName;
	}
	
	public String getTransitionName()
	{
		return this.funcName;
	}
	
	public String getSourceState() {
		return this.sourceState;
	}
	
	public String getTargetState() {
		return this.targetState;
	}
	
	public String getRegionNo()
	{
		return this.regNo;
	}
	
	
	public void print() {
		System.out.println("ParentState: "+ parentStateName+" Name: " + funcName + "  Source_State: "
				+ sourceState + "  TargetState: " + targetState + " Region: "+ regNo);
	}
}
