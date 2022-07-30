package fruitfly.virus.entities.npc;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.MUtil;
import fruitfly.virus.Movement;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.IEntity;
import fruitfly.virus.entities.damager.IDamager;
import fruitfly.virus.entities.light.ILight;
import fruitfly.virus.tiles.Tile;

public class Slime extends NPC implements ILight {


	abstract class SlimeStateBase extends NPCState {

		SlimeStateBase() {
			currentFrame = MathUtils.random(2);
			ticksPerFrame = new int[] { 10, 5, 10};
			ticks = MathUtils.random(ticksPerFrame[currentFrame]-1);
		}
		
		@Override
		SubTexture[] getTexturesForAngle(float angle) {

			if (angle >= 345 || angle < 15) {
				return G.textureMap.slimeFront;
			}
			else if (angle >= 15 && angle < 45) {
				return G.textureMap.slimePlus30;
			}
			else if (angle >= 45 && angle < 75) {
				return G.textureMap.slimePlus60;
			}
			else if (angle >= 75 && angle < 105) {
				return G.textureMap.slimePlus90;
			}
			else if (angle >= 315 && angle < 345) {
				return G.textureMap.slimeMinus30;
			}
			else if (angle >= 285 && angle < 315) {
				return G.textureMap.slimeMinus60;
			}
			else if (angle >= 255 && angle < 285) {
				return G.textureMap.slimeMinus90;
			}
			else {
				return G.textureMap.slimeBack;
			}
		}
	}
	
	class SlimeStateIdle extends SlimeStateBase {

		@Override
		public void think() {
			Tile playerTile = G.player.getTile();
			if (G.player.activeWeapon != null && G.player.activeWeapon.isFiring()) {
				Vector3 dirToPlayer = G.player.getPosition().cpy();
				dirToPlayer.sub(position);
				float angleToPlayer = MUtil.angleFromDirection(dirToPlayer);
				
				if (G.raycaster.isTileVisible(G.world, position, angleToPlayer, playerTile)) {
					currentState = new SlimeStateAggresive();
				}
			}
			else if (	G.raycaster.isTileVisible(G.world, position, rotation, playerTile) ||
					G.raycaster.isTileVisible(G.world, position, rotation-10.0f, playerTile) ||
					G.raycaster.isTileVisible(G.world, position, rotation+10.0f, playerTile)) {
				follow(G.player.position);
				move(Movement.FORWARD, 0.2f);
			}
		}
	}
	
	class SlimeStateAggresive extends SlimeStateBase {

		@Override
		public void think() {
			follow(G.player.position);
			move(Movement.FORWARD, 0.4f);
		}
	}
	
	

	public Slime(Vector3 pos, float rot) {
		super(pos, rot, 1.0f, 1.0f);
		
		currentState = new SlimeStateIdle();
		deathSound = G.audio.slimeDies;
		health = 50;
	}

	@Override
	public void hit(IDamager d, Vector3 hitPoint) {
		super.hit(d, hitPoint);
		currentState = new SlimeStateAggresive();
	}
	
	@Override
	public Vector3 getLightPosition() {
		Vector3 v = new Vector3(getPosition());
		v.z += 0.4f;
		return v;
	}
	
	@Override
	public Color getLightColor() {
		return Color.GREEN;
	}

	@Override
	public float getLightIntensity() {
		return 0.5f;
	}

	@Override
	public float getAlpha() {
		return 0.8f;
	}

	@Override
	protected Color getHitParticleColor() {
		return Color.GREEN;
	}
	
	protected float getHitParticleSize() {
		return 0.1f;
	}

	@Override
	protected SubTexture[] getDeathParticleTextures() {
		return G.textureMap.slimeGibs;
	}

	@Override
	public void tick(long tick) {
		super.tick(tick);
		
		if (G.player.getPosition().dst(position) <= 0.6f) {
			G.player.hit(new IDamager() {
				
				@Override
				public Vector3 getPosition() {
					return position;
				}
				
				@Override
				public Vector3 getDirection() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Sound getDamageSound(IEntity e) {
					return G.audio.laserHitSlime;
				}
				
				@Override
				public int getDamage() {
					return 1;
				}
			}, G.player.position);
		}
	}
}
