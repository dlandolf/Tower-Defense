package monsters;

import java.io.IOException;
import general.Game;
import java.io.File;
import javax.imageio.ImageIO;

public class FoxMonster extends Monster {
	
	public FoxMonster(int x, int y, Game game) throws IOException {
		super(x,y,game);
		this.hp = (int) (5 + Math.floor(game.frameid/4));
		this.speed = 2;
		this.img = ImageIO.read(new File("fox.png"));
	}
	
}