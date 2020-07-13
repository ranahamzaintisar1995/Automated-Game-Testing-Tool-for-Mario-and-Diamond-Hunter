package Driver1;

import java.io.IOException;

public class GameAutoStartStop {
	private int status;
	public GameAutoStartStop(){
		status=0;
	}
	public void StartGame(){
		try {
    	    Process p = Runtime
    	                    .getRuntime()
    	                    .exec("cmd /c start /min cmd.exe /K \"cd.. && cd Mario Side-Scroller(Game) && java -jar mario.jar\"");
    	    status=1;
    	} catch (IOException e) {
    	        // TODO Auto-generated catch block
    	        e.printStackTrace();
    	} 
	}
	public void StopGame(){
		try {
			status=0;
	        Runtime.getRuntime().exec("taskkill /IM cmd.exe") ;
	    } catch (Exception e) {
	        e.printStackTrace();  
	    }
	}
}
