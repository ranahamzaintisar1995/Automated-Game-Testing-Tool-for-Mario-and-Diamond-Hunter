package devforrest.mario.core.animation;

import java.awt.Graphics;

import devforrest.mario.FileWriter.WriteInFile;

/**
 * Animatible is an abstract class that a class should extend if it wants to be drawn using
 * an Animation. 
 */

abstract public class Animatible {
	
	private WriteInFile writer=new WriteInFile();
	
	private Animation currAnim;
	private int offsetX;
	private int offsetY;

	public abstract void draw(Graphics g, int pixelX, int pixelY);
	public abstract void draw(Graphics g, int pixelX, int pixelY, int offsetX, int offsetY);
	public abstract int getHeight();
	public abstract int getWidth();

	public Animation currentAnimation() {
		
		//writer.WriteToFile("Animatible::Animation currentAnimation()");
		return currAnim;
	}
	
	public void setAnimation(Animation currAnim) {
		//writer.WriteToFile("Animatible::setAnimation(Animation currAnim)");
		this.currAnim = currAnim;
	}
	
	public void update(int time) {
		//writer.WriteToFile("Animatible::update(int time)");
		currAnim.update(time);
	}
	
	public void setOffsetX(int offsetX) {
		//writer.WriteToFile("Animatible::setOffsetX(int offsetX)");
		this.offsetX = offsetX;
	}
	
	public void setOffsetY(int offsetY) {
		//writer.WriteToFile("Animatible::setOffsetY(int offsetY)");
		this.offsetY = offsetY;
	}
	
	public int getOffsetX() {
		//writer.WriteToFile("Animatible::int getOffsetX()");
		return offsetX;
	}
	
	public int getOffsetY() {
		//writer.WriteToFile("Animatible::int getOffsetY()");
		return offsetY;
	}
}
