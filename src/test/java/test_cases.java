import towers.*;
import monsters.*;
import general.*;
import sample.*;

import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.*;

public class test_cases extends ApplicationTest{
	
	private Scene s;
	private MyController MC;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Tower Defence");
        s = new Scene(root, 600, 480);
        primaryStage.setScene(s);
        primaryStage.show();
        MC = (MyController)loader.getController();
        MC.createArena();   		
	}
	
	@Test
	public void testNextFrameButton() {
		clickOn("#buttonNextFrame");
		AnchorPane b = (AnchorPane)s.lookup("#paneArena");
		for (javafx.scene.Node i : b.getChildren()) {
			if (i.getClass().getName().equals("javafx.scene.control.Label")) {
				Label h = (Label)i;
				if (h.getLayoutX() == 20 && h.getLayoutY() == 20) {
					Image image = new Image(getClass().getResourceAsStream("/penguin.png"), 20, 20, false, false);
					ImageView graphic = new ImageView(image);
					Assert.assertEquals(h.getGraphic(), graphic);
				}
			}
		}
	}

	
	@Test
	public void testShoot() {
		
		MyController mc = MC;
		
		BasicTower testTower = new BasicTower(40,40);
		Monster testMonster = new PenguinMonster(20, 20, mc.getGame());
		
		List<Monster> monsterList = new ArrayList<Monster>();
		monsterList.add(testMonster);
		
		mc.getGame().setMonsterList(monsterList);
		mc.getGame().buildTower(testTower);
		
		testTower.shoot(mc.getGame());
		
		List<Object> attackPair = new ArrayList<Object>();
		attackPair.add(testTower);
		attackPair.add(testMonster);
		
		Assert.assertEquals(mc.getGame().getAttackList().get(0), attackPair);
	}
	
	
	@Test
	public void testIceTowerShoot() {
		
		MyController mc = MC;
		
		IceTower testTower = new IceTower(40,40);
		Monster testMonster = new PenguinMonster(20, 20, mc.getGame());
		
		List<Monster> monsterList = new ArrayList<Monster>();
		monsterList.add(testMonster);
		
		mc.getGame().setMonsterList(monsterList);
		mc.getGame().buildTower(testTower);
		
		testTower.shoot(mc.getGame());
		
		List<Object> attackPair = new ArrayList<Object>();
		attackPair.add(testTower);
		attackPair.add(testMonster);
		
		Assert.assertEquals(mc.getGame().getAttackList().get(0), attackPair);
		
		Assert.assertEquals(testMonster.getHp(), 5);
		Assert.assertEquals(testMonster.getSpeed(), 1);
		Assert.assertEquals(testMonster.getSlowForFrames(), 5);

	}
	
	@Test
	public void testCatapultTowerShoot() {
		
		MyController mc = MC;
		
		CatapultTower testTower = new CatapultTower(40,40);
		
		Monster testMonsterPenguin = new PenguinMonster(20, 20, mc.getGame());
		Monster testMonsterFox = new FoxMonster(180, 60, mc.getGame());
		Monster testMonsterUnicorn = new UnicornMonster(180, 60, mc.getGame());
		
		List<Monster> monsterList = new ArrayList<Monster>();
		monsterList.add(testMonsterPenguin);
		monsterList.add(testMonsterFox);
		monsterList.add(testMonsterUnicorn);
		
		mc.getGame().setMonsterList(monsterList);
		mc.getGame().buildTower(testTower);
		
		testTower.shoot(mc.getGame());
		
		List<Object> attackPairFox = new ArrayList<Object>();
		attackPairFox.add(testTower);
		attackPairFox.add(testMonsterFox);
		
		Assert.assertEquals(mc.getGame().getMonsterList().get(0).getHp(), 5);
		Assert.assertEquals(mc.getGame().getMonsterList().get(1).getHp(), 4);
		Assert.assertEquals(mc.getGame().getMonsterList().get(2).getHp(), 9);
		
		Assert.assertEquals(mc.getGame().getAttackList().get(0), attackPairFox);
		
		testTower.shoot(mc.getGame());
		Assert.assertEquals(mc.getGame().getMonsterList().get(1).getHp(), 4);
		
		
	}
	
	@Test
	public void testLaserTowerShoot() {
		
		MyController mc = MC;
		
		LaserTower testTower = new LaserTower(40,0);
		
		Monster testMonsterPenguin = new PenguinMonster(20, 20, mc.getGame());
		Monster testMonsterFox = new FoxMonster(100, 20, mc.getGame());
		Monster testMonsterUnicorn = new UnicornMonster(180, 20, mc.getGame());
		
		List<Monster> monsterList = new ArrayList<Monster>();
		monsterList.add(testMonsterPenguin);
		monsterList.add(testMonsterFox);
		monsterList.add(testMonsterUnicorn);
		
		mc.getGame().setMonsterList(monsterList);
		mc.getGame().buildTower(testTower);
		
		Assert.assertEquals(testMonsterUnicorn.getHp(), 10);
		
		testTower.shoot(mc.getGame());
		
		List<Object> attackPairFox = new ArrayList<Object>();
		attackPairFox.add(testTower);
		attackPairFox.add(testMonsterFox);
		
		Assert.assertEquals(mc.getGame().getAttackList().get(0), attackPairFox);
		
		Assert.assertEquals(testMonsterPenguin.getHp(), 5);
		Assert.assertEquals(testMonsterFox.getHp(), 4);
		Assert.assertEquals(testMonsterUnicorn.getHp(), 9);
	}
	
	@Test
	public void testDeadMonster() {
		
		MyController mc = MC;
		
		int frame = mc.getGame().getFrameId();
		Assert.assertEquals(frame, 0);
		
		boolean gameover = mc.getGame().getGameOver();
		Assert.assertEquals(gameover, false);
		
		clickOn("#buttonNextFrame");
		
		gameover = mc.getGame().getGameOver();
		Assert.assertEquals(gameover, false);
		
		frame = mc.getGame().getFrameId();
		Assert.assertEquals(frame, 1);
		
//		BasicTower testTower1 = new BasicTower(40, 0);
//		BasicTower testTower2 = new BasicTower(40,160);
//		testTower2.setAttackPower(5);
//			
//		mc.getGame().buildTower(testTower1);
//		mc.getGame().buildTower(testTower2);
//		mc.getGame().printTowers();
//		mc.getGame().getTower(40,160);
//		
//		Assert.assertEquals(mc.getGame().getTower(40,160), testTower2);
//		boolean istower = mc.getGame().isTower(testTower1);
//		Assert.assertEquals(istower, true);
//		
//		clickOn("#buttonNextFrame");	//Penguin monster should be created
//		Assert.assertEquals(mc.getGame().getMonsterList().get(0).getAlive(), true);
//		Assert.assertEquals(mc.getGame().getMonsterList().get(0).getHp(), 4);
		
//		clickOn("#buttonNextFrame");	//Penguin monster moves and regains HP
//		Assert.assertEquals(mc.getGame().getMonsterList().get(0).getHp(), 5);
//		
//		clickOn("#buttonNextFrame"); //Penguin should move and be shot by a tower and die
//		Assert.assertEquals(mc.getGame().getMonsterList().get(0).getAlive(), false);
		
//		//label should be replaced by collision
//		AnchorPane b = (AnchorPane)s.lookup("#paneArena");
//		for (javafx.scene.Node i : b.getChildren()) {
//			if (i.getClass().getName().equals("javafx.scene.control.Label")) {
//				Label h = (Label)i;
//				if (h.getLayoutX() == 20 && h.getLayoutY() == 180) {
//					Image image = new Image(getClass().getResourceAsStream("/collision.png"), 20, 20, false, false);
//					ImageView graphic = new ImageView(image);
//					Assert.assertEquals(h.getGraphic(), graphic);
//				}
//			}
//		}
//		
//		clickOn("#buttonNextFrame");
//		//added penguinmonster to deadmonsterlist
//		Assert.assertEquals(mc.getGame().getDeadMonsterList().get(0).getType(), "Penguin");
		
	}
	
	@Test
	public void testMovingMonsters() {
		
		MyController mc = MC;
		
		Monster testMonsterPenguin = new PenguinMonster(20, 460, mc.getGame());
		Monster testMonsterFox = new FoxMonster(100, 60, mc.getGame());
		Monster testMonsterUnicorn = new UnicornMonster(340, 100, mc.getGame());
		
		List<Monster> monsterList = new ArrayList<Monster>();
		monsterList.add(testMonsterPenguin);
		monsterList.add(testMonsterFox);
		monsterList.add(testMonsterUnicorn);
		mc.getGame().setMonsterList(monsterList);
		
		testMonsterPenguin.move();
		testMonsterFox.move();
		testMonsterUnicorn.move();
		Assert.assertEquals(mc.getGame().getMonsterList().get(0).getX(), 100);
		Assert.assertEquals(mc.getGame().getMonsterList().get(0).getY(), 460);
		
		Assert.assertEquals(mc.getGame().getMonsterList().get(1).getX(), 180);
		Assert.assertEquals(mc.getGame().getMonsterList().get(1).getY(), 60);
		
		Assert.assertEquals(mc.getGame().getMonsterList().get(2).getX(), 340);
		Assert.assertEquals(mc.getGame().getMonsterList().get(2).getY(), 180);
		
	}
	
	@Test
	public void testGameOver() {
		
		MyController mc = MC;
		
		Monster testMonsterFox = new FoxMonster(420, 60, mc.getGame());
		
		List<Monster> monsterList = new ArrayList<Monster>();
		monsterList.add(testMonsterFox);
		mc.getGame().setMonsterList(monsterList);
		
		testMonsterFox.move();
		
		Assert.assertEquals(mc.getGame().getGameOver(), true);
		
		testMonsterFox.move();
		
		Assert.assertEquals(mc.getGame().getGameOver(), true);
		
	}
	
	@Test
	public void testsetGameOver() {
		
		MyController mc = MC;
		
		clickOn("#buttonNextFrame");
		Assert.assertEquals(mc.getGame().getGameOver(), false);
		
		mc.getGame().setGameOver(true);
		
		clickOn("#buttonNextFrame");
		
		Assert.assertEquals(mc.getGame().getGameOver(), true);
	}
	
	@Test
	public void addDifferentMonsters() {
		
		MyController mc = MC;
		
		clickOn("#buttonNextFrame"); //penguin generated with hp 5
		
		clickOn("#buttonNextFrame");
		
		clickOn("#buttonNextFrame"); //Fox generated
		
		clickOn("#buttonNextFrame");
		
		clickOn("#buttonNextFrame"); //unicorn generated
		
		Assert.assertEquals(mc.getGame().getMonsterList().get(0).getType(), "Penguin");
		
		Assert.assertEquals(mc.getGame().getMonsterList().get(1).getType(), "Fox");
		
		Assert.assertEquals(mc.getGame().getMonsterList().get(2).getType(), "Unicorn");
		
		Assert.assertEquals(mc.getGame().getMonsterList().size(), 3);
		
	}
	
//	@Test
//	public void addTowerTest() {
//		
//		//handle
//		
//		BasicTower testTower1 = new BasicTower(40, 0);
//		//BasicTower testTower2 = new BasicTower(40,160);
//		//Label lab = mc.getBasicTowerImg();
//		
//		
//		
//		//mc.addTower(testTower1, lab);
//		//mc.addTower(testTower2, lab);
//		
//		Assert.assertEquals(mc.getGame().isTower(testTower1), true);
//		//Assert.assertEquals(mc.getGame().isTower(testTower2), true);
//		
//	}
	
	
}

