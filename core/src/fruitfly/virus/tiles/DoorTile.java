package fruitfly.virus.tiles;

import com.badlogic.gdx.math.Vector3;

import fruitfly.virus.G;
import fruitfly.virus.ITrigger;
import fruitfly.virus.ITriggerTarget;
import fruitfly.virus.Key;
import fruitfly.virus.TextureMap.SubTexture;
import fruitfly.virus.timer.ITimeoutListener;

public abstract class DoorTile extends Tile implements ITrigger, ITimeoutListener {

	public final boolean horizontal;
	protected float slide = 1.0f;
	protected State state = State.closed;
	protected Key requiredKey = null;
	protected SubTexture texture = null;
	
	protected enum State {
		closed,
		opening,
		open,
		closing
	}
	
	public DoorTile(int x, int y, boolean horizontal, SubTexture texture, SubTexture floorTexture) {
		super(x, y);
		this.horizontal = horizontal;
		this.texture = texture;
		this.floorTexture = floorTexture;
	}

	static final float halfTexWidth = 1.0f/64.0f;
	
	@Override
	public void setTarget(ITriggerTarget t) {
		
	}

	@Override
	public boolean isTriggered() {
		return state == state.opening || state == state.open;
	}

	@Override
	public void trigger() {
		if (requiredKey != null && !G.player.getInventory().getKeys().contains(requiredKey)) {
			G.console.output("You need the " + requiredKey + " to open this door.", 180);
			G.audio.doorShut.play();
			return;
		}
		state = State.opening;
		G.renderer3d.forceVisibilityRecalculation();
		G.audio.doorOpening.play(G.audio.getVolume(G.player.position, new Vector3(x + 0.5f, y + 0.5f, 0.5f)));
		G.timer.registerPeriodicTimer(1, this);
	}
	@Override
	public void timeout(Object data) {
		if (state == State.opening) {
			if (slide <= 0.1f) {
				slide = 0.1f;
				state = State.open;
				G.timer.unregisterTimer(this);
				G.timer.registerPeriodicTimer(360, this);
			}
			else {
				slide -= 0.02f;
			}
		}
		else if (state == State.closing) {
			if (slide >= 1.0f) {
				slide = 1.0f;
				state = State.closed;
				G.audio.doorShut.play(G.audio.getVolume(G.player.position, new Vector3(x + 0.5f, y + 0.5f, 0.5f)));
				G.renderer3d.forceVisibilityRecalculation();
				G.timer.unregisterTimer(this);
			}
			else {
				slide += 0.02f;
			}
		}
		else if (state == State.open) {
			if (this.getResidentEntities().size() == 0 && G.player.getTile() != this) {
				int dxNeightbor, dyNeightbor;
				if (horizontal) {
					dxNeightbor = 0;
					dyNeightbor = 1;
				}
				else {
					dxNeightbor = 1;
					dyNeightbor = 0;
				}
				
				Tile t1 = G.world.getTile(x + dxNeightbor, y + dyNeightbor);
				Tile t2 = G.world.getTile(x - dxNeightbor, y - dyNeightbor);
				if ((t1 == null || t1.getResidentEntities().size() == 0) && (t2 == null || t2.getResidentEntities().size() == 0) &&
					t1 != G.player.getTile() && t2 != G.player.getTile()) {
					state = State.closing;
					G.audio.doorClosing.play(G.audio.getVolume(G.player.position, new Vector3(x + 0.5f, y + 0.5f, 0.5f)));
					G.timer.registerPeriodicTimer(1, this);
				}
			}
		}
	}
	@Override
	public boolean isBlockingVisibility() {
		return state == State.closed;
	}
	@Override
	public boolean isBlockingMovement() {
		return state == State.closed;
	}
	
	public Key getRequiredKey() {
		return requiredKey;
	}

	public void setRequiredKey(Key requiredKey) {
		this.requiredKey = requiredKey;
	}
}
