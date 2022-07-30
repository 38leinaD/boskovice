package fruitfly.virus.weapons;

import fruitfly.virus.TextureMap.SubTexture;

public interface IWeapon {
	public boolean canFire();
	public void fire();
	public void aim();
	public float getAimingAdvance();
	public SubTexture getSprite();
	public boolean isFiring();
}
