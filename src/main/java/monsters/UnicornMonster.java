package monsters;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import general.Game;

public class UnicornMonster extends Monster {
	
	public UnicornMonster(int x, int y, Game game) throws IOException {
		super(x,y,game);
		this.hp = (int) (10 + Math.floor(game.frameid/4));
		this.speed = 1;
		this.img = ImageIO.read(new File("unicorn.png"));
	}

}