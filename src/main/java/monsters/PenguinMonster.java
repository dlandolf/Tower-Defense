package monsters;

import general.Game;
/**
 * Class for Penguin Monster
 *
 */
public class PenguinMonster extends Monster {
	private int initialHp;
	
	/**
	 * Constructor for penguin monster
	 * @param x
	 * @param y
	 * @param game
	 */
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
	
	/**
	 * Function to replenish HP because penguin
	 */
	@Override
	public void replenishHP() {
		//if (this.getHp() + 2 <= initialHp) {
		//	this.setHp(this.getHp() + 2);
		//}
		if (this.getHp() + 1 <= initialHp) {
			this.setHp(this.getHp() + 1);
		}
	}

}
