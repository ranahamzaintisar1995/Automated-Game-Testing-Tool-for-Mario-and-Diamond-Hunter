package Driver;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SSGA {

	Random rnd = new Random();
	int crossover_rate = 1;

	@SuppressWarnings("unused")

	public void Solution(TestCase testcases) {
		int fitnessValue;
		Order o = new Order();
		ArrayList<TestPath> tp = testcases.getTestPaths();

		TestPath mutated = new TestPath();
		ArrayList<TestPath> parents = new ArrayList<TestPath>();
		ArrayList<TestPath> offsprings = new ArrayList<TestPath>();
		int size = tp.size();

		for (int j = 0; j < 30; j++) {
			for (int i = 0; i < size - 1; i++) {
				fitnessValue = o.orderingFuction(tp.get(i), tp.get(i + 1));
				parents.add(tp.get(0));
				parents.add(tp.get(1));

				if (fitnessValue == 0) {
					// System.out.println("Path to be run on game is: ");
					// parents.get(0).consolePrint();
					RunTestPath rt = new RunTestPath();
					//rt.run(parents.get(0));
					// Run On Game
					mutated = Mutation(tp.get(i));
					offsprings = crossover(parents.get(0), mutated);
					tp.addAll(offsprings);
				} else if (fitnessValue == 1) {
					// System.out.println("Path to be run on game is: ");
					// parents.get(1).consolePrint();
					RunTestPath rt = new RunTestPath();
					//rt.run(parents.get(1));
					// Run On Game
					mutated = Mutation(tp.get(i + 1));
					offsprings = crossover(parents.get(1), mutated);
					tp.addAll(offsprings);
				}

				doSort(tp);
				parents.clear();
				offsprings.clear();
			}
		}
	}

	@SuppressWarnings("null")
	public ArrayList<TestPath> crossover(TestPath t1, TestPath t2) {
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
		System.out.println("==============Random Number :" + crossover_rate);
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
		System.out.println("==============================");

		return temp;
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

		System.out.println(rand1 + " " + rand2);
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
		System.out.println("==============================");
		t1.consolePrint();
		mutatedPath.consolePrint();

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
