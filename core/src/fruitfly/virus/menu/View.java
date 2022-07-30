package fruitfly.virus.menu;

import java.util.LinkedList;
import java.util.List;

public abstract class View {
	protected float x, y, width, height;
	protected List<View> subviews = new LinkedList<View>();
	protected boolean enabled = true;
	
	protected String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public abstract void render();
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	
	protected boolean click(int x, int y) {
		return false;
	}
	
	protected boolean handleClick(int x, int y) {
		if (x >= this.getX() && x <= this.getX() + this.getWidth() && y >= this.getY() && y <= this.getY() + this.getHeight()) {
			if (isEnabled() && click(x, y)) {
				return true;
			}
			else {
				for (View v : subviews) {
					if (v.handleClick(x, y)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	protected void addSubView(View c) {
		subviews.add(c);
	}
	
	protected List<View> getSubViews() {
		return subviews;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
