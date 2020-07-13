package devforrest.mario.objects.creatures;

import java.awt.image.BufferedImage;

import devforrest.mario.FileWriter.WriteInFile;
import devforrest.mario.core.animation.Animation;
import devforrest.mario.core.tile.TileMap;
import devforrest.mario.objects.base.Creature;
import devforrest.mario.util.ImageManipulator;




public class Mushroom extends Creature {
	
	private WriteInFile writer=new WriteInFile();
	
	private Animation redMushroom;
	private int updateNum;
	
	public Mushroom(int pixelX, int pixelY) {
		super(pixelX, pixelY);
		
		//writer.WriteToFile("Mushroom::Mushroom(int pixelX, int pixelY)");
		
		setIsItem(true);
		setIsAlwaysRelevant(true);
		BufferedImage shroom = ImageManipulator.loadImage("items/Mushroom.png");
		redMushroom = new Animation();
		redMushroom.addFrame(shroom, 1000);
		redMushroom.addFrame(shroom, 1000);
		setAnimation(redMushroom);
		updateNum = 0;
		dy = -.15f;
		dx = .07f;
	}
	
	public void updateCreature(TileMap map, int time) {
		
		//writer.WriteToFile("Mushroom::updateCreature(TileMap map, int time)");
		
		if(updateNum < 10) {
			setX(getX() + getdX()*time);
			setY(getY() + getdY()*time);
		} else if(updateNum < 200){
			super.updateCreature(map, time);
		} else if(updateNum < 300) {
			if(updateNum % 4 == 0 || updateNum % 4 == 1) {
				setIsInvisible(true);
			} else {
				setIsInvisible(false);
			}
			super.updateCreature(map, time);
		} else {
			kill();
		}
		updateNum += 1;
	}
}

