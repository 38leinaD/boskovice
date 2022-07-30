package fruitfly.virus.particles;

import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.tiles.Tile;

public class ParticleSystem {
	private List<Particle> particles = new LinkedList<Particle>();
	private boolean fadeOut;
	public SubTexture texture;
	public int ticksPerFrame = 0;

	private boolean staticc = true;
	
	public ParticleSystem(int count, boolean fadeOut, float particleSize, Vector3 explosionCenter, Vector3 explosionVector, SubTexture[] textures, Color color, int generations) {
		G.world.particleManager.claim(particles, count);
		this.fadeOut = fadeOut;
		for (Particle p : particles) {
			p.generations = generations;
			p.color = color.cpy();
			p.size = particleSize;
			p.textures = textures;
			p.currentTexture = MathUtils.random(textures.length-1);
			p.position.set(explosionCenter);
			if (explosionVector != null) {
				p.position.set(explosionCenter);

				staticc = false;
				p.velocity.set(explosionVector);
				p.velocity.scl(0.1f);
				p.velocity.add(MathUtils.random(-0.2f, 0.2f), MathUtils.random(-0.2f, 0.2f), MathUtils.random(-0.1f, 0.1f));
			}
			else {
				p.position.set(explosionCenter);
				p.position.x += MathUtils.random(-0.5f, 0.5f);
				p.position.y += MathUtils.random(-0.5f, 0.5f);

				staticc = true;
			}
		}
	}
	
	public void tick(long tick) {
		Iterator<Particle> it = particles.iterator();
		while (it.hasNext()) {
			Particle p = it.next();
			if (ticksPerFrame > 0) {
				if (tick % ticksPerFrame == 0) p.currentTexture = (p.currentTexture + 1) % p.textures.length;
			}
			
			if (p.generations == 0) {
				it.remove();
				
				if (fadeOut) {
					G.world.particleManager.release(p);
				}
				else if (G.world.particleManager.getPoolSize() > 50) {
					Tile t = G.world.getTile((int)p.position.x, (int)p.position.y);
					if (t != null) {
						t.getResidentParticles().add(p);
					}
					else {
						G.world.particleManager.release(p);
					}
				}
				else {
					System.out.println("*** PARTICLE-SYSTEM POOL REACHED A LOW AT " + G.world.particleManager.getPoolSize() + " ***");
					G.world.particleManager.release(p);
				}
			}
			else {
				p.generations--;
				
				if (!staticc) {
					if (p.position.z > p.size/2.0f) p.position.add(p.velocity.x * 0.1f, p.velocity.y * 0.1f, p.velocity.z * 0.1f);
					p.velocity.sub(0.0f, 0.0f, 0.02f);
					if (p.position.z < p.size/2.0f) p.position.z = p.size/2.0f;
				}
				if (fadeOut && p.generations <= 10) {
					p.color.a -= 0.1f;
				}
			}
		}
	}
	
	public void writeVertexData(FloatBuffer fb) {
		for (Particle p : particles) {
			fb.put(p.position.x).put(p.position.y).put(p.position.z);
		}
	}
	
	public List<Particle> getParticles() {
		return particles;
	}
}
