package towers;
import monsters.*;

import java.util.ArrayList;
import java.util.List;

import general.*;

public class CatapultTower extends BasicTower{

	private int coolDownTime;
	private int inCoolDownFrames;
	private int damageRadius;
	
	public CatapultTower(int x,int y){
		super(x,y);
		this.coolDownTime = 5;
		this.inCoolDownFrames = 0;
		this.damageRadius = 25;
		this.setImg("/catapult.png");
		this.setMaxRange(150);
		this.setMinRange(50);
	}
	@Override
	public String getType() {
		return "Catapult";
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
	public void shoot(Game game) {
		if(inCoolDownFrames == 0) {
			Monster selectedMonster = selectMonster(game.getMonsterList(), game);
			//shoot Monster
					if(selectedMonster != null) {
						
						for(Monster monster: game.getMonsterList()) {
							if(checkIfInZone(selectedMonster, monster)) {
								System.out.println("Catapult@(" + getX()/40+"," + getY()/40+")"+ " -> " + selectedMonster.getType() + "@(" + (selectedMonster.getX()-20)/40+"," + (selectedMonster.getY()-20)/40+")");
								game.drawShoot(getX()+20, getY()+20, selectedMonster.getX(), selectedMonster.getY());
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
		inCoolDownFrames--;
	}
	
	@Override
	public void upgradeTower() {
		coolDownTime = coolDownTime - 1;
		this.setLevel(this.getLevel()+1);
	}
}