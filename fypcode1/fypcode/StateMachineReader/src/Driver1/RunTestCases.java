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
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import Controller1.RecievingThread;

@SuppressWarnings("resource")
public class RunTestCases {
	ArrayList<String> testcases = new ArrayList<String>();
	ArrayList<String> Readytestcase = new ArrayList<String>();
	ArrayList<String> userTransitions = new ArrayList<String>();
	ArrayList<Integer> userKeys = new ArrayList<Integer>();
	Controller1.RecievingThread r;
	public void SetTestcases(ArrayList<String> cases){
		this.testcases = cases;
	}
	public void setUsertransitions(){
		this.userTransitions.add("movebackward");
		this.userTransitions.add("moveforward");
		this.userTransitions.add("jump");
	}
	public void setUserKeys(){
		this.userKeys.add(KeyEvent.VK_LEFT);
		this.userKeys.add(KeyEvent.VK_RIGHT);
		this.userKeys.add(KeyEvent.VK_SPACE);
	}
	public RunTestCases(){
		
		this.setUsertransitions();
		this.setUserKeys();
	}
	public boolean isUserTransition(String transition){
		if(userTransitions.contains(transition))
			return true;
		else
			return false;
	}
	public void PrepareTestCase(int [] TestNo){
		this.runTestCase(TestNo);
	}
	public void PrepareTestCase(int min){
		this.runTestCase(min);
		//this.PrepareTestCase();
		/*//for(String cases : testcases){
			String[] tokens = testcases.get(num).split(",");
			ArrayList<String> readyTestCase = new ArrayList<>();
			int index = 0;
			for (String string : tokens) {
				if(index%2 == 1){
					//System.out.println(string);
					//if(isUserTransition(string))
					readyTestCase.add(string);
				}
				index++;
			}
			if(!readyTestCase.isEmpty()){
				this.Readytestcase = readyTestCase;
				runTestCase(readyTestCase);
			}		*/
		//}
	}
	public void PrepareTestCase(){
		//for(String cases : testcases){
		long start_time = System.currentTimeMillis();
		long wait_time = 20000;
		long end_time = start_time + wait_time;
		int TestCaseIndex = 0;
		while (System.currentTimeMillis() < end_time) {
		
			String[] tokens = testcases.get(TestCaseIndex).split(",");
			ArrayList<String> readyTestCase = new ArrayList<>();
			int index = 0;
			for (String string : tokens) {
				if(index%2 == 1){
					//System.out.println(string);
					//if(isUserTransition(string))
					readyTestCase.add(string);
				}
				index++;
			}
			if(!readyTestCase.isEmpty()){
				this.Readytestcase = readyTestCase;
		//		runTestCase(readyTestCase);
			}
			System.out.println("Testcase" + TestCaseIndex);
			TestCaseIndex++;
			
		}
		//}
	}
	public void runTestCase(int [] TestNo){
		try {
			r = new RecievingThread(this.testcases, TestNo);
			r.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.SetList();
		
		
	}
	public void runTestCase(int min){
		try {
			r = new RecievingThread(this.testcases, min);
			r.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.SetList();
		
		
	}
	public void generateReport(){
		
		File file = new File("Report1.pdf");
		FileOutputStream fileout = null;
		try {
			fileout = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, fileout);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.addAuthor("AGTT");
		document.addTitle("AGTT Report");

		document.open();
		Boolean status = true;
		BufferedReader reader = null;
		try {
			reader= new BufferedReader(new FileReader("Results.txt"));
			String line = null;
			String testCase =null;
			Chunk chunk = null;
			int index = 0;
			List list = new List();
			while((line = reader.readLine())!= null){
				if(line.contains("Case"))
				{
					
					if(index > 0)
					{
						System.out.println(line+status+list.size());
						if(status ==false){ chunk.setBackground(Color.RED);	}
						if(status ==true){ chunk.setBackground(Color.GREEN);	}
						document.add(chunk);
						document.add((Element) list);
						status = true;
						list = new List();
						testCase = null;
					}
					chunk = new Chunk(line+"\n");
					Font font = new Font(Font.TIMES_ROMAN);
					font.setSize(18);
					chunk.setFont(font);
					index++;
				}
				else
				{
					if(line.contains("not"))
					{
						status = false;
					}
					list.add(line);
				}
		//	document.add(chunk);
			}
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}	
		document.close();
	}
	public String checkTestCase(){
		String result = "";
		BufferedReader reader = null;
		ArrayList<String> testcases = new ArrayList<>();
		try {
			reader= new BufferedReader(new FileReader("C:\\Users\\Noman\\Videos\\AutomatedGameTesting-master\\AutomatedGameTesting-master\\AutomatedGameTesting-master\\Mario Side-Scroller(Game)\\flags.txt"));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String line = null;
			while((line = reader.readLine())!= null)
			{
				for(String s : Readytestcase){
					String[] tokens = line.split(",");
					if(tokens[0].equals(s)){
						if(tokens[1].equals("true")){
							result=result+ s + " transition Executed successfully !!\n";
						}
					}
				}
			}
		}
		catch (Exception e) {		 
		}
		return result;
	}
}
