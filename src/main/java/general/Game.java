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
	
	
	public Game(int resources) {
		this.frameId=0;
		this.setResources(resources);
		towerList = new ArrayList<BasicTower>();
		setMonsterList(new ArrayList<Monster>());
		setAttackList(new ArrayList<Object>());
		gameOver = false;
		endzonex = 11; //assume grid coordinates of endzone!
		endzoney = 0;
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
			//requirement to print <type>:<HP> of generated monsters
			System.out.println(newMonster.getType() + ":" + newMonster.getHp());
		}

		for (BasicTower tower : towerList) {
			tower.shoot(this); //switch to private and add monsterlist as argument
		}
		
		for (Monster monster : getMonsterList()) {
			if (monster.updateAlive()) return true;
		}
		
		return false;
	}
	
	//TODO: it adds only the first monster...!?
	public Monster addNewMonster() {
		//every second frame a monster is created with the sequence: fox, unicorn, penguin.
		int spawnX = sample.MyController.getGridWidth()/2;
		int spawnY = sample.MyController.getGridHeight()/2;
		Monster addmonster = null;
		
		if (frameId == 1) {
			addmonster = new FoxMonster(spawnX, spawnY, this); 
		}
		else if ((frameId-1) % 6 == 0) {
			addmonster = new PenguinMonster(spawnX, spawnY, this); 
		}
		else if ((frameId-1) % 4 == 0) {
			addmonster = new UnicornMonster(spawnX, spawnY, this); 
		}
		else if ((frameId-1) % 2 == 0) {
			addmonster = new FoxMonster(spawnX, spawnY, this); 
		}
		
		return addmonster;
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
