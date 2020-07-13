package Driver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class RecievingThread1 extends MessagingPassingGeneric {
	int check = 0;
	ServerSocket serverSocket;
	int port = 2000;
	int minutes = 0;
	DataInputStream is = null;
	Socket clientSocket = null;
	ArrayList<String> testCases = new ArrayList<String>();
	int[] Testno;
	RunTestcaseThread rtt = null;

	public RecievingThread1() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("I/O error connecting to the port" + e);
		}
	}

	public int getCheck() {
		return check;
	}

	public void run() {

		String message = null;
		int foo = 0;
		char tempchar = '\n';
		int index = 0;

		index++;
		try {
			File file = new File("users.txt");
			file.createNewFile();
			while (true) 
			{
				FileWriter fw = new FileWriter("sync.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
						
				FileWriter fw_diamond = new FileWriter("ForDiamond.txt", true);
				BufferedWriter bw_diamond = new BufferedWriter(fw_diamond);
				PrintWriter out_diamond = new PrintWriter(bw_diamond);
							
				FileWriter fw_goals = new FileWriter("ForGoals.txt", true);
				BufferedWriter bw_goal = new BufferedWriter(fw_goals);
				PrintWriter out_goals = new PrintWriter(bw_goal);
							
				FileWriter fw_health = new FileWriter("ForHealth.txt", true);	
				BufferedWriter bw_health = new BufferedWriter(fw_health);
				PrintWriter out_health = new PrintWriter(bw_health);
				
				clientSocket = serverSocket.accept();

				is = new DataInputStream(clientSocket.getInputStream());
				message = is.readLine();

				if (message != null) {

					if (message.contains("Dead")) {
						out.println("Dead");
					} else if (message.contains("DiamondComplete")) {
						out.println("DiamondComplete");
					} else if (message.contains("BonusComplete")) {
						out.println("BonusComplete");
					} else if (message.contains("Treeahead")) {
						out.println("TreeNotAhead");
						check = 2;
						// e.setCheck(2);
					} else if (message.contains("Total")) {

						String temp = message;

						if (temp.length() == 6) {

							tempchar = temp.charAt(5);
							foo = Character.getNumericValue(tempchar);
							out_diamond.println("Diamond: "+foo);
						} else if (temp.length() == 7) {
							char tempchar1;
							tempchar = temp.charAt(5);
							tempchar1 = temp.charAt(6);
							String t = Character.toString(tempchar) + Character.toString(tempchar1);
							foo = Integer.parseInt(t);
							out_diamond.println("Diamond: "+foo);
						}

						// System.out.println("Diamonds collected are :"+);
					}else if(message.contains("Health")){
						String temp = message;

						if (temp.length() == 7) {

							tempchar = temp.charAt(6);
							foo = Character.getNumericValue(tempchar);
							out_health.println("Health: "+foo);
						}
					}
					else if(message.contains("Axe")){
						
						out_goals.println("Bonus: "+1);
						}else if(message.contains("Boat")){
							
							out_goals.println("Bonus: "+2);
							}else if(message.contains("Axe+Boat")){
								
								out_goals.println("Bonus: "+2);
								}
					/*else {
						System.out.println("Tree ahead ");
						out.println("TreeAhead ");
						check = 1;
						// e.setCheck(1);
					}*/

				} else {
					System.out.println("null value");
				}

				out.close();
				out_diamond.close();
				out_health.close();
				out_goals.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
