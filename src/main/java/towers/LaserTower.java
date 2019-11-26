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
	
		int vx = selectedMonster.getX()-(getX()+20);
		
		if(vx == 0) {
			if(monster.getX() == selectedMonster.getX()) {
				return true;
			}
			else {
				return false;
			}
		}
		
		else {
			int vy = selectedMonster.getY()-(getY()+20);
			double a = (monster.getX()-(getX()+20))/vx;

			if(a>0 && (getY()+20) + a * vy - laserWidth <= monster.getY() && monster.getY() <= (getY()+20) + a * vy + laserWidth) {

				return true;
			}
			else {
				return false;
			}
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
						game.getMonsterList().get(game.getMonsterList().indexOf(monster)).setHp(monster.getHp() - getAttackPower());
					}
				}
				
				System.out.println("LaserTower@(" + getX()/40+"," + getY()/40+")"+ " -> " + selectedMonster.getType() + "@(" + (selectedMonster.getX()-20)/40+"," + (selectedMonster.getY()-20)/40+")");
				game.drawShoot(getX()+20, getY()+20, selectedMonster.getX(), selectedMonster.getY());
				
				List<Object> attackPair = new ArrayList<Object>();
				attackPair.add(this);
				attackPair.add(selectedMonster);
				game.getAttackList().add(attackPair);
				
				game.setResources(game.getResources() - attackCost);
			}
		}
	}
	
	
	
}