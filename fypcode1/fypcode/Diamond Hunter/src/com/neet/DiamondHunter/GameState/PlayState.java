// The main playing GameState.
// Contains everything you need for gameplay:
// Player, TileMap, Diamonds, etc.
// Updates and draws all game objects.

package com.neet.DiamondHunter.GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

import com.neet.DiamondHunter.Entity.Diamond;
import com.neet.DiamondHunter.Entity.Item;
import com.neet.DiamondHunter.Entity.Player;
import com.neet.DiamondHunter.Entity.Sparkle;
import com.neet.DiamondHunter.HUD.Hud;
import com.neet.DiamondHunter.Main.GamePanel;
import com.neet.DiamondHunter.Manager.Data;
import com.neet.DiamondHunter.Manager.GameStateManager;
import com.neet.DiamondHunter.Manager.JukeBox;
import com.neet.DiamondHunter.Manager.Keys;
import com.neet.DiamondHunter.TileMap.TileMap;

public class PlayState extends GameState {

	// player
	private Player player;

	// tilemap
	private TileMap tileMap;

	// diamonds
	private ArrayList<Diamond> diamonds;

	// items
	private ArrayList<Item> items;

	// sparkles
	private ArrayList<Sparkle> sparkles;

	// camera position
	private int xsector;
	private int ysector;
	private int sectorSize;

	// hud
	private Hud hud;

	// events
	private boolean blockInput;
	private boolean eventStart;
	private boolean eventFinish;
	private int eventTick;
	// thread
	MessagePassing mp;
	MessagePassing1 mp1;
	MessagePassing2 mp2;
	// transition box
	private ArrayList<Rectangle> boxes;
	// check
	int count = 1;
	int count1 = 1;
	int counttemp = 1;
	int count2 = 1;

	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {

		// create lists
		diamonds = new ArrayList<Diamond>();
		sparkles = new ArrayList<Sparkle>();
		items = new ArrayList<Item>();

		// load map
		tileMap = new TileMap(16);
		tileMap.loadTiles("/Tilesets/testtileset.gif");
		tileMap.loadMap("/Maps/testmap.map");

		// create player
		player = new Player(tileMap);

		// fill lists
		populateDiamonds();
		populateItems();

		// initialize player
		player.setTilePosition(17, 17);
		player.setTotalDiamonds(diamonds.size());

		// set up camera position
		sectorSize = GamePanel.WIDTH;
		xsector = player.getx() / sectorSize;
		ysector = player.gety() / sectorSize;
		tileMap.setPositionImmediately(-xsector * sectorSize, -ysector * sectorSize);

		// load hud
		hud = new Hud(player, diamonds);

		// load music
		JukeBox.load("/Music/bgmusic.mp3", "music1");
		JukeBox.setVolume("music1", -10);
		JukeBox.loop("music1", 1000, 1000, JukeBox.getFrames("music1") - 1000);
		JukeBox.load("/Music/finish.mp3", "finish");
		JukeBox.setVolume("finish", -10);

		// load sfx
		JukeBox.load("/SFX/collect.wav", "collect");
		JukeBox.load("/SFX/mapmove.wav", "mapmove");
		JukeBox.load("/SFX/tilechange.wav", "tilechange");
		JukeBox.load("/SFX/splash.wav", "splash");

		// start event
		boxes = new ArrayList<Rectangle>();
		eventStart = true;
		eventStart();

	}

