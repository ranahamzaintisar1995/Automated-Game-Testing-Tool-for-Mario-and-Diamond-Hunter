package Driver;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class TestPath {
	private static int counter;
	private PrintWriter writer = null;
	ArrayList<StateMachineTransition> transitions=new ArrayList<StateMachineTransition>();
	
	public TestPath(){
		counter=0;
	}
	
	public void addNextStateMachineTransition(StateMachineTransition stateMachineTransition) {
		transitions.add(stateMachineTransition);
	}
	
	public ArrayList<StateMachineTransition> getStateMachineTransitions() {
		return this.transitions;
	}
	
	public StateMachineTransition getSingleTransition(String name)
	{
		StateMachineTransition found = null;
		for (StateMachineTransition stateMachineTransition : transitions)
		{
			if(stateMachineTransition.getTransitionName().equals(name))
			{
				found = stateMachineTransition;
				break;
			}
				
		}
		return found;
	}
	
	public ArrayList<StateMachineTransition> copyTransitions() {
		ArrayList<StateMachineTransition> transitions1=new ArrayList<StateMachineTransition>();
		for (StateMachineTransition stateMachineTransition : transitions) {
			transitions1.add(stateMachineTransition);
		}
		return transitions1;
	}
	
	public void print(PrintWriter writer) {
		writer.print("{ "+transitions.get(0).getSourceState() + " }, ");
		for (StateMachineTransition stateMachineTransition : transitions) {
			writer.print(stateMachineTransition.getTransitionName() + ", { " + stateMachineTransition.getTargetState() + " }, ");
		}writer.println();
	}
	
	public void consolePrint() {
		System.out.print(transitions.get(0).getSourceState()+", ");
		for (StateMachineTransition stateMachineTransition : transitions) {
			System.out.print(stateMachineTransition.getTransitionName() + ", " + stateMachineTransition.getTargetState() + ", ");
		}System.out.println();
		
		if (counter==0){
		
			try {
				writer = new PrintWriter("newPaths.txt", "UTF-8");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			writer.print(transitions.get(0).getSourceState()+", ");
			for (StateMachineTransition stateMachineTransition : transitions) {
				writer.print(stateMachineTransition.getTransitionName() + ", " + stateMachineTransition.getTargetState() + ", ");
			}writer.println();
			writer.close();
			counter++;
		}
		else{
			try(FileWriter fw = new FileWriter("newPaths.txt", true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				out.print(transitions.get(0).getSourceState()+", ");
				for (StateMachineTransition stateMachineTransition : transitions) {
					out.print(stateMachineTransition.getTransitionName() + ", " + stateMachineTransition.getTargetState() + ", ");
				}out.println();
				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
			counter++;
		}
		
	}
	
	public String consolePrint2() {
		String output;
		output=transitions.get(0).getSourceState()+",";
		for (StateMachineTransition stateMachineTransition : transitions) {
			output=output+stateMachineTransition.getTransitionName() + "," + stateMachineTransition.getTargetState() + ",";
		}
		
		return output;
	}
	
	
	
	public String consolePrint1() {
		String output;
		output=transitions.get(0).getSourceState()+"\n";
		for (StateMachineTransition stateMachineTransition : transitions) {
			output=output+stateMachineTransition.getTransitionName() + "\n" + stateMachineTransition.getTargetState() + "\n";
		}
		
		if (counter==0){
		
			try {
				writer = new PrintWriter("newPaths.txt", "UTF-8");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			writer.print(transitions.get(0).getSourceState()+", ");
			for (StateMachineTransition stateMachineTransition : transitions) {
				writer.print(stateMachineTransition.getTransitionName() + ", " + stateMachineTransition.getTargetState() + ", ");
			}writer.println();
			writer.close();
			counter++;
		}
		else{
			try(FileWriter fw = new FileWriter("newPaths.txt", true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				out.print(transitions.get(0).getSourceState()+", ");
				for (StateMachineTransition stateMachineTransition : transitions) {
					out.print(stateMachineTransition.getTransitionName() + ", " + stateMachineTransition.getTargetState() + ", ");
				}out.println();
				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
			counter++;
		}
		return output;
	}
	public void mapKeysAgainsTransitions(PrintWriter writer)
	{
		for (StateMachineTransition stateMachineTransition : transitions) {
			if(stateMachineTransition.getTransitionName().equalsIgnoreCase("movebackward"))
			{
				writer.print("LeftKey { "+stateMachineTransition.getTransitionName()+" }, ");
			}
			else if(stateMachineTransition.getTransitionName().equalsIgnoreCase("moveforward"))
			{
				writer.print("RightKey { "+stateMachineTransition.getTransitionName()+" }, ");
			}
			else if(stateMachineTransition.getTransitionName().equalsIgnoreCase("Jump"))
			{
				writer.print("SPACE { "+stateMachineTransition.getTransitionName()+" }, ");
			}
			else
			{
				writer.print("{ "+stateMachineTransition.getTransitionName()+" }, ");
			}
		}
		writer.println();
	}
}
