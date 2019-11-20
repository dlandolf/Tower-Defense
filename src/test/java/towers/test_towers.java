import towers.*;
import org.junit.Assert;
import org.junit.Test;

public class test_towers {

	@Test
	public void testGetX() {

		BasicTower testTower = new BasicTower(10,20);
		Assert.assertEquals(testTower.getX(), 10);
		
	}
	
	@Test
	public void testDistance() {
		
		BasicTower testTower = new BasicTower(10,20);
		
		int distance = testTower.distance(10,20, 10, 50);
		Assert.assertEquals(distance, 30);
		
	}
	
	@Test
	public void testUpgrade() {
		
		BasicTower testTower = new BasicTower(10,20);
		
		testTower.upgradeTower();
		Assert.assertEquals(testTower.getLevel(), 2);
		Assert.assertEquals(testTower.getAttackPower(), 2);
		
	}
	
}
