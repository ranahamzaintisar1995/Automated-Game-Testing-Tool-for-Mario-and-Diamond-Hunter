package Driver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EA {
	TestPath Parent1;
	TestPath Parent2;
	TestPath output;
	TestPath child;
	Random rnd = new Random();
	int crossover_rate = 1;
	static int check = 0;

	public void setCheck(int check_) {
		check = check_;
	}

	@SuppressWarnings("unused")
	public void Solution(TestCase testcases) {
		int fitnessValue;
		Order o = new Order();
		ArrayList<TestPath> tp = testcases.getTestPaths();
		int size = tp.size();
		for (int i = 0; i < size - 1; i++) {
			fitnessValue = o.orderingFuction(tp.get(i), tp.get(i + 1));

		}
	}

	public void EA_Run(TestCase TC) throws IOException {
		
		
		
		
		ArrayList<TestPath> paths = TC.getTestPaths();

		int ran1 = 0 + (int) (Math.random() * paths.size());
		int ran2 = 0 + (int) (Math.random() * paths.size());
		while (ran1 == ran2) {
			ran2 = 0 + (int) (Math.random() * paths.size());
		}
		Parent1 = paths.get(ran1);
		Parent2 = paths.get(ran2);
		//for (int i = 0; i < 500; i++) {
		new Thread() {
            public void run() {
		for(int i=0;i<10000;i++){
			// System.out.println(":*");
			
			BufferedReader br;
			String sCurrentLine;
			String lastLine = "";
			BufferedReader br1;
			BufferedReader br2;
			//br2 = new BufferedReader(new FileReader("GameCordinates.txt"));
			try {
				br1 = new BufferedReader(new FileReader("sync.txt"));
				
				try {
					if (br1.readLine() == null) {
						int ran1 = 0 + (int) (Math.random() * paths.size());
						int ran2 = 0 + (int) (Math.random() * paths.size());
						while (ran1 == ran2) {
							ran2 = 0 + (int) (Math.random() * paths.size());
						}
						Parent1 = paths.get(ran1);
						Parent2 = paths.get(ran2);
						//out_tp.println(Parent1.consolePrint2());
						//o  ut_tp.println(Parent2.consolePrint2());
					   System.out.println("No error, file empty");
					} 
					
 	 			} catch (IOException e) {
					System.out.println("++++++++++++++++++++++++++++");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
			
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}     
		    
/*
		    try {
		    	br = new BufferedReader(new FileReader("sync.txt"));
				while ((sCurrentLine = br.readLine()) != null) 
				{
				    System.out.println(sCurrentLine);
				    lastLine = sCurrentLine;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		    
		    PrintWriter writer;
			try {
				writer = new PrintWriter("sync.txt");
				writer.print("");
			    writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    /*if(!lastLine.equals("TreeNotAhead")){
			//for (int j = 0; j < 10; j++) {
		    	int ran1 = 0 + (int) (Math.random() * paths.size());
				int ran2 = 0 + (int) (Math.random() * paths.size());
				while (ran1 == ran2) {
					ran2 = 0 + (int) (Math.random() * paths.size());
				}
				Parent1 = paths.get(ran1);
				Parent2 = paths.get(ran2);
				System.out.println("============================");
		    }*/
		    
				
				
				Order o = new Order();
				int order = o.orderingFuction(Parent1, Parent2);

				if (order == 1) {
					// System.out.println("Path to be run on game is: ");
					// Parent1.consolePrint();
					//System.out.println("lun khao");
					 //new Thread() {
				            //public void run() {
				            	//System.out.println("Tum bhhy kha lo");
					try {
						
						FileWriter fw_tp;
						fw_tp = new FileWriter("ForTestPaths.txt", true);
					
						BufferedWriter bw_tp = new BufferedWriter(fw_tp);
						PrintWriter out_tp = new PrintWriter(bw_tp);
						out_tp.println(Parent1.consolePrint2());
						out_tp.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							//	System.out.println(Parent1.consolePrint2());
				            	RunTestPath rt = new RunTestPath();
				            	
								output = Parent1;
				                try {
									rt.run(Parent1);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					
					// Run On Game
					child = Mutation(Parent1);
					crossover(Parent1, child);

				} else {
					// System.out.println("Path to be run on game is: ");
					// Parent2.consolePrint();
					//new Thread() {
			            //public void run() {
					
					try {
						FileWriter fw_tp;
						fw_tp = new FileWriter("ForTestPaths.txt", true);
					
						BufferedWriter bw_tp = new BufferedWriter(fw_tp);
						PrintWriter out_tp = new PrintWriter(bw_tp);
						out_tp.println(Parent2.consolePrint2());
						out_tp.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//	System.out.println(Parent2.consolePrint2()+"==========");
	            	
			            	RunTestPath rt = new RunTestPath();
							output = Parent2;
			                try {
								rt.run(Parent2);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

					
					// Run On Game
					child = Mutation(Parent2);
					crossover(Parent2, child);
				}
			//}
		}
		//rt.run(Parent2);
            }
        }.start();
        
	}

	public TestPath getOutput() {
		return output;
	}

	@SuppressWarnings("null")
	public void crossover(TestPath t1, TestPath t2) {
		TestPath temp1 = new TestPath();
		TestPath temp2 = new TestPath();
		ArrayList<StateMachineTransition> st = new ArrayList<StateMachineTransition>();
		ArrayList<StateMachineTransition> st1 = new ArrayList<StateMachineTransition>();

		st = t1.getStateMachineTransitions();
		int size = st.size();
		st1 = t2.getStateMachineTransitions();
		int size1 = st1.size();
		int min = Math.min(size, size1);
		crossover_rate = rnd.nextInt(min);
		// System.out.println("==============Random Number :" + crossover_rate);
		for (int i = 0; i < crossover_rate; i++) {
			temp1.addNextStateMachineTransition(st.get(i));
			temp2.addNextStateMachineTransition(st1.get(i));
		}
		for (int i = crossover_rate; i < size; i++) {
			temp1.addNextStateMachineTransition(st1.get(i));

		}
		for (int i = crossover_rate; i < size; i++) {
			temp2.addNextStateMachineTransition(st.get(i));

		}
		ArrayList<TestPath> temp = new ArrayList<TestPath>();
		temp.add(temp1);
		temp.add(temp2);
		// System.out.println("==============================");
		// temp.get(0).consolePrint();
		// temp.get(1).consolePrint();

	}

	public TestPath Mutation(TestPath t1) {
		TestPath mutatedPath = new TestPath();
		ArrayList<StateMachineTransition> st1 = new ArrayList<StateMachineTransition>();
		StateMachineTransition temp1 = new StateMachineTransition();
		StateMachineTransition temp2 = new StateMachineTransition();

		st1 = t1.getStateMachineTransitions();
		int num = st1.size();
		int rand1 = ThreadLocalRandom.current().nextInt(1, num);
		int rand2 = ThreadLocalRandom.current().nextInt(1, num);

		while (rand1 == rand2)
			rand2 = ThreadLocalRandom.current().nextInt(1, num);

		// System.out.println(rand1+" "+rand2);
		temp1 = st1.get(rand1);
		temp2 = st1.get(rand2);

		for (int i = 0; i < num; i++) {
			if (i != rand1 && i != rand2)
				mutatedPath.addNextStateMachineTransition(st1.get(i));
			else if (i == rand1)
				mutatedPath.addNextStateMachineTransition(st1.get(rand2));
			else if (i == rand2)
				mutatedPath.addNextStateMachineTransition(st1.get(rand1));
		}
		// System.out.println("==============================");
		// t1.consolePrint();
		// mutatedPath.consolePrint();

		return mutatedPath;
	}

	public static void swap(ArrayList<TestPath> sort, int i, int j) {
		TestPath tmp = sort.get(i);
		sort.set(i, sort.get(j));
		sort.set(j, tmp);
	}

	public static void doSort(ArrayList<TestPath> sort) {
		int min;

		Order o = new Order();
		for (int i = 0; i < sort.size(); ++i) {
			// find minimum in the rest of array
			min = i;
			for (int j = i + 1; j < sort.size(); ++j) {
				if (o.orderingFuction(sort.get(j), sort.get(min)) >= 0) {
					min = j;
				}
			}

			// do swap
			swap(sort, i, min);
		}
	}
}
