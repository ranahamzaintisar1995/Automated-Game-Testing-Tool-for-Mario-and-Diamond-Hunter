package Driver;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class StateMachineFlattening {

	/**
	 * 
	 * @param transitions
	 * @return ArrayList<StateMachineTransition>
	 */
	public ArrayList<StateMachineTransition> getPathReadyTransitions(
			ArrayList<StateMachineTransition> transitions) {
		ArrayList<StateMachineTransition> modifiedTransitions = new ArrayList<StateMachineTransition>();
		copyTransitions(modifiedTransitions, transitions);
		ArrayList<StateMachineTransition> flattenedTransitions=new ArrayList<>();
		
		for (StateMachineTransition parentStateTransition : transitions) {
			if(parentStateTransition.getTargetState()!=null && isTarget_AParentOfAnyState(parentStateTransition.getTargetState(),transitions))
			{
				ArrayList<StateMachineTransition> allNestedTransitions=getStateNestedTransitions(parentStateTransition.getTargetState(), transitions);
				//==========================
				ArrayList<StateMachineTransition> parentExitTransitions=getParentStateExitTransitions(transitions,parentStateTransition.getTargetState());
				addParentExitTransitions(modifiedTransitions, allNestedTransitions, parentExitTransitions);
				//==========================
				// Saperate parallel regions
				ArrayList<StateMachineRegion> parallelRegions=new ArrayList<>();
				getRegionTransitionsSeperate(allNestedTransitions, parallelRegions);
				
				if(parallelRegions.size()>1)
				{
					flattenedTransitions=doFlatteningOnParallelRegions(flattenedTransitions, parallelRegions);
				}
				//==========================
				if(parentStateTransition.getTargetState()!=parentStateTransition.getSourceState())
					addParentEntryTransitions(allNestedTransitions, modifiedTransitions, parentStateTransition);
			}
		}
		modifiedTransitions=deleteDuplicateTransition(modifiedTransitions);
		return modifiedTransitions;
	}
	
	/**
	 * 
	 * @param modifiedTransitions
	 * @param transitions
	 */

	public void copyTransitions(ArrayList<StateMachineTransition> modifiedTransitions, ArrayList<StateMachineTransition> transitions) {
		for (StateMachineTransition stateMachineTransition : transitions) {
				modifiedTransitions.add(stateMachineTransition);
		}
	}
	
	/**
	 * 
	 * @param stateName
	 * @param tranitions
	 * @return Boolean
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
	 * @return ArrayList<StateMachineTransition>
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
	
	/**
	 * 
	 * @param transitions
	 * @param parentStateName
	 * @return ArrayList<StateMachineTransition>
	 */
	public ArrayList<StateMachineTransition> getParentStateExitTransitions(ArrayList<StateMachineTransition> transitions, String parentStateName) {
		ArrayList<StateMachineTransition> exitTransitions=new ArrayList<StateMachineTransition>();
		for (StateMachineTransition stateMachineTransition : transitions) {
			if(stateMachineTransition.getSourceState()!=null && stateMachineTransition.getSourceState().equalsIgnoreCase(parentStateName))
			{
				if(stateMachineTransition.getSourceState()!=stateMachineTransition.getTargetState())	//if not parentState self transitions
					exitTransitions.add(stateMachineTransition);
			}
		}
		return exitTransitions;
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
				StateMachineTransition newTransition= new StateMachineTransition(exitTransition.getTransitionName(), nestedState, exitTransition.getTargetState(), null, exitTransition.getGaurdCondition(), stateMachineTransition.getRegionNo());
				if(!containsTransition(newTransition, modifiedTransitions))
					modifiedTransitions.add(newTransition);
			}
		}
	}

	/**
	 * 
	 * @param nestedTransitions
	 * @param modifiedTransitions
	 * @param transitionToDuplicate
	 */
	public void addParentEntryTransitions(ArrayList<StateMachineTransition> nestedTransitions, ArrayList<StateMachineTransition> modifiedTransitions, StateMachineTransition transitionToDuplicate) {
		
		String newTargetState=null;
		String prevRegion=null;
		String curRegion=null;
		for (StateMachineTransition stateMachineTransition : nestedTransitions) {
			
			// adding entry transition on initial state of each parallel region
			if(curRegion!=null)
				prevRegion=curRegion;
			
			curRegion=stateMachineTransition.getRegionNo();
			newTargetState=stateMachineTransition.getSourceState();
			if(!curRegion.equalsIgnoreCase(prevRegion))
			{
				StateMachineTransition newTransition= new StateMachineTransition(transitionToDuplicate.getTransitionName(), transitionToDuplicate.getTargetState(), newTargetState, null, transitionToDuplicate.getGaurdCondition(), curRegion);
				if (!containsTransition(newTransition, modifiedTransitions)) {
					modifiedTransitions.add(newTransition);
				}
			}
			
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

	/**
	 * 
	 * @param allNestedTransitions
	 * @param parallelRegions
	 */
	public void getRegionTransitionsSeperate(
			ArrayList<StateMachineTransition> allNestedTransitions,
			ArrayList<StateMachineRegion> parallelRegions) {
		String nextRegion=allNestedTransitions.get(0).getRegionNo();
		int i=0;
		while(i<allNestedTransitions.size())
		{
			StateMachineTransition nestedTransition = allNestedTransitions.get(i);
			StateMachineRegion stateMachineRegion= new StateMachineRegion();
			nextRegion=nestedTransition.getRegionNo();
			
			for (StateMachineTransition stateMachineTransition : allNestedTransitions) {
				if(stateMachineTransition.getRegionNo().equalsIgnoreCase(nextRegion))
				{
					stateMachineRegion.addTransition(stateMachineTransition);
					i++;
				}
			}
			parallelRegions.add(stateMachineRegion);
			
		}
	}

	/**
	 * 
	 * @param flattenedTransitions
	 * @param parallelRegions
	 * @return ArrayList<StateMachineTransition>
	 */
	public ArrayList<StateMachineTransition> doFlatteningOnParallelRegions(
			ArrayList<StateMachineTransition> flattenedTransitions,
			ArrayList<StateMachineRegion> parallelRegions) {
		
		//pick first state of each region
		String startState="";
		for (StateMachineRegion stateMachineRegion : parallelRegions) {
			startState+=stateMachineRegion.getTransitions().get(0).getSourceState()+"|"; //initial source
		}
		startState=startState.substring(0,startState.length()-1);

		for (StateMachineRegion stateMachineRegion : parallelRegions) {
			String flattenedSourceState=startState;
			String flattenedRegion=parallelRegions.get(0).getTransitions().get(0).getRegionNo();
			for (StateMachineTransition stateMachineTransition : stateMachineRegion.getTransitions()) {
				
				int regNo=Integer.parseInt(stateMachineTransition.getRegionNo().substring(stateMachineTransition.getRegionNo().length()-1, stateMachineTransition.getRegionNo().length()));
				flattenedSourceState=getFlattenedState(flattenedSourceState, regNo-1, stateMachineTransition.getSourceState());
				String flattenedTargetState=getFlattenedState(flattenedSourceState,regNo-1,stateMachineTransition.getTargetState());
				StateMachineTransition transition= new StateMachineTransition(stateMachineTransition.getTransitionName(), flattenedSourceState, flattenedTargetState,
						stateMachineTransition.getParentStateName()==null ? null : stateMachineTransition.getParentStateName(), stateMachineTransition.getGaurdCondition(), flattenedRegion);
				
				if(!containsTransition(transition, flattenedTransitions));
					flattenedTransitions.add(transition);
				
			}
		}
		return flattenedTransitions;
	}

	/**
	 * 
	 * @param stateToChange
	 * @param position
	 * @param targetState
	 * @return
	 */
	public String getFlattenedState(String stateToChange, int position, String targetState)
	{
		String flattenedState="";
		StringTokenizer stateToChangeTokens= new StringTokenizer(stateToChange, "|");
		int i=0;
		while (stateToChangeTokens.hasMoreElements()) {
			if(i==position)
			{
				flattenedState+=targetState+"|";
				stateToChangeTokens.nextElement().toString();
			}
			else
				flattenedState+=stateToChangeTokens.nextElement().toString()+"|";
			i++;
		}flattenedState=flattenedState.substring(0,flattenedState.length()-1);
		return flattenedState;
	}

	/**
	 * 
	 * @param modifedTransitions
	 * @return ArrayList<StateMachineTransition>
	 */
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
	


}
