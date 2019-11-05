package monsters;

import general.Game;

public class FoxMonster extends Monster {
	
	public FoxMonster(int x, int y, Game game) {
		super(x,y,game);
		this.setHp((int) (5 + Math.floor(game.getFrameId()/4)));
		this.setInitialSpeed(4);
		this.setSpeed(this.getInitialSpeed());
		this.setImg("/fox.png");
		this.setType("Fox");
	}
	
}