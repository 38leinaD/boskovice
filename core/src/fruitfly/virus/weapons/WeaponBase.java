package fruitfly.virus.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.Movement;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.IEntity;
import fruitfly.virus.entities.damager.IDamager;
import fruitfly.virus.timer.ITimeoutListener;

public abstract class WeaponBase implements IWeapon, ITimeoutListener {

	protected enum State {
		AIMING, IDLE, FIRING;
	}
	protected State state = State.AIMING;
	protected SubTexture[] textures;
	
	protected int interShotDelay = 20;
	protected Sound sound = G.audio.laserShot;

	protected float inaccuracyStanding = 0.02f;
	protected float inaccuracyMoving = 0.05f;
	
	protected float aimingAdvance = 0.0f;
	
	public boolean canFire() {
		return state == State.IDLE;
	}
	
	public void fire() {
		// TODO also expose direction of player; don't use camera; could be detached
		Vector3 dir = G.camera.camera.direction.cpy();
		Vector3 inaccurateDir = dir.cpy();
		float inacurracy = inaccuracyStanding;
		if (G.player.getMovements().contains(Movement.FORWARD) || G.player.getMovements().contains(Movement.BACKWARD)) {
			inacurracy = inaccuracyMoving;
		}
		
		inaccurateDir.x += MathUtils.random(-inacurracy, inacurracy);
		inaccurateDir.y += MathUtils.random(-inacurracy, inacurracy);
		inaccurateDir.z += MathUtils.random(-inacurracy, inacurracy);
		
		IEntity damager = createBullet(new Vector3(G.player.position.x + dir.x*0.5f, G.player.position.y + dir.y*0.5f, 0.5f), dir, inaccurateDir);
		
		G.world.entityManager.addEntity(damager);
		sound.play();
		Gdx.input.vibrate(50);
		
		state = State.FIRING;
		G.timer.registerTimer(interShotDelay, this);
	}
	
	protected abstract IEntity createBullet(Vector3 position, Vector3 direction, Vector3 inacurateDirection);
	
	public void aim() {
		aimingAdvance = 0.0f;
		G.timer.registerPeriodicTimer(1, this);
	}
	
	public float getAimingAdvance() {
		return aimingAdvance;
	}
	
	public boolean isFiring() {
		return state != State.IDLE;
	}
	
	@Override
	public void timeout(Object data) {
		if (state == state.FIRING) {
			state = State.IDLE;
		}
		else if (state == State.AIMING) {
			aimingAdvance += 0.05f;
			if (aimingAdvance >= 1.0f) {
				aimingAdvance = 1.0f;
				state = State.IDLE;
				G.timer.unregisterTimer(this);
			}
		}
	}

	@Override
	public SubTexture getSprite() {
		if (state != State.FIRING) {
			return textures[0];
		}
		else {
			return textures[1];
		}
	}

}
