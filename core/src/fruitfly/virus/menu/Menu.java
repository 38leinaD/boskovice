package fruitfly.virus.menu;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import fruitfly.virus.C;
import fruitfly.virus.G;

public class Menu {
	private List<View> subviews = new LinkedList<View>();
	public static Sound selectSound;
	public Menu() {
		selectSound = Gdx.audio.newSound(Gdx.files.internal("data/audio/select.wav"));
	}
	
	public void addView(View v) {
		this.subviews.add(v);
	}
	
	public void render() {

		G.renderer2d.begin();
		
		for (View v : subviews) {
			v.render();
		}

		G.renderer2d.end();
	}
	
	
	
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX() / C.screenScale;
			int y = Gdx.input.getY() / C.screenScale;
			
			for (View v : subviews) {
				if (v.handleClick(x, y)) break;
			}
		}
	}
}
