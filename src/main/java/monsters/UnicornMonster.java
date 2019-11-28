package monsters;

import general.Game;
/**
 * Class for Unicorn Monster
 */
public class UnicornMonster extends Monster {
	
	/**
	 * Constructor for unicorn monster
	 * @param x
	 * @param y
	 * @param game
	 */
	public UnicornMonster(int x, int y, Game game) {
		super(x,y,game);
		this.setHp((int) (10 + Math.floor(game.getFrameId()/2)));
		this.setInitialSpeed(2);
		this.setSpeed(this.getInitialSpeed());
		this.setImg("/unicorn.png");
		this.setType("Unicorn");
		this.updateLabel();
	}

}