package fruitfly.virus.entities.decals;

import com.badlogic.gdx.math.Vector2;

import fruitfly.virus.C.Orientation;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.tiles.Wall;

public class Poster extends Decal {

	public Poster(Wall wall, Vector2 position, Orientation ori, SubTexture poster) {
		super(wall, position, ori);
		this.texture = poster;
		this.size = 0.8f;
	}

}
