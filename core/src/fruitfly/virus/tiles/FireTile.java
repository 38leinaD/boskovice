package fruitfly.virus.tiles;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.IEntity;
import fruitfly.virus.entities.damager.IDamager;
import fruitfly.virus.entities.npc.NPC;
import fruitfly.virus.particles.ParticleSystem;
import fruitfly.virus.timer.ITimeoutListener;

public class FireTile extends Tile implements ITimeoutListener, IDamager {

	public FireTile(int x, int y, SubTexture floor) {
		super(x, y);
		this.floorTexture = floor;
		
		ParticleSystem ps = new ParticleSystem(10, false, 0.2f, new Vector3(x+0.5f, y+0.5f, 0.1f), null, G.textureMap.fire, Color.WHITE, -1);
		ps.ticksPerFrame = 10;
		G.world.particleManager.addParticleSystem(ps);
		G.timer.registerPeriodicTimer(10, this);
	}
	
	@Override
	public Vector3 getPosition() {
		return null;
	}

	@Override
	public Vector3 getDirection() {
		return new Vector3(MathUtils.random(0.5f), MathUtils.random(0.5f), 1.0f).scl(0.3f);
	}

	@Override
	public int getDamage() {
		return 10;
	}

	@Override
	public Sound getDamageSound(IEntity e) {
		return G.audio.laserHitSlime;
	}

	@Override
	public void timeout(Object data) {
		for (IEntity e : getResidentEntities()) {
			if (e instanceof NPC) {
				NPC npc = (NPC) e;
				((NPC) e).hit(this, npc.getCenterPosition());
			}
		}
		
		if (G.player.position.dst(this.x + 0.5f, this.y + 0.5f, 0.0f) < 1.1f && G.player.getPosition().z == 0.0f) {
			G.player.hit(this, G.player.getEyePosition());
		}
	}
}
