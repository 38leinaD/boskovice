package fruitfly.virus.entities.npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.MUtil;
import fruitfly.virus.Movement;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.damager.Explosion;
import fruitfly.virus.entities.damager.IDamager;
import fruitfly.virus.particles.ParticleSystem;
import fruitfly.virus.tiles.Tile;

public class SecureBot extends NPC {
	private int tickerDisplacement;
	private Vector3 lastHitDirection = new Vector3();
	private Vector3 instantanteousPosition = new Vector3();

	abstract class SecureBotStateBase extends NPCState {

		SecureBotStateBase() {
			currentFrame = MathUtils.random(2);
			ticksPerFrame = new int[] { 10, 10, 10 };
		}
		
		@Override
		SubTexture[] getTexturesForAngle(float angle) {
			if (angle >= 345 || angle < 15) {
				return G.textureMap.secureBotFront;
			}
			else if (angle >= 15 && angle < 45) {
				return G.textureMap.secureBotPlus30;
			}
			else if (angle >= 45 && angle < 75) {
				return G.textureMap.secureBotPlus60;
			}
			else if (angle >= 75 && angle < 105) {
				return G.textureMap.secureBotPlus90;
			}
			else if (angle >= 315 && angle < 345) {
				return G.textureMap.secureBotMinus30;
			}
			else if (angle >= 285 && angle < 315) {
				return G.textureMap.secureBotMinus60;
			}
			else if (angle >= 255 && angle < 285) {
				return G.textureMap.secureBotMinus90;
			}
			else {
				return G.textureMap.secureBotBack;
			}
		}
	}
	
	class SecureBotStateIdle extends SecureBotStateBase {
		public void think() {
			Tile playerTile = G.player.getTile();
			if (G.raycaster.isTileVisible(G.world, position, rotation, playerTile)) {
				currentState = stateApproaching;
			}
			else {
				move(Movement.TURN_LEFT, 0.7f);
			}
		}
	}
	
	class SecureBotStateScanning extends SecureBotStateBase {
		public void think() {
			Tile playerTile = G.player.getTile();
			if (!G.raycaster.isTileVisible(G.world, position, rotation, playerTile)) {
				if (!MUtil.isAlmostEqual(lastHitDirection, direction)) {
					turnTo(lastHitDirection, 1.0f);
				}
				else {
					currentState = stateIdle;
				}
			}
			else {
				currentState = stateApproaching;
			}
		}
	}
	
	class SecureBotStateApproaching extends SecureBotStateBase {
		public void think() {
			Tile playerTile = G.player.getTile();
			if (G.raycaster.isTileVisible(G.world, position, rotation, playerTile)) {
				follow(G.player.position);
				move(Movement.FORWARD, 0.4f);
				
				if (G.player.getPosition().dst(position) <= 1.0f) {
					Vector3 explosionPos = position.cpy();
					explosionPos.z += height/2.0f;
					Explosion e = new Explosion(explosionPos, 0.3f, false);
					G.world.entityManager.addEntity(e);
					G.world.entityManager.removeEntity(SecureBot.this);
				}
			}
			else {
				currentState = new SecureBotStateScanning();
			}
		}
	}
	
	public SecureBot(Vector3 pos, float rot) {
		super(pos, rot, 0.4f, 0.8f);
		minZ = 0.1f;
		position.z = minZ;
		currentState = stateIdle;
		deathSound = G.audio.secureBotDies;
		bodyRadius = 0.2f;
		
		tickerDisplacement = MathUtils.random(60);
	}

	private NPCState stateIdle = new SecureBotStateIdle();
	private NPCState stateScanning = new SecureBotStateScanning();
	private NPCState stateApproaching = new SecureBotStateApproaching();
	
	@Override
	public void hit(IDamager d, Vector3 hitPoint) {
		super.hit(d, hitPoint);
		
		Vector3 v = d.getDirection().cpy().scl(0.5f);
		move(v.x, v.y, 0.0f);
		
		lastHitDirection.set(d.getDirection()).scl(-1.0f);
		
		if (currentState == stateIdle) {
			currentState = stateScanning;
		}
	}
	
	@Override
	public void tick(long tick) {
		super.tick(tick);
		//instantPosition.add(0.0f, 0.0f, 0.05f * MathUtils.sin((G.game.ticker + tickerDisplacement)/10.0f));
		//instantPosition.add(0.02f * MathUtils.cos(G.game.ticker/10.0f), 0.02f * MathUtils.sin(G.game.ticker/10.0f), 0.0f);
		
		//G.world.entityManager.updateTileResidencyLists(this, position, instantPosition);
	}

	@Override
	public Vector3 getPosition() {
		this.instantanteousPosition.set(position);
		this.instantanteousPosition.z += 0.01f * MathUtils.sin(G.game.ticker / 7.0f);
		return instantanteousPosition;
	}

	@Override
	protected Color getHitParticleColor() {
		return Color.ORANGE;
	}


	@Override
	protected float getHitParticleSize() {
		return 0.05f;
	}
	
	@Override
	protected SubTexture[] getDeathParticleTextures() {
		return G.textureMap.secureBotGibs;
	}

}
