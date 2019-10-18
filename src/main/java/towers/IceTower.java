package towers;
import general.*;
import monsters.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class IceTower extends BasicTower{
	
	int slowingFactor;
	int slowingFrames;
	
	public IceTower(int x,int y, Game game) throws IOException{
		super(x,y,game);
		this.slowingFactor = 2;
		this.slowingFrames = 5;
		this.attackPower = 0;
		this.img = ImageIO.read(new File("iceTower.png"));
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
