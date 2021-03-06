package Driver1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.eclipse.emf.ecore.xmi.UnresolvedReferenceException;
import org.eclipse.uml2.uml.Model;

public class test {

	public ArrayList<String> getAllTestCases(){
		 BufferedReader reader = null;
		 ArrayList<String> testcases = new ArrayList<>();
		 try {
			 reader= new BufferedReader(new FileReader("TestCasesNew.txt"));
		 } 
		 catch (Exception e) {
			 e.printStackTrace();
		 }
		 try {
			String line = null;
			while((line = reader.readLine())!= null)
			{
				testcases.add(line);
			}
		 }
		 catch (Exception e) {		 
		 }
		return testcases;
	}
	public void generateTestCases() {
		// TODO Auto-generated method stub

		StateMachineReader smReader = new StateMachineReader();
		UMLReader reader = new UMLReader();
		JavaCodeStructure javaCodeStructure = new JavaCodeStructure();

		Model model = reader.loadModel((new File("InputFiles/DiamondHunter.uml"))
				.toURI().toString());
		try {
			javaCodeStructure = smReader.processTheModel(model);
		} catch (UnresolvedReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<StateMachineState> states;
		ArrayList<StateMachineTransition> transitions = null;
		
		states = smReader.getStates();
		transitions = smReader.getTransitions();
		
		//========================================
		TestCase testCaseReady=new TestCase();
		ArrayList<StateMachineTransition> modifiedTransitions = testCaseReady.getPathReadyTransitions(transitions);
		//========================================
		
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("StatesNew.txt");
			//System.out.println("\nStates :");
			for (StateMachineState stateMachineState : states) {
			//	stateMachineState.print();
				// write in TextFile
				writer.println(stateMachineState.getStateName());

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}

		
		try {
			writer = new PrintWriter("TransitionsNew.txt");

			//System.out.println("\nTransitions :");
			for (StateMachineTransition stateMachineTransition : modifiedTransitions) {
				//stateMachineTransition.print();
				// write in TextFile
				writer.println(stateMachineTransition.getTransitionName());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}
		
		//System.out.print("\nTestCaseGeneration :");
		TestCase testCases= new TestCase();
		/*testCases.generateTestCase(transitions, 0);
		testCases.generateTestCase(transitions, 1);*/
		
		//set priorityScore
		ArrayList<Integer> scores=new ArrayList<Integer>();
		for (int i = 0; i < modifiedTransitions.size(); i++) {
			scores.add((i%2)+1);
		}
		//setTesterAssignedScores(scores, transitions);
		
		//To get a TestPath containing max number of Transitions
		setScoresAutomatically(modifiedTransitions);
		
	//	System.out.println("\nTestPathsGeneration :");
		testCases.generateTestPaths(modifiedTransitions, null,null,new TestPath());
		testCases.printTestPaths();
		
		Integer score=testCases.getTestPathsMaxScore();
		//System.out.println("\nPreferredTestPaths with Score :"+score);
		ArrayList<TestPath> preferredTestPaths=testCases.getTestPathsWithScore(score);
		for (TestPath testPath : preferredTestPaths) {
			//testPath.print();
		}
	}

	

	public static void setTesterAssignedScores(ArrayList<Integer> scores, ArrayList<StateMachineTransition> transitions) {
		for (int i = 0; i < transitions.size(); i++) {
			transitions.get(i).setPriorityScore(scores.get(i));
		}
	}
	
	public static void setScoresAutomatically(
			ArrayList<StateMachineTransition> transitions) {
		for (int i = 0; i < transitions.size(); i++) {
			transitions.get(i).setPriorityScore(1);
		}
	}
	public ArrayList<String> getTransitions()
	 {
		 BufferedReader reader =null;
		 ArrayList<String> transitions = new ArrayList<>();
		 try {
			 reader= new BufferedReader(new FileReader("TransitionsNew.txt"));
		 } 
		 catch (Exception e) {
			 e.printStackTrace();
		 }
		 try {
			String line = null;
			while((line = reader.readLine())!= null)
			{
				if(!transitions.contains(line))
					transitions.add(line);
			}
		 }
		 catch (Exception e) {		 
		 }
		 
		 return transitions;
	 }
}

