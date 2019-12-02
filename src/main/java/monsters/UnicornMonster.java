package monsters;

import general.Game;
/**
 * Class for Unicorn Monster
 */
public class UnicornMonster extends Monster {
	
	/**
	 * Constructor for unicorn monster
	 * @param x x_position of center of current grid
	 * @param y y_position of center of current grid
	 * @param game Game to be placed in
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