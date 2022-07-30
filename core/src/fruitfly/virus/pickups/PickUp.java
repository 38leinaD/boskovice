package fruitfly.virus.pickups;

import java.nio.FloatBuffer;

import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.TextureMap.SubTexture;

public abstract class PickUp {
	private Vector3 position;
	private float size;
	protected SubTexture texture;
	
	public PickUp(float x, float y, float size) {
		this.position = new Vector3(x, y, 0.0f);
		this.size = size;
	}
	
	public void writeVertexData(FloatBuffer fb) {
		fb.put(position.x).put(position.y).put(size/2.0f);
	}
	
	public Vector3 getPosition() {
		return position;
	}
	
	public float getSize() {
		return size;
	}
	
	public SubTexture getTexture() {
		return texture;
	}
}
