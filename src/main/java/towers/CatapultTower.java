package towers;
import monsters.*;

import java.util.ArrayList;
import java.util.List;

import general.*;

/**
 * Class for catapult Tower
 *
 */
public class CatapultTower extends BasicTower{

	private int coolDownTime;
	private int inCoolDownFrames;
	private int damageRadius;
	
	/**
	 * Constructor for catapult tower
	 * @param x
	 * @param y
	 */
	public CatapultTower(int x,int y){
		super(x,y);
		this.coolDownTime = 5;
		this.inCoolDownFrames = 0;
		this.damageRadius = 25;
		this.setImg("/catapult.png");
		this.setMaxRange(149);
		this.setMinRange(51);
	}
	
	/**
	 * Getter for Type
	 * @return Catapult
	 */
	@Override
	public String getType() {
		return "Catapult";
	}
	
	/**
	 * Check if monster is in the zone for damage
	 * @param selectedMonster
	 * @param monster
	 * @return
	 */
	boolean checkIfInZone(Monster selectedMonster, Monster monster) {
		
		if(distance(selectedMonster.getX(),selectedMonster.getY(), monster.getX(), monster.getY()) <= damageRadius) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Shoot function for catapult tower
	 * @param game
	 */
	@Override
	public void shoot(Game game) {
		if(inCoolDownFrames == 0) {
			Monster selectedMonster = selectMonster(game.getMonsterList(), game);
			//shoot Monster
			if(selectedMonster != null) {
				
				for(Monster monster: game.getMonsterList()) {
					if(checkIfInZone(selectedMonster, monster)) {
					
						game.getMonsterList().get(game.getMonsterList().indexOf(monster)).setHp(monster.getHp() - getAttackPower());
						
					}
				}
				System.out.println("Catapult@(" + getX()/40+"," + getY()/40+")"+ " -> " + selectedMonster.getType() + "@(" + (selectedMonster.getX()-20)/40+"," + (selectedMonster.getY()-20)/40+")");
				game.drawShoot(getX()+20, getY()+20, selectedMonster.getX(), selectedMonster.getY());
				inCoolDownFrames = coolDownTime;
				
				List<Object> attackPair = new ArrayList<Object>();
				attackPair.add(this);
				attackPair.add(selectedMonster);
				game.getAttackList().add(attackPair);
			}
		}
		else {
			inCoolDownFrames--;
		}
	}
	
	/**
	 * Upgrade Tower function
	 */
	@Override
	public void upgradeTower() {
		if(coolDownTime > 0) {
			coolDownTime = coolDownTime - 1;
		}
		this.setLevel(this.getLevel()+1);
	}
}