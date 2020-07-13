package com.neet.DiamondHunter.GameState;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessagePassing1 extends Thread {
  String message = null;
    int index = 0;
	String str=null;
	 Socket clientSocket = null;
	    DataInputStream is = null;
	    PrintStream os = null;
	    DataInputStream inputLine = null;
	public MessagePassing1(String s) {
		// TODO Auto-generated constructor stub
		str=s;
	}

public void closeConnection(){
	try {
		clientSocket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


	@Override
	public void run() {
		try {
			
			clientSocket = new Socket("localhost", 2000);
		     os = new PrintStream(clientSocket.getOutputStream());
		     
			
		} catch (UnknownHostException e) {
			System.err.println("unknown host "+e);
		} catch (IOException e) {
			System.err.println("NO I/O for connection to host"+e);
		}
		if(clientSocket!=null && os!=null){
			try {
				System.out.println("client has started");
			     os.println(str);
			     os.close();
			     clientSocket.close();
				
			} catch (UnknownHostException e) {
				System.err.println("Trying to connect to unknown host "+e);
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("IO exception "+e);
				
			}
		}
	}
}
