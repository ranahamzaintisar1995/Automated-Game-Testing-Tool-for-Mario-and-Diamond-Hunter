// The only subclass the fully utilizes the
// Entity superclass (no other class requires
// movement in a tile based map).
// Contains all the gameplay associated with
// the Player.

package com.neet.DiamondHunter.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.neet.DiamondHunter.GameState.MessagePassing;
import com.neet.DiamondHunter.GameState.MessagePassing1;
import com.neet.DiamondHunter.Manager.Content;
import com.neet.DiamondHunter.Manager.JukeBox;
import com.neet.DiamondHunter.Manager.Keys;
import com.neet.DiamondHunter.TileMap.TileMap;

public class Player extends Entity {
	
	// sprites
	private BufferedImage[] downSprites;
	private BufferedImage[] leftSprites;
	private BufferedImage[] rightSprites;
	private BufferedImage[] upSprites;
	private BufferedImage[] downBoatSprites;
	private BufferedImage[] leftBoatSprites;
	private BufferedImage[] rightBoatSprites;
	private BufferedImage[] upBoatSprites;
	
	// animation
	private final int DOWN = 0;
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	private final int DOWNBOAT = 4;
	private final int LEFTBOAT = 5;
	private final int RIGHTBOAT = 6;
	private final int UPBOAT = 7;
	
	// gameplay
	private int numDiamonds;
	private int totalDiamonds;
	private boolean hasBoat;
	private boolean hasAxe;
	private boolean onWater;
	private long ticks;
	private int health;
	//check
	int count=1;
//	private int bonus;
	//thread
	MessagePassing mp;
	MessagePassing1 mp1;
	public Player(TileMap tm) {
		
		super(tm);
		
		width = 16;
		height = 16;
		cwidth = 12;
		cheight = 12;
		health= 3;
		//bonus=0;
		moveSpeed = 2;
		
		numDiamonds = 0;
		
		downSprites = Content.PLAYER[0];
		leftSprites = Content.PLAYER[1];
		rightSprites = Content.PLAYER[2];
		upSprites = Content.PLAYER[3];
		downBoatSprites = Content.PLAYER[4];
		leftBoatSprites = Content.PLAYER[5];
		rightBoatSprites = Content.PLAYER[6];
		upBoatSprites = Content.PLAYER[7];
		
		animation.setFrames(downSprites);
		animation.setDelay(10);
		
	}
	
	private void setAnimation(int i, BufferedImage[] bi, int d) {
		currentAnimation = i;
		animation.setFrames(bi);
		animation.setDelay(10);
	}
	
	public void collectedDiamond() { numDiamonds++; }
	public int numDiamonds() { return numDiamonds; }
	public int getTotalDiamonds() { return totalDiamonds; }
	public void setTotalDiamonds(int i) { totalDiamonds = i; }
	
	public void gotBoat() { hasBoat = true; tileMap.replace(22, 4); }
	public void gotAxe() { hasAxe = true; }
	public boolean hasBoat() { return hasBoat; }
	public boolean hasAxe() { return hasAxe; }
	
	// Used to update time.
	public long getTicks() { return ticks; }
	
	// Keyboard input. Moves the player.
	public void setDown() {
		super.setDown();
	}
	public void setLeft() {
		super.setLeft();
	}
	public void setRight() {
		super.setRight();
	}
	public void setUp() {
		super.setUp();
	}
	
