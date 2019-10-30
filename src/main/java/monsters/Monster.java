package monsters;

import java.io.IOException;
import java.awt.image.BufferedImage;
import general.Game;

public class Monster {
	Game game;
	int hp;
	int speed;
	int slowForFrames;
	int x;
	int y;
	BufferedImage img;
	
	public Monster(int x, int y, Game game) throws IOException {
		this.game = game;
		this.x = x;
		this.y = y;
	}
	
}
