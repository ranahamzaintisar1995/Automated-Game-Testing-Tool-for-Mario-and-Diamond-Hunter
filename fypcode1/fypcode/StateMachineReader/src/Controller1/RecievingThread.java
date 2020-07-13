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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Driver1.RunTestcaseThread;

public class RecievingThread extends Thread {

		ServerSocket server;
    	int port = 9876;
    	int minutes=0;
    	ObjectInputStream ois = null;
    	Socket socket = null;
    	RunTestcaseThread rtt = null;
    	ArrayList<String> testCases = new ArrayList<String>();
    	int [] Testno;
    	public RecievingThread(ArrayList<String> Cases, int min ) throws IOException{
    		server = new ServerSocket(port);   
    		this.testCases = Cases;
    		this.minutes = min;
    	}
    	public RecievingThread(ArrayList<String> Cases, int [] TestNo ) throws IOException{
    		server = new ServerSocket(port);   
    		this.testCases = Cases;
    		this.Testno = TestNo;
    	}
    	public void SetList(){
    		if (minutes!=0)
    			rtt = new RunTestcaseThread(testCases,minutes);
    		else
    			rtt = new RunTestcaseThread(testCases,Testno);
    		rtt.start();
    	}
    	
    	public void run()
    	{
    		
    		String message = "a";
    		char tempchar;
    		int index = 0;
    		int foo = 0;
			
			try{
			FileWriter fw_mcoins = new FileWriter("ForMarioCoins.txt", true);
			BufferedWriter bw_mcoins = new BufferedWriter(fw_mcoins);
			PrintWriter out_mcoins = new PrintWriter(bw_mcoins);
			
			
			FileWriter fw_mhealth = new FileWriter("ForMarioHealth.txt", true);
			BufferedWriter bw_mhealth = new BufferedWriter(fw_mhealth);
			PrintWriter out_mhealth = new PrintWriter(bw_mhealth);
			
			FileWriter fw_menemy = new FileWriter("ForMarioEnemy.txt", true);
			BufferedWriter bw_menemy = new BufferedWriter(fw_menemy);
			PrintWriter out_menemy = new PrintWriter(bw_menemy);
	      while(true)
	      {
	    	  index ++;
	            
					socket = server.accept();
					ois = new ObjectInputStream(socket.getInputStream());
		            
					message = (String) ois.readObject();
					if(message.equals("Enemey Ahead")){
							out_menemy.println("Enemy Ahead");
							rtt.setEnemeyFlag(true);
					}else if(message.contains("coins")){
						String temp = message;
						System.out.println(message );
						
						if (temp.length() == 6) {

							tempchar = temp.charAt(5);
							foo = Character.getNumericValue(tempchar);
							System.out.println("current coin is: " + foo);
							out_mcoins.println("coin: "+foo);
						} else if (temp.length() == 7) {
							char tempchar1;
							tempchar = temp.charAt(5);
							tempchar1 = temp.charAt(6);
							String t = Character.toString(tempchar) + Character.toString(tempchar1);
							foo = Integer.parseInt(t);
							System.out.println("current coin is: " + foo);
							out_mcoins.println("coins: "+foo);
						}
					}else if(message.contains("Health")){
						System.out.println("Current health is:"+message);
						String temp = message;

						if (temp.length() == 7) {

							tempchar = temp.charAt(6);
							foo = Character.getNumericValue(tempchar);
							System.out.println("Health is: " + foo);
							out_mhealth.println("Health: "+foo);
						}
					}
					out_mcoins.close();
					out_mhealth.close();
					try {
						ois.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				
	      }
 			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    	public void EndThread() throws IOException{
			ois.close();
			socket.close();
    	}
}
