package general;

import java.util.*;
import towers.*;
import monsters.*;
import sample.*;
/**
 * Game class to handle monsters list and towers list
 * 
 */
public class Game {
	private int resources;
	private int endzonex;
	private int endzoney;
	private int frameId;
	private boolean gameOver;
	
	private List<BasicTower> towerList;
	private List<Monster> monsterList;
	private List<Monster> deadMonsterList;
	private List<Object> attackList;
	private MyController mc;
	
	/**
	 * Constructor for game
	 * @param resources Initial Resources
	 * @param mc MyController reference
	 */
	public Game(int resources, MyController mc) {
		this.frameId=0;
		this.setResources(resources);
		towerList = new ArrayList<BasicTower>();
		setMonsterList(new ArrayList<Monster>());
		setDeadMonsterList(new ArrayList<Monster>());
		setAttackList(new ArrayList<Object>());
		gameOver = false;
		endzonex = 440; //assume pixel coordinates of endzone!
		endzoney = 0;
		this.mc = mc;
	}
	/**
	 * Getter for gameover state
	 * @return gameOver
	 */
	public boolean getGameOver() {
		return gameOver;
	}
	
	/**
	 * Set for gameover state
	 * @param gameOver Set gameOver
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	/**
	 * Getter EndZoneX
	 * @return endzonex
	 */
	public int getEndzonex() {
		return endzonex;
	}

	/**
	 * Getter EndZoneY
	 * @return endzoney
	 */
	public int getEndzoney() {
		return endzoney;
	}

	/**
	 * Getter for monster's list
	 * @return monsterList
	 */
	public List<Monster> getMonsterList() {
		return monsterList;
	}

	/**
	 * Setter MonstersList
	 * @param monsterList Set to this monsterList
	 */
	public void setMonsterList(List<Monster> monsterList) {
		this.monsterList = monsterList;
	}
	
	/**
	 * Getter for dead monsters list
	 * @return deadMonsterList
	 */
	public List<Monster> getDeadMonsterList() {
		return deadMonsterList;
	}
	
	/**
	 * Getter for TowerList
	 * @return towerList
	 */
	public List<BasicTower> getTowerList() {
		return towerList;
	}

	/**
	 * Setter for deadMonsterList
	 * @param deadMonsterList set to this deadMonsterList
	 */
	public void setDeadMonsterList(List<Monster> deadMonsterList) {
		this.deadMonsterList = deadMonsterList;
	}

	/**
	 * Getter AttackList
	 * @return attackList
	 */
	public List<Object> getAttackList() {
		return attackList;
	}

	/**
	 * Setter for attacklist
	 * @param attackList set to this attackList
	 */
	public void setAttackList(List<Object> attackList) {
		this.attackList = attackList;
	}

	/**
	 * Get resources
	 * @return resources
	 */
	public int getResources() {
		return resources;
	}

	/**
	 * Set resources
	 * @param resources Set to new Resources
	 */
	public void setResources(int resources) {
		this.resources = resources;
	}
	
	/**
	 * Getter for FrameID
	 * @return frameId
	 */
	public int getFrameId() {
		return frameId;
	}

	/**
	 * Next frame function called each frame
	 * Moving monster
	 * Shooting towers
	 */
	public void nextframe () {
		System.out.println("-------------------------");
		frameId++;

		getDeadMonsterList().clear();

		for (Monster monster : getMonsterList()) {
			monster.move();
		}
		
		for (Monster deadmonster : getDeadMonsterList()) {
				getMonsterList().remove(getMonsterList().indexOf(deadmonster));
		}
		if (!gameOver) {
			Monster newMonster = addNewMonster();
			if (newMonster != null) {
				getMonsterList().add(newMonster);
				System.out.println(newMonster.getType() + ":" + newMonster.getHp() + " generated");
			}
			
			for (BasicTower tower : towerList) {
				tower.shoot(this); //switch to private and add monsterlist as argument
			}
		}
		
		for (Monster monster : getMonsterList()) {
			monster.updateAlive();
		}
		
	}
	
	/**
	 * Adding a new monster to the grid
	 * @return monster if one was added, otherwise null
	 */
	public Monster addNewMonster() {
		//every second frame a monster is created
		int spawnX = sample.MyController.getGridWidth()/2;
		int spawnY = sample.MyController.getGridHeight()/2;
		Monster addmonster = null;
			if ((frameId-1) % 6 == 0) {
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
	
	/**
	 * Adding a new tower to the list
	 * @param tower Tower to built
	 * @return true if tower could be built
	 */
	public boolean buildTower(BasicTower tower) {
		towerList.add(tower);
		return true;
	}
	
	/**
	 * Printing towers to check list
	 */
	public void printTowers() {
		for (BasicTower tower : towerList) {
			System.out.println("Tower" + tower.getType() + "   x: " + tower.getX() + "  y: " + tower.getY());
		}
	}
	
	/**
	 * Getters for tower at a position
	 * @param i x_position of tower
	 * @param j y_position of tower
	 * @return tower if it was found
	 */
	public BasicTower getTower(int i, int j) {
		for (BasicTower tower : towerList) {
			if (tower.getX()==i && tower.getY()==j) {
				return tower;
			}
		}
		return null;
	}
	
	/**
	 * if tower is in the list
	 * @param tower Tower to check if in list
	 * @return true if it was found
	 */
	public boolean isTower(BasicTower tower) {
		for (BasicTower towerenum : towerList) {
			if (towerenum == tower) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Destroy tower at a position
	 * @param i x_position of tower
	 * @param j y_position of tower
	 * @return true if it was possible
	 */
	public boolean destroyTower(int i, int j) {
		for (BasicTower tower : towerList) {
			if (tower.getX()==i && tower.getY()==j) {
				towerList.remove(tower);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * draw shoot (line between monster and tower) at a position i,j -- k,l
	 * @param i x_position of first object
	 * @param j y_position of first object
	 * @param k x_position of second object
	 * @param l y_position of second object
	 */
	public void drawShoot(int i, int j, int k, int l) {
		
		mc.addLine(i, j, k, l);
	}
	
	/**
	 * if it is a Monster
	 * @param monster Check for this Monster
	 * @return true if it was found
	 */
	
	public boolean isMonster(Monster monster) {
		for (Monster monsterenum : monsterList) {
			if (monsterenum == monster) {
				return true;
			}
		}
		return false;
	}
}
