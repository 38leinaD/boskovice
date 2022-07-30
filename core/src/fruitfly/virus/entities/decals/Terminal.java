package fruitfly.virus.entities.decals;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.C.Orientation;
import fruitfly.virus.G;
import fruitfly.virus.ITrigger;
import fruitfly.virus.ITriggerTarget;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.entities.light.FlickerLight;
import fruitfly.virus.tiles.Wall;
import fruitfly.virus.timer.ITimeoutListener;

public class Terminal extends Decal implements ITrigger, ITimeoutListener {

	private boolean isTriggered = false;
	private SubTexture[] textures;
	private FlickerLight light;
	
	private Object worldFinished = new Object();
	
	public Terminal(Wall wall, Orientation ori) {
		super(wall, new Vector2(0.5f, 0.6f), ori);
		textures = G.textureMap.terminal;
		texture = textures[0];
		size = 0.5f;
		
		G.timer.registerPeriodicTimer(30, this);
		
		Vector3 lightPos = new Vector3(position);
		lightPos.z = 0.6f;
		if (ori == Orientation.North) {
			lightPos.y += 0.3f;
		}
		else if (ori == Orientation.South) {
			lightPos.y -= 0.3f;
		}
		else if (ori == Orientation.West) {
			lightPos.x -= 0.3f;
		}
		else if (ori == Orientation.East) {
			lightPos.x += 0.3f;
		}
		light = new FlickerLight(lightPos, Color.BLUE.cpy(), 0.2f);
		G.world.entityManager.addEntity(light);
	}

	@Override
	public void setTarget(ITriggerTarget t) {

	}

	@Override
	public boolean isTriggered() {
		return isTriggered;
	}

	ITimeoutListener worldFinishedTimeout = new ITimeoutListener() {
		@Override
		public void timeout(Object data) {
			G.game.startNextLevel();
		}
	};
	
	@Override
	public void trigger() {
		isTriggered = true;		
		G.timer.unregisterTimer(this);
		texture = textures[2];
		
		light.getLightColor().set(Color.GREEN);
		
		G.console.output("Game Over!", 600);
		G.console.output("You successfully completed the demo-level.", 600);
		G.console.output("Thanks for playing!", 600);
		
		G.timer.registerTimer(500, worldFinishedTimeout);
	}

	@Override
	public void timeout(Object data) {
		if (texture == textures[0]) texture = textures[1];
		else texture = textures[0];
	}
}