	private void populateDiamonds() {

		Diamond d;

		d = new Diamond(tileMap);
		d.setTilePosition(22, 22);
		d.addChange(new int[] { 23, 19, 1 });
		d.addChange(new int[] { 23, 20, 1 });
		diamonds.add(d);
		// new1
		d = new Diamond(tileMap);
		d.setTilePosition(19, 17);
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(12, 36);
		d.addChange(new int[] { 31, 17, 1 });
		diamonds.add(d);
		// new
		d = new Diamond(tileMap);
		d.setTilePosition(14, 33);
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(28, 4);
		d.addChange(new int[] { 27, 7, 1 });
		d.addChange(new int[] { 28, 7, 1 });
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(4, 34);
		d.addChange(new int[] { 31, 21, 1 });
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(25, 18);
		diamonds.add(d);
		// new
		d = new Diamond(tileMap);
		d.setTilePosition(29, 19);// on the middle tree of 3, *1
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(29, 13);
		diamonds.add(d);
		// new
		d = new Diamond(tileMap);
		d.setTilePosition(33, 16);
		diamonds.add(d);
		// new
		d = new Diamond(tileMap);
		d.setTilePosition(36, 4);
		diamonds.add(d);
		// new
		d = new Diamond(tileMap);
		d.setTilePosition(21, 10);
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(35, 26);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(38, 36);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(27, 25);
		diamonds.add(d);
		// new
		d = new Diamond(tileMap);// first to right of *1
		d.setTilePosition(30, 28);
		diamonds.add(d);
		// new
		d = new Diamond(tileMap);
		d.setTilePosition(23, 30);
		diamonds.add(d);
		// new
		d = new Diamond(tileMap);
		d.setTilePosition(23, 36);
		diamonds.add(d);
		// new
		d = new Diamond(tileMap);
		d.setTilePosition(29, 34);// new first water to right
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(20, 30);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(14, 25);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(4, 21);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(9, 14);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(4, 3);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(20, 14);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(13, 20);
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(18, 21);
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(27, 21);
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(30, 17);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(34, 14);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(37, 11);
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(38, 4);
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(34, 2);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(25, 28);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(22, 26);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(20, 27);
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(13, 29);
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(11, 33);
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(18, 35);
		diamonds.add(d);

		d = new Diamond(tileMap);
		d.setTilePosition(27, 32);
		diamonds.add(d);
	}

	private void populateItems() {

		Item item;

		item = new Item(tileMap);
		item.setType(Item.AXE);
		item.setTilePosition(26, 37);
		items.add(item);

		item = new Item(tileMap);
		item.setType(Item.BOAT);
		item.setTilePosition(12, 4);
		items.add(item);

	}

	public void update() throws IOException {

		// check keys
		handleInput();

		// check events
		if (eventStart)
			eventStart();
		if (eventFinish)
			eventFinish();

		// check for finish condition at 25 diamonds
		if (player.numDiamonds() == 1 && count2 == 1) {
			System.out.println("Diamonds: " + player.numDiamonds());
			mp1 = new MessagePassing1("Total" + Integer.toString(player.numDiamonds()));
			mp1.start();
			count2++;
		} else if (counttemp > count1) {
			count1 = player.numDiamonds();
			System.out.println("Diamonds: " + player.numDiamonds());
			mp1 = new MessagePassing1("Total" + Integer.toString(player.numDiamonds()));
			mp1.start();
			counttemp = 0;
		} else {
			counttemp = player.numDiamonds();
		}

		if (player.numDiamonds() >= 25 && count == 1) {

			count++;
			eventFinish = blockInput = true;
			String s = "DiamondComplete";

			mp = new MessagePassing(s);
			mp.start();
			System.out.println("Player has collecteded the diamonds");
		}

		// update camera
		int oldxs = xsector;
		int oldys = ysector;
		xsector = player.getx() / sectorSize;
		ysector = player.gety() / sectorSize;
		tileMap.setPosition(-xsector * sectorSize, -ysector * sectorSize);
		tileMap.update();

		if (oldxs != xsector || oldys != ysector) {
			JukeBox.play("mapmove");
		}

		if (tileMap.isMoving())
			return;

		// update player
		player.update();

		// update diamonds
		for (int i = 0; i < diamonds.size(); i++) {

			Diamond d = diamonds.get(i);
			d.update();

			// player collects diamond
			if (player.intersects(d)) {

				// remove from list
				diamonds.remove(i);
				i--;

				// increment amount of collected diamonds
				player.collectedDiamond();

				// play collect sound
				JukeBox.play("collect");

				// add new sparkle
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(d.getx(), d.gety());
				sparkles.add(s);

				// make any changes to tile map
				ArrayList<int[]> ali = d.getChanges();
				for (int[] j : ali) {
					tileMap.setTile(j[0], j[1], j[2]);
				}
				if (ali.size() != 0) {
					JukeBox.play("tilechange");
				}

			}
		}

		// update sparkles
		for (int i = 0; i < sparkles.size(); i++) {
			Sparkle s = sparkles.get(i);
			s.update();
			if (s.shouldRemove()) {
				sparkles.remove(i);
				i--;
			}
		}

		// update items

		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);

