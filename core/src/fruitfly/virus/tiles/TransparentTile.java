package fruitfly.virus.tiles;

import java.nio.FloatBuffer;

public abstract class TransparentTile extends Tile {
	public TransparentTile(int x, int y) {
		super(x, y);
	}

	public abstract void writeVertexDataTransparent(FloatBuffer buffer);
}
