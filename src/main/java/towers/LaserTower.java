package towers;
import monsters.*;
import general.*;

import java.util.ArrayList;
import java.util.List;

public class LaserTower extends BasicTower{

	int attackCost;
	int laserWidth;
	
	public LaserTower(int x,int y, Game game){
		super(x,y,game);
		this.attackCost = 1;
		this.laserWidth = 3;
		this.img = "/laserTower.png";
	}
	
	boolean checkIfOnBeam(Monster selectedMonster, Monster monster) {
	
		int vx = selectedMonster.x-x;
		int vy = selectedMonster.y-y;
		double a = (monster.x-x)/vx;
		
		if(a>0 && y + a * vy - laserWidth < monster.y && monster.y < y + a * vy + laserWidth) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public void shoot() {
		Monster selectedMonster = selectMonster(game.monsterList);
		//shoot Monster
				if(selectedMonster != null) {
					if(game.resources >= attackCost) {
						for(Monster monster: game.monsterList) {
							if(checkIfOnBeam(selectedMonster, monster)) {
								game.monsterList.get(game.monsterList.indexOf(monster)).hp = selectedMonster.hp - attackPower;
								List<Object> attackPair = new ArrayList<Object>();
								attackPair.add(this);
								attackPair.add(monster);
								game.attackList.add(attackPair);
							}
						}
					}
					game.resources = game.resources - attackCost;
			
				}
	}
	
	
	
}
