package objects.item;

import java.util.ArrayList;

import engine.MessageLog;
import objects.effect.EffectNormal;
import objects.mob.Player;

public class Inventory {
	protected ArrayList<Item> content;
	protected Item quickItem1;
	protected Item quickItem2;
	protected int size;
	protected MessageLog log;
	protected Player player;
	
	public Inventory(int size, MessageLog log, Player p) {
		this.size = size;
		this.content = new ArrayList<Item>();
		this.quickItem1 = null;
		this.quickItem2 = null;
		this.player = p;
		this.log = log;
	}
	
	public void clear() { this.content.clear(); }
	
	public int getSize() { return content.size(); }
	public int getMaxSize() { return this.size; }
	
	public Item get(int index) { return content.get(index); }

	public void setQuickItem1(Item i) {
		if(this.quickItem2 == i) { this.resetQuickItem2(); }
		this.quickItem1 = i; 
	}
	public Item getQuickItem1() { return this.quickItem1; }
	public void resetQuickItem1() { this.quickItem1 = null; }
	
	public void setQuickItem2(Item i) {
		if(this.quickItem1 == i) { this.resetQuickItem1(); }
		this.quickItem2 = i; 
	}
	public Item getQuickItem2() { return this.quickItem2; }
	public void resetQuickItem2() { this.quickItem2 = null; }
	
	public int checkStackable(Item i) {
		if(!i.isStackable()) { return -1; }
		for(Item it : content) {
			if(it.isEqualTo(i)) {
				return content.indexOf(it);
			}
		}
		return -1;
	}
	
	public boolean addItem(Item i) { 
		int itemSlot=checkStackable(i);
		if(itemSlot!=-1 || content.size()<size) {
			if(itemSlot!=-1) { 
				if(content.get(itemSlot) instanceof Potion) {
					content.get(itemSlot).setVal(content.get(itemSlot).getVal()+1); 
				} else if(content.get(itemSlot) instanceof Equipement) {
					((Equipement)content.get(itemSlot)).resetDurability();
				}
			} else { content.add(i); }
			log.appendMessage(i+" taken");
			return true;
		} else {
			log.appendMessage("Inventory full !");
			return false;
		}
	}
	
	public void removeItem(Item i) {
		if(this.quickItem1==i) { this.quickItem1 = null; }
		else if(this.quickItem2==i) { this.quickItem2 = null; }
		content.remove(i);
	}
	
	public void dropItem(Item i) {
		if(i instanceof Equipement && ((Equipement)i).isEquiped()) {
			player.unequip(i);
		}
		this.removeItem(i);
		i.pos.x=player.pos.x;
		i.pos.y=player.pos.y;
		player.getDungeon().getMap().getItems().add(i);
		log.appendMessage(i.description+" dropped !");
	}
	
	public void repareItem(Item i) {
		if(i instanceof Equipement) {
			player.repareEquipement((Equipement)i);
		}
	}
	
	public void use(Item i) {
		if(i instanceof Equipement) {
			if(!((Equipement)i).isEquiped()) {
				if(i instanceof Weapon ||
						i instanceof Bow) {
					player.setWeapon((Equipement)i);
				} else if(i instanceof Shield) {
					player.setShield((Shield)i);
				} else if(i instanceof Helmet) {
					player.setHelmet((Helmet)i);
				}
			} else {
				((Equipement)i).unequip();
				if(i instanceof Weapon ||
						i instanceof Bow) {
					player.setWeapon(new Weapon());
				} else if(i instanceof Shield) {
					player.setShield(new Shield());
				} else if(i instanceof Helmet) {
					player.setHelmet(new Helmet());
				}
			}
			player.stuffLooker();
		} else if(i instanceof Potion) {
			if(i instanceof Antidote) {
				if(!(player.getEffect() instanceof EffectNormal)) {
					player.setEffect(new EffectNormal());
					i.setVal(i.getVal()-1);
				}
			} else if(i instanceof HealingPotion) {
				if(!player.isFullHealth()) {
					player.heal(20);
					i.setVal(i.getVal()-1);
				}
			}
			if(i.getVal()==0) { 
				this.removeItem(i); 
			}
		}
	}
}
