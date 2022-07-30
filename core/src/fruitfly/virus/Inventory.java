package fruitfly.virus;

import java.util.LinkedList;
import java.util.List;

import fruitfly.virus.weapons.IWeapon;

public class Inventory {
	private List<IWeapon> weapons = new LinkedList<IWeapon>();
	private List<Key> keys = new LinkedList<Key>();
	
	public List<IWeapon> getWeapons() {
		return weapons;
	}
	public List<Key> getKeys() {
		return keys;
	}
}
