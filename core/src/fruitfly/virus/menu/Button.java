package fruitfly.virus.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import fruitfly.virus.G;
import fruitfly.virus.TextureMap.SubTexture;

public class Button extends View {

	private SubTexture image;
	private SubTexture imagePressed;
	
	private String text;
	
	private IActionTarget target;
	
	public Button(float x, float y, SubTexture image, SubTexture imagePressed) {
		this.x = x;
		this.y = y;
		this.image = image;
		this.imagePressed = imagePressed;
		
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
	}
	
	public void render() {
		if (!isEnabled()) return;
		
		G.renderer2d.render(image, x + width/2 - image.getWidth()/2, y, 1.0f);
		G.renderer2d.setLightColor(new Color(1.0f, 0.0f, 0.0f, 0.7f));
		Vector2 textSize = new Vector2();
		G.renderer2d.getStringSize(text, textSize);
		G.renderer2d.renderString(text, x + width/2 - textSize.x/2, y + image.getHeight()  + 2);
		G.renderer2d.setLightColor(Color.WHITE);

	}
	
	public void setTarget(IActionTarget target) {
		this.target = target;
	}
	
	public void setText(String text) {
		this.text = text;
		Vector2 textSize = new Vector2();
		G.renderer2d.getStringSize(text, textSize);
		
		if (textSize.x > width) width = textSize.x;
		if (textSize.y > height) height = textSize.y;
	}

	@Override
	protected boolean click(int x, int y) {
		Menu.selectSound.play();
		this.target.handleAction(this);
		return true;
	}
}
