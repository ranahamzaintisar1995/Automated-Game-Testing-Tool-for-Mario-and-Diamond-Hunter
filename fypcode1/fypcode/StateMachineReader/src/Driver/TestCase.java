package Driver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author Noman
 *
 */
public class TestCase {
	
	ArrayList<TestPath> paths=new ArrayList<TestPath>();
	ArrayList<TestPath> prefferedTestPaths=new ArrayList<TestPath>();

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
	
	public ArrayList<TestPath> getTestPaths()
	{
		return this.paths;
	}
	
	/**
	 * 
	 * @param transitions
	 * @param targetState
	 * @param transitionName
	 * @param testPath
	 * @return Boolean
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
	 * @return TestPath
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
	 * @return ArrayList<StateMachineTransition>
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
	
	
	public void printTestPaths() {
		PrintWriter writer=null;
		Integer i=0;
		
		try {
			writer = new PrintWriter("FYP.txt");
			for (TestPath tPath : paths) {
				i++;
				writer.print(i +":: ");
				tPath.print(writer);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}
		
		
	}
	
	public StateMachineTransition getSingleTransition(String name)
	{
		StateMachineTransition found = null;
		for (TestPath testPath : paths) {
			found  = testPath.getSingleTransition(name);
			if(found != null)
				break;
		} 
		return found;
	}
	
	public void printPathsOnConsole() {
		Integer i=0;
		
			for (TestPath tPath : paths) {
				i++;
				//System.out.print(i +":: ");
				tPath.consolePrint();
			}
			
	}


	/**
	 * 
	 * @param transition
	 * @param modifiedTransitions
	 * @return Boolean
	 */
	public Boolean containsTransition(StateMachineTransition transition, ArrayList<StateMachineTransition> modifiedTransitions) {
		for (StateMachineTransition stateMachineTransition : modifiedTransitions) {
			if(transition.getTransitionName()!=null && stateMachineTransition.getTransitionName().equalsIgnoreCase(transition.getTransitionName()) &&
					(transition.getSourceState()!=null && stateMachineTransition.getSourceState().equalsIgnoreCase(transition.getSourceState())) &&
					(transition.getTargetState()!=null && stateMachineTransition.getTargetState().equalsIgnoreCase(transition.getTargetState())) &&
					transition.getRegionNo().equalsIgnoreCase(stateMachineTransition.getRegionNo()))
			{
				return true;
			}
		}
		return false;
	}
	

}
