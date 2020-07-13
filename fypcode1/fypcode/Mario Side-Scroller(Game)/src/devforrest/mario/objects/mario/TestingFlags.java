package devforrest.mario.objects.mario;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TestingFlags {
	
	Boolean isIdle=false;
	Boolean isMovingForward=false;
	Boolean isMovingBackward=false;
	Boolean isGameOver=false;
	Boolean isPaused=false;
	Boolean isJumping=false;
	Boolean isCollidingWithCreature=false;
	Boolean isAbovePlateform=false;
	
	public void setIsMovingForward(Boolean check){
		this.isMovingForward = check;
	}
	public void setIsMovingBackward(Boolean check){
		this.isMovingBackward = check;
	}
	public void setIsJumping(Boolean check){
		this.isJumping = check;
	}
	public void setIsCollidingWithCreature(Boolean check){
		this.isCollidingWithCreature = check;
	}
	public void writeToFile(){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("flags.txt");
			writer.println(" runbackward," +isMovingBackward);
			writer.println(" runforward," +isMovingForward);
			writer.println(" jump," +isJumping);
			writer.println("Jump," +isJumping);
			writer.println("colliding," +isCollidingWithCreature);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
}