	// Keyboard input.
	// If Player has axe, dead trees in front
	// of the Player will be chopped down.
	public void setAction() throws IOException {
	if(hasAxe) {
		//bonus++;
			if(currentAnimation == UP && tileMap.getIndex(rowTile - 1, colTile) == 21) {
				tileMap.setTile(rowTile - 1, colTile, 1);
				JukeBox.play("tilechange");
			}
			if(currentAnimation == DOWN && tileMap.getIndex(rowTile + 1, colTile) == 21) {
				tileMap.setTile(rowTile + 1, colTile, 1);
				JukeBox.play("tilechange");
			}
			if(currentAnimation == LEFT && tileMap.getIndex(rowTile, colTile - 1) == 21) {
				tileMap.setTile(rowTile, colTile - 1, 1);
				JukeBox.play("tilechange");
			}
			if(currentAnimation == RIGHT && tileMap.getIndex(rowTile, colTile + 1) == 21) {
				tileMap.setTile(rowTile, colTile + 1, 1);
				JukeBox.play("tilechange");
			}

		}
		// health for small tree
			if(currentAnimation == UP && tileMap.getIndex(rowTile - 1, colTile) == 21) {
				health--;
			}
			else if(currentAnimation == DOWN && tileMap.getIndex(rowTile + 1, colTile) == 21) {
				health--;
			}
			else if(currentAnimation == LEFT && tileMap.getIndex(rowTile, colTile - 1) == 21) {
				health--;
			}
			else if(currentAnimation == RIGHT && tileMap.getIndex(rowTile, colTile + 1) == 21) {
				health--;
			}
			
			//health for water	
			if(!hasBoat){
			if(currentAnimation == UP && tileMap.getIndex(rowTile - 1, colTile) == 22) {
				health--;
			}
			else if(currentAnimation == DOWN && tileMap.getIndex(rowTile + 1, colTile) == 22) {
				health--;
			}
			else if(currentAnimation == LEFT && tileMap.getIndex(rowTile, colTile - 1) == 22) {
				health--;
			}
			else if(currentAnimation == RIGHT && tileMap.getIndex(rowTile, colTile + 1) == 22) {
				health--;
			}
			}
			
			
			
			// System.out.println("Health : "+health);
		//Thread for health
			if(health == 0)
			{
			String s="Dead";
			mp=new MessagePassing(s);
			System.out.println(s);
			mp.start(); 
			System.out.println("Health : "+health); 
			mp1=new MessagePassing1("Health"+Integer.toString(health));
			mp1.start();
			System.exit(0);
			}else{
				mp1=new MessagePassing1("Health"+Integer.toString(health));
				mp1.start();
				System.out.println("Health : "+health);   
			}
			

		
	}
	/*public void setAction1() throws IOException {
		if(!hasBoat){
			if(currentAnimation == UP && tileMap.getIndex(rowTile - 1, colTile) == 22 &&Keys.isDown(Keys.UP)) {
				health--;
			}
			else if(currentAnimation == DOWN && tileMap.getIndex(rowTile + 1, colTile) == 22 && Keys.isDown(Keys.DOWN)) {
				health--;
			}
			else if(currentAnimation == LEFT && tileMap.getIndex(rowTile, colTile - 1) == 22&& Keys.isDown(Keys.LEFT)) {
				health--;
			}
			else if(currentAnimation == RIGHT && tileMap.getIndex(rowTile, colTile + 1) == 22&& Keys.isDown(Keys.RIGHT)) {
				health--;
			}
			
		}
		
		if(health == 0)
		{
		String s="Dead";
		mp=new MessagePassing(s);
		
		mp.start(); 
		System.out.println("Health : "+health); 
		System.exit(0);
		}else{
			System.out.println("Health : "+health);   
		}
	}*/
	
	public void update() {
		
		ticks++;
		
		
		// check if on water
		boolean current = onWater;
		if(tileMap.getIndex(ydest / tileSize, xdest / tileSize) == 4) {
			onWater = true;
		}
		else {
			onWater = false;
		}
		// if going from land to water
		if(!current && onWater) {
			JukeBox.play("splash");	
		}
		
			
		
		// set animation
		if(down) {
			if(onWater && currentAnimation != DOWNBOAT) {
				setAnimation(DOWNBOAT, downBoatSprites, 10);
			}
			else if(!onWater && currentAnimation != DOWN) {
				setAnimation(DOWN, downSprites, 10);
			}
		}
		if(left) {
			if(onWater && currentAnimation != LEFTBOAT) {
				setAnimation(LEFTBOAT, leftBoatSprites, 10);
			}
			else if(!onWater && currentAnimation != LEFT) {
				setAnimation(LEFT, leftSprites, 10);
			}
		}
		if(right) {
			if(onWater && currentAnimation != RIGHTBOAT) {
				setAnimation(RIGHTBOAT, rightBoatSprites, 10);
			}
			else if(!onWater && currentAnimation != RIGHT) {
				setAnimation(RIGHT, rightSprites, 10);
			}
		}
		if(up) {
			if(onWater && currentAnimation != UPBOAT) {
				setAnimation(UPBOAT, upBoatSprites, 10);
			}
			else if(!onWater && currentAnimation != UP) {
				setAnimation(UP, upSprites, 10);
			}
		}
		
		// update position
		super.update();
		
	}
	
	// Draw Player.
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}