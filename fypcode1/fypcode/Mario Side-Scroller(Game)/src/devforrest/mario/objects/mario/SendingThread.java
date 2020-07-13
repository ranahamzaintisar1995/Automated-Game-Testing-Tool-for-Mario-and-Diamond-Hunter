package devforrest.mario.objects.mario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingWorker;

public class SendingThread extends SwingWorker<Integer, Integer> {
	Socket socket = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    String message = "";
    int index = 0;
	
	public void SendMessage() throws IOException{
		while(index != 10){
		oos.writeObject("Enemey Ahead");
		index++;
		System.out.println(index);
		}
	}
	
	public void EndThread() throws IOException{
		 oos.close();
	}

	@Override
	protected Integer doInBackground() throws Exception {
		try {
			socket = new Socket(InetAddress.getByName("localhost"), 9876);
			oos = new ObjectOutputStream(socket.getOutputStream());
			//while(index != 10){
				oos.writeObject("Enemey Ahead");
			//	index++;
			//	System.out.println(index);
			//	}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
