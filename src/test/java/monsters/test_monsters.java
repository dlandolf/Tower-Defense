import towers.BasicTower;
import org.junit.Assert;
import org.junit.Test;

public class test_monsters {

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
	
}
