package Driver;
import Controller.*;
import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import de.ksquared.system.keyboard.KeyEvent;

public class RunTestPath {
	public void run(TestPath path) throws IOException{
		BufferedReader br2;
	
		ArrayList<StateMachineTransition> trans=path.getStateMachineTransitions();
		for(int i=0;i<trans.size();i++){
			Robot r;
			try {
				FileWriter fw_cs = new FileWriter("ForCurrentState.txt", true);
				BufferedWriter bw_cs = new BufferedWriter(fw_cs);
				PrintWriter out_cs = new PrintWriter(bw_cs);
				out_cs.println(trans.get(i).getSourceState());
				out_cs.close();
			} catch (IOException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			
			
			
			try {
				FileWriter fw_ct = new FileWriter("ForCurrentTransition.txt", true);
				BufferedWriter bw_ct = new BufferedWriter(fw_ct);
				PrintWriter out_ct = new PrintWriter(bw_ct);
				out_ct.println(trans.get(i).getTransitionName());
				out_ct.close();
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			
			
			
			try {
				//ArrayList<StateMachineTransition> temp=new ArrayList<StateMachineTransition>();
				FileWriter fw_nt;
				fw_nt = new FileWriter("ForNextTransition.txt", true);
				BufferedWriter bw_nt = new BufferedWriter(fw_nt);
				PrintWriter out_nt = new PrintWriter(bw_nt);
				
				if(i+1<trans.size()-1){
					//temp.addAll(trans.get(i).getTargetState());
					out_nt.println(trans.get(i+1).getTransitionName());
				}
				out_nt.close();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}	
		
			
			
			try {
			FileWriter fw_pt;
				fw_pt = new FileWriter("ForPreviousTransition.txt", true);
				BufferedWriter bw_pt = new BufferedWriter(fw_pt);
				PrintWriter out_pt = new PrintWriter(bw_pt);
				if(i>0){
					//temp.addAll(trans.get(i).getTargetState());
					out_pt.println(trans.get(i-1).getTransitionName());
				}
				out_pt.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			
			
			try {
				r = new Robot();
				br2 = new BufferedReader(new FileReader("GameCordinates.txt"));
				//if(br2.readLine().contains(s))
				//String last_coordinate=br2.readLine();
				List<String> tmp = new ArrayList<String>();
				String lastCoordinate=null;
				String Line;
				String temp=trans.get(i).getTransitionName();
			    do {
			        Line = br2.readLine();
			        tmp.add(Line);
			        //out.print(ch+"<br/>"); 
			    } while (Line != null);
			    
			    for(int j=0;j<tmp.size()-1;j++) {
			    	
			        //System.out.println(j+tmp.get(j)+"<br/>");
			        if(j==tmp.size()-2){
			        	lastCoordinate=tmp.get(j);
			        }
			        String[] moves={"RunForward","RunBackward","RunDown","RunUp"};
			        if(lastCoordinate!=null &&tmp.get(j).contains(lastCoordinate)&&tmp.get(j).contains(trans.get(i).getTransitionName())){
			        	int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
			        	while(moves[randomNum].contains(trans.get(i).getTransitionName())){
			        		randomNum = ThreadLocalRandom.current().nextInt(0, 4);
			        	}
			        	temp=moves[randomNum];
			        }
			    }
				int count=0;
				
				//System.out.println(lastCoordinate);
				/*try {
				    String line;
				    while ((line = br2.readLine()) != null) {
				    	if (count==0){
				    		
				    	}
				       // process the line.
				    }
				}*/
				if(temp.contains("RunForward")){
					r.keyPress(KeyEvent.VK_RIGHT);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					r.keyRelease(KeyEvent.VK_RIGHT);
					
					r.keyPress(KeyEvent.VK_SPACE);
					
					r.keyRelease(KeyEvent.VK_SPACE);
				}
				 if(temp.contains("RunBackward")){
					r.keyPress(KeyEvent.VK_LEFT);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					r.keyRelease(KeyEvent.VK_LEFT);
					r.keyPress(KeyEvent.VK_SPACE);
					
					r.keyRelease(KeyEvent.VK_SPACE);
				}
				 if(temp.contains("RunUp")){
					r.keyPress(KeyEvent.VK_UP);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					r.keyRelease(KeyEvent.VK_UP);
					r.keyPress(KeyEvent.VK_SPACE);
					
					r.keyRelease(KeyEvent.VK_SPACE);
				}
				if(temp.contains("RunDown")){
					r.keyPress(KeyEvent.VK_DOWN);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					r.keyRelease(KeyEvent.VK_DOWN);
					r.keyPress(KeyEvent.VK_SPACE);
					
					r.keyRelease(KeyEvent.VK_SPACE);
				}
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void run(ArrayList<StateMachineTransition> trans){
		
		for(int i=0;i<trans.size();i++){
			Robot r;
			try {
				r = new Robot();
			
				if(trans.get(i).getTransitionName().contains("RunForward")){
					r.keyPress(KeyEvent.VK_RIGHT);
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					r.keyRelease(KeyEvent.VK_RIGHT);
				}
				if(trans.get(i).getTransitionName().contains("RunBackward")){
					r.keyPress(KeyEvent.VK_LEFT);
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					r.keyRelease(KeyEvent.VK_LEFT);
				}
				if(trans.get(i).getTransitionName().contains("RunUp")){
					r.keyPress(KeyEvent.VK_UP);
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					r.keyRelease(KeyEvent.VK_UP);
				}
				if(trans.get(i).getTransitionName().contains("RunDown")){
					r.keyPress(KeyEvent.VK_DOWN);
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					r.keyRelease(KeyEvent.VK_DOWN);
				}
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
