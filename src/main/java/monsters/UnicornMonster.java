package monsters;

import general.Game;

public class UnicornMonster extends Monster {
	
	public UnicornMonster(int x, int y, Game game) {
		super(x,y,game);
		this.hp = (int) (10 + Math.floor(game.frameid/2));
		this.speed = 2;
		this.img = "/unicorn.png";
	}

}