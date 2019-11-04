package monsters;

import general.Game;

public class PenguinMonster extends Monster {
	
	public PenguinMonster(int x, int y, Game game) {
		super(x,y,game);
		this.hp = (int) (5 + Math.floor(game.frameid/4));
		this.speed = 2;
		this.img = "/penguin.png";
	}
	
	//add function to replenish HP!!

}
