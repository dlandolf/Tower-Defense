package monsters;

import general.Game;

public class FoxMonster extends Monster {
	
	public FoxMonster(int x, int y, Game game) {
		super(x,y,game);
		this.hp = (int) (5 + Math.floor(game.frameid/4));
		this.speed = 4;
		this.img = "/fox.png";
	}
	
}