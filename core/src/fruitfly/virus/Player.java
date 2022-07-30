package fruitfly.virus;

import java.util.EnumSet;
import java.util.Set;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.IEntity;
import fruitfly.virus.entities.damager.IDamager;
import fruitfly.virus.entities.decals.Decal;
import fruitfly.virus.entities.light.ILight;
import fruitfly.virus.particles.ParticleSystem;
import fruitfly.virus.pickups.BlueKeyPickUp;
import fruitfly.virus.pickups.DoorKeyPickUp;
import fruitfly.virus.pickups.GreenKeyPickUp;
import fruitfly.virus.pickups.HealthPickUp;
import fruitfly.virus.pickups.LaserAmmoPickUp;
import fruitfly.virus.pickups.LaserPistolPickUp;
import fruitfly.virus.pickups.PickUp;
import fruitfly.virus.pickups.RedKeyPickUp;
import fruitfly.virus.pickups.ScorePickUp;
import fruitfly.virus.tiles.Tile;
import fruitfly.virus.tiles.TriggerTile;
import fruitfly.virus.tiles.VoidTile;
import fruitfly.virus.tiles.Wall;
import fruitfly.virus.timer.ITimeoutListener;
import fruitfly.virus.weapons.IWeapon;
import fruitfly.virus.weapons.LaserPistol;

public class Player implements IEntity {
	public final Vector3 position;
	public float rotation;
	private Vector3 velocity = new Vector3();
	public float eyeHeight;
	public float motionDisplacement; // height-displacement due to motion
	public IWeapon activeWeapon;
	private Inventory inventory = new Inventory();
	
	private float bodyRadius = 0.3f;
	private BoundingBox bbox = new BoundingBox();
	// Calculated state
	private Vector3 direction;

	public int score = 0;
	
	private EnumSet<Movement> currentMovements = EnumSet.noneOf(Movement.class);
	// Updated for current tick to also notice end of movements as we don't get an input event in this case.
	private EnumSet<Movement> tickMovements = EnumSet.noneOf(Movement.class); 

	private ILight prominentLight;
	
	public int health = 1000;
	private boolean isHit = false;
	
	public Player(float x, float y, float rot) {
		this.position = new Vector3(x, y, 0.0f);
		this.rotation = rot;
		this.eyeHeight = 0.6f;
		//this.activeWeapon = new LaserPistol();
		//this.inventory.getWeapons().add(this.activeWeapon);
		//this.activeWeapon.aim();
		this.direction = new Vector3();
		updateCalculatedState();
	}
	
	public Inventory getInventory() {
		return inventory;
	}

	private void updateCalculatedState() {
		MUtil.setDirectionFromAngle(rotation, direction);
		this.direction.nor();
		bbox.set(new Vector3(position).sub(0.3f, 0.3f, 0.0f), new Vector3(position).add(0.3f, 0.3f, 0.7f));
	}
	
	
	
	public void move(Movement movement, float itensity) {
		float dx = 0.0f;
		float dy = 0.0f;
	
		if (movement == Movement.FORWARD) {
			tickMovements.add(Movement.FORWARD);

			dx = (float) (0.05f * MathUtils.cosDeg(rotation) * itensity);
			dy = (float) (0.05f * MathUtils.sinDeg(rotation) * itensity);
		}
		else if (movement == Movement.BACKWARD) {
			tickMovements.add(Movement.BACKWARD);

			dx = (float) (0.05f * MathUtils.cosDeg(rotation) * -itensity);
			dy = (float) (0.05f * MathUtils.sinDeg(rotation) * -itensity);
		}

		if (movement == Movement.BACKWARD || movement == Movement.FORWARD) {
			move(dx, dy);
		}
		
		if (movement == Movement.TURN_LEFT) {
			tickMovements.add(Movement.TURN_LEFT);

			this.rotation += 2.0f * itensity;
			this.rotation = MUtil.normalizeAngleDeg(this.rotation);
		}
		else if (movement == Movement.TURN_RIGHT) {
			tickMovements.add(Movement.TURN_RIGHT);

			this.rotation -= 2.0f * itensity;
			this.rotation = MUtil.normalizeAngleDeg(this.rotation);
		}
		
		updateCalculatedState();
	}
	
