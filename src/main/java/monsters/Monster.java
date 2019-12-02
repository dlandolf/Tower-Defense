package monsters;

import java.util.ArrayList;
import java.util.List;

import general.Game;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Class for Monster (abstract monster)
 *
 */
public class Monster {
	Game game;
	private int hp;
	private int speed;
	private int initialSpeed;
	private int slowForFrames;
	private int x;
	private int y;
	private List<Integer> x_prior;
	private List<Integer> y_prior;
	private boolean alive;
	private boolean isNew;
	private String img;
	private String type;
	private Label label;
	
	/**
	 * Constructor for monster
	 * @param x x_position of center of current grid
	 * @param y y_position of center of current grid
	 * @param game Game to be placed in
	 */
	public Monster(int x, int y, Game game) {
		this.game = game;
		this.setX(x);
		this.setY(y);
		this.x_prior = new ArrayList<Integer>();
		this.y_prior = new ArrayList<Integer>();
		x_prior.add(x);
		y_prior.add(y);
		this.setAlive(true);
		setIsNew(true);
	}
	
	/**
	 * Get his label
	 * @return label
	 */
	public Label getLabel() {
		return label;
	}
	
	/**
	 * Update label (image of monster)
	 */
	public void updateLabel() {
		Image image = new Image(getClass().getResourceAsStream(this.getImg()), 20, 20, false, false);
		label = new Label();
		label.setLayoutX(x-sample.MyController.getGridWidth()/4);
		label.setLayoutY(y-sample.MyController.getGridHeight()/4);
		label.setGraphic(new ImageView(image));
		label.setMouseTransparent(true);
		
	}
	
	/**
	 * Getter for type
	 * @return type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Setter for type
	 * @param type Type of Monster
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Getter for X
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter for x
	 * @param x New x_position
	 */
	public void setX(int x) {
		this.x = x;
	}


	/**
	 * Getter for y
	 * @return y
	 */
	public int getY() {
		return y;
	}


	/**
	 * Setter for y
	 * @param y New y_position
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Getter for XPrior
	 * @return x_prior
	 */
	public List<Integer> getXPrior() {
		return x_prior;
	}

	/**
	 * Setter XPrior
	 * @param x_prior New list of x_prior 
	 */
	public void setXPrior(List<Integer> x_prior) {
		this.x_prior = x_prior;
	}
	
	/**
	 * Getter for YPrior
	 * @return y_prior
	 */
	public List<Integer> getYPrior() {
		return y_prior;
	}

	/**
	 * Setter YPrior 
	 * @param y_prior New list of y_prior 
	 */
	public void setYPrior(List<Integer> y_prior) {
		this.y_prior = y_prior;
	}

	/**
	 * Getter for HP
	 * @return hp
	 */
	public int getHp() {
		return hp;
	}
	
	/**
	 * Setter for HP
	 * @param hp New HP
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}


	/** 
	 * getter for Initial speed
	 * @return initialSpeed
	 */
	public int getInitialSpeed() {
		return initialSpeed;
	}

	/**
	 * Setter for initialSpeed
	 * @param initialSpeed New initialSpeed
	 */
	public void setInitialSpeed(int initialSpeed) {
		this.initialSpeed = initialSpeed;
	}

	/**
	 * Getter for speed
	 * @return speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 *  setter for speed
	 * @param speed New speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Getter for slowforframes
	 * @return slowforframes
	 */
	public int getSlowForFrames() {
		return slowForFrames;
	}

	/**
	 * Setter for slowforframes
	 * @param slowForFrames updated slowForFrames
	 */
	public void setSlowForFrames(int slowForFrames) {
		this.slowForFrames = slowForFrames;
	}

	/**
	 * Getter for image
	 * @return image
	 */
	public String getImg() {
		return img;
	}


	/**
	 * Setter for image
	 * @param img New img_string
	 */
	public void setImg(String img) {
		this.img = img;
	}
	
	/**
	 * Getter for Alive
	 * @return alive
	 */
	public boolean getAlive() {
		return alive;
	}
	
	/**
	 * Setter for alive
	 * @param alive New state of alive
	 */
	public void setAlive(Boolean alive) {
		this.alive = alive;
	}
	
	
	/**
	 * Getter for New
	 * @return isNew
	 */
	public boolean getIsNew() {
		return isNew;
	}

	/**
	 * Setter for isNew
	 * @param isNew New state of isNew
	 */
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	/**
	 * Move function launched each frame
	 */
	public void move() {
		setIsNew(false);
		x_prior.clear();
		y_prior.clear();
	
		if (alive) {
			if (slowForFrames > 0) {
				this.moveTowardsEndzone(speed);
				
				slowForFrames = slowForFrames - 1;
				if (slowForFrames == 0) {
					speed = initialSpeed;
				}
			}
			else {
				this.moveTowardsEndzone(speed);
			}
			
			this.replenishHP();
			
		}
		else {
			game.getDeadMonsterList().add(this);
		}
		
	}
	
	/**
	 * Move towards endzone for the quickest way
	 * @param speed Speed in which monster moves
	 */
	public void moveTowardsEndzone(int speed) {
		int gridWidth = sample.MyController.getGridWidth();
		int gridHeight = sample.MyController.getGridHeight();
		
		for (int i=0; i < speed; i++) {
			int gridIdxX = (int) x/gridWidth;
			int gridIdxY = (int) y/gridHeight;
			
			//endzone position in pixel or grid coordinates? ASSUME PIXEL COORDINATES:
			if (gridIdxX == (int) game.getEndzonex()/gridWidth && gridIdxY == (int) game.getEndzoney()/gridHeight) {
				game.setGameOver(true);
				System.out.println("Gameover");
				break;
			}
				
			/*If GRID coordinates:
			if (gridIdxX == game.getEndzonex() && gridIdxY == game.getEndzoney()) {
				game.setGameOver(true);
				System.out.println("Gameover");
				break;
			}*/
			
			x_prior.add(x);
			y_prior.add(y);
			
			//if field at grid position gridIdxX+1 is white, move there!
			if ((gridIdxX+1) % 2 == 0 || (gridIdxY) == ((gridIdxX + 2) / 2 % 2) * 11) {
				x = x + gridWidth;
			}
			else if (gridIdxX % 4 == 0) {
				y = y + gridHeight; //move down!
			}
			
			else {
				y = y - gridHeight; //move up!
			}
			

		}
	}
	
	/**
	 * Update alive at the end of the frame if HP is under 0
	 */
	public void updateAlive() {
		if (hp > 0) {
			return;
		}
		else {
			alive = false;
			img = "/collision.png";
			game.setResources(game.getResources() + 100);
		}
	}
	
	/**
	 * Replenish HP for all mosnters (0 unless penguins)
	 */
	public void replenishHP() {
		hp = hp + 0;
	}
	
}
