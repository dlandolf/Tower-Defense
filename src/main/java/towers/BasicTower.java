package towers;
import monsters.*;
import general.*;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math; 

/**
 * Class for basicTower
 *
 */
public class BasicTower {
	private int maxRange;
	private int minRange;
	private int attackPower;
	private int x; //towers x is in upper left corner of grid 
	private int y; //towers y is in upper left corner of grid 
	private int cost;
	private String img;
	private int level;
	
	/**
	 * Constructor for basic tower
	 * @param x x_position of upper left corner of grid
	 * @param y y_position of upper left corner of grid
	 */
	public BasicTower(int x, int y){
		this.maxRange = 65;
		this.minRange = 0;
		this.setAttackPower(1);
		this.setX(x);
		this.setY(y);
		this.cost = 10;
		this.level = 1;
		this.img = "/basicTower.png";
	}
	
	
	/**
	 * Getter for type
	 * @return type
	 */
	public String getType() {
		return "Basic";
	}
	
	/**
	 * Getter for img
	 * @return img
	 */
	public String getImg() {
		return img;
	}
	
	/**
	 * Setter for image
	 * @param img New img_string to set to
	 */
	public void setImg(String img) {
		this.img = img;
	}
	
	/**
	 * Getter for X
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter for x
	 * @param x New x_position to set to
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Getter for Y
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter for y
	 * @param y New y_position to set to
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Getter for Attack Power
	 * @return attackPower
	 */
	public int getAttackPower() {
		return attackPower;
	}
	
	/**
	 * Setter for attackPower
	 * @param attackPower New attackPower
	 */
	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}
	
	/**
	 * Getter for cost
	 * @return cost
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Getter for MaxRange
	 * @return maxRange
	 */
	public int getMaxRange() {
		return maxRange;
	}
	
	/**
	 * Setter for MaxRange
	 * @param max New maxRange
	 */
	public void setMaxRange(int max) {
		this.maxRange = max;
	}
	
	/**
	 * Getter for minRange
	 * @return minRange
	 */
	public int getMinRange() {
		return minRange;
	}
	
	/**
	 * Setter for minrange
	 * @param min New minRange
	 */
	public void setMinRange(int min) {
		this.minRange = min;
	}
	
	/**
	 * Getter for level
	 * @return level
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Setter level
	 * @param level New level
	 */
	public void setLevel(int level) {
		this.level= level;
	}

	/**
	 * Calculate distance between 2 objects
	 * @param x1 x_position of first object
	 * @param y1 y_position of first object
	 * @param x2 x_position of second object
	 * @param y2 y_position of second object
	 * @return euclidean distance
	 */
	public int distance(int x1, int y1,int x2, int y2) {
		int distance = (int) Math.abs(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2,2)));
		return distance;
	}
	
	/**
	 * Select monster to shoot
	 * @param monsterList Select a monster from this list
	 * @param game Game where the tower is in
	 * @return Selected Monster
	 */
	public Monster selectMonster(List<Monster> monsterList, Game game) {
		
		List<Monster> inrange_monster = new ArrayList<Monster>();
		for(Monster monster: monsterList) {
			
			//Check if the monster in range
			int distance = distance(getX()+20, getY()+20, monster.getX(), monster.getY());
			int distance_prior = distance(getX()+20, getY()+20, monster.getXPrior().get(0), monster.getYPrior().get(0));
			
			if(distance <= this.getMaxRange() && distance >= this.getMinRange()) {	
				inrange_monster.add(monster);
			}
			else if(distance_prior > this.getMaxRange() || distance_prior < this.getMinRange()) {
				for(int i = 0; i < monster.getXPrior().size(); i++) {
					distance_prior = distance(getX()+20, getY()+20, monster.getXPrior().get(i), monster.getYPrior().get(i));
					if(distance_prior <= this.getMaxRange() && distance_prior >= this.getMinRange()) {	
						inrange_monster.add(monster);
					}
				}
			}
			
		}
		
		
		//Iterate through all monsters and select the one to shoot
		Monster selectedMonster = null;
		for(Monster monster: inrange_monster) {
			
			//Chose which monster to shoot if more than one is in range
			if(selectedMonster != null) {
				//Decide which monster is closer to endzone, chose endzone as in demo (440,0)
				if(monster.getX() > selectedMonster.getX()) {
					selectedMonster = monster;
				}
				
				if(monster.getX() == selectedMonster.getX()) {
					int column = (int) monster.getX()/40;
					if(monster.getY() > selectedMonster.getY() && column%4 == 0) {
						selectedMonster = monster;
					}
					if(monster.getY() < selectedMonster.getY() && column%4 != 0) {
						selectedMonster = monster;
					}
				}
			}
			else {
				selectedMonster = monster;
			}
		}
		

		return selectedMonster;
	}
	
	/**
	 * Shoot function for basic Tower
	 * @param game Game where to shoot in
	 */
	public void shoot(Game game) {
		Monster selectedMonster = selectMonster(game.getMonsterList(), game);
		//shoot Monster
		if(selectedMonster != null) {
			System.out.println("Basic Tower@(" + getX()/40+"," + getY()/40+")"+ " -> " + selectedMonster.getType() + "@(" + (selectedMonster.getX()-20)/40+"," + (selectedMonster.getY()-20)/40+")");
			game.drawShoot(getX()+20, getY()+20, selectedMonster.getX(), selectedMonster.getY());
			game.getMonsterList().get(game.getMonsterList().indexOf(selectedMonster)).setHp(selectedMonster.getHp() - getAttackPower());
			List<Object> attackPair = new ArrayList<Object>();
			attackPair.add(this);
			attackPair.add(selectedMonster);
			game.getAttackList().add(attackPair);
		}
	}
	
	/**
	 * UpgradeTower function
	 */
	public void upgradeTower() {
		setAttackPower(getAttackPower() + 1);
		this.level++;
	}
	
	
}