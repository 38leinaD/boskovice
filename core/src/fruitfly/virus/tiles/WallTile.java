package fruitfly.virus.tiles;

import fruitfly.virus.TextureMap.SubTexture;


public class WallTile extends Tile {

	public final Wall north, east, south, west;
	
	public WallTile(int x, int y, SubTexture texture) {
		super(x, y);
		
		north = new Wall(x + 1, y + 1, x, y + 1, 1, texture);
		east = new Wall(x + 1, y, x + 1, y + 1, 1, texture);
		south = new Wall(x, y, x + 1, y, 1, texture);
		west = new Wall(x, y + 1, x, y, 1, texture);
	}
	
	public boolean isBlockingVisibility() {
		return true;
	}
	
	public boolean isBlockingMovement() {
		return true;
	}
}
