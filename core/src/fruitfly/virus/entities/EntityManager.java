package fruitfly.virus.entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.entities.light.ILight;
import fruitfly.virus.tiles.Tile;

public class EntityManager {
	private List<IEntity> newEntities = new LinkedList<IEntity>();
	private List<IEntity> entities = new LinkedList<IEntity>();
	private List<IEntity> removedEntities = new LinkedList<IEntity>();
	private Map<IEntity, Vector3> entityPositions = new HashMap<IEntity, Vector3>();
	
	private List<ILight> lights = new LinkedList<ILight>();
	
	private Map<Class<IEntity>, List<IEntity>> groups = new HashMap<Class<IEntity>, List<IEntity>>();
	
	public void addEntity(IEntity entity) {
		newEntities.add(entity);
	}
	
	public void removeEntity(IEntity entity) {
		removedEntities.add(entity);
	}
	
	public List<IEntity> getEntities() {
		return entities;
	}
	
	public List<IEntity> getEntities(Class clazz) {
		return entities;
	}
	
	public List<ILight> getLights() {
		return lights;
	}
	
	public void tick(long tick) {
		for (IEntity e : entities) {
			Vector3 oldPosition = entityPositions.get(e);
			if (!e.getPosition().equals(oldPosition)) {
				updateTileResidencyLists(e, oldPosition, e.getPosition());
				oldPosition.set(e.getPosition());
			}
		}
		
		for (IEntity e : removedEntities) {
			entities.remove(e);
			entityPositions.remove(e);
			if (e instanceof ILight) lights.remove((ILight)e);			
			e.dispose();
			
			// Remove entity from tile's entity-list
			Tile t = G.world.getTile((int)e.getPosition().x, (int)e.getPosition().y);
			if (t == null) {
				// TODO: Seems to happen for projectiles...
				System.out.println("!!! UNABLE TO REMOVE ENTITY " + e + " FROM NULL TILE AT " + e.getPosition());
			}
			else {
				t.getResidentEntities().remove(e);
			}
		}
		removedEntities.clear();
		
		for (IEntity e : newEntities) {
			entities.add(e);
			entityPositions.put(e, new Vector3(e.getPosition()));
			if (e instanceof ILight) lights.add((ILight) e);

			// Add entity to tile's entity-list
			Tile t = G.world.getTile((int)e.getPosition().x, (int)e.getPosition().y);
			t.getResidentEntities().add(e);
		}
		newEntities.clear();
		
		for (IEntity e : entities) {
			e.tick(tick);
		}
	}
	
	private void updateTileResidencyLists(IEntity e, Vector3 oldPosition, Vector3 newPosition) {
		int oldPosX = (int) oldPosition.x;
		int oldPosY = (int) oldPosition.y;
		int newPosX = (int) newPosition.x;
		int newPosY = (int) newPosition.y;
		
		if (oldPosX == newPosX && oldPosY == newPosY) {
			return;
		}
		else {
			Tile oldTile = G.world.getTile(oldPosX, oldPosY);
			Tile newTile = G.world.getTile(newPosX, newPosY);
			
			if (oldTile != null) oldTile.getResidentEntities().remove(e);
			if (newTile != null) newTile.getResidentEntities().add(e);
		}
	}
}
