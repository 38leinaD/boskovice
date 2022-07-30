package fruitfly.virus.entities;

import com.badlogic.gdx.graphics.Color;

import fruitfly.virus.TextureMap.SubTexture;

public interface IPointSpriteEntity extends IEntity {
	public SubTexture getSpriteTexture();
	public Color getSpriteColor();
	public float getSpriteSize();
}
