package fruitfly.virus.entities.npc;

import java.nio.FloatBuffer;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import fruitfly.virus.C;
import fruitfly.virus.G;
import fruitfly.virus.MUtil;
import fruitfly.virus.Movement;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.IEntity;
import fruitfly.virus.entities.ISpriteEntity;
import fruitfly.virus.entities.damager.IDamager;
import fruitfly.virus.entities.damager.LaserShot;
import fruitfly.virus.particles.ParticleSystem;
import fruitfly.virus.render.WorldRenderer;
import fruitfly.virus.tiles.Tile;
import fruitfly.virus.timer.ITimeoutListener;

public class NPC implements ISpriteEntity, ITimeoutListener {

	public Vector3 position, oldPosition, centerPosition, velocity;
	public float rotation;
	public float width, height;
	
	protected Vector3 _v = new Vector3();

	// Calculated state
	public Vector3 direction;
	public float angleToPlayer;
	
	private int currentTextureIndex;  
	private BoundingBox bbox;
	
	protected int health = 30;
		
	protected NPCState currentState;
	protected Sound deathSound;
	
	protected float bodyRadius = 0.3f;
	protected float speed = 0.2f;
	
	protected float bboxWidth, bboxHeight;
	
	protected boolean isAttacking = false;
	protected static int interAttackDelay = 100;
	
	protected float minZ = 0.0f;
	
	public NPC(Vector3 position, float rotation, float width, float height) {
		this.position = position.cpy();
		this.oldPosition = position.cpy();
		this.centerPosition = position.cpy();
		this.velocity = new Vector3();
		this.rotation = rotation;
		this.width = width;
		this.height = height;
		
		this.bboxWidth = this.width;
		this.bboxHeight = this.height;
		
		this.bbox = new BoundingBox();
		this.direction = new Vector3();
		
		updateCalculatedState();
	}
	
	protected void updateCalculatedState() {

		this.direction.x = MathUtils.cosDeg(rotation);
		this.direction.y = MathUtils.sinDeg(rotation);

		this.centerPosition.x = this.position.x;
		this.centerPosition.y = this.position.y;
		this.centerPosition.z = this.position.z + height/2.0f;
		
		this.direction.z = 0.0f;
		
		angleToPlayer = 0.0f;
		angleToPlayer = MUtil.normalizeAngle(angleToPlayer);
		
		bbox.set(new Vector3(position).sub(bboxWidth/2.0f, bboxWidth/2.0f, 0.0f), new Vector3(position).add(bboxWidth/2.0f, bboxWidth/2.0f, bboxHeight));
	}
	
	public Vector3 getCenterPosition() {
		return centerPosition;
	}
	
	@Override
	public Vector3 getPosition() {
		return position;
	}
	
	public int getHealth() {
		return health;
	}
	
	@Override
	public void tick(long tick) {
		updateCalculatedState();
		
		move(velocity.x * 0.1f, velocity.y * 0.1f, velocity.z * 0.1f);
		position.z += velocity.z * 0.1f;

		velocity.z -= 0.03f;
		
		if (position.z <= minZ) {
			velocity.set(0.0f, 0.0f, 0.0f);
		}
				
		think();
		
		if (currentState.ticks == currentState.ticksPerFrame[currentState.currentFrame]) {
			currentState.currentFrame = (currentState.currentFrame + 1) % currentState.ticksPerFrame.length;
			currentState.ticks = 0;
		}
		else {
			currentState.ticks++;
		}
		
		float playerNPCAngle = MathUtils.atan2(position.x - G.player.position.x, position.y - G.player.position.y) * MathUtils.radiansToDegrees;
		
		//if (this.rotation >= )
		angleToPlayer = MUtil.normalizeAngleDeg(-(-this.rotation - playerNPCAngle - 90.0f));
	}

	public void move(Movement movement, float itensity) {
		float dx = 0.0f;
		float dy = 0.0f;
	
		if (movement == Movement.FORWARD) {
			dx = (float) (0.05f * MathUtils.cosDeg(rotation) * itensity);
			dy = (float) (0.05f * MathUtils.sinDeg(rotation) * itensity);
		}
		else if (movement == Movement.BACKWARD) {
			dx = (float) (0.05f * MathUtils.cosDeg(rotation) * -itensity);
			dy = (float) (0.05f * MathUtils.sinDeg(rotation) * -itensity);
		}

		if (movement == Movement.BACKWARD || movement == Movement.FORWARD) {
			move(dx, dy, 0.0f);
		}
		
		if (movement == Movement.TURN_LEFT) {
			this.rotation += 2.0f * itensity;
			this.rotation = MUtil.normalizeAngleDeg(this.rotation);
		}
		else if (movement == Movement.TURN_RIGHT) {
			this.rotation -= 2.0f * itensity;
			this.rotation = MUtil.normalizeAngleDeg(this.rotation);
		}
		
		updateCalculatedState();
	}
	
