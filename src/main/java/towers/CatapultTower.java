package towers;
import monsters.*;

import java.util.ArrayList;
import java.util.List;

import general.*;

public class CatapultTower extends BasicTower{

	private int coolDownTime;
	private int inCoolDownFrames;
	private int minRange;
	private int maxRange;
	private int damageRadius;
	
	public CatapultTower(int x,int y){
		super(x,y);
		this.coolDownTime = 5;
		this.inCoolDownFrames = 0;
		this.minRange = 50;
		this.maxRange = 150;
		this.damageRadius = 25;
		this.setImg("/catapult.png");
	}
	
	boolean checkIfInZone(Monster selectedMonster, Monster monster) {
		
		if(distance(selectedMonster.getX(),selectedMonster.getY(), monster.getX(), monster.getY()) <= damageRadius) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public Monster selectMonster(List<Monster> monsterList, Game game) {
		//Iterate through all monsters and select the one to shoot
		Monster selectedMonster = null;
		for(Monster monster: monsterList) {
			
			//Check if the monster in range
			int distance = distance(getX(), getY(), monster.getX(), monster.getY());
			if(distance <= maxRange && distance >= minRange) {
				
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
	
	@Override
	public void shoot(Game game) {
		if(inCoolDownFrames == 0) {
			Monster selectedMonster = selectMonster(game.getMonsterList(), game);
			//shoot Monster
					if(selectedMonster != null) {
						
						for(Monster monster: game.getMonsterList()) {
							if(checkIfInZone(selectedMonster, monster)) {
								game.getMonsterList().get(game.getMonsterList().indexOf(monster)).setHp(selectedMonster.getHp() - getAttackPower());
								List<Object> attackPair = new ArrayList<Object>();
								attackPair.add(this);
								attackPair.add(monster);
								game.getAttackList().add(attackPair);
							}
						}
				
					}
			inCoolDownFrames = coolDownTime;
		}
	}
	
	@Override
	public void upgradeTower() {
		coolDownTime = coolDownTime - 1;
	}
}
