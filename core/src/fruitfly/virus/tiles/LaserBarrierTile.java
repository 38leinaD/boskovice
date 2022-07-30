package fruitfly.virus.tiles;

import java.nio.FloatBuffer;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.IEntity;
import fruitfly.virus.entities.damager.IDamager;
import fruitfly.virus.entities.npc.NPC;
import fruitfly.virus.render.WorldRenderer;
import fruitfly.virus.timer.ITimeoutListener;

public class LaserBarrierTile extends TransparentTile implements ITimeoutListener, IDamager {

	private int pattern;
	private int currentTone = 0;
	private static final int toneWidth = 50;
	private int ticker = 0;
	private float interp = 0.0f;
	private int[] sideTex = new int[4];
	
	public LaserBarrierTile(int x, int y, int pattern) {
		super(x, y);
		this.floorTexture = G.textureMap.laserBarrierFloor;
		this.pattern = pattern;
		
		for (int i=0; i<4; i++) {
			sideTex[i] = MathUtils.random(2);
		}
		
		G.timer.registerPeriodicTimer(1, this);
	}

	@Override
	public void writeVertexDataTransparent(FloatBuffer buffer) {

		float height = Interpolation.bounceOut.apply(interp);
		float inset = 0.0f;
		
		// South
		G.textureMap.laserBarrierWall[sideTex[0]].getTextureCoords(WorldRenderer.texCoords0);
		buffer.put(x+0.0f).put(y).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
		buffer.put(x+1.0f).put(y).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
		buffer.put(x+inset).put(y+inset).put(height);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
		
		buffer.put(x+inset).put(y+inset).put(height);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
		buffer.put(x+1.0f).put(y).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
		buffer.put(x+1.0f-inset).put(y+inset).put(height);
		buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
		
		// North
		G.textureMap.laserBarrierWall[sideTex[1]].getTextureCoords(WorldRenderer.texCoords0);
		buffer.put(x+0.0f).put(y+1.0f).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
		buffer.put(x+1.0f).put(y+1.0f).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
		buffer.put(x+inset).put(y+1.0f-inset).put(height);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
		
		buffer.put(x+inset).put(y+1.0f-inset).put(height);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
		buffer.put(x+1.0f).put(y+1.0f).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
		buffer.put(x+1.0f-inset).put(y+1.0f-inset).put(height);
		buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);

		// West
		G.textureMap.laserBarrierWall[sideTex[2]].getTextureCoords(WorldRenderer.texCoords0);
		buffer.put(x).put(y+0.0f).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
		buffer.put(x).put(y+1.0f).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
		buffer.put(x+inset).put(y+inset).put(height);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
		buffer.put(x+inset).put(y+inset).put(height);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
		buffer.put(x).put(y+1.0f).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
		buffer.put(x+inset).put(y+1.0f-inset).put(height);
		buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
		
		// East
		G.textureMap.laserBarrierWall[sideTex[3]].getTextureCoords(WorldRenderer.texCoords0);
		buffer.put(x+1.0f).put(y+0.0f).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);
		buffer.put(x+1.0f).put(y+1.0f).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
		buffer.put(x+1.0f-inset).put(y+inset).put(height);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
		buffer.put(x+1.0f-inset).put(y+inset).put(height);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);
		buffer.put(x+1.0f).put(y+1.0f).put(0.0f);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);
		buffer.put(x+1.0f-inset).put(y+1.0f-inset).put(height);
		buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
		
	}

	@Override
	public void timeout(Object data) {
		if (G.game.ticker % 10 == 0) {
			for (int i=0; i<4; i++) {
					sideTex[i] = (sideTex[i] + 1) % 3;
			}
		}
		
		if (G.game.ticker % 20 == 0 && ((pattern >> currentTone) & 1) == 1) {
			for (IEntity e : getResidentEntities()) {
				if (e instanceof NPC) {
					NPC npc = (NPC) e;
					((NPC) e).hit(this, npc.getPosition());
				}
			}
			
			if (G.player.getTile() == this) {
				G.player.hit(this, G.player.position);
			}
		}
		
		ticker++;
		pattern = 7;
		
		if (ticker == toneWidth) {
			currentTone = (currentTone + 1) % 8;
			ticker = 0;
		}
		if (((pattern >> currentTone) & 1) == 1) {
			if (ticker <= 5) interp = ticker / 5.0f;
			else if (ticker >= toneWidth - 5) interp = 1 - ((ticker - (toneWidth - 5)) / 5.0f);
			else interp = 1.0f;
		}
		else {
			interp = 0.0f;
		}

	}

	@Override
	public Vector3 getPosition() {
		return null;
	}
	
	@Override
	public Vector3 getDirection() {
		return new Vector3(MathUtils.random(0.5f), MathUtils.random(0.5f), 1.0f);
	}

	@Override
	public int getDamage() {
		return 10;
	}

	@Override
	public Sound getDamageSound(IEntity e) {
		return G.audio.laserHitSlime;
	}

}