	public void move(float dx, float dy) {
		// Control point west
		float dxWithRadius = dx + (dx > 0.0f ? 1.0f : -1.0f) * bodyRadius;
		float dyWithRadius = dy + (dy > 0.0f ? 1.0f : -1.0f) * bodyRadius;
		
		Tile tXY = G.world.getTile((int)(position.x + dxWithRadius), (int)(position.y + dyWithRadius));
		Tile tX = G.world.getTile((int)(position.x + dxWithRadius), (int)(position.y));
		Tile tY = G.world.getTile((int)(position.x), (int)(position.y + dyWithRadius));

		if (tXY == null || (!tXY.isBlockingMovement() || G.noClip)) {
			this.position.x += dx;
			this.position.y += dy;
		}
		else if (tX == null || (!tX.isBlockingMovement() || G.noClip)) {
			this.position.x += dx;
		}
		else if (tY == null || (!tY.isBlockingMovement() || G.noClip)) {
			this.position.y += dy;
		}
		
		//G.world.entityManager.updateTileResidencyLists(this, oldPosition, position);
		G.renderer3d.forceVisibilityRecalculation();
	}
	
	private ITimeoutListener useDelay = null;
	
	public void use() {
		if (useDelay != null) return;
		
		if (G.raycaster.selectedWall != null) {
			Wall w = G.raycaster.selectedWall;
			for (Decal d : w.getDecals()) {
				if (d instanceof ITrigger) {
					ITrigger trigger = (ITrigger) d;
					if (!trigger.isTriggered() && G.player.position.dst(d.position) < 1.1f) {
						trigger.trigger();
					}
					break;
				}
			}
		}
		
		if (G.raycaster.selectedTile != null) {
			Tile t = G.raycaster.selectedTile;
			
			if (t instanceof ITrigger) {
				ITrigger trigger = (ITrigger) t;
				if (!trigger.isTriggered() && G.player.position.dst(t.x + 0.5f, t.y + 0.5f, 0.5f) < 1.1f) {
					trigger.trigger();
				}
			}
		}
		
		useDelay = new ITimeoutListener() {
			@Override
			public void timeout(Object data) {
				useDelay = null;
			}
		};
		G.timer.registerTimer(30, useDelay);
	}
	
	float t = 90.0f;
	
