package fruitfly.virus.entities.damager;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.IEntity;
import fruitfly.virus.entities.IPointSpriteEntity;
import fruitfly.virus.entities.ISpriteEntity;
import fruitfly.virus.entities.light.ILight;
import fruitfly.virus.particles.ParticleSystem;
import fruitfly.virus.tiles.FireTile;
import fruitfly.virus.tiles.Tile;

public class Explosion implements IPointSpriteEntity {

	private Vector3 position = new Vector3();
	private int currentFrame = 0;
	private SubTexture[] animation = G.textureMap.explosion;
	private boolean inflictedDamage = false;
	private boolean producesFire = false;
	private float strength;
	
	public Explosion(Vector3 position, float strength, boolean producesFire) {
		this.position.set(position);
		G.audio.explosion.play();
		flash = new ExplosionFlash();
		G.world.entityManager.addEntity(flash);
		this.producesFire = producesFire;
		this.strength = strength;
	}
	
	@Override
	public Vector3 getPosition() {
		return position;
	}

	@Override
	public void tick(long tick) {
		if (tick % 10 == 0) {
			currentFrame++;
			if (currentFrame == animation.length) {
				currentFrame = animation.length - 1;
				G.world.entityManager.removeEntity(this);
				G.world.entityManager.removeEntity(flash);
			}
		}
		
		if (tick % 20 == 0 && !inflictedDamage) {
			if (producesFire) {
				ParticleSystem ps = new ParticleSystem(10, true, 0.2f, new Vector3(position.x, position.y, position.z), new Vector3(0.0f, 0.0f, 1.0f), G.textureMap.fire, Color.WHITE, 120);
				ps.ticksPerFrame = 10;
				G.world.particleManager.addParticleSystem(ps);
				
				int tileX = (int)position.x;
				int tileY = (int)position.y;
				Tile oldTile = G.world.getTile(tileX, tileY);
				G.world.setTile(tileX, tileY, new FireTile(tileX, tileY, oldTile.floorTexture));
			}
			
			inflictedDamage = true;
			for (IEntity e : G.world.entityManager.getEntities()) {
				if (e instanceof ISpriteEntity) {
					ISpriteEntity se = (ISpriteEntity) e;
					final float dist = se.getPosition().dst(this.position);
					final IEntity ee = e;
					if (dist <= 2.0f) {
						se.hit(new IDamager() {
							
							@Override
							public Vector3 getPosition() {
								return position;
							}
							
							@Override
							public Vector3 getDirection() {
								Vector3 dir =  new Vector3(ee.getPosition().x - position.x, ee.getPosition().y - position.y, 0.5f);
								dir.scl(strength);
								dir.nor();
								return dir;
							}
							
							@Override
							public Sound getDamageSound(IEntity e) {
								return null;
							}
							
							@Override
							public int getDamage() {
								return (int) ((1.0f - dist/2.0f) * 100.0f * strength);
							}
						}, se.getPosition());

					}
				}
			}

			final float dist = G.player.getPosition().dst(this.position);
			if (dist <= 2.0f) {
				G.player.hit(new IDamager() {
					
					@Override
					public Vector3 getPosition() {
						return position;
					}
					
					@Override
					public Vector3 getDirection() {
						Vector3 dir =  new Vector3(G.player.position.x - position.x, G.player.position.y - position.y, 0.5f);
						dir.scl(strength);
						dir.nor();
						return dir;
					}
					
					@Override
					public Sound getDamageSound(IEntity e) {
						return null;
					}
					
					@Override
					public int getDamage() {
						return (int) ((1.0f - dist/2.0f) * 100.0f);
					}
				}, G.player.getPosition());

			}
		}

	}

	@Override
	public void dispose() {

	}

	@Override
	public SubTexture getSpriteTexture() {
		return animation[currentFrame];
	}

	@Override
	public Color getSpriteColor() {
		return Color.WHITE;
	}

	@Override
	public float getSpriteSize() {
		return 0.7f;
	}
	
	private ExplosionFlash flash = null;
	
	private class ExplosionFlash implements ILight {
		private int tick = 0;
		
		@Override
		public Vector3 getPosition() {
			return position;
		}

		@Override
		public void tick(long tick) {
			this.tick++;
		}

		@Override
		public void dispose() {
			
		}

		@Override
		public Color getLightColor() {
			return Color.ORANGE;
		}

		@Override
		public float getLightIntensity() {
			return 1.0f - Interpolation.bounce.apply(tick / 80.0f);
		}

		@Override
		public Vector3 getLightPosition() {
			return position;
		}
		
	}
}
