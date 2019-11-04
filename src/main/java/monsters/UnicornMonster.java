package monsters;

import general.Game;

public class UnicornMonster extends Monster {
	
	public UnicornMonster(int x, int y, Game game) {
		super(x,y,game);
		this.setHp((int) (10 + Math.floor(game.getFrameId()/2)));
		this.setSpeed(2);
		this.setImg("/unicorn.png");
	}

}