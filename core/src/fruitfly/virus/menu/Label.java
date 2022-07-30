package fruitfly.virus.menu;

import com.badlogic.gdx.math.Vector2;

import fruitfly.virus.G;

public class Label extends View {
	String text;
	
	public Label(float x, float y, String text) {
		this.text = text;
		this.x = x;
		this.y = y;
		
		Vector2 textSize = new Vector2();
		G.renderer2d.getStringSize(text, textSize);
		this.width = textSize.x;
		this.height = textSize.y;
	}
	
	@Override
	public void render() {
		G.renderer2d.renderString(text, x, y);
	}

}
