package objects.item;

import java.util.ArrayList;

import engine.MessageLog;
import objects.mob.Player;

public class Inventory {
	protected ArrayList<Item> content;
	protected int size;
	protected MessageLog log;
	protected Player player;
	
	public Inventory(int size, MessageLog log, Player p) {
		this.size = size;
		this.content = new ArrayList<Item>();
		this.player = p;
		this.log = log;
	}
	
	public void clear() { this.content.clear(); }
	
	public int getSize() { return content.size(); }
	public int getMaxSize() { return this.size; }
	
	public Item get(int index) { return content.get(index); }
	
	public boolean addItem(Item i) { 
		if(content.size()<size) {
			content.add(i);
			log.appendMessage(i+" added to Inventory");
			return true;
		} else {
			log.appendMessage("Inventory full !");
			return false;
		}
	}
	
	public void removeItem(Item i) {
		content.remove(i);
	}
	
	public void dropItem(Item i) {
		content.remove(i);
		i.pos.x=player.pos.x;
		i.pos.y=player.pos.y;
		player.getDungeon().getMap().getItems().add(i);
		log.appendMessage(i.description+" dropped !");
	}
	
	public void use(Item i) {
		if(i instanceof Equipement) {
			if(!((Equipement)i).isEquiped()) {
				if(i instanceof Weapon) {
					player.setWeapon((Weapon)i);
				} else if(i instanceof Shield) {
					player.setShield((Shield)i);
				}
			} else {
				((Equipement)i).unequip();
				if(i instanceof Weapon) {
					player.setWeapon(new Weapon());
				} else if(i instanceof Shield) {
					player.setShield(new Shield());
				}
			}
			player.stuffLooker();
		}
	}
}
