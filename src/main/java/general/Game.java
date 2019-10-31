package general;

import java.util.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import towers.*;
import monsters.*;

public class Game {
	public int resources;
	public int endzonex;
	public int endzoney;
	
	public List<BasicTower> towerList;
	public List<Monster> monsterList;
	public List<Object> attackList;
	public Monster monstertoadd;
	
	public Game(int resources) {
		this.resources= resources;
		towerList = new ArrayList<BasicTower>();
		monsterList = new ArrayList<Monster>();
		attackList=new ArrayList<Object>();
		monstertoadd = null;
	}
	
	public boolean nextframe () {
		for (Monster monster : monsterList) {
			//if (monster.move()) return true;
		}
		Monster newMonster = addNewMonster();
		if (newMonster != null) {
			monsterList.add(newMonster);
		}

		for (BasicTower tower : towerList) {
			tower.shoot(); //switch to private and add monsterlist as argument
		}
		return false;
	}
	
	public Monster addNewMonster() {
		return monstertoadd;
	}
	
	public boolean buildTower(BasicTower tower) {
		towerList.add(tower);
		return false;
	}
}
