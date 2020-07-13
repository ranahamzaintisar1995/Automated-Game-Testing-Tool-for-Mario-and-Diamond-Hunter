 
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
package Controller1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Driver1.GameAutoStartStop;
import Driver1.RunTestCases;
import Driver1.TestCase;
import Driver1.TestCaseGeneration;
import de.ksquared.test.system.keyboard.KeyboardHookTest;

public class AGTT {

	TestCaseGeneration testCasegeneration;
	RunTestCases runtestcase;
	KeyboardHookTest keyboardTest ;
	TestCase testCase;
	GameAutoStartStop gameStartStop;
	ControllerMain CM;
	
	public AGTT(){
		testCasegeneration = new TestCaseGeneration();
		runtestcase = new RunTestCases();
		keyboardTest = new KeyboardHookTest();
		testCase = new TestCase();
		gameStartStop =new GameAutoStartStop();
		CM = new ControllerMain();
	}
	public void generateTestCases(){
		testCasegeneration.generateTestCases();
	}
	public ArrayList<String> getAllTestCases(){
		return testCasegeneration.getAllTestCases();
	}
	public void SetTestCases(ArrayList<String> Cases){
		runtestcase.SetTestcases(Cases);
	}
	public void prepareTestcases( int min){
		runtestcase.PrepareTestCase(min);
	}
	public void prepareTestcases( int [] TestNo){
		runtestcase.PrepareTestCase(TestNo);
	}
	public ArrayList<String> KeysConversion() throws NumberFormatException, IOException
	{
		return keyboardTest.ConvertKeys();
	}
	public ArrayList<String> GetTransitions()
	{
		return testCasegeneration.getTransitions();
	}
	public void getReport(){
		 runtestcase.generateReport();
		
	}
	public void StartGame(){
		gameStartStop.StartGame();
	}
	public void displayTestCase(ArrayList<String> testCase) throws IOException{
		
		try {
			FileWriter fwOb;
			fwOb = new FileWriter("ForMarioSync.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
		
		FileWriter fw_msync = new FileWriter("ForMarioSync.txt", true);
		BufferedWriter bw_msync = new BufferedWriter(fw_msync);
		PrintWriter out_msync = new PrintWriter(bw_msync);
		out_msync.println(testCase);
		System.out.println(testCase);
		CM.displayTestCase(testCase);
		out_msync.close();
	}
}
