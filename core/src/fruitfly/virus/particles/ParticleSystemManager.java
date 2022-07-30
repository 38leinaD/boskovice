package fruitfly.virus.particles;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ParticleSystemManager {
	private List<Particle> pool = new LinkedList<Particle>();
	private List<ParticleSystem> particleSystems = new LinkedList<ParticleSystem>();
	
	public ParticleSystemManager(int poolSize) {
		for (int i=0; i<poolSize; i++) {
			pool.add(new Particle());
		}
	}
	
	public void claim(List<Particle> particles, int count) {
		for (int i=0; i<count; i++) {
			if (pool.size() > 0) particles.add(pool.remove(0));
		}
	}
	
	public void release(Particle p) {
		pool.add(p);
	}
	
	public void tick(long tick) {
		Iterator<ParticleSystem> it = particleSystems.iterator();
		while (it.hasNext()) {
			ParticleSystem ps = it.next();
			ps.tick(tick);
			if (ps.getParticles().size() == 0) it.remove();
		}
	}
	
	public void addParticleSystem(ParticleSystem ps) {
		particleSystems.add(ps);
	}
	
	public List<ParticleSystem> getParticleSystems() {
		return particleSystems;
	}
	
	public int getPoolSize() {
		return pool.size();
	}
}
