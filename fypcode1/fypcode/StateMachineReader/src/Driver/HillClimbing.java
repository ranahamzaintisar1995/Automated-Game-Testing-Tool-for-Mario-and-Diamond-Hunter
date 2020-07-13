package Driver;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HillClimbing {
	Random rd = new Random();
	ArrayList<String> restrictedTransitions = new ArrayList<>();
	ArrayList<String> restrictedStates = new ArrayList<>();
	int health = 3;
	int DiamondsComplete = 0;
	ArrayList<StateMachineState> sms;

	public HillClimbing(ArrayList<StateMachineState> s) {
		sms = s;
	}

	public HillClimbing() {
	}

	// Here main hill climbing will be implemented
	public void evaluate(TestCase testcases) {
		String target = "Idle";
		Order o = new Order(testcases);
		ArrayList<TestPath> arr = testcases.getTestPaths();

		ArrayList<StateMachineTransition> nextTransition = new ArrayList<StateMachineTransition>();
		ArrayList<StateMachineTransition> temp = new ArrayList<StateMachineTransition>();

		TestPath tp = new TestPath();
		int n = sms.size();
		// System.out.println(n);
		int id = 0;
		for (int i = 0; i < 1000; i++) {
			for (int k = 0; k < n; k++) {
				String s = sms.get(k).getStateName();
				if (s.equals(target)) {
					id = k;
					break;
				}
			}
			target = null;
			System.out.println("Position of state: " + id);
			nextTransition.addAll(check(sms.get(id), testcases));
			RunTestPath rt = new RunTestPath();
			rt.run(nextTransition);
			// System.out.println("++");
			// tp.consolePrint();
			int size = nextTransition.size() - 1;
			for (int q = 0; q < size + 1; q++) {
				System.out.println("New Transitions: " + nextTransition.get(q).getTransitionName());
			}

			if (nextTransition.size() == 0) {
				break;
			} else if (nextTransition.size() == 1) {
				temp.add(nextTransition.get(0));
				System.out.println(temp.get(0).getTransitionName());
			}

			else {
				for (int j = 0; j < size; j++) {

					int n1 = o.orderingFuction(nextTransition.get(j), nextTransition.get(j + 1));
					if (n1 == 1 && !temp.contains(nextTransition.get(j))) {
						temp.add(nextTransition.get(j));
					} else if (n1 == -1 && !temp.contains(nextTransition.get(j + 1))) {
						temp.add(nextTransition.get(j + 1));
					} else if (n1 == 0) {
						if (!temp.contains(nextTransition.get(j)))
							temp.add(nextTransition.get(j));
						if (!temp.contains(nextTransition.get(j + 1)))
							temp.add(nextTransition.get(j + 1));
					}
				}

				/*
				 * for(int a=0;a<size+1;a++) {
				 * System.out.println("Transitions in temp: "+temp.get(a).
				 * getTransitionName()); }
				 */

			}

			System.out.println("value of temp array is :" + temp.size());
			/*
			 * for (int l = 0; l < temp.size(); l++) {
			 * System.out.println(temp.get(l).getTransitionName()); }
			 */
			int num = temp.size();
			System.out.println("Size of temp array: " + num);
			if (num == 1) {
				tp.addNextStateMachineTransition(temp.get(0));
				target = temp.get(0).getTargetState();
				System.out.println("Target State: " + target);
			} else {
				int random_value = rd.nextInt(num);
				System.out.println("Random value is : " + random_value);
				tp.addNextStateMachineTransition(temp.get(random_value));
				target = temp.get(random_value).getTargetState();
				System.out.println("Target State: " + target);
			}

			nextTransition.clear();
			temp.clear();
			System.out.println("\n\n\n");
		}

		for (int j = 0; j < 2000; j++) {
			for (int i = j; i < arr.size(); i++) {
				int rand1 = ThreadLocalRandom.current().nextInt(1, arr.size());
				int rand2 = ThreadLocalRandom.current().nextInt(1, arr.size());
				Order o1 = new Order();
				int order = o1.orderingFuction(arr.get(rand1), arr.get(rand2));
				if (order == 1) {
					// System.out.println("Path to be run on game is: ");
					// Parent1.consolePrint();
					RunTestPath rt = new RunTestPath();
					// output=Parent1;
					//rt.run(arr.get(rand1));

				} else {
					RunTestPath rt = new RunTestPath();
					//rt.run(arr.get(rand2));
				}

			}
		}

	}

	public ArrayList<StateMachineTransition> check(StateMachineState st, TestCase tc) {
		ArrayList<StateMachineTransition> temp = new ArrayList<StateMachineTransition>();
		// System.out.println("+++++");
		for (TestPath tp : tc.getTestPaths()) {
			// System.out.println("-----");
			for (StateMachineTransition stateMachineTransition : tp.getStateMachineTransitions()) {
				// System.out.println(stateMachineTransition.getSourceState());
				if (stateMachineTransition.getSourceState().equals(st.getStateName())) {
					if (!temp.contains(stateMachineTransition)) {
						temp.add(stateMachineTransition);
						// System.out.println(stateMachineTransition.getTransitionName());
					}

				}
			}

		}
		return temp;
	}
}
