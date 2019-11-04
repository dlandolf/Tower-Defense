package monsters;

import general.Game;

public class PenguinMonster extends Monster {
	
	public PenguinMonster(int x, int y, Game game) {
		super(x,y,game);
		this.setHp((int) (5 + Math.floor(game.getFrameId()/4)));
		this.setSpeed(2);
		this.setImg("/penguin.png");
	}
	
	@Override
	public void replenishHP() {
		this.setHp(this.getHp() + 2);
	}

}
