package fruitfly.virus.entities.npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.MUtil;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.damager.IDamager;
import fruitfly.virus.tiles.Tile;

public class Turret extends NPC {

	private Vector3 lastHitDirection = new Vector3();
	
	abstract class TurretStateBase extends NPCState {

		TurretStateBase() {
			currentFrame = 0;
			ticksPerFrame = new int[] { 100 };
			ticks = 0;
		}
		
		@Override
		SubTexture[] getTexturesForAngle(float angle) {

			if (angle >= 202.5f && angle < 247.5f) {
				return G.textureMap.turretMinus135;
			}
			else if (angle >= 247.5f && angle < 292.5f) {
				return G.textureMap.turretMinus90;
			}
			else if (angle >= 292.5f && angle < 337.5f) {
				return G.textureMap.turretMinus45;
			}
			else if (angle >= 337.5f || angle < 22.5f) {
				return G.textureMap.turretFront;
			}
			else if (angle >= 22.5f && angle < 67.5f) {
				return G.textureMap.turretPlus45;
			}
			else if (angle >= 67.5f && angle < 112.5f) {
				return G.textureMap.turretPlus90;
			}
			else if (angle >= 112.5f && angle < 157.5f) {
				return G.textureMap.turretPlus135;
			}
			else /*if (angle >= 112.5f && angle < 202.5f) */{
				return G.textureMap.turretBack;
			}
		}
	}
	
	class TurretStateIdle extends TurretStateBase {
		@Override
		public void think() {
			Tile playerTile = G.player.getTile();
			if (G.raycaster.isTileVisible(G.world, position, rotation, playerTile)) {
				currentState = stateAttacking;
			}
		}
	}
	
	class TurretStateSearching extends TurretStateBase {
		@Override
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
				currentState = stateAttacking;
			}
		}
	}
	
	class TurretStateAttacking extends TurretStateBase {

		@Override
		public void think() {
			Tile playerTile = G.player.getTile();
			if (G.raycaster.isTileVisible(G.world, position, rotation, playerTile, 45.0f, 3)) {
				follow(G.player.position);
				fire();
			}
			else {
				currentState = stateSearching;
			}
		}
	}
	
	private NPCState stateIdle = new TurretStateIdle();
	private NPCState stateSearching = new TurretStateSearching();
	private NPCState stateAttacking = new TurretStateAttacking();

	public Turret(Vector3 position, float rotation) {
		super(position, rotation, 0.6f, 0.6f);
		this.currentState = stateIdle;
		this.health = 40;
		this.deathSound = G.audio.turretDies;
		this.bboxWidth = 0.4f;
		updateCalculatedState();
	}

	@Override
	public void hit(IDamager d, Vector3 hitPoint) {
		super.hit(d, hitPoint);
		
		lastHitDirection.set(d.getDirection()).scl(-1.0f);
		
		if (currentState == stateIdle) {
			currentState = stateSearching;
		}
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
		return G.textureMap.turretGibs;
	}

}
