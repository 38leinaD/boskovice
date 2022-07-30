package fruitfly.virus.entities.damager;

import java.nio.FloatBuffer;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.C.Orientation;
import fruitfly.virus.G;
import fruitfly.virus.Player;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.IEntity;
import fruitfly.virus.entities.IPointSpriteEntity;
import fruitfly.virus.entities.ISpriteEntity;
import fruitfly.virus.entities.decals.LaserHitDecal;
import fruitfly.virus.entities.light.ILight;
import fruitfly.virus.entities.npc.NPC;
import fruitfly.virus.entities.npc.Slime;
import fruitfly.virus.render.WorldRenderer;
import fruitfly.virus.tiles.GlassTile;
import fruitfly.virus.tiles.Tile;
import fruitfly.virus.tiles.WallTile;

public class LaserShot implements IPointSpriteEntity, ILight, IDamager {
	public Vector3 position, direction;
	private Vector3 oldPosition;
	private Color color;
	
	public LaserShot(Vector3 position, Vector3 direction) {
		this.position = position.cpy();
		this.oldPosition = position.cpy();
		this.direction = direction.cpy();
		this.color = new Color(1.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public Vector3 getPosition() {
		return position;
	}
	
	public Vector3 getDirection() {
		return direction;
	}
	
	public void tick(long tick) {
		oldPosition.x = position.x;
		oldPosition.y = position.y;
		oldPosition.z = position.z;
		
		position.x += direction.x * 0.2f;
		position.y += direction.y * 0.2f;
		position.z += direction.z * 0.2f;
		
		//G.world.entityManager.updateTileResidencyLists(this, oldPosition, position);
		
		int oldTileX = MathUtils.floor(oldPosition.x);
		int oldTileY = MathUtils.floor(oldPosition.y);
		int tileX = MathUtils.floor(position.x);
		int tileY = MathUtils.floor(position.y);
		
		Tile t = G.world.getTile(tileX, tileY);
		if (t == null) {
			
		}
		else if (t instanceof WallTile && position.z > 0.0f && position.z < 1.0f) {
			WallTile wt = (WallTile) t;
			
			float volumne = G.audio.getVolume(G.player.position, position);
			
			if (oldTileX != tileX) {
				if (direction.x >= 0) {
					// west
					oldPosition.x = tileX;
					new LaserHitDecal(wt.west, new Vector2(oldPosition.y - MathUtils.floor(oldPosition.y), oldPosition.z), Orientation.West);
				}
				else {
					// east
					oldPosition.x = tileX + 1;
					new LaserHitDecal(wt.east, new Vector2(oldPosition.y - MathUtils.floor(oldPosition.y), oldPosition.z), Orientation.East);
				}
				G.world.entityManager.removeEntity(this);
				getDamageSound(this).play(volumne);
				return;
			}
			else if (oldTileY != tileY) {
				if (direction.y >= 0) {
					// south
					oldPosition.y = tileY;
					new LaserHitDecal(wt.south, new Vector2(oldPosition.x - MathUtils.floor(oldPosition.x), oldPosition.z), Orientation.South);
				}
				else {
					// north
					oldPosition.y = tileY + 1;
					new LaserHitDecal(wt.north, new Vector2(oldPosition.x - MathUtils.floor(oldPosition.x), oldPosition.z), Orientation.North);
				}
				G.world.entityManager.removeEntity(this);
				getDamageSound(this).play(volumne);

				return;
			}
		}
		else if (t.isBlockingMovement()) {
			if (t instanceof GlassTile) {
				GlassTile gt = (GlassTile) t;
				gt.destroy();
			}
			
			float volumne = (float) Math.pow(1.0f - Math.min(G.player.position.dst(position), 20.0f) / 20.0f,2) * 0.5f;
			
			G.world.entityManager.removeEntity(this);
			getDamageSound(this).play(volumne);
			return;
		}
		
		for (IEntity e : G.world.entityManager.getEntities(NPC.class)) {
			if (e instanceof ISpriteEntity) {
				ISpriteEntity se = (ISpriteEntity) e;
				if (se.getBoundingBox() != null && se.getBoundingBox().contains(position)) {
					se.hit(this, position);
					G.world.entityManager.removeEntity(this);
				}
			}
		}
		
		if (G.player.getBoundingBox().contains(position)) {
			G.player.hit(this, position);
			G.world.entityManager.removeEntity(this);
		}
		
		if (!G.world.isInBounds(position.x, position.y)) {
			G.world.entityManager.removeEntity(this);
		}
	}

	@Override
	public Vector3 getLightPosition() {
		return position;
	}

	@Override
	public Color getLightColor() {
		return color;
	}
	
	@Override
	public float getLightIntensity() {
		return 0.5f;
	}

	@Override
	public String toString() {
		return "Bullet [position=" + position + ", direction=" + direction
				+ "]";
	}

	@Override
	public int getDamage() {
		return 10;
	}

	@Override
	public Sound getDamageSound(IEntity e) {
		if (e instanceof Slime) return G.audio.laserHitSlime;
		if (e instanceof Player) return G.audio.laserHitSlime;
		else return G.audio.laserHit;
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public SubTexture getSpriteTexture() {
		return G.textureMap.particle0;
	}

	@Override
	public Color getSpriteColor() {
		return Color.RED;
	}

	@Override
	public float getSpriteSize() {
		return 0.2f;
	}
}
