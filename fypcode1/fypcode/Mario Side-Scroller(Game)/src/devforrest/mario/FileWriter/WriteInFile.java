package devforrest.mario.FileWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class WriteInFile {

	private static PrintWriter writer = null;
	
	public void WriteToFile(String line) {
		
		try {
			//writer = new PrintWriter(new BufferedWriter(new FileWriter("TrackRecords.txt",true)));
			//writer.println(line);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//writer.close();
		}
	}

}
