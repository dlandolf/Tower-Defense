package monsters;

import general.Game;

public class Monster {
	Game game;
	private int hp;
	private int speed;
	private int slowForFrames;
	private int x;
	private int y;
	private Boolean alive;
	private String img;
	
	public Monster(int x, int y, Game game) {
		this.game = game;
		this.setX(x);
		this.setY(y);
		this.setAlive(true);
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
	
	
	public Boolean getAlive() {
		return alive;
	}
	
	
	public void setAlive(Boolean alive) {
		this.alive = alive;
	}
	
	
	
	//update state:
	//move monsters and update their HP
	public Boolean move() {
		//remove dead monsters from list!
		
		
		if (this.getHp() > 0) {
			//move in DIRECTION TOWARDS ENDZONE
			if (getSlowForFrames() > 0) {
				
			}
			
		}
		else {
			//change image to collision.png (or just indicate that it is dead?)
			//give ressource to the player!?
			//set alive to false
			this.setAlive(false);
		}
		return true;
	}
	
}
