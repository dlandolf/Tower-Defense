package general;

import java.util.*;
import towers.*;
import monsters.*;

public class Game {
	private int resources;
	private int endzonex;
	private int endzoney;
	private int frameId;
	private boolean gameOver;
	
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
		gameOver = false;
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
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
		//	if (monster.move()) return true;
		//}
		for (int idx = 0; idx < getMonsterList().size(); idx++) {
			Monster monster = getMonsterList().get(idx);
			if (monster.move(idx)) return true;
		}
		
		Monster newMonster = addNewMonster();
		if (newMonster != null) {
			getMonsterList().add(newMonster);
		}

		for (BasicTower tower : towerList) {
			tower.shoot(this); //switch to private and add monsterlist as argument
		}
		
		for (Monster monster : getMonsterList()) {
			if (monster.updateAlive()) return true;
		}
		
		return false;
	}
	
	public Monster addNewMonster() {
		//every second frame a monster is created with the sequence: fox, unicorn, penguin.
		int spawnX = sample.MyController.getGridWidth()/2;
		int spawnY = sample.MyController.getGridHeight()/2;
		if (frameId % 6 == 1) {
			monstertoadd = new PenguinMonster(spawnX, spawnY, this); 
		}
		else if (frameId % 4 == 1) {
			monstertoadd = new UnicornMonster(spawnX, spawnY, this); 
		}
		else if (frameId % 2 == 1) {
			monstertoadd = new FoxMonster(spawnX, spawnY, this); 
		}
		else {
			monstertoadd = null;
		}
		return monstertoadd;
	}
	
	public boolean buildTower(BasicTower tower) {
		towerList.add(tower);
		return false;
	}
}
