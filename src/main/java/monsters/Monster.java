package monsters;

import java.util.ArrayList;
import java.util.List;

import general.Game;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
	
	public Label getLabel() {
		return label;
	}
	
	public void updateLabel() {
		Image image = new Image(getClass().getResourceAsStream(this.getImg()), 20, 20, false, false);
		label = new Label();
		label.setLayoutX(x-sample.MyController.getGridWidth()/4);
		label.setLayoutY(y-sample.MyController.getGridHeight()/4);
		label.setGraphic(new ImageView(image));
		label.setMouseTransparent(true);
		
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	
	public int getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}



	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}
	
	public List<Integer> getXPrior() {
		return x_prior;
	}



	public void setXPrior(List<Integer> x_prior) {
		this.x_prior = x_prior;
	}
	
	public List<Integer> getYPrior() {
		return y_prior;
	}



	public void setYPrior(List<Integer> y_prior) {
		this.y_prior = y_prior;
	}



	public int getHp() {
		return hp;
	}



	public void setHp(int hp) {
		this.hp = hp;
	}



	public int getInitialSpeed() {
		return initialSpeed;
	}

	public void setInitialSpeed(int initialSpeed) {
		this.initialSpeed = initialSpeed;
	}

	public int getSpeed() {
		return speed;
	}



	public void setSpeed(int speed) {
		this.speed = speed;
	}



	public int getSlowForFrames() {
		return slowForFrames;
	}



	public void setSlowForFrames(int slowForFrames) {
		this.slowForFrames = slowForFrames;
	}



	public String getImg() {
		return img;
	}



	public void setImg(String img) {
		this.img = img;
	}
	
	
	public boolean getAlive() {
		return alive;
	}
	
	
	public void setAlive(Boolean alive) {
		this.alive = alive;
	}
	
	
	
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

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
	
	public void replenishHP() {
		hp = hp + 0;
	}
	
}
