package general;

import java.util.*;
import towers.*;
import monsters.*;

public class Game {
	private int resources;
	private int endzonex;
	private int endzoney;
	private int frameId;
	
	private List<BasicTower> towerList;
	private List<Monster> monsterList;
	private List<Object> attackList;
	private Monster monstertoadd;
	
	
	public Game(int resources) {
		this.frameId=0;
		this.setResources(resources);
		towerList = new ArrayList<BasicTower>();
		setMonsterList(new ArrayList<Monster>());
		setAttackList(new ArrayList<Object>());
		monstertoadd = null;
	}
	
	
	public int getEndzonex() {
		return endzonex;
	}

	public int getEndzoney() {
		return endzoney;
	}

	public List<Monster> getMonsterList() {
		return monsterList;
	}

	public void setMonsterList(List<Monster> monsterList) {
		this.monsterList = monsterList;
	}

	public List<Object> getAttackList() {
		return attackList;
	}

	public void setAttackList(List<Object> attackList) {
		this.attackList = attackList;
	}

	public int getResources() {
		return resources;
	}

	public void setResources(int resources) {
		this.resources = resources;
	}
	
	public int getFrameId() {
		return frameId;
	}

	
	public boolean nextframe () {
		frameId++;
		//for (Monster monster : getMonsterList()) {
			//if (monster.move()) return true;
		//}
		Monster newMonster = addNewMonster();
		if (newMonster != null) {
			getMonsterList().add(newMonster);
		}

		for (BasicTower tower : towerList) {
			tower.shoot(this); //switch to private and add monsterlist as argument
		}
		return false;
	}
	
	public Monster addNewMonster() {
		return monstertoadd;
	}
	
	public boolean buildTower(BasicTower tower) {
		towerList.add(tower);
		return true;
	}
	public void printTowers() {
		for (BasicTower tower : towerList) {
			System.out.println("Tower" + tower.getType() + "   x: " + tower.getX() + "  y: " + tower.getY());
		}
	}
	
	public BasicTower getTower(int i, int j) {
		for (BasicTower tower : towerList) {
			if (tower.getX()==i && tower.getY()==j) {
				return tower;
			}
		}
		return null;
	}
	public boolean isTower(BasicTower tower) {
		for (BasicTower towerenum : towerList) {
			if (towerenum == tower) {
				return true;
			}
		}
		return false;
	}
	public boolean destroyTower(int i, int j) {
		for (BasicTower tower : towerList) {
			if (tower.getX()==i && tower.getY()==j) {
				towerList.remove(tower);
				return true;
			}
		}
		return false;
	}
}
