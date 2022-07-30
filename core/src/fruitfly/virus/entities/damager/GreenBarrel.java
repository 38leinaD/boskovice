package fruitfly.virus.entities.damager;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.IEntity;
import fruitfly.virus.particles.ParticleSystem;

public class GreenBarrel extends StaticEntity implements IDamager {

	public GreenBarrel(Vector3 position) {
		super(position, G.textureMap.barrelGreen);
	}

	@Override
	public void tick(long tick) {
		if (tick % (MathUtils.random(60) + 1) == 0) {
			G.world.particleManager.addParticleSystem(new ParticleSystem(10, true, 0.05f, new Vector3(position.x, position.y, position.z + size.y), new Vector3(0.0f, 0.0f, 1.0f), new SubTexture[] {G.textureMap.greenBubble}, Color.WHITE, 50));
		}
		
		if (tick % 30 == 0 && G.player.getPosition().dst(position) <= 0.8f) {
			G.player.hit(this, new Vector3(G.player.position.x, G.player.position.y, G.player.position.z + 0.5f));
		}
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public Vector3 getDirection() {
		return null;
	}

	@Override
	public int getDamage() {
		return 5;
	}

	@Override
	public Sound getDamageSound(IEntity e) {
		return G.audio.acidHit;
	}

	@Override
	public void hit(IDamager damager, Vector3 position) {
		Sound sound = damager.getDamageSound(this);
		if (sound != null) damager.getDamageSound(this).play();
	}
}
