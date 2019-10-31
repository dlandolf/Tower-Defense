package towers;
import general.*;
import monsters.*;

import java.util.ArrayList;
import java.util.List;


public class IceTower extends BasicTower{
	
	int slowingFactor;
	int slowingFrames;
	
	public IceTower(int x,int y, Game game){
		super(x,y,game);
		this.slowingFactor = 2;
		this.slowingFrames = 5;
		this.attackPower = 0;
		this.img = "/iceTower.png";
	}
	
	@Override
	public void shoot() {
		Monster selectedMonster = selectMonster(game.monsterList);
		//shoot Monster
				if(selectedMonster != null) {
					game.monsterList.get(game.monsterList.indexOf(selectedMonster)).hp = selectedMonster.hp - attackPower;
					game.monsterList.get(game.monsterList.indexOf(selectedMonster)).speed = selectedMonster.speed/slowingFactor;
					game.monsterList.get(game.monsterList.indexOf(selectedMonster)).slowForFrames = slowingFrames;
					
					List<Object> attackPair = new ArrayList<Object>();
					attackPair.add(this);
					attackPair.add(selectedMonster);
					game.attackList.add(attackPair);
				}
	}
	
	@Override
	public void upgradeTower() {
		slowingFactor = slowingFactor + 1;
	}
	
	
	
}
