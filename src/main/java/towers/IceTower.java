package towers;
import general.*;
import monsters.*;

import java.util.ArrayList;
import java.util.List;


public class IceTower extends BasicTower{
	
	private int slowingFactor;
	private int slowingFrames;
	
	public IceTower(int x,int y){
		super(x,y);
		this.slowingFactor = 2;
		this.slowingFrames = 5;
		this.setAttackPower(0);
		this.setImg("/iceTower.png");
	}
	
	@Override
	public String getType() {
		return "Ice";
	}
	
	@Override
	public void shoot(Game game) {
		Monster selectedMonster = selectMonster(game.getMonsterList(), game);
		//shoot Monster
				if(selectedMonster != null) {
					System.out.println("IceTower@(" + getX()/40+"," + getY()/40+")"+ " -> " + selectedMonster.getType() + "@(" + (selectedMonster.getX()-20)/40+"," + (selectedMonster.getY()-20)/40+")");
					game.getMonsterList().get(game.getMonsterList().indexOf(selectedMonster)).setHp(selectedMonster.getHp() - getAttackPower());
					game.getMonsterList().get(game.getMonsterList().indexOf(selectedMonster)).setSpeed(selectedMonster.getInitialSpeed()/slowingFactor);
					game.getMonsterList().get(game.getMonsterList().indexOf(selectedMonster)).setSlowForFrames(slowingFrames);
					
					List<Object> attackPair = new ArrayList<Object>();
					attackPair.add(this);
					attackPair.add(selectedMonster);
					game.getAttackList().add(attackPair);
				}
	}
	
	@Override
	public void upgradeTower() {
		slowingFrames = slowingFrames + 2;
		this.setLevel(this.getLevel()+1);
	}
	
	
	
}
