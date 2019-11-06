import monsters.*;
import org.junit.Assert;
import org.junit.Test;

public class TestMonsterGeneration {
	
	@Test
	public void testGetName() {
		Monster nameMonster = new Monster(1, 4, "Max");
		Assert.assertEquals(nameMonster.getName(), "Max");
	}
	
	@Test
	public void testGetXPos() {
		Monster posMonster = new Monster(2, 3, "Tom");
		Assert.assertEquals(posMonster.getXPos(), 2);
	}

}
