package fruitfly.virus.entities.decals;

import com.badlogic.gdx.math.Vector2;

import fruitfly.virus.C.Orientation;
import fruitfly.virus.G;
import fruitfly.virus.tiles.Wall;

public class LaserHitDecal extends Decal {

	public LaserHitDecal(Wall wall, Vector2 position, Orientation ori) {
		super(wall, position, ori);
		texture = G.textureMap.laserHitDecal;
	}

}
