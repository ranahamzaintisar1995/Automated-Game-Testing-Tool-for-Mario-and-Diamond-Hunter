package Driver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Order {
	private TestCase testCases;

	public Order(TestCase testCase) {
		testCases = testCase;
	}

	public Order() {

	}

	public int orderingFuction(StateMachineTransition t1, StateMachineTransition t2) {
		int badCount1 = getBadCounts(t1);
		int badCount2 = getBadCounts(t2);
		if (badCount1 < badCount2) {
			// System.out.println("Transition 1 is better");
			return 1;
		} else if (badCount1 == badCount2) {
			// System.out.println("Both transitions are the same");
			return 0;
		} else {
			// System.out.println("Transition 2 is better");
			return -1;
		}
	}

	public int orderingFuction(TestPath t1, TestPath t2) {
		int badCount1 = getBadCounts(t1);
		int badCount2 = getBadCounts(t2);
		if (badCount1 < badCount2) {
			// System.out.println("testpath 1 is better");
			return 1;
		} else if (badCount1 == badCount2) {
			// System.out.println("Both testpath are the same");
			return 0;
		} else {
			// System.out.println("testpath 2 is better");
			return -1;
		}
	}
	/*
	 * public int getGuardConditionCount(StateMachineTransition t1){
	 * 
	 * 
	 * int count=0; String condition=t1.getGaurdCondition(); if(condition ==
	 * null || condition.isEmpty()) { return count; } else{ String or="||";
	 * String and="&&"; count = 1; if ( condition.indexOf(or) != -1 ) { Pattern
	 * p = Pattern.compile(or); Matcher m = p.matcher( condition ); while
	 * (m.find()) { count++; } } else if (condition.indexOf(and) != -1){ Pattern
	 * p2 = Pattern.compile(and); Matcher m2 = p2.matcher( condition ); while
	 * (m2.find()) { count++; } } return count; }
	 * 
	 * }
	 */

	public int getBadCounts(TestPath TP) {
		int transitionsCount = 1000;
		int GuardConditionCount = 0;
		// ArrayList<TestPath> goalPaths=new ArrayList<TestPath>;

		int counter = 0;
		int end = 1;
		int counter1 = 0;

		for (StateMachineTransition s : TP.getStateMachineTransitions()) {

			if (s.getGaurdCondition() != null && !s.getGaurdCondition().isEmpty()) {
				counter1++;
			}
			if (s.getTargetState().equals("DiamondsComplete") || s.getTargetState().equals("BonusComplete")
					|| s.getTargetState().equals("ZeroHealth")) {
				// System.out.println("2");
				end++;
				;
			} else if (end == 1) {
				// System.out.println("3");
				counter++;
			}
		}
		if (end == 2 && counter < transitionsCount) {
			// goalPaths.add(t);
			// t.consolePrint();
			// for(StateMachineTransition s: t.getStateMachineTransitions()){
			// System.out.println(s.getTransitionName()+" - "+s.getRegionNo());
			// }
			transitionsCount = counter;
			GuardConditionCount = counter1;
		}

		// System.out.println("Transition Count: " + transitionsCount);
		// System.out.println("Guard condition count: " + GuardConditionCount);
		int badCounts = transitionsCount + GuardConditionCount;
		// System.out.println("Total bad counts: " + badCounts);
		return badCounts;
	}

	public int getBadCounts(StateMachineTransition t1) {
		int transitionsCount = 1000;
		int GuardConditionCount = 0;
		// ArrayList<TestPath> goalPaths=new ArrayList<TestPath>;

		for (TestPath t : testCases.getTestPaths()) {
			int counter = 0;
			int end = 0;
			int counter1 = 0;

			for (StateMachineTransition s : t.getStateMachineTransitions()) {

				if (s == t1) {
					// System.out.println("1");
					end++;
				}
				if (s.getGaurdCondition() != null && !s.getGaurdCondition().isEmpty()) {
					counter1++;
				}
				if (s.getTargetState().equals("DiamondsComplete") || s.getTargetState().equals("BonusComplete")
						|| s.getTargetState().equals("ZeroHealth")) {
					// System.out.println("2");
					end++;
					;
				} else if (end == 1) {
					// System.out.println("3");
					counter++;
				}
			}
			if (end == 2 && counter < transitionsCount) {
				// goalPaths.add(t);
				// t.consolePrint();
				for (StateMachineTransition s : t.getStateMachineTransitions()) {
					// System.out.println(s.getTransitionName()+" -
					// "+s.getRegionNo());
				}
				transitionsCount = counter;
				GuardConditionCount = counter1;
			}
		}
		// System.out.println("Transition Count: " + transitionsCount);
		// System.out.println("Guard condition count: " + GuardConditionCount);
		int badCounts = transitionsCount + GuardConditionCount;
		// System.out.println("Total bad counts: " + badCounts);
		return badCounts;

	}

	// Hamza k lye
	public int orderingFuction(TestPath tp, StateMachineTransition t1, StateMachineTransition t2) {
		int badCount1 = getBadCounts(tp, t1);
		int badCount2 = getBadCounts(tp, t2);
		if (badCount1 < badCount2) {
			// System.out.println("Transition 1 is better");
			return 1;
		} else if (badCount1 == badCount2) {
			// System.out.println("Both transitions are the same");
			return 0;
		} else {
			// System.out.println("Transition 2 is better");
			return -1;
		}
	}

	public int getBadCounts(TestPath TP, StateMachineTransition st) {
		int transitionsCount = 1000;
		int GuardConditionCount = 0;
		// ArrayList<TestPath> goalPaths=new ArrayList<TestPath>;

		int counter = 0;
		int end = 1;
		int counter1 = 0;
		ArrayList<StateMachineTransition> st1 = TP.getStateMachineTransitions();

		for (int i = 0; i < st1.size(); i++) {

			if (st.getGaurdCondition() != null && !st.getGaurdCondition().isEmpty()) {
				counter1++;
			}
			if (st.getTargetState().equals("DiamondsComplete") || st.getTargetState().equals("BonusComplete")
					|| st.getTargetState().equals("ZeroHealth")) {
				// System.out.println("2");
				end++;

			} else if (end == 1) {
				// System.out.println("3");
				counter++;
			}
		}
		if (end == 2 && counter < transitionsCount) {
			// goalPaths.add(t);
			// t.consolePrint();
			// for(StateMachineTransition s: t.getStateMachineTransitions()){
			// System.out.println(s.getTransitionName()+" - "+s.getRegionNo());
			// }
			transitionsCount = counter;
			GuardConditionCount = counter1;
		}

		// System.out.println("Transition Count: " + transitionsCount);
		// System.out.println("Guard condition count: " + GuardConditionCount);
		int badCounts = transitionsCount + GuardConditionCount;
		// System.out.println("Total bad counts: " + badCounts);
		return badCounts;
	}

	public int orderingFuction(StateMachineTransition t1, StateMachineTransition t2, StateMachineTransition t3,
			StateMachineTransition t4) {
		int badCount1 = getBadCounts(t1);
		int badCount2 = getBadCounts(t2);
		int badCount3 = getBadCounts(t3);
		int badCount4 = getBadCounts(t4);
		int max = Math.max(badCount1, badCount2);
		int max1 = Math.max(badCount3, max);
		if (max1 == badCount1) {
			// System.out.println("Transition 1 is better");
			return 1;
		} else if (badCount1 == badCount2 && badCount2 == badCount3 && badCount3 == badCount4) {
			// System.out.println("All transitions are the same");
			return 0;
		} else if (max1 == badCount2) {
			// System.out.println("Transition 2 is better");
			return 2;
		} else if (max1 == badCount3) {
			// System.out.println("Transition 3 is better");
			return 3;
		} else if (max1 == badCount4) {
			// System.out.println("Transition 4 is better");
			return 4;
		} else if (badCount1 == badCount2 || badCount1 == badCount3 || badCount2 == badCount3
				|| badCount3 == badCount4) {
			return -1;
		} else {
			return -2;
		}

	}

	public void sort(TestPath t1, TestPath t2) {

	}
}
