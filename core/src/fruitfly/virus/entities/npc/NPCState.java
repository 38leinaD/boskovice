package fruitfly.virus.entities.npc;

import fruitfly.virus.TextureMap.SubTexture;

abstract class NPCState {
	int ticks;
	int currentFrame;
	int[] ticksPerFrame;
	
	abstract SubTexture[] getTexturesForAngle(float angle);
	abstract public void think();
}
