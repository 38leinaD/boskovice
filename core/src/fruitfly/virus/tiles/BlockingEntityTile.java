package fruitfly.virus.tiles;

import fruitfly.virus.TextureMap.SubTexture;

public class BlockingEntityTile extends Tile {

	public BlockingEntityTile(int x, int y, SubTexture floorTexture) {
		super(x, y);
		this.floorTexture = floorTexture;
	}

}
