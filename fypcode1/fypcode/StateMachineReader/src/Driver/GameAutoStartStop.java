package Driver;

import java.io.IOException;

public class GameAutoStartStop {
	private int status;

	public GameAutoStartStop() {
		status = 0;
	}

	public void StartGame(String jarPath) {
		
		String[] path=jarPath.split("\\\\");
		jarPath="cd \\";
		String jar=path[path.length-1];
		for(int i=1;i<path.length-1;i++){
			jarPath=jarPath+path[i]+"\\";
		}
		jarPath=jarPath.substring(0, jarPath.length()-1);
		jarPath=jarPath+" ";
		String v="cmd /c start /min cmd.exe /K \""+jarPath+ "&& java -jar "+jar+"\"";
		System.out.println(jarPath+jar);
		System.out.println(v);
		try { 
			Process p = Runtime.getRuntime()
					//C:\Users\ranahamzaintisar\fypcode1\fypcode1/fypcode
					.exec(v);
			status = 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void StopGame() {
		try {
			status = 0;
			Runtime.getRuntime().exec("taskkill /IM cmd.exe");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
