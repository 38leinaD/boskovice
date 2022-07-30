package fruitfly.virus.entities.decals;

import java.nio.FloatBuffer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.C.Orientation;
import fruitfly.virus.CoordinateSystem;
import fruitfly.virus.G;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.IPointSpriteEntity;
import fruitfly.virus.render.WorldRenderer;
import fruitfly.virus.tiles.Wall;

public abstract class Decal {
	public Vector3 position;
	public Vector2 wallPosition;
	public Orientation ori;
	float[] normal;
	CoordinateSystem cs;
	
	private Wall wall;
	
	public float size = 0.05f;
	public SubTexture texture;
	
	public Decal(Wall wall, Vector2 position, Orientation ori) {
		this.wall = wall;
		this.wall.getDecals().add(this);
		this.wallPosition = position.cpy();
		this.ori = ori;
		this.position = new Vector3();
		
		if (ori == Orientation.North) {
			cs = CoordinateSystem.north;
			normal = WorldRenderer.normalNorth;
			this.position.set(wall.xEnd + wallPosition.x, wall.yStart, wallPosition.y);
		}
		else if (ori == Orientation.East) {
			cs = CoordinateSystem.east;
			normal = WorldRenderer.normalEast;
			this.position.set(wall.xStart, wall.yStart + wallPosition.x, wallPosition.y);
		}
		else if (ori == Orientation.South) {
			cs = CoordinateSystem.south;
			normal = WorldRenderer.normalSouth;
			this.position.set(wall.xStart + wallPosition.x, wall.yStart, wallPosition.y);
		}
		else if (ori == Orientation.West) {
			cs = CoordinateSystem.west;
			normal = WorldRenderer.normalWest;
			this.position.set(wall.xStart, wall.yEnd + wallPosition.x, wallPosition.y);
		}
	}
	
	public void writeVertexData(FloatBuffer buffer) {
		texture.getTextureCoords(WorldRenderer.texCoords0);
		// TODO; this is not right; caluclate correct subtexture for decal
		G.world.getLightmap().getTextureCoords(wall.lightmapId, WorldRenderer.texCoords1);
		
		Vector3 w = null;
		Vector3 v = position.cpy();
		
		float ratio = texture.getWidth() / (float)texture.getHeight();
		
		w = cs.x.cpy();
		w.scl(-size/2.0f);
		v.add(w);
		w = cs.y.cpy();
		w.scl(-size/2.0f);
		v.add(w);
		buffer.put(v.x).put(v.y).put(v.z);
		buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
		buffer.put(WorldRenderer.texCoords1[2]).put(WorldRenderer.texCoords1[3]);
		buffer.put(normal);
		
		w = cs.x.cpy();
		w.scl(size);
		v.add(w);
		buffer.put(v.x).put(v.y).put(v.z);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
		buffer.put(WorldRenderer.texCoords1[4]).put(WorldRenderer.texCoords1[5]);
		buffer.put(normal);

		w = cs.x.cpy();
		w.scl(-size);
		v.add(w);
		w = cs.y.cpy();
		w.scl(size);
		v.add(w);
		buffer.put(v.x).put(v.y).put(v.z);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
		buffer.put(WorldRenderer.texCoords1[0]).put(WorldRenderer.texCoords1[1]);
		buffer.put(normal);

		w = cs.x.cpy();
		w.scl(size);
		v.add(w);

		buffer.put(v.x).put(v.y).put(v.z);
		buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
		buffer.put(WorldRenderer.texCoords1[6]).put(WorldRenderer.texCoords1[7]);
		buffer.put(normal);
	}

	public Vector3 getPosition() {
		return position;
	}

	// TODO at the moment decals are never disposed
	public void dispose() {
		wall.getDecals().remove(this);
	}
}
