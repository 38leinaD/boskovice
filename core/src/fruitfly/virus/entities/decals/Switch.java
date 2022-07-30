package fruitfly.virus.entities.decals;

import java.nio.FloatBuffer;

import com.badlogic.gdx.math.Vector2;

import fruitfly.virus.C.Orientation;
import fruitfly.virus.G;
import fruitfly.virus.ITrigger;
import fruitfly.virus.ITriggerTarget;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.tiles.Wall;

public class Switch extends Decal implements ITrigger {

	private SubTexture[] textures;
	private boolean isFlipped = false;
	private ITriggerTarget target;
	
	public Switch(Wall wall, Orientation ori) {
		super(wall, new Vector2(0.5f, 0.5f), ori);
		textures = G.textureMap.buttonSwitch;
		size = 0.3f;
	}

	@Override
	public void writeVertexData(FloatBuffer buffer) {
		texture = isFlipped ? textures[1] : textures[0];
		super.writeVertexData(buffer);
	}

	public void flip() {
		isFlipped = !isFlipped;
	}
	
	@Override
	public void setTarget(ITriggerTarget target) {
		this.target = target;
	}
	
	@Override
	public boolean isTriggered() {
		return isFlipped;
	}

	@Override
	public void trigger() {
		isFlipped = true;
		target.triggered(this);
		G.audio.pressSwitch.play();
	}
}
