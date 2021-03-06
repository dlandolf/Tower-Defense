package monsters;
import javafx.scene.control.Label;
import general.Game;

/**
 * Class for Fox Monster
 *
 */
public class FoxMonster extends Monster {
	
	/**
	 * Constructor for Fox Monster
	 * @param x x_position of center of current grid
	 * @param y y_position of center of current grid
	 * @param game Game to be placed in
	 */
	public FoxMonster(int x, int y, Game game) {
		super(x,y,game);
		this.setHp((int) (5 + Math.floor(game.getFrameId()/4)));
		this.setInitialSpeed(4);
		this.setSpeed(this.getInitialSpeed());
		this.setImg("/fox.png");
		this.setType("Fox");
		this.updateLabel();
	}
	
}