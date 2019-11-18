import towers.*;
import monsters.*;
import general.*;
import sample.*;

import static org.junit.Assert;
import org.junit.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;

public class test_cases{
	
//	@Test
//	public void testAll() {
//		Main main = new Main();
//		Stage primaryStage = new Stage();
//		
//		try {
//			main.start(primaryStage);
//			}
//			catch(Exception e) {
//				//print("Error");
//			}
//	}
	

	
	@Test
	public void testShoot() {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
//        Parent root = loader.load();
//        primaryStage.setTitle("Tower Defence");
//        primaryStage.setScene(new Scene(root, 600, 480));
//        primaryStage.show();
//        MyController appController = (MyController)loader.getController();
//        appController.createArena();
//		
//		game.nextframe();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
		MyController appController = (MyController)loader.getController();
		Game game = new Game(100, appController);
		
		BasicTower testTower = new BasicTower(40,40);
		Monster testMonster = game.addNewMonster();
		
		List<Monster> monsterList = new ArrayList<Monster>();
		monsterList.add(testMonster);
		
		game.setMonsterList(monsterList);
		game.buildTower(testTower);
		
		testTower.shoot(game);
		
		List<Object> attackPair = new ArrayList<Object>();
		attackPair.add(testTower);
		attackPair.add(testMonster);
		
		Assert.assertEquals(game.getAttackList().get(0), attackPair);
	}
//	
//	@Test
//	public void testIceTowerShoot() {
//		MyController mc = new MyController();
//		Game game = new Game(100, mc);
//		
//		IceTower testTower = new IceTower(40,40);
//		FoxMonster testMonster = new FoxMonster(20,20,game);
//		
//		List<Monster> monsterList = new ArrayList<Monster>();
//		monsterList.add(testMonster);
//		
//		game.setMonsterList(monsterList);
//		game.buildTower(testTower);
//		
//		testTower.shoot(game);
//		
//		List<Object> attackPair = new ArrayList<Object>();
//		attackPair.add(testTower);
//		attackPair.add(testMonster);
//		
//		Assert.assertEquals(testMonster.getHp(), 5);
//		Assert.assertEquals(testMonster.getSpeed(), 4);
//		Assert.assertEquals(testMonster.getSlowForFrames(), 5);
//		Assert.assertEquals(game.getAttackList().get(0), attackPair);
//	}
//	
//	@Test
//	public void testCatapultTowerShoot() {
//		Game game = new Game();
//		CatapultTower testTower = new CatapultTower(10,20,game);
//		Monster testMonster1 = new Monster(10,125);
//		Monster testMonster2 = new Monster(10,69);
//		Monster testMonster3 = new Monster(10,100);
//		
//		game.monsterList.add(testMonster1);
//		game.monsterList.add(testMonster2);
//		game.monsterList.add(testMonster3);
//		game.towerList.add(testTower);
//		
//		testTower.shoot();
//		
//		List<Object> attackPair1 = new ArrayList<Object>();
//		attackPair1.add(testTower);
//		attackPair1.add(testMonster1);
//		
//		List<Object> attackPair2 = new ArrayList<Object>();
//		attackPair2.add(testTower);
//		attackPair2.add(testMonster3);
//		
//		Assert.assertEquals(game.monsterList.get(0).hp, 4);
//		Assert.assertEquals(game.monsterList.get(1).hp, 5);
//		Assert.assertEquals(game.monsterList.get(2).hp, 4);
//		
//		Assert.assertEquals(game.attackList.get(0), attackPair1);
//		Assert.assertEquals(game.attackList.get(1), attackPair2);
//	}
//	
//	
	
	
}

