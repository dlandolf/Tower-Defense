package towers;
import monsters.*;
import general.*;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math; 


public class BasicTower {
	private int maxRange;
	private int minRange;
	private int attackPower;
	private int x;
	private int y;
	private int cost;
	private String img;
	private int level;
	
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
	
	public String getType() {
		return "Basic";
	}
	
	public String getImg() {
		return img;
	}
	
	public void setImg(String img) {
		this.img = img;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getAttackPower() {
		return attackPower;
	}
	
	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getMaxRange() {
		return maxRange;
	}
	public void setMaxRange(int max) {
		this.maxRange = max;
	}
	public int getMinRange() {
		return minRange;
	}
	public void setMinRange(int min) {
		this.minRange = min;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level= level;
	}


	public int distance(int x1, int y1,int x2, int y2) {
		int distance = (int) Math.abs(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2,2)));
		return distance;
	}
	
	public Monster selectMonster(List<Monster> monsterList, Game game) {
		//Iterate through all monsters and select the one to shoot
		Monster selectedMonster = null;
		for(Monster monster: monsterList) {
			
			//Check if the monster in range
			int distance = distance(getX(), getY(), monster.getX(), monster.getY());
			if(distance <= this.getMaxRange() && distance >= this.getMinRange()) {				
				//Chose which monster to shoot if more than one is in range
				if(selectedMonster != null) {
					//Decide which monster is closer to endzone, chose endzone as in demo (440,0)
					if(distance(monster.getX(),monster.getY(),game.getEndzonex(),game.getEndzoney()) < distance(selectedMonster.getX(),selectedMonster.getY(),game.getEndzonex(),game.getEndzoney())) {
						selectedMonster = monster;
					}
				}
				else {
					selectedMonster = monster;
				}
			}
		}

		
		return selectedMonster;
	}
	
	public void shoot(Game game) {
		Monster selectedMonster = selectMonster(game.getMonsterList(), game);
		//shoot Monster
				if(selectedMonster != null) {
					game.getMonsterList().get(game.getMonsterList().indexOf(selectedMonster)).setHp(selectedMonster.getHp() - getAttackPower());
					
					List<Object> attackPair = new ArrayList<Object>();
					attackPair.add(this);
					attackPair.add(selectedMonster);
					game.getAttackList().add(attackPair);
				}
	}
	
	
	public void upgradeTower() {
		setAttackPower(getAttackPower() + 1);
		this.level++;
	}
	
	
}