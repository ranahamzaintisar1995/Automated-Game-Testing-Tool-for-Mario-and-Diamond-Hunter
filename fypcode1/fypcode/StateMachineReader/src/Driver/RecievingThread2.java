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

public class RecievingThread2 extends MessagingPassingGeneric {
	int check = 0;
	ServerSocket serverSocket;
	int port = 12345;
	int minutes = 0;
	DataInputStream is = null;
	Socket clientSocket = null;
	ArrayList<String> testCases = new ArrayList<String>();
	int[] Testno;
	RunTestcaseThread rtt = null;
	public RecievingThread2() {
		// TODO Auto-generated constructor stub
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("I/O error connecting to the port" + e);

		}
	}

	public int getCheck() {
		return check;
	}

	@SuppressWarnings("deprecation")
	public void run() {

		String message = null;

		try {

			File file = new File("users.txt");

			file.createNewFile();

			int count=0;
			while (true) {
				FileWriter fw = new FileWriter("GameTransition.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out2 = new PrintWriter(bw);
				
				
				FileWriter fw1 = new FileWriter("GameCordinates.txt", true);
				BufferedWriter bw1 = new BufferedWriter(fw1);
				PrintWriter out = new PrintWriter(bw1);
				
				clientSocket = serverSocket.accept();

				is = new DataInputStream(clientSocket.getInputStream());
				message = is.readLine();

				if (message != null) {
					if(message.contains("Up")) {
						if (count==1){
							out.println("RunUp");
							count=0;
						}
						out2.println("RunUp");
					}else if(message.contains("Left")){
						if (count==1){
							out.println("RunBackwards");
							count=0;
						}
						out2.println("RunBackward");
					}else if(message.contains("Down")){
						if (count==1){
							out.println("RunDown");
							count=0;
						}
						out2.println("RunDown");
					}else if(message.contains("3")){
						if (count==1){
							out.println("RunForward");
							count=0;
						}
						out2.println("RunForward");
					}else if(message.contains("Q")){
						out.print(message);
						count=1;
					}
					
				} else {
					System.out.println("null value");
				}
				out2.close();
				out.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