			if (player.intersects(item)) {
				items.remove(i); // collect axe or boat
				i--;
				item.collected(player);

				JukeBox.play("collect");
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(item.getx(), item.gety());
				sparkles.add(s);
			}
		}

	}

	public void draw(Graphics2D g) {

		// draw tilemap
		tileMap.draw(g);

		// draw player
		player.draw(g);

		// draw diamonds
		for (Diamond d : diamonds) {
			d.draw(g);
		}

		// draw sparkles
		for (Sparkle s : sparkles) {
			s.draw(g);
		}

		// draw items
		for (Item i : items) {
			i.draw(g);
		}

		// draw hud
		hud.draw(g);

		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for (int i = 0; i < boxes.size(); i++) {
			g.fill(boxes.get(i));
		}

	}

	public void handleInput() throws IOException {
		if (Keys.isPressed(Keys.ESCAPE)) {
			JukeBox.stop("music1");
			gsm.setPaused(true);
		}
		if (blockInput)
			return;
		if (Keys.isDown(Keys.LEFT)) {
			player.setLeft();
			System.out.println("Left");
			
			mp2=new MessagePassing2("Left2");
			
			mp2.start();
		}
		if (Keys.isDown(Keys.RIGHT)) {
			player.setRight();
			System.out.println("RIght");
			
			mp2=new MessagePassing2("Right3");
			
			mp2.start();
		}
		if (Keys.isDown(Keys.UP)) {
			player.setUp();
			System.out.println("Up");
			
			mp2=new MessagePassing2("Up1");
			
			mp2.start();
			
		}
		if (Keys.isDown(Keys.DOWN)) {
			player.setDown();
			System.out.println("Down");
			
			mp2=new MessagePassing2("Down0");
			
			mp2.start();
		}
		if (Keys.isPressed(Keys.SPACE))
			player.setAction();
		mp2=new MessagePassing2("Space4");
		
		mp2.start();

	}

	// ===============================================

	private void eventStart() {
		eventTick++;
		if (eventTick == 1) {
			boxes.clear();
			for (int i = 0; i < 9; i++) {
				boxes.add(new Rectangle(0, i * 16, GamePanel.WIDTH, 16));
			}
		}
		if (eventTick > 1 && eventTick < 32) {
			for (int i = 0; i < boxes.size(); i++) {
				Rectangle r = boxes.get(i);
				if (i % 2 == 0) {
					r.x -= 4;
				} else {
					r.x += 4;
				}
			}
		}
		if (eventTick == 33) {
			boxes.clear();
			eventStart = false;
			eventTick = 0;
		}
	}

	private void eventFinish() {
		eventTick++;
		if (eventTick == 1) {
			boxes.clear();
			for (int i = 0; i < 9; i++) {
				if (i % 2 == 0)
					boxes.add(new Rectangle(-128, i * 16, GamePanel.WIDTH, 16));
				else
					boxes.add(new Rectangle(128, i * 16, GamePanel.WIDTH, 16));
			}
			JukeBox.stop("music1");
			JukeBox.play("finish");
		}
		if (eventTick > 1) {
			for (int i = 0; i < boxes.size(); i++) {
				Rectangle r = boxes.get(i);
				if (i % 2 == 0) {
					if (r.x < 0)
						r.x += 4;
				} else {
					if (r.x > 0)
						r.x -= 4;
				}
			}
		}
		if (eventTick > 33) {
			if (!JukeBox.isPlaying("finish")) {
				Data.setTime(player.getTicks());
				gsm.setState(GameStateManager.GAMEOVER);
			}
		}
	}

}
