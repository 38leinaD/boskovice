package fruitfly.virus.tiles;

import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;

import fruitfly.virus.G;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.IEntity;
import fruitfly.virus.particles.Particle;
import fruitfly.virus.render.WorldRenderer;

public class Tile {
	public final int x, y;
	private boolean isBlockingMovement = false;
	
	public SubTexture floorTexture;
	private List<IEntity> entities = new LinkedList<IEntity>();
	private List<Particle> particles = new LinkedList<Particle>();
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isBlockingVisibility() {
		return false;
	}
	
	public boolean isBlockingMovement() {
		return isBlockingMovement;
	}
	
	public void setBlockingMovement(boolean blocking) {
		isBlockingMovement = blocking;
	}
	
	public void writeVertexData(FloatBuffer buffer) {
		if (getFloorTexture() == null) return;
		
		getFloorTexture().getTextureCoords(WorldRenderer.texCoords0);
		G.world.getLightmap().getTextureCoords(0, WorldRenderer.texCoords1);

		buffer.put(x).put(y).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
		buffer.put(WorldRenderer.texCoords1[2]).put(WorldRenderer.texCoords1[3]);
		buffer.put(WorldRenderer.normalUp);
		
		buffer.put(x+1.0f).put(y).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
		buffer.put(WorldRenderer.texCoords1[4]).put(WorldRenderer.texCoords1[5]);
		buffer.put(WorldRenderer.normalUp);

		buffer.put(x).put(y+1.0f).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
		buffer.put(WorldRenderer.texCoords1[0]).put(WorldRenderer.texCoords1[1]);
		buffer.put(WorldRenderer.normalUp);

		
		buffer.put(x).put(y+1.0f).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
		buffer.put(WorldRenderer.texCoords1[0]).put(WorldRenderer.texCoords1[1]);
		buffer.put(WorldRenderer.normalUp);

		buffer.put(x+1.0f).put(y).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
		buffer.put(WorldRenderer.texCoords1[4]).put(WorldRenderer.texCoords1[5]);
		buffer.put(WorldRenderer.normalUp);

		buffer.put(x+1.0f).put(y+1.0f).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
		buffer.put(WorldRenderer.texCoords1[6]).put(WorldRenderer.texCoords1[7]);
		buffer.put(WorldRenderer.normalUp);
	}

	
	public SubTexture getFloorTexture() {
		return floorTexture;
	}
	
	public List<IEntity> getResidentEntities() {
		return entities;
	}
	
	public List<Particle> getResidentParticles() {
		return particles;
	}
	
	/**
	 * Visibility-/Raycasting-related 
	 */
	
	private boolean isVisible = false;
	private boolean wasVisible = false;
	
	public boolean isVisible() {
		return isVisible;
	}
	
	public void setVisible() {
		this.isVisible = true;
	}
	
	public void resetVisibility() {
		this.wasVisible = isVisible;
		this.isVisible = false;
	}
	
	public boolean hasVisibilityChanged() {
		return this.wasVisible != this.isVisible;
	}
}
