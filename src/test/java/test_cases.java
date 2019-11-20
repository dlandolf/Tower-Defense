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
	private MyController mc;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Tower Defence");
        s = new Scene(root, 600, 480);
        primaryStage.setScene(s);
        primaryStage.show();
        mc = (MyController)loader.getController();
        mc.createArena();   		
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
		
		CatapultTower testTower = new CatapultTower(40,40);
		
		Monster testMonsterPenguin = new PenguinMonster(20, 20, mc.getGame());
		Monster testMonsterFox = new FoxMonster(140, 20, mc.getGame());
		Monster testMonsterUnicorn = new UnicornMonster(20, 60, mc.getGame());
		
		List<Monster> monsterList = new ArrayList<Monster>();
		monsterList.add(testMonsterPenguin);
		monsterList.add(testMonsterFox);
		monsterList.add(testMonsterUnicorn);
		
		mc.getGame().setMonsterList(monsterList);
		mc.getGame().buildTower(testTower);
		
		testTower.shoot(mc.getGame());
		
		List<Object> attackPair = new ArrayList<Object>();
		
		attackPair.add(testTower);
		attackPair.add(testMonsterFox);
		
		Assert.assertEquals(mc.getGame().getMonsterList().get(0).getHp(), 5);
		Assert.assertEquals(mc.getGame().getMonsterList().get(1).getHp(), 4);
		Assert.assertEquals(mc.getGame().getMonsterList().get(2).getHp(), 10);
		
		Assert.assertEquals(mc.getGame().getAttackList().get(0), attackPair);
		
	}
	
	
	
	
}

