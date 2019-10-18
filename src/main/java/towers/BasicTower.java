package towers;
import monsters.*;
import general.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math; 
import javax.imageio.ImageIO;


public class BasicTower {
	Game game;
	int range;
	int attackPower;
	int x;
	int y;
	int cost;
	BufferedImage img;
	
	public BasicTower(int x, int y, Game game) throws IOException {
		this.game = game;
		this.range = 65;
		this.attackPower = 1;
		this.x = x;
		this.y = y;
		this.cost = 10;
		this.img = ImageIO.read(new File("basicTower.png"));
	}
	
	public int distance(int x1, int y1,int x2, int y2) {
		int distance = (int) Math.abs(Math.sqrt((x1 - x2)^2 + (y1 - y2)^2));
		return distance;
	}
	
	public Monster selectMonster(List<Monster> monsterList) {
		//Iterate through all monsters and select the one to shoot
		Monster selectedMonster = null;
		for(Monster monster: monsterList) {
			
			//Check if the monster in range
			int distance = distance(x, y, monster.x, monster.y);
			if(distance <= range) {
				
				//Chose which monster to shoot if more than one is in range
				if(selectedMonster != null) {
					//Decide which monster is closer to endzone, chose endzone as in demo (440,0)
					if(distance(monster.x,monster.y,game.endzonex,game.endzoney) < distance(selectedMonster.x,selectedMonster.y,game.endzonex,game.endzoney)) {
						selectedMonster = monster;
					}
				}
				else {
					selectedMonster = monster;
				}
			}
		}

		
		return selectedMonster;
	}
	
	public void shoot() {
		Monster selectedMonster = selectMonster(game.monsterList);
		//shoot Monster
				if(selectedMonster != null) {
					game.monsterList.get(game.monsterList.indexOf(selectedMonster)).hp = selectedMonster.hp - attackPower;
					
					List<Object> attackPair = new ArrayList<Object>();
					attackPair.add(this);
					attackPair.add(selectedMonster);
					game.attackList.add(attackPair);
				}
	}
	
	
	public void upgradeTower() {
		attackPower = attackPower + 1;
	}
	
	
}

