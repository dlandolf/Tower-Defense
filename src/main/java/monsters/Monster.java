package monsters;

import general.Game;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import sample.MyController;

public class Monster {
	Game game;
	private int hp;
	private int speed;
	private int initialSpeed;
	private int slowForFrames;
	private int x;
	private int y;
	private boolean alive;
	private String img;
	private String type;
	
	public Monster(int x, int y, Game game) {
		this.game = game;
		this.setX(x);
		this.setY(y);
		this.setAlive(true);
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
	
	
	
	//move monsters and delete them if they died in the last round.
	public void move(int idx) {
		//System.out.println(hp);
		//remove dead monsters from list!
		if (!alive) {
			//remove collision image?
			game.getMonsterList().remove(idx);
		}
		else {
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
			
			//what to do if GAMEOVER?
			
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
		}
		else {
			alive = false;
			img = "/collision.png";
			game.setResources(game.getResources() + 200);
		}
	}
	
	public void replenishHP() {
		hp = hp + 0;
	}
	
}
