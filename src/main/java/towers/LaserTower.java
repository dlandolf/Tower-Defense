package towers;
import monsters.*;
import general.*;

import java.util.ArrayList;
import java.util.List;
/**
 * class for LaserTower
 *
 */
public class LaserTower extends BasicTower{

	private int attackCost;
	private int laserWidth;
	
	/**
	 * Constructor for tower
	 * @param x x_position of upper left corner of grid
	 * @param y y_position of upper left corner of grid
	 */
	public LaserTower(int x,int y){
		super(x,y);
		this.attackCost = 1;
		this.laserWidth = 3;
		this.setImg("/laserTower.png");
	}
	
	/**
	 * Getter for type
	 * @return Laser
	 */
	@Override
	public String getType() {
		return "Laser";
	}
	
	/**
	 * if monsters are on beam
	 * @param selectedMonster Selected Monster
	 * @param monster Monster to check
	 * @return true if monster is on beam
	 */
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
	
	/**
	 * draw laser according to position
	 * @param selectedMonster Selected Monster
	 * @param game Game to draw line in
	 */
	void draw_laserBeam(Monster selectedMonster, Game game) {
		
		int vx = selectedMonster.getX()-(getX()+20);
		
		if(vx == 0) {
			if(selectedMonster.getY()>getY()+20) {
				game.drawShoot(getX()+20, getY()+20, selectedMonster.getX(), 480);
			}
			else {
				game.drawShoot(getX()+20, getY()+20, selectedMonster.getX(), 0);
			}
		}
		else {
			int vy = selectedMonster.getY()-(getY()+20);
			if(vy == 0) {
				if(selectedMonster.getX()>getX()+20) {
					game.drawShoot(getX()+20, getY()+20, 480, selectedMonster.getY());
				}
				else {
					game.drawShoot(getX()+20, getY()+20, 0, selectedMonster.getY());
				}
			}
			else {
				double scaling = 40 * 12 * Math.sqrt(2);
				double vx_n = scaling * vx/Math.sqrt(Math.pow(vx, 2)+ Math.pow(vy, 2));
				double vy_n = scaling * vy/Math.sqrt(Math.pow(vx, 2)+ Math.pow(vy, 2));
				game.drawShoot(getX()+20, getY()+20, (int)(getX()+20+ vx_n), (int)(getY()+20+ vy_n));
			}
		}
	}
	
	
	/**
	 * Function for shoot for laser tower
	 * @param game Game where to shoot in
	 */
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
				
				draw_laserBeam(selectedMonster, game);
				
				List<Object> attackPair = new ArrayList<Object>();
				attackPair.add(this);
				attackPair.add(selectedMonster);
				game.getAttackList().add(attackPair);
				
				game.setResources(game.getResources() - attackCost);
			}
		}
	}
	
	
	
}