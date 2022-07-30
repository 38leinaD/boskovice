package fruitfly.virus.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.TextureMap.SubTexture;

public class Gibs implements IPointSpriteEntity {
	
	private Vector3 position = new Vector3();
	private SubTexture texture;
	
	public Gibs(float x, float y, SubTexture tex) {
		this.texture = tex;
		this.position.set(x, y, getSpriteSize()/2.0f);
	}
	
	@Override
	public Vector3 getPosition() {
		return position;
	}

	@Override
	public void tick(long tick) {
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public SubTexture getSpriteTexture() {
		return texture;
	}

	@Override
	public Color getSpriteColor() {
		return Color.WHITE;
	}

	@Override
	public float getSpriteSize() {
		return 0.3f;
	}

}
