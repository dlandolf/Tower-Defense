package towers;
import monsters.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import general.*;

public class CatapultTower extends BasicTower{

	int coolDownTime;
	int inCoolDownFrames;
	int minRange;
	int maxRange;
	int damageRadius;
	
	public CatapultTower(int x,int y, Game game) throws IOException{
		super(x,y,game);
		this.coolDownTime = 5;
		this.inCoolDownFrames = 0;
		this.minRange = 50;
		this.maxRange = 150;
		this.damageRadius = 25;
		this.img = ImageIO.read(new File("catapult.png"));
	}
	
	boolean checkIfInZone(Monster selectedMonster, Monster monster) {
		
		if(distance(selectedMonster.x,selectedMonster.y, monster.x, monster.y) <= damageRadius) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public Monster selectMonster(List<Monster> monsterList) {
		//Iterate through all monsters and select the one to shoot
		Monster selectedMonster = null;
		for(Monster monster: monsterList) {
			
			//Check if the monster in range
			int distance = distance(x, y, monster.x, monster.y);
			if(distance <= maxRange && distance >= minRange) {
				
				//Chose which monster to shoot if more than one is in range
				if(selectedMonster != null) {
					//Decide which monster is closer to endzone, chose endzone as in demo (440,0)
					if(distance(monster.x,monster.y,game.endzonex,game.endzoney) < distance(selectedMonster.x,selectedMonster.y,game.endzonex,game.endzoney)) {
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
	public void shoot() {
		if(inCoolDownFrames == 0) {
			Monster selectedMonster = selectMonster(game.monsterList);
			//shoot Monster
					if(selectedMonster != null) {
						
						for(Monster monster: game.monsterList) {
							if(checkIfInZone(selectedMonster, monster)) {
								game.monsterList.get(game.monsterList.indexOf(monster)).hp = selectedMonster.hp - attackPower;
								List<Object> attackPair = new ArrayList<Object>();
								attackPair.add(this);
								attackPair.add(monster);
								game.attackList.add(attackPair);
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
