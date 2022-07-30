package fruitfly.virus.menu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import fruitfly.virus.G;

public class HorizontalList extends View implements IActionTarget {

	private List<View> controls = new LinkedList<View>();
	private int selected = 0;
	
	private Button next;
	private Button previous;
	
	private String text;
	
	public HorizontalList(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.previous = new Button(0, 0, G.baseTextureMap.previousButton, null);
		this.previous.setX(20.0f);
		this.previous.setY(y + height/2.0f - this.previous.getWidth()/2.0f);
		this.previous.setTarget(this);
		this.addSubView(previous);
		
		this.next = new Button(0, 0, G.baseTextureMap.nextButton, null);
		this.next.setX(width - this.next.getWidth() - 20.0f);
		this.next.setY(y + height/2.0f - this.previous.getWidth()/2.0f);
		this.next.setTarget(this);
		this.addSubView(next);
	}
	
	public void addItem(View v) {
		this.addSubView(v);
		this.controls.add(v);
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	private static Vector2 _size = new Vector2();
	
	@Override
	public void render() {
		if (selected > 0) previous.setEnabled(true);
		else previous.setEnabled(false);
		
		previous.render();
		View selectedControl = controls.get(selected);
		selectedControl.setX(x + width/2 - selectedControl.getWidth()/2);
		selectedControl.setY(y + 20);
		selectedControl.render();
		
		
		if (selected < (controls.size() - 1)) next.setEnabled(true);
		else next.setEnabled(false);
		next.render();
		
		if (this.text != null) {
			G.renderer2d.getStringSize(text, _size);
			G.renderer2d.renderString(text, width/2.0f - _size.x/2.0f, y + 7);
		}
	}

	@Override
	public void handleAction(View source) {
		if (source == previous) {
			controls.get(selected).setEnabled(false);
			selected--;
			controls.get(selected).setEnabled(true);

			
		}
		else if (source == next) {
			controls.get(selected).setEnabled(false);
			System.out.println("NEXT CLICKED");
			selected++;
			controls.get(selected).setEnabled(true);
		}
	}

}
