package towers;
import general.*;
import monsters.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for Ice Tower
 *
 */

public class IceTower extends BasicTower{
	
	private int slowingFactor;
	private int slowingFrames;
	
	/**
	 * constructor for IceTower
	 * @param x
	 * @param y
	 */
	public IceTower(int x,int y){
		super(x,y);
		this.slowingFactor = 2;
		this.slowingFrames = 5;
		this.setAttackPower(0);
		this.setImg("/iceTower.png");
	}
	
	/**
	 * Getter for type
	 * @return Ice
	 */
	@Override
	public String getType() {
		return "Ice";
	}
	
	/**
	 * Shoot function for ice tower
	 * @param game
	 */
	@Override
	public void shoot(Game game) {
		Monster selectedMonster = selectMonster(game.getMonsterList(), game);
		//shoot Monster
		if(selectedMonster != null) {
			System.out.println("IceTower@(" + getX()/40+"," + getY()/40+")"+ " -> " + selectedMonster.getType() + "@(" + (selectedMonster.getX()-20)/40+"," + (selectedMonster.getY()-20)/40+")");
			game.drawShoot(getX()+20, getY()+20, selectedMonster.getX(), selectedMonster.getY());
			game.getMonsterList().get(game.getMonsterList().indexOf(selectedMonster)).setHp(selectedMonster.getHp() - getAttackPower());
			game.getMonsterList().get(game.getMonsterList().indexOf(selectedMonster)).setSpeed(selectedMonster.getInitialSpeed()/slowingFactor);
			
			if(game.getMonsterList().get(game.getMonsterList().indexOf(selectedMonster)).getSlowForFrames() < slowingFrames) {
				game.getMonsterList().get(game.getMonsterList().indexOf(selectedMonster)).setSlowForFrames(slowingFrames);
			}
			
			List<Object> attackPair = new ArrayList<Object>();
			attackPair.add(this);
			attackPair.add(selectedMonster);
			game.getAttackList().add(attackPair);
		}
	}
	
	/**
	 * Upgrade Tower function
	 */
	@Override
	public void upgradeTower() {
		slowingFrames = slowingFrames + 2;
		this.setLevel(this.getLevel()+1);
	}
	
	
	
}
