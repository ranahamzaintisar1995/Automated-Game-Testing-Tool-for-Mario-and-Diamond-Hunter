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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TestCase {
	
	//ArrayList<String> testPaths=new ArrayList<String>();
	ArrayList<TestPath> paths=new ArrayList<TestPath>();
	ArrayList<TestPath> prefferedTestPaths=new ArrayList<TestPath>();
	
	/**
	 * 
	 * @param path
	 * @param target
	 * @return
	 */
	public Boolean isTargetDuplicateInPath(TestPath path, String target) {
		Boolean isduplicate=false;
		ArrayList<StateMachineTransition> pathTransitions=new ArrayList<StateMachineTransition>();
		pathTransitions=path.getStateMachineTransitions();
		if(target!=null)
		{
			for (StateMachineTransition stateMachineTransition : pathTransitions) {
				
				if((stateMachineTransition.getTargetState()!=null && target.equalsIgnoreCase(stateMachineTransition.getTargetState()))
						|| (stateMachineTransition.getSourceState()!=null && target.equalsIgnoreCase(stateMachineTransition.getSourceState())))
					isduplicate=true;
			}
		}
		return isduplicate;
	}
	
	/**
	 * 
	 * @param transitions
	 * @param targetState
	 * @param transitionName
	 * @param testPath
	 * @return
	 */
	
	
	public Boolean generateTestPaths(ArrayList<StateMachineTransition> transitions, String targetState, String transitionName, TestPath testPath) {
		Boolean foundTerminal=false;
		if(testPath.getStateMachineTransitions().size()>0)
		{
			int visitedIndex=0;
			
			for (StateMachineTransition stateMachineTransition : transitions) {
				if(stateMachineTransition.getSourceState().equalsIgnoreCase(targetState) && !isTargetDuplicateInPath(testPath, stateMachineTransition.getTargetState()))
				{foundTerminal=true;
				
					ArrayList<StateMachineTransition> nonVisitedTransitions = getNonVisitedTransitions(transitions, visitedIndex);
					//----------------------------------------
					TestPath ttPath = copyPreviousTestPath(testPath);
					ttPath.addNextStateMachineTransition(stateMachineTransition);
					//----------------------------------------
					foundTerminal=generateTestPaths(nonVisitedTransitions, stateMachineTransition.getTargetState(), stateMachineTransition.getTransitionName(), ttPath);
				}
				visitedIndex++;
			}
			if(!foundTerminal)
				{
					paths.add(testPath);
					foundTerminal=true;
				}
		}
		
		else
		{
			String startPathFromState=transitions.get(0).getSourceState();
			for (int i = 0; i < transitions.size(); i++) {
				if (transitions.get(i).getSourceState().equalsIgnoreCase(startPathFromState)) {
					testPath=new TestPath();
					testPath.addNextStateMachineTransition(transitions.get(i));
					
					foundTerminal=true;
					foundTerminal=generateTestPaths(transitions, transitions.get(i).getTargetState(), transitions.get(i).getTransitionName(), testPath);
				}
			}		
		}
		return foundTerminal;
	}


	/**
	 * 
	 * @param tPath
	 * @return
	 */
	public TestPath copyPreviousTestPath(TestPath tPath) {
		TestPath ttPath=new TestPath();
		for (StateMachineTransition stateMachineTransition2 : tPath.getStateMachineTransitions()) {
			ttPath.addNextStateMachineTransition(stateMachineTransition2);
		}
		return ttPath;
	}


	/**
	 * 
	 * @param transitions
	 * @param visitedIndex
	 * @return
	 */
	public ArrayList<StateMachineTransition> getNonVisitedTransitions(
			ArrayList<StateMachineTransition> transitions, int visitedIndex) {
		ArrayList<StateMachineTransition> nonVisitedTransitions=new ArrayList<StateMachineTransition>();
		for (StateMachineTransition sMTransition : transitions) {
			nonVisitedTransitions.add(sMTransition);
		}
		nonVisitedTransitions.remove(visitedIndex);
		return nonVisitedTransitions;
	}
	
	/**
	 * 
	 */
	public void printTestPaths() {
		Integer i=0;
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("TestCases.txt");
		for (TestPath tPath : paths) {
			//System.out.print(++i +":: ");
			String path  = tPath.print();
			//System.out.println(path);
			writer.println(path);
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getTestPathsMaxScore() {
		Integer maxScore=0;
		for (TestPath testPath : paths) {
			if(testPath.getScore()>=maxScore)
			{
				maxScore=testPath.getScore();
			}
		}
		return maxScore;
	}
	
	/**
	 * 
	 * @param score
	 * @return
	 */
	public ArrayList<TestPath> getTestPathsWithScore(Integer score) {
		for (TestPath testPath : paths) {
			if (testPath.getScore()==score) {
				this.prefferedTestPaths.add(testPath);
			}
		}
		return this.prefferedTestPaths;
	}
	
	/**
	 * 
	 * @param transitions
	 * @param toStartFromIndex
	 */
	public void generateTestCase(ArrayList<StateMachineTransition> transitions, int toStartFromIndex) {
		
		//boolean needToChangeTargetState=false;
		String sourceState=transitions.get(toStartFromIndex).getSourceState();
		String targetState=transitions.get(toStartFromIndex).getTargetState();
		String transitionName=transitions.get(toStartFromIndex).getTransitionName();
		
		System.out.print("\nState :"+ sourceState +
				", Transition :"+ transitionName +
				", State :" + targetState);
		
		//transition ---target--> STATE <--source-- transition
		for (int i = toStartFromIndex+1; i < transitions.size(); i++) {
			
			if(transitions.get(i).getSourceState().equalsIgnoreCase(targetState))
			{
				sourceState=transitions.get(i).getSourceState();
				targetState=transitions.get(i).getTargetState();
				transitionName=transitions.get(i).getTransitionName();
				
				System.out.print(", State :"+ sourceState +
						", Transition :"+ transitionName +
						", State :" + targetState);
				
				//needToChangeTargetState=true;
			}
		}
		
//		if(needToChangeTargetState==false)// No path(Transition) further-->Change Transition
//		{
//			for (int i = toStartFromIndex; i < transitions.size(); i++) {
//				if(transitions.get(i).getSourceState().equalsIgnoreCase(sourceState) && !transitions.get(i).getTransitionName().equalsIgnoreCase(transitionName))
//				{
//					targetState=transitions.get(i).getTargetState();
//					System.out.print("\nS :"+ sourceState +
//							"T :"+ transitions.get(0).getTransitionName() +
//							"S :" + targetState);
//					transitionName=transitions.get(i).getTransitionName();
//					needToChangeTargetState=true;
//				}
//			}
//		}
	}
	
	/**
	 * 
	 * @param transitions
	 * @return
	 */
	public ArrayList<StateMachineTransition> getPathReadyTransitions(
			ArrayList<StateMachineTransition> transitions) {
		ArrayList<StateMachineTransition> modifiedTransitions = new ArrayList<StateMachineTransition>();
		copyTransitions(modifiedTransitions, transitions);
		
		for (StateMachineTransition parentStateTransition : transitions) {
			if(parentStateTransition.getTargetState()!=null && isTarget_AParentOfAnyState(parentStateTransition.getTargetState(),transitions))
			{
				ArrayList<StateMachineTransition> nestedTransitions=getStateNestedTransitions(parentStateTransition.getTargetState(), transitions);
				//==========================
				ArrayList<StateMachineTransition> parentExitTransitions=getParentStateExitTransitions(transitions,parentStateTransition.getTargetState());
				addParentExitTransitions(modifiedTransitions, nestedTransitions, parentExitTransitions);
				//==========================
				if(parentStateTransition.getTargetState()!=parentStateTransition.getSourceState())
					addParentEntryTransitions(nestedTransitions, modifiedTransitions, parentStateTransition);				
			}
		}
		modifiedTransitions=deleteDuplicateTransition(modifiedTransitions);
		return modifiedTransitions;
	}
	
	/**
	 * 
	 * @param modifiedTransitions
	 * @param nestedTransitions
	 * @param parentExitTransitions
	 */
	public void addParentExitTransitions(ArrayList<StateMachineTransition> modifiedTransitions, ArrayList<StateMachineTransition> nestedTransitions, ArrayList<StateMachineTransition> parentExitTransitions) {
		//select Nested States
		for (StateMachineTransition stateMachineTransition : nestedTransitions) {
			String nestedState=stateMachineTransition.getSourceState();
			for (StateMachineTransition exitTransition : parentExitTransitions) {
				StateMachineTransition newTransition= new StateMachineTransition(exitTransition.getTransitionName(), nestedState, exitTransition.getTargetState(), null);
				if(!containsTransition(newTransition, modifiedTransitions))
					modifiedTransitions.add(newTransition);
			}
		}
	}
	/**
	 * 
	 * @param transitions
	 * @param parentStateName
	 * @return
	 */
	public ArrayList<StateMachineTransition> getParentStateExitTransitions(ArrayList<StateMachineTransition> transitions, String parentStateName) {
		ArrayList<StateMachineTransition> exitTransitions=new ArrayList<StateMachineTransition>();
		for (StateMachineTransition stateMachineTransition : transitions) {
			if(stateMachineTransition.getSourceState()!=null && stateMachineTransition.getSourceState().equalsIgnoreCase(parentStateName))
			{
				if(stateMachineTransition.getSourceState()!=stateMachineTransition.getTargetState())
					exitTransitions.add(stateMachineTransition);
			}
		}
		return exitTransitions;
	}
	
	/**
	 * 
	 * @param stateName
	 * @param tranitions
	 * @return
	 */
	public Boolean isTarget_AParentOfAnyState(String stateName, ArrayList<StateMachineTransition> tranitions) {
		for (StateMachineTransition stateMachineTransition : tranitions) {
			if(stateMachineTransition.getParentStateName()!=null && stateMachineTransition.getParentStateName().equalsIgnoreCase(stateName))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param stateName
	 * @param tranitions
	 * @return
	 */
	public ArrayList<StateMachineTransition> getStateNestedTransitions(String stateName, ArrayList<StateMachineTransition> tranitions) {
		ArrayList<StateMachineTransition> nestedTransitions=new ArrayList<StateMachineTransition>();
		
		for (StateMachineTransition stateMachineTransition : tranitions) {
			if(stateMachineTransition.getParentStateName()!=null && stateMachineTransition.getParentStateName().equalsIgnoreCase(stateName))
			{
				nestedTransitions.add(stateMachineTransition);
			}
		}
		return nestedTransitions;
	}
	
	public ArrayList<StateMachineTransition> deleteDuplicateTransition(ArrayList<StateMachineTransition> modifedTransitions) {
		ArrayList<StateMachineTransition> uniqueTransitions=new ArrayList<StateMachineTransition>();
		for (int i = 0; i < modifedTransitions.size(); i++) {
			Boolean containsDuplicate=false;
			for (int j = i+1; j < modifedTransitions.size()-(i+1); j++) {
				if(modifedTransitions.get(i).getTransitionName()!=null && modifedTransitions.get(j).getTransitionName().equalsIgnoreCase(modifedTransitions.get(i).getTransitionName()) &&
						(modifedTransitions.get(i).getSourceState()!=null && modifedTransitions.get(j).getSourceState().equalsIgnoreCase(modifedTransitions.get(i).getSourceState())) &&
						(modifedTransitions.get(i).getTargetState()!=null && modifedTransitions.get(j).getTargetState().equalsIgnoreCase(modifedTransitions.get(i).getTargetState())))
				{
					containsDuplicate=true;
				}
			}
			if (!containsDuplicate) {
				uniqueTransitions.add(modifedTransitions.get(i));
			}
		}
		return uniqueTransitions;	
	}
	
	public void addParentEntryTransitions(ArrayList<StateMachineTransition> nestedTransitions, ArrayList<StateMachineTransition> modifiedTransitions, StateMachineTransition transitionToDuplicate) {
		
		String state=null;
		for (StateMachineTransition stateMachineTransition : nestedTransitions) {
			Boolean isNewTarget=true;
			state=stateMachineTransition.getSourceState();
			for (StateMachineTransition stateMachineTransition2 : nestedTransitions) {
				if(stateMachineTransition2.getTargetState()!=null && stateMachineTransition2.getTargetState().equalsIgnoreCase(state))	//check for self transition
				{
					if(stateMachineTransition2.getTargetState()!=stateMachineTransition2.getSourceState())
						isNewTarget=false;
				}
			}
			if(isNewTarget)
			{
				StateMachineTransition newTransition= new StateMachineTransition(transitionToDuplicate.getTransitionName(), transitionToDuplicate.getTargetState(), state, null);
				if (!containsTransition(newTransition, modifiedTransitions)) {
					modifiedTransitions.add(newTransition);
					//deleteTransition(stateMachineTransition, modifiedTransitions);
				}
			}
			
		}
	}

	public Boolean containsTransition(StateMachineTransition transition, ArrayList<StateMachineTransition> modifiedTransitions) {
		for (StateMachineTransition stateMachineTransition : modifiedTransitions) {
			if(transition.getTransitionName()!=null && stateMachineTransition.getTransitionName().equalsIgnoreCase(transition.getTransitionName()) &&
					(transition.getSourceState()!=null && stateMachineTransition.getSourceState().equalsIgnoreCase(transition.getSourceState())) &&
					(transition.getTargetState()!=null && stateMachineTransition.getTargetState().equalsIgnoreCase(transition.getTargetState())))
			{
				return true;
			}
		}
		return false;
	}
	public void copyTransitions(ArrayList<StateMachineTransition> modifiedTransitions, ArrayList<StateMachineTransition> transitions) {
		for (StateMachineTransition stateMachineTransition : transitions) {
			//if(stateMachineTransition.getParentStateName()==null)	
				modifiedTransitions.add(stateMachineTransition);
		}
	}
}
