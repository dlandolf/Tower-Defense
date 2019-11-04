package monsters;

import general.Game;

public class FoxMonster extends Monster {
	
	public FoxMonster(int x, int y, Game game) {
		super(x,y,game);
		this.setHp((int) (5 + Math.floor(game.getFrameId()/4)));
		this.setSpeed(4);
		this.setImg("/fox.png");
	}
	
}