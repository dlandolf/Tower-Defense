package monsters;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import general.Game;

public class PenguinMonster extends Monster {
	
	public PenguinMonster(int x, int y, Game game) throws IOException {
		super(x,y,game);
		this.hp = (int) (5 + Math.floor(game.frameid/4));
		this.speed = 1;
		this.img = ImageIO.read(new File("penguin.png"));
	}
	
	//add function to replenish HP!!

}
