package devforrest.mario.core.tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import devforrest.mario.FileWriter.WriteInFile;
import devforrest.mario.objects.base.Creature;
import devforrest.mario.objects.creatures.Platform;
import devforrest.mario.objects.mario.Mario;
import devforrest.mario.objects.tiles.SlopedTile;

/**
 * The TileMap class contains all data for a tile-based map. 
 */

public class TileMap {
	
	private WriteInFile writer=new WriteInFile();
	
	// fields
	private GameTile[][] tiles; 
	private List<Platform> platforms; // List of Platforms on the current screen.
	private List<Creature> creatures; // Starts containing every Creature and decreases as they die.
	private List<Creature> relevantCreatures; // List of relevant Creatures to the current frame.
											  // This is a subset of creatures.
	private List<Creature> creaturesToAdd; // List of Creatures to be added inbetween frames.
	private List<GameTile> animatedTiles;
	private List<SlopedTile> slopedTiles;
	private Mario player; 
	
	/**
	 * Constructs a new TileMap with the specified width and height (in number of tiles)
	 * of the map.
	 */
	public TileMap(int width, int height) {
		
		//writer.WriteToFile("TileMap::TileMap(int width, int height)");
		
		tiles = new GameTile[width][height];
		creatures = new LinkedList<Creature>();
		relevantCreatures = new ArrayList<Creature>();
		creaturesToAdd = new ArrayList<Creature>();
		platforms = new ArrayList<Platform>();
		animatedTiles = new ArrayList<GameTile>();
		slopedTiles = new ArrayList<SlopedTile>();
	}
	
	public GameTile[][] getTiles() {
		//writer.WriteToFile("TileMap::GameTile[][] getTiles()");
		return tiles;
	}
	
	/**
	 * @return the width of this TileMap in GameTiles.
	 */
	public int getWidth() {
		//writer.WriteToFile("TileMap::int getWidth()");
		return tiles.length;
	}
	
	/**
	 * @return the height of this TileMap in GameTiles.
	 */
	public int getHeight() {
		//writer.WriteToFile("TileMap::int getHeight()");
		return tiles[0].length;
	}
	
	/**
	 * @return the GameTiles at tiles[x][y]. If x or y is out of bounds
	 * or if tiles[x][y] == null, null is returned.
	 */
	public GameTile getTile(int x, int y) {
		
		//writer.WriteToFile("TileMap::GameTile getTile(int x, int y)");
		
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			return null;
		} else {
			if(tiles[x][y] != null) {
				return tiles[x][y];
			} else {
				return null;
			}
		}
	}
	
	/**
	 * @return the image of the GameTiles at tiles[x][y]. If x or y is out of bounds
	 * or if tiles[x][y] == null, null is returned.
	 */
	public BufferedImage getImage(int x, int y) {
		
		//writer.WriteToFile("TileMap::BufferedImage getImage(int x, int y)");
		
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			return null;
		} else {
			if(tiles[x][y] != null) {
			     return tiles[x][y].getImage();
			} else {
				return null;
			}
		}
	}
	
	/**
	 * Sets tiles[x][y] equal to parameter tile.
	 * This is used to set animated GameTiles.
	 */
	public void setTile(int x, int y, GameTile tile) {
		//writer.WriteToFile("TileMap::setTile(int x, int y, GameTile tile)");
		tiles[x][y] = tile;
	}
	
	/**
	 * Sets tiles[x][y] equal to a new Tile with no animation and the constant Image img.
	 * This is used to set non-animated GameTiles.
	 */
	public void setTile(int x, int y, BufferedImage img) {
		//writer.WriteToFile("TileMap::setTile(int x, int y, BufferedImage img)");
		tiles[x][y] = new GameTile(x, y, null, img);
	}
	
	/**
	 * @return the player sprite.
	 */
	public Mario getPlayer() {
		
		//writer.WriteToFile("TileMap::Mario getPlayer()");
		return player;
	}
	
	/**
	 * Sets the player sprite for this map.
	 */
	public void setPlayer(Mario player) {
		//writer.WriteToFile("TileMap::setPlayer(Mario player)");
		this.player = player;
	}
	
	
	/**
	 * @return a List containing every Platform in this map.
	 */
	public List<Platform> platforms() {
		////writer.WriteToFile("TileMap::List<Platform> platforms()");
		return platforms;
	}
	
	/**
	 * @return a List containing every Creature in this map.
	 */
	public List<Creature> creatures() {
		//writer.WriteToFile("TileMap::List<Creature> creatures()");
		return creatures;
	}
	
	/**
	 * @return a List containing Creatures to add to this map after the next game update.
	 */
	public List<Creature> creaturesToAdd() {
		//writer.WriteToFile("TileMap::List<Creature> creaturesToAdd()");
		return creaturesToAdd;
	}
	
	/**
	 * @return a List containing animated Tile in this map.
	 */
	public List<GameTile> animatedTiles() {
		//writer.WriteToFile("TileMap::List<GameTile> animatedTiles()");
		return animatedTiles;
	}
	
	/**
	 * @return a List containing every SlopedTile in this map.
	 */
	public List<SlopedTile> slopedTiles() {
		//writer.WriteToFile("TileMap::List<SlopedTile> slopedTiles()");
		return slopedTiles;
	}
	
	/**
	 * @return a List containing every relevant Creature in this map. 
	 * 
	 * A 'relevant Creature' is a Creature that the current frame cares about. 
	 * This is generally creatures on screen or creatures that need to be updated globally. 
	 */
	public List<Creature> relevantCreatures() {
		//writer.WriteToFile("TileMap::List<Creature> relevantCreatures()");
		return relevantCreatures;
	}
}
