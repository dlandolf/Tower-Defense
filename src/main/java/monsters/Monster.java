package monsters;

import general.Game;

public class Monster {
	Game game;
	int hp;
	int speed;
	int slowForFrames;
	int x;
	int y;
	Boolean alive;
	String img;
	
	public Monster(int x, int y, Game game) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.alive = true;
	}
	
	//update state:
	//move monsters and update their HP
	public Boolean move() {
		//remove dead monsters?
		
		if (this.hp > 0) {
			//move in DIRECTION TOWARDS ENDZONE
			if (slowForFrames > 0) {
				
			}
			
		}
		else {
			//change image to collision.png (or just indicate that it is dead?)
			//give ressource to the player!?
			//set alive to false
			this.alive = false;
		}
		return true;
	}
	
}
