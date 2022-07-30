package fruitfly.virus.entities;

import java.nio.FloatBuffer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import fruitfly.virus.G;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.damager.IDamager;
import fruitfly.virus.render.WorldRenderer;

public class GenericSprite implements ISpriteEntity {

	private Vector3 position;
	private float size;
	private SubTexture texture;
	
	public GenericSprite(Vector3 position, float size, SubTexture texture) {
		this.position = position;
		this.size = size;
		this.texture = texture;
	}
	
	@Override
	public Vector3 getPosition() {
		return position;
	}

	@Override
	public void tick(long tick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BoundingBox getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getAlpha() {
		return 1.0f;
	}

	@Override
	public void writeVertexData(FloatBuffer buffer) {
		texture.getTextureCoords(WorldRenderer.texCoords0);
		
		float ratio = texture.getWidth()/(float)texture.getHeight();

		buffer.put(-size * ratio / 2.0f).put(0.0f).put(position.z);
		buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);

		buffer.put(+size * ratio / 2.0f).put(0.0f).put(position.z);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);

		buffer.put(-size * ratio / 2.0f).put(0.0f).put(position.z+size);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);

		buffer.put(+size * ratio / 2.0f).put(0.0f).put(position.z+size);
		buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
	}

	@Override
	public void hit(IDamager damager, Vector3 position) {
		
	}

}
