package devforrest.mario.objects.creatures;



import java.awt.image.BufferedImage;

import devforrest.mario.FileWriter.WriteInFile;
import devforrest.mario.core.animation.Animation;
import devforrest.mario.core.tile.TileMap;
import devforrest.mario.objects.base.Creature;
import devforrest.mario.util.ImageManipulator;



public class Score extends Creature {
	
	private WriteInFile writer=new WriteInFile();
	
	public Animation oneHundred;
	
	public Score(int x, int y) {
		super(x, y);
		
		//writer.WriteToFile("Score::Score(int x, int y)");
		
		setIsItem(true);
		
		dy = -.45f;

		BufferedImage one_hundred = ImageManipulator.loadImage("items/Score_100_New6.png");
		
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				//writer.WriteToFile("Score::DeadAfterAnimation::endOfAnimationAction()");
				kill();
			}
		}
		
		oneHundred = new DeadAfterAnimation();
		
		oneHundred.addFrame(one_hundred, 380);
		oneHundred.addFrame(one_hundred, 380);	
		setAnimation(oneHundred);
	}
	
	public void updateCreature(TileMap map, int time) {
		
		//writer.WriteToFile("Score::updateCreature(TileMap map, int time)");
		
		this.update((int) time);
		y = y + dy * time;
		if(dy < 0) {
			dy = dy + .032f;
		} else {
			dy = 0;
		}
	}

}
