package fruitfly.virus.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.TextureMap.SubTexture;

public class Particle {
	public int generations;
	public Vector3 position;
	public Vector3 velocity;
	public float size;
	public Color color;
	public SubTexture textures[];
	public int currentTexture = 0;
	
	public Particle() {
		position = new Vector3();
		velocity = new Vector3();
	}
}
