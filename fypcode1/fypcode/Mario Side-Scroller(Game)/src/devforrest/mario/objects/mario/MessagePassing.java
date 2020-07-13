package devforrest.mario.objects.mario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessagePassing extends Thread {
	Socket socket = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    String message = "";
    int index = 0;
	
	public void SendMessage() throws IOException{
		oos.writeObject("Enemey Ahead");
		
	}
	
	public void EndThread() throws IOException{
		 oos.close();
		 socket.close();
	}

	@Override
	public void run() {
		try {
			socket = new Socket(InetAddress.getByName("localhost"), 9876);
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject("Enemey Ahead");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