	public boolean isFacingNorth() {
		if (this.rotation < 360.0f && this.rotation >= 180.0f) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isFacingEast() {
		if (this.rotation >= 90.0f && this.rotation < 270.0f) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void tick(long tick) {
		updateCalculatedState();

		move(velocity.x * 0.1f, velocity.y * 0.1f);
		position.z += velocity.z * 0.1f;

		velocity.z -= 0.03f;
		
		if (position.z <= 0.0f) {
			velocity.set(0.0f, 0.0f, 0.0f);
			position.z = 0.0f;
		}

		isHit = false;
		
		currentMovements.clear();
		currentMovements.addAll(tickMovements);
		tickMovements.clear();
		
		// Walking motion
		if (this.getMovements().contains(Movement.FORWARD) || this.getMovements().contains(Movement.BACKWARD)) {
			t += 10.0f;
		}
		else if (t < 80.0f || t > 100.0f) {
			if (t > 90.0f && t < 180.0f) {
				t -= 20.0f; // walking the curve in opposite direction takes us faster to the high-point
			}
			else {
				t += 20.0f;
			}
		}
		
		if (t >= 360.0f) t -= 360.0f;

		motionDisplacement = 0.05f * MathUtils.sinDeg(t);
		
		// Calculate closest light
		// TODO: Calculate light with highest impact; might be a different one based on intensity
		prominentLight = null;
		float minDistance = Float.MAX_VALUE;
		for (int i=0; i<G.renderer3d.visibleLightCount; i++) {
			ILight l = G.renderer3d.visibleLights[i];
			float distance = l.getPosition().dst(G.player.position);
			if (distance < minDistance) {
				prominentLight = l;
				minDistance = distance;
			}
		}
		
		// Check for pickUps
		Tile t = G.world.getTile((int)position.x, (int)position.y);
		if (t instanceof VoidTile) {
			VoidTile vt = (VoidTile) t;
			PickUp pickUp = vt.getPickUp();
			if (pickUp != null) {
				
				if (pickUp.getPosition().dst(position) >= bodyRadius) {
					// Pick up the pickUp...
					vt.removePickup();
					collect(pickUp);
				}
			}
		}
		
		if (t instanceof TriggerTile) {
			TriggerTile tt = (TriggerTile) t;
			if (!tt.isTriggered()) tt.trigger();
		}
	}
	
	private void collect(PickUp pickUp) {
		if (pickUp instanceof HealthPickUp) {
			G.audio.healthPickUp.play();
			G.player.health += 20.0f;
		}
		else if (pickUp instanceof LaserPistolPickUp) {
			G.audio.pickUp.play();
			boolean hasLaserPistol = false;
			for (IWeapon weapon : G.player.getInventory().getWeapons()) {
				if (weapon instanceof LaserPistol) {
					LaserPistol laserPistol = (LaserPistol) weapon;
					laserPistol.setCapacity(laserPistol.getCapacity() + 0.2f);
					hasLaserPistol = true;
					break;
				}
			}
			
			if (!hasLaserPistol) {
				LaserPistol laserPistol = new LaserPistol();
				G.player.getInventory().getWeapons().add(laserPistol);
				G.player.activeWeapon = laserPistol;
				G.player.activeWeapon.aim();
			}
			
		}
		else if (pickUp instanceof LaserAmmoPickUp) {
			G.audio.pickUp.play();
			for (IWeapon weapon : G.player.getInventory().getWeapons()) {
				if (weapon instanceof LaserPistol) {
					LaserPistol laserPistol = (LaserPistol) weapon;
					laserPistol.setCharge(laserPistol.getCapacity());
					break;
				}
			}
		}
		else if (pickUp instanceof DoorKeyPickUp) {
			G.audio.pickUp.play();
			if (pickUp instanceof RedKeyPickUp) {
				G.player.getInventory().getKeys().add(Key.redKey);
			}
			else if (pickUp instanceof GreenKeyPickUp) {
				G.player.getInventory().getKeys().add(Key.greenKey);
			}
			else if (pickUp instanceof BlueKeyPickUp) {
				G.player.getInventory().getKeys().add(Key.blueKey);
			}
		}
		else if (pickUp instanceof ScorePickUp) {
			G.audio.pickUp.play();
			G.player.score += ((ScorePickUp) pickUp).getScore();
		}
	}
	
	public void hit(IDamager damager, Vector3 hitPoint) {
		isHit = true;
		health -= damager.getDamage();
		
		Sound sound = damager.getDamageSound(this);
		if (sound != null) damager.getDamageSound(this).play();
		
		Vector3 direction = new Vector3();
		
		if (damager.getDirection() != null) {
			direction.set(damager.getDirection());
		}
		else {
			direction.set(hitPoint.x - damager.getPosition().x, hitPoint.y - damager.getPosition().y, hitPoint.z - damager.getPosition().z);
			direction.nor();
			direction.scl(0.3f);
		}
		
		Vector3 splaterDir = new Vector3();
		splaterDir.set(direction);
		splaterDir.scl(-1.0f);
		splaterDir.z += 1.7f;
		
		G.world.particleManager.addParticleSystem(new ParticleSystem(10, true, 0.05f, hitPoint, splaterDir, new SubTexture[] {G.textureMap.particle1}, Color.RED, 100));
		
		velocity.set(direction.x, direction.y, direction.z);
		
		if (health <= 0) {
		}
	}
	
	public ILight getProminentLight() {
		return prominentLight;
	}
	
	public Set<Movement> getMovements() {
		return currentMovements;
	}
	
	public float getEyeHeight() {
		return eyeHeight + motionDisplacement + position.z;
	}
	
	public Vector3 getDirection() {
		return direction;
	}
	
	public Vector3 getEyePosition() {
		Vector3 ep = new Vector3(position);
		ep.z += eyeHeight;
		return ep;
	}
	
	@Override
	public String toString() {
		return "Player [pos=" + position + ", rot=" + rotation + "]";
	}
	
	public Tile getTile() {
		return G.world.getTile((int)position.x, (int)position.y);
	}
	
	public BoundingBox getBoundingBox() {
		return bbox;
	}

	@Override
	public Vector3 getPosition() {
		return position;
	}

	public Vector3 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3 velocity) {
		this.velocity = velocity;
	}

	@Override
	public void dispose() {
		
	}
	
	public boolean isHit() {
		return isHit;
	}
}
