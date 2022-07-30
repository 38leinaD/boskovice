package fruitfly.virus;

public class Key {
	public static Key redKey = new Key("Red Key");
	public static Key blueKey = new Key("Green Key");
	public static Key greenKey = new Key("Blue Key");
	
	private String name;
	
	public Key(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}
