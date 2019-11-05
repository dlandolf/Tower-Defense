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
					game.getMonsterList().get(game.getMonsterList().indexOf(selectedMonster)).hp = selectedMonster.hp - getAttackPower();
					game.getMonsterList().get(game.getMonsterList().indexOf(selectedMonster)).speed = selectedMonster.speed/slowingFactor;
					game.getMonsterList().get(game.getMonsterList().indexOf(selectedMonster)).slowForFrames = slowingFrames;
					
					List<Object> attackPair = new ArrayList<Object>();
					attackPair.add(this);
					attackPair.add(selectedMonster);
					game.getAttackList().add(attackPair);
				}
	}
	
	@Override
	public void upgradeTower() {
		slowingFactor = slowingFactor + 1;
		this.setLevel(this.getLevel()+1);
	}
	
	
	
}
