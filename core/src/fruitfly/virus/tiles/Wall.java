package fruitfly.virus.tiles;

import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.Lightmap;
import fruitfly.virus.C.Orientation;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.decals.Decal;
import fruitfly.virus.render.WorldRenderer;

public class Wall {
	public final float xStart, yStart, xEnd, yEnd;
	public final float height;
	private Vector3 normal;
	public SubTexture texture;
	private Orientation orientation;
	
	private List<Decal> decals = new LinkedList<Decal>();
	
	private boolean isVisible = false;
	
	public int lightmapId = Lightmap.generateId();

	public Wall(float xStart, float yStart, float xEnd, float yEnd, float height, SubTexture texture) {
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.height = height;
		this.texture = texture;
		
		if (xStart < xEnd) orientation = Orientation.South;
		else if (xStart > xEnd) orientation = Orientation.North;
		else if (yStart < yEnd) orientation = Orientation.East;
		else if (yStart > yEnd) orientation = Orientation.West;

		this.normal = new Vector3(yStart -  yEnd, -(xEnd - xStart), 0.0f).nor();
	}
	
	public void writeVertexData(FloatBuffer buffer, boolean positionOnly) {
		texture.getTextureCoords(WorldRenderer.texCoords0);
		G.world.getLightmap().getTextureCoords(this.lightmapId, WorldRenderer.texCoords1);
		
		this.normal = new Vector3(yEnd -  yStart, xStart - xEnd, 0.0f).nor();
		
		buffer.put(xStart).put(yStart).put(height);
		if (!positionOnly) {
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.texCoords1[0]).put(WorldRenderer.texCoords1[1]);
			buffer.put(normal.x).put(normal.y).put(normal.z);
		}
		
		buffer.put(xStart).put(yStart).put(0.0f);
		if (!positionOnly) {
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
			buffer.put(WorldRenderer.texCoords1[2]).put(WorldRenderer.texCoords1[3]);
			buffer.put(normal.x).put(normal.y).put(normal.z);
		}
		
		buffer.put(xEnd).put(yEnd).put(0.0f);
		if (!positionOnly) {
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.texCoords1[4]).put(WorldRenderer.texCoords1[5]);
			buffer.put(normal.x).put(normal.y).put(normal.z);
		}
		
		buffer.put(xStart).put(yStart).put(height);
		if (!positionOnly) {
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
			buffer.put(WorldRenderer.texCoords1[0]).put(WorldRenderer.texCoords1[1]);
			buffer.put(normal.x).put(normal.y).put(normal.z);
		}
		
		buffer.put(xEnd).put(yEnd).put(0.0f);
		if (!positionOnly) {
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(WorldRenderer.texCoords1[4]).put(WorldRenderer.texCoords1[5]);
			buffer.put(normal.x).put(normal.y).put(normal.z);
		}
		
		buffer.put(xEnd).put(yEnd).put(height);
		if (!positionOnly) {
			buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
			buffer.put(WorldRenderer.texCoords1[6]).put(WorldRenderer.texCoords1[7]);
			buffer.put(normal.x).put(normal.y).put(normal.z);
	
		}
	}
	
	public List<Decal> getDecals() {
		return decals;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public Orientation getOrientation() {
		return orientation;
	}
}
