package monsters;

import general.Game;

public class PenguinMonster extends Monster {
	private int initialHp;
	
	public PenguinMonster(int x, int y, Game game) {
		super(x,y,game);
		initialHp = (int) (5 + Math.floor(game.getFrameId()/4));
		this.setHp(initialHp);
		this.setInitialSpeed(2);
		this.setSpeed(this.getInitialSpeed());
		this.setImg("/penguin.png");
		this.setType("Penguin");
		this.updateLabel();
	}
	
	@Override
	public void replenishHP() {
		if (this.getHp() + 2 <= initialHp) {
			this.setHp(this.getHp() + 2);
		}
		else if (this.getHp() + 1 <= initialHp) {
			this.setHp(this.getHp() + 1);
		}
	}

}