	public void move(float dx, float dy, float dz) {
		// Control point west
		float dxWithRadius = dx + (dx > 0.0f ? direction.x : -direction.x) * bodyRadius;
		float dyWithRadius = dy + (dy > 0.0f ? direction.y : -direction.y) * bodyRadius;
		
		Tile tXY = G.world.getTile((int)(position.x + dxWithRadius), (int)(position.y + dyWithRadius));
		Tile tX = G.world.getTile((int)(position.x + dxWithRadius), (int)(position.y));
		Tile tY = G.world.getTile((int)(position.x), (int)(position.y + dyWithRadius));

		oldPosition.set(position);
		
		if (tXY == null || !tXY.isBlockingMovement()) {
			this.position.x += dx;
			this.position.y += dy;
		}
		else if (tX == null || !tX.isBlockingMovement()) {
			this.position.x += dx;
		}
		else if (tY == null || !tY.isBlockingMovement()) {
			this.position.y += dy;
		}
		
		if (this.position.z > minZ) {
			this.position.z += dz;
			if (this.position.z < minZ) this.position.z = minZ;
		}
		
		//G.world.entityManager.updateTileResidencyLists(this, oldPosition, position);
	}
	
	public void turnTo(Vector3 direction, float speed) {
		float turnToAngle = MUtil.normalizeAngleDeg(MUtil.angleFromDirection(direction));
		float angleLeft = MUtil.normalizeAngleDeg(rotation - turnToAngle);
		float angleRight = MUtil.normalizeAngleDeg(turnToAngle - rotation);

		if (angleLeft < angleRight) {
			move(Movement.TURN_RIGHT, speed);
		}
		else {
			move(Movement.TURN_LEFT, speed);
		}
	}
	
	public void follow(Vector3 position) {
		Vector3 v = position.cpy();
		v.sub(this.position);
		rotation = MUtil.angleFromDirection(v);

		updateCalculatedState();
	}
	
	@Override
	public void writeVertexData(FloatBuffer buffer) {
		currentState.getTexturesForAngle(angleToPlayer)[currentState.currentFrame].getTextureCoords(WorldRenderer.texCoords0);
		
		buffer.put(-width/2.0f).put(0.0f).put(position.z);
		buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);

		buffer.put(+width/2.0f).put(0.0f).put(position.z);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);

		buffer.put(-width/2.0f).put(0.0f).put(position.z+height);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);

		buffer.put(+width/2.0f).put(0.0f).put(position.z+height);
		buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
	}

	public void hit(IDamager damager, Vector3 hitPoint) {
		health -= damager.getDamage();
		Sound sound = damager.getDamageSound(this);
		if (sound != null) damager.getDamageSound(this).play();
		
		Vector3 splaterDir = damager.getDirection().cpy();
		splaterDir.scl(-1.0f);
		G.world.particleManager.addParticleSystem(new ParticleSystem(10, true, getHitParticleSize(), hitPoint, splaterDir, new SubTexture[] {getHitParticleTexture()}, getHitParticleColor(), 100));
		
		velocity.set(damager.getDirection().x * 0.2f, damager.getDirection().y * 0.4f, damager.getDirection().z * 0.2f);
		
		if (health <= 0) {
			die();
		}
	}
	
	public void fire() {
		if (isAttacking) return;
		Vector3 dir = new Vector3();
		MUtil.setDirectionFromAngle(rotation, dir);

		float inacurracy = 0.05f;
		
		dir.x += MathUtils.random(-inacurracy, inacurracy);
		dir.y += MathUtils.random(-inacurracy, inacurracy);
		dir.z += MathUtils.random(-inacurracy, inacurracy);
		
		IEntity damager = new LaserShot(new Vector3(position.x + dir.x*0.5f, position.y + dir.y*0.5f, 0.5f), dir);
		
		G.world.entityManager.addEntity(damager);
		G.audio.laserShot.play();
		
		isAttacking = true;
		G.timer.registerTimer(interAttackDelay, this);
	}
	
	@Override
	public BoundingBox getBoundingBox() {
		return bbox;
	}
	
	public Vector3 getDirection() {
		return direction;
	}
	
	public void die() {
		if (deathSound != null) deathSound.play();
		G.world.entityManager.removeEntity(this);
		_v.set(position);
		_v.z = 0.5f;
		G.world.particleManager.addParticleSystem(new ParticleSystem(5, false, 0.2f, _v, C.zAxis, getDeathParticleTextures(), getDeathParticleColor(), 100));
	}

	protected void think() {
		currentState.think();
	}
	
	protected Color getHitParticleColor() {
		return Color.WHITE;
	}
	
	protected SubTexture getHitParticleTexture() {
		return G.textureMap.particle1;
	}
	
	protected float getHitParticleSize() {
		return 0.3f;
	}
	
	protected Color getDeathParticleColor() {
		return Color.WHITE;
	}
	
	protected SubTexture[] getDeathParticleTextures() {
		return new SubTexture[] { G.textureMap.particle1 };
	}
	
	@Override
	public void dispose() {
		//currentState = null;
	}

	@Override
	public float getAlpha() {
		return 1.0f;
	}

	@Override
	public void timeout(Object data) {
		isAttacking = false;		
	}
}
