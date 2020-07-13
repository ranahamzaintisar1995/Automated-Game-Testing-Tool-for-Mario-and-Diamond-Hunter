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
*/
package Driver1;

public class StateMachineTransition {

	private String funcName;
	private String sourceState, targetState;
	private Integer priorityScore;
	private String parentStateName;

	public StateMachineTransition(String funcName, String sourceState,
			String targetState, String parentStateName) {
		this.funcName = funcName;
		this.sourceState = sourceState;
		this.targetState = targetState;
		this.parentStateName=parentStateName;
	}

	public void setPriorityScore(Integer score) {
		this.priorityScore=score;
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
	public void print() {
		System.out.println("ParentState: "+ parentStateName+" Name: " + funcName + "  Source_State: "
				+ sourceState + "  TargetState: " + targetState);
	}
}
