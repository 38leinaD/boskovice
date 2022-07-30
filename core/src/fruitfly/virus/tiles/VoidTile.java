package fruitfly.virus.tiles;

import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.pickups.PickUp;

public class VoidTile extends Tile {

	private PickUp pickUp;
	
	public VoidTile(int x, int y, SubTexture floor) {
		super(x, y);
		this.floorTexture = floor;
		this.pickUp = pickUp;
	}
	
	public PickUp getPickUp() {
		return pickUp;
	}
	
	public void removePickup() {
		pickUp = null;
	}
	
	public void setPickup(PickUp pickup) {
		this.pickUp = pickup;
	}
}
