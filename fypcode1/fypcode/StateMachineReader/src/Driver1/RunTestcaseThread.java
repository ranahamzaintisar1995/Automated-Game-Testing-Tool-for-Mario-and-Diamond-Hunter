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

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

import Controller1.AGTT;
import de.ksquared.system.keyboard.KeyEvent;

public class RunTestcaseThread extends Thread {
	
	private AGTT obj = new AGTT();
	
	ArrayList<String> testcases = new ArrayList<>();
	PrintWriter writer;
	Robot runer = null;
	Boolean Enemey = false;
	int time=0;
	int [] Testno;
	public RunTestcaseThread(ArrayList<String> cases, int min){
		this.testcases = cases;
		this.time = min;
	}
	public RunTestcaseThread(ArrayList<String> cases, int [] TestNo){
		this.testcases = cases;
		this.Testno = TestNo;
	}
	public void setEnemeyFlag(Boolean flag){
		this.Enemey = flag;
	}
	public ArrayList<String> PrepareTestCase(int num){
		
			String[] tokens = testcases.get(num).split(",");
			ArrayList<String> readyTestCase = new ArrayList<>();
			int index = 0;
			for (String string : tokens) {
				if(index%2 == 1){
					readyTestCase.add(string);
				}
				index++;
			}
			return readyTestCase;
	}
	public void RunTest(ArrayList<String> testCase, int number){
		try{
		
		
		
		FileWriter fct = new FileWriter("ForCurrentTransitionMario.txt", true);
		BufferedWriter bct = new BufferedWriter(fct);
		PrintWriter out_ct = new PrintWriter(bct);
		
		FileWriter fnt = new FileWriter("ForNextTransitionMario.txt", true);
		BufferedWriter bnt = new BufferedWriter(fnt);
		PrintWriter out_nt = new PrintWriter(bnt);
		
		FileWriter fpt = new FileWriter("ForPreviousTransitionMario.txt", true);
		BufferedWriter bpt = new BufferedWriter(fpt);
		PrintWriter out_pt = new PrintWriter(bpt);
		String CurrentState="";
		String PreviousTransition="";
		String NextTransition="";
		String CurrentTransition="";
		if(testCase!=null){
			try {
				obj.displayTestCase(testCase);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//System.out.println(testCase+"He;lo");
		String NextState = "";
		int index = 0;
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(true)
		{
		/*if(index == testcase.size())
		{
			try {
				Thread.sleep(3000);
				int key = KeyEvent.VK_E;
				runer.setAutoDelay(500);
				runer.keyPress(key);
				runer.keyRelease(key);
				break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/
		
			int key = KeyEvent.VK_RIGHT;	
			System.out.println("Run Forward");
			if(NextState.contains(" jump") ){
				
				FileWriter cs = new FileWriter("ForCurrentStateMario.txt", true);
				BufferedWriter bcs = new BufferedWriter(cs);
				PrintWriter out_cs = new PrintWriter(bcs);
				CurrentState="jump" ; 
				out_cs.println("jump");
				out_cs.close();
				
				System.out.println("Jump");
				runer.setAutoDelay(500);
				runer.keyPress(key);				
				runer.keyRelease(key);
				if(Enemey == true)
				{
					key = KeyEvent.VK_SPACE;
					runer.setAutoDelay(500);
					runer.keyPress(key);
					runer.keyRelease(key);
					
					break;
				}
			}
			else if(NextState.contains(" Get Hit")){
				FileWriter cs = new FileWriter("ForCurrentStateMario.txt", true);
				BufferedWriter bcs = new BufferedWriter(cs);
				PrintWriter out_cs = new PrintWriter(bcs);
				CurrentState="Get HIt" ; 
				out_cs.println("Get Hit");
				out_cs.close();
				System.out.println("Get Hit");
				runer.setAutoDelay(500);
				runer.keyPress(key);				
				runer.keyRelease(key);
				if(Enemey == true)
				{
					key = KeyEvent.VK_DOWN;
					runer.setAutoDelay(500);
					runer.keyPress(key);
					runer.keyRelease(key);
					break;
				}
			}
			else{
				String trans = testCase.get(index);
				if(trans.equals(" runforward")){
					FileWriter cs = new FileWriter("ForCurrentStateMario.txt", true);
					BufferedWriter bcs = new BufferedWriter(cs);
					PrintWriter out_cs = new PrintWriter(bcs);
					out_cs.println("Running Forward");
					out_cs.close();
					PreviousTransition=CurrentTransition;
					CurrentTransition="runforward";
					out_pt.println(PreviousTransition);
					out_ct.println(CurrentTransition);
					System.out.println("Run Forward");
					key = KeyEvent.VK_RIGHT;	}
				if(trans.equals(" runbackward")){
					FileWriter cs = new FileWriter("ForCurrentStateMario.txt", true);
					BufferedWriter bcs = new BufferedWriter(cs);
					PrintWriter out_cs = new PrintWriter(bcs);
					out_cs.println("Running Backward");
					out_cs.close();
					PreviousTransition=CurrentTransition;
					CurrentTransition="runbackward";
					out_pt.println(PreviousTransition);
					out_ct.println(CurrentTransition);
					System.out.println("Run Backward");
					key = KeyEvent.VK_LEFT;		}
				if((testCase.size()-1) > index )
				{
					
					runer.setAutoDelay(500);
					runer.keyPress(key);
					runer.keyRelease(key);
					if(Enemey == true)
					{
						FileWriter cs = new FileWriter("ForCurrentStateMario.txt", true);
						BufferedWriter bcs = new BufferedWriter(cs);
						PrintWriter out_cs = new PrintWriter(bcs);
						out_cs.println("Jumping");
						out_cs.close();
						PreviousTransition=CurrentTransition;
						CurrentTransition="jump";
						
						out_pt.println(PreviousTransition);
						out_ct.println(CurrentTransition);
						int key1 = key;
						System.out.println("Jump");
						//runer.setAutoDelay(500);
						key = KeyEvent.VK_SPACE ;

						runer.keyPress(key);
						runer.delay(100);
						
						runer.keyPress(key1);
						runer.delay(500);
						runer.keyRelease(key1);
						runer.keyRelease(key);
						
					}
				}
			 	 else
			 	 {
			 		// NextState= "";
			 		 break;
			 	 }
				index++;
			}      
		}
		this.checkTestCase(testCase, number+1);
		//out_cs.close();
		out_ct.close();
		out_pt.close();
		out_nt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String checkTestCase(ArrayList<String> test, int number){
		String result = "";
		BufferedReader reader = null;
		ArrayList<String> testcases = new ArrayList<>();
		try {
			reader= new BufferedReader(new FileReader("C:\\Users\\ranahamzaintisar\\fypcode1\\fypcode1\\fypcode\\Mario Side-Scroller(Game)\\flags.txt"));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String line = null;
			
			writer.println("Test Case # "+number);
			while((line = reader.readLine())!= null)
			{
				for(String s : test){
					String[] tokens = line.split(",");
					if(tokens[0].equals(s)){
						if(tokens[1].equals("true")){
							writer.println( s + " transition Executed successfully !!");
						}
						else
						{
							writer.println( s + " transition not Executed successfully !!");
						}
					}
				}
			}
			
		}
		catch (Exception e) {		 
		}
		return result;
	}
	public void run(){
		try {
			runer = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter("Results.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(time!=0){
			long start_time = System.currentTimeMillis();
			long end_time = start_time + time;
			int testCaseIndex = 0;
			while(System.currentTimeMillis() < end_time){
				System.out.println("time = "+ (end_time - System.currentTimeMillis()));
			//	System.out.println(testCaseIndex);
				ArrayList<String> testCase = this.PrepareTestCase(testCaseIndex);	
				this.RunTest(testCase, testCaseIndex);
				testCaseIndex++;
			}
		}
		else{
			int l= Testno.length;
			for(int i=0;i<l;i++){
				ArrayList<String> testCase = this.PrepareTestCase(Testno[i]);	
				this.RunTest(testCase, Testno[i]);
			}
			
		}
		GameAutoStartStop gameStartStop =new GameAutoStartStop();
		gameStartStop.StopGame();
		writer.close();
	}
}
