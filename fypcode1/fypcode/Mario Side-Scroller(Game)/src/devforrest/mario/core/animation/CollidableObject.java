package devforrest.mario.core.animation;

import devforrest.mario.FileWriter.WriteInFile;
import devforrest.mario.core.sound.specific.MarioSoundManager22050Hz;



public class CollidableObject extends Sprite {
	
	private WriteInFile writer=new WriteInFile();
	
	protected MarioSoundManager22050Hz soundManager;
	private boolean isCollidable;
	private boolean isOnScreen;
	
	public CollidableObject(int pixelX, int pixelY, MarioSoundManager22050Hz soundManager) {
		
		super(pixelX, pixelY);
		//writer.WriteToFile("CollidableObject::CollidableObject(int pixelX, int pixelY, MarioSoundManager22050Hz soundManager)");
		this.isCollidable = true;
		setIsOnScreen(false);
		this.soundManager = soundManager;
	}
	
	public CollidableObject(int pixelX, int pixelY) {
		
		
		this(pixelX, pixelY, null);
		//writer.WriteToFile("CollidableObject::CollidableObject(int pixelX, int pixelY)");
	}
	
	public boolean isCollidable() {
		//writer.WriteToFile("CollidableObject::boolean isCollidable()");
		
		return isCollidable;
	}
	
	public void setIsCollidable(boolean isCollidable) {
		//writer.WriteToFile("CollidableObject::setIsCollidable(boolean isCollidable)");
		
		this.isCollidable = isCollidable;
	}
	
	public boolean isOnScreen() {
		//writer.WriteToFile("CollidableObject::boolean isOnScreen()");
		return isOnScreen;
	}
	
	public void setIsOnScreen(boolean isOnScreen) {
		//writer.WriteToFile("CollidableObject::setIsOnScreen(boolean isOnScreen)");
		this.isOnScreen = isOnScreen;
	}
}
