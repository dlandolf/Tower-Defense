package towers;
import monsters.*;
import general.*;

import java.util.ArrayList;
import java.util.List;

public class LaserTower extends BasicTower{

	private int attackCost;
	private int laserWidth;
	
	public LaserTower(int x,int y){
		super(x,y);
		this.attackCost = 1;
		this.laserWidth = 3;
		this.setImg("/laserTower.png");
	}
	
	@Override
	public String getType() {
		return "Laser";
	}
	
	boolean checkIfOnBeam(Monster selectedMonster, Monster monster) {
	
		int vx = selectedMonster.x-getX();
		int vy = selectedMonster.y-getY();
		double a = (monster.x-getX())/vx;
		
		if(a>0 && getY() + a * vy - laserWidth < monster.y && monster.y < getY() + a * vy + laserWidth) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public void shoot(Game game) {
		Monster selectedMonster = selectMonster(game.getMonsterList(), game);
		//shoot Monster
				if(selectedMonster != null) {
					if(game.getResources() >= attackCost) {
						for(Monster monster: game.getMonsterList()) {
							if(checkIfOnBeam(selectedMonster, monster)) {
								game.getMonsterList().get(game.getMonsterList().indexOf(monster)).hp = selectedMonster.hp - getAttackPower();
								List<Object> attackPair = new ArrayList<Object>();
								attackPair.add(this);
								attackPair.add(monster);
								game.getAttackList().add(attackPair);
							}
						}
					}
					game.setResources(game.getResources() - attackCost);
			
				}
	}
	
	
	
}
