package fruitfly.virus.tiles;

import java.nio.FloatBuffer;

import fruitfly.virus.G;
import fruitfly.virus.ITrigger;
import fruitfly.virus.ITriggerTarget;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.render.WorldRenderer;
import fruitfly.virus.timer.ITimeoutListener;

public class ForceFieldTile extends TransparentTile implements ITimeoutListener, ITriggerTarget {

	private boolean horizontal;
	private boolean isActive = true;
	
	public ForceFieldTile(int x, int y, boolean horizontal, SubTexture floorTexture) {
		super(x, y);
		this.floorTexture = floorTexture;
		G.timer.registerPeriodicTimer(10, this);
		this.horizontal = horizontal;
	}

	private int texId = 0;
	
	@Override
	public void timeout(Object data) {
		texId = (texId + 1) % 3;
	}

	@Override
	public void writeVertexDataTransparent(FloatBuffer buffer) {
		if (!isActive) return;
		G.textureMap.forceWall[texId].getTextureCoords(WorldRenderer.texCoords0);
		
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

	public void triggered(ITrigger t) {
		G.audio.disableForceField.play();
		isActive = false;
		G.timer.unregisterTimer(this);
	}

	@Override
	public boolean isBlockingMovement() {
		return isActive;
	}
}
