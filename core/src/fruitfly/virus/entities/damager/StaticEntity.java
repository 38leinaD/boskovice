package fruitfly.virus.entities.damager;

import java.nio.FloatBuffer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import fruitfly.virus.G;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.ISpriteEntity;
import fruitfly.virus.particles.ParticleSystem;
import fruitfly.virus.render.WorldRenderer;

public abstract class StaticEntity implements ISpriteEntity {

	protected Vector3 position = new Vector3();
	protected Vector2 size = new Vector2();
	protected BoundingBox bbox;
	protected SubTexture texture;

	public StaticEntity(Vector3 position, SubTexture texture) {
		this.position.set(position);
		this.size.set(0.5f, 0.6f);
		this.bbox = new BoundingBox(new Vector3(position.x - size.x/2, position.y - size.x/2, position.z), new Vector3(position.x + size.x/2, position.y + size.x/2, position.z + size.y));
		this.texture = texture;
	}
	
	@Override
	public Vector3 getPosition() {
		return position;
	}

	@Override
	public BoundingBox getBoundingBox() {
		return bbox;
	}

	@Override
	public float getAlpha() {
		return 1.0f;
	}

	@Override
	public void writeVertexData(FloatBuffer buffer) {
		this.texture.getTextureCoords(WorldRenderer.texCoords0);
		
		buffer.put(-size.x/2.0f).put(0.0f).put(position.z);
		buffer.put(WorldRenderer.texCoords0[2]).put(WorldRenderer.texCoords0[3]);

		buffer.put(+size.x/2.0f).put(0.0f).put(position.z);
		buffer.put(WorldRenderer.texCoords0[4]).put(WorldRenderer.texCoords0[5]);

		buffer.put(-size.x/2.0f).put(0.0f).put(position.z+size.y);
		buffer.put(WorldRenderer.texCoords0[0]).put(WorldRenderer.texCoords0[1]);

		buffer.put(+size.x/2.0f).put(0.0f).put(position.z+size.y);
		buffer.put(WorldRenderer.texCoords0[6]).put(WorldRenderer.texCoords0[7]);
	}

}
