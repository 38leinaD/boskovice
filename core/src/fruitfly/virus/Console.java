package fruitfly.virus;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Console {
	BitmapFont font;
	
	private final static int defaultDisplayTicks = 180;
	private final static int defaultBuildUpTicksPerChar = 3;
	private final static int defaultFadeOutTicks = 20;
	private final static int defaultInterLineDelay = 10;

	enum ConsoleItemState {
		buildUp,
		lineFeed,
		display,
		fadeOut
	};
	
	private class ConsoleItem {
		String text;
		int ticks;
		int displayTicks;
		int charsDisplayed;
		int buildUpTicksPerChar;
		Color color;
		
		ConsoleItemState state;
		
		ConsoleItem(String text) {
			this.text = text;
			this.ticks = 0;
			this.charsDisplayed = 0;
			this.displayTicks = defaultDisplayTicks;
			this.buildUpTicksPerChar = defaultBuildUpTicksPerChar;
			this.color = Color.WHITE.cpy();
			this.state = ConsoleItemState.buildUp;
		}
	}
	
	private List<ConsoleItem> buffer = new LinkedList<ConsoleItem>();
	
	public Console() {
		font = new BitmapFont(Gdx.files.internal("data/fonts/consolas.fnt"), true);
	}
	
	public void tick(long tick) {

		Iterator<ConsoleItem> it = buffer.iterator();
		
		boolean previousLineComplete = true;
		while (it.hasNext()) {
			ConsoleItem ci = it.next();
			
			if (!previousLineComplete) break;
			previousLineComplete = true;
			
			if (ci.charsDisplayed < ci.text.length()) {
				previousLineComplete = false;
				if (ci.ticks < ci.buildUpTicksPerChar) {
					ci.ticks++;
				}
				else {
					ci.ticks = 0;
					G.audio.consoleChar.play(0.01f);
					ci.charsDisplayed++;
				}
			}
			else if (ci.state == ConsoleItemState.buildUp) {
				ci.state = ConsoleItemState.lineFeed;
				ci.ticks = defaultInterLineDelay;
				previousLineComplete = false;
			}
			else if (ci.state == ConsoleItemState.lineFeed) {
				if (ci.ticks == 0) {
					ci.state = ConsoleItemState.display;
					ci.ticks = 0;
				}
				else {
					previousLineComplete = false;
				}
				ci.ticks--;
			}
			else if (ci.state == ConsoleItemState.display) {
				if (ci.ticks == ci.displayTicks) {
					ci.state = ConsoleItemState.fadeOut;
					ci.ticks = defaultFadeOutTicks;
				}
				else {
					ci.ticks++;
				}
			}
			else if (ci.state == ConsoleItemState.fadeOut){
				if (ci.ticks == 0) {
					it.remove();
				}
				else {
					ci.color.a = ci.ticks/(float)defaultFadeOutTicks;
					ci.ticks--;				
				}
			}
		}
	}
	
	public void render(SpriteBatch batch) {
		int i=0;
		for (ConsoleItem ci : buffer) {
			font.setColor(ci.color);
			font.draw(batch, ci.text.substring(0, ci.charsDisplayed), 10, 10 + i * 30);
			i++;

		}
	}
	
	public void output(String out, int displayTicks) {
		ConsoleItem ci = new ConsoleItem(out);
		ci.displayTicks = displayTicks;
		buffer.add(ci);
	}
	
	public void output(String out) {
		output(out, defaultDisplayTicks);
	}
}
