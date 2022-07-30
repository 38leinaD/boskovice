package fruitfly.virus.tiles;

import java.nio.FloatBuffer;

import javax.swing.text.Position;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.particles.ParticleSystem;
import fruitfly.virus.render.WorldRenderer;

public class GlassTile extends TransparentTile {
	private boolean horizontal;
	private boolean destroyed = false;
	public GlassTile(int x, int y, SubTexture floorTexture, boolean horizontal) {
		super(x, y);
		this.floorTexture = floorTexture;
		this.horizontal = horizontal;
	}

	@Override
	public void writeVertexDataTransparent(FloatBuffer buffer) {
		if (destroyed) return;
		G.textureMap.glassWall.getTextureCoords(WorldRenderer.texCoords0);
		
		if (horizontal) {
			buffer.put(x+0.0f).put(y+0.5f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
			buffer.put(x+1.0f).put(y+0.5f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(x+0.0f).put(y+0.5f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
			
			buffer.put(x+0.0f).put(y+0.5f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
			buffer.put(x+1.0f).put(y+0.5f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(x+1.0f).put(y+0.5f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
		}
		else {
			buffer.put(x+0.5f).put(y+0.0f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
			buffer.put(x+0.5f).put(y+1.0f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(x+0.5f).put(y+0.0f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);

			
			buffer.put(x+0.5f).put(y+0.0f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
			buffer.put(x+0.5f).put(y+1.0f).put(0.0f);
			buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
			buffer.put(x+0.5f).put(y+1.0f).put(1.0f);
			buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
		}
	}

	@Override
	public boolean isBlockingMovement() {
		return !destroyed;
	}
	
	public void destroy() {
		if (destroyed) return;
		destroyed = true;
		
		G.world.particleManager.addParticleSystem(new ParticleSystem(15, false, 0.4f, new Vector3(x + 0.5f, y + 0.5f, 0.5f), new Vector3(0.0f, 0.0f, 1.0f), G.textureMap.shetteredGlass, Color.WHITE, 100));
	}
}
