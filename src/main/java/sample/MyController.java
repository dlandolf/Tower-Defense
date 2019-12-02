package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Arc;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Line;
import general.*;
import towers.*;
import monsters.*;

/**
 * Class for MyController for the game window
 * @author senamec
 *
 */
public class MyController {
	@FXML
	private Button buttonNextFrame;
	@FXML
	private AnchorPane paneArena;
	@FXML
	private Label labelBasicTower;
	@FXML
	private Label labelIceTower;
	@FXML
	private Label labelCatapult;
	@FXML
	private Label labelLaserTower;
	@FXML
	private Label labelMoney;
	@FXML
	private Label basicTowerImg;
	@FXML
	private Label iceTowerImg;
	@FXML
	private Label catapultImg;
	@FXML
	private Label laserTowerImg;
	private static final int ARENA_WIDTH = 480;
	private static final int ARENA_HEIGHT = 480;
	private static final int GRID_WIDTH = 40;
	private static final int GRID_HEIGHT = 40;
	private static final int MAX_H_NUM_GRID = 12;
	private static final int MAX_V_NUM_GRID = 12;
	private static final int START_RESOURCES = 1500;
	private Label grids[][] = new Label[MAX_V_NUM_GRID][MAX_H_NUM_GRID]; // the grids on arena
	private List<Label> monsterLabelList = new ArrayList<Label>();
	private List<Line> lineList = new ArrayList<Line>();
	private Game game;
	private Button but1 = null;
	private Button but2 = null;
	
	/**
	 * Getter for basicTowerImg
	 * @return basicTowerImg
	 */
	public Label getBasicTowerImg() {
		return basicTowerImg;
	}
	
	/**
	 * Getter for gridwidth
	 * @return grid_width
	 */
	public static int getGridWidth() {
		return GRID_WIDTH;
	}

	/**
	 * getter for gridHeight
	 * @return grid_height
	 */
	public static int getGridHeight() {
		return GRID_HEIGHT;
	}

	/**
	 * Getter for monsterLabelList
	 * @return monsterLabelList
	 */
	public List<Label> getMonsterLabelList() {
		return monsterLabelList;
	}

	/**
	 * Setter for monsterLabel List
	 * @param monsterLabelList New monsterLabelList
	 */
	public void setMonsterLabelList(List<Label> monsterLabelList) {
		this.monsterLabelList = monsterLabelList;
	}

	/**
	 * Next Frame function to refresh images and call the frame actions of the game
	 */
	@FXML
	private void nextFrame() {
		removeLines();
		String nbAsStr = String.valueOf(game.getResources());
		labelMoney.setText(nbAsStr);
		if (!game.getGameOver()) {
			game.nextframe();
			updateMonsterLabels();
			updateLineList();
			nbAsStr = String.valueOf(game.getResources());
			labelMoney.setText(nbAsStr);
			if (game.getGameOver()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Gameover");
				alert.setHeaderText("Gameover!");
				alert.setContentText("You lost.");
				alert.showAndWait();
			}
		}
	}
	
	/**
	 * Update monster label and images
	 */
	public void updateMonsterLabels() {
		
		for (Monster deadmonster : game.getDeadMonsterList()) {
			paneArena.getChildren().remove(paneArena.getChildren().indexOf(deadmonster.getLabel()));
			getMonsterLabelList().remove(getMonsterLabelList().indexOf(deadmonster.getLabel()));
		}
		
		for (Monster monster : game.getMonsterList()) {
			if (monster.getIsNew()) {
				Image image = new Image(getClass().getResourceAsStream(monster.getImg()), 20, 20, false, false);
				getMonsterLabelList().add(monster.getLabel());
				getMonsterLabelList().get(game.getMonsterList().indexOf(monster)).setGraphic(new ImageView(image));
				paneArena.getChildren().addAll(monster.getLabel());
			}
			else {
				Image image = new Image(getClass().getResourceAsStream(monster.getImg()), 20, 20, false, false);
				getMonsterLabelList().get(game.getMonsterList().indexOf(monster)).setLayoutX(monster.getX()-sample.MyController.getGridWidth()/4);
				getMonsterLabelList().get(game.getMonsterList().indexOf(monster)).setLayoutY(monster.getY()-sample.MyController.getGridHeight()/4);
				getMonsterLabelList().get(game.getMonsterList().indexOf(monster)).setGraphic(new ImageView(image));
			}
			
		}
		
		for (int i = 0; i < MAX_V_NUM_GRID; i++)
			for (int j = 0; j < MAX_H_NUM_GRID; j++) {
				
				if (j % 2 == 0 || i == ((j + 1) / 2 % 2) * (MAX_V_NUM_GRID - 1)) {
					Label target = grids[i][j];
					Label infos = new Label();
					boolean monsterInField = false;
					int nMonster = 0;
					target.setOnMouseEntered(new MouseEnteredMonsterEventHandler(target, monsterInField, paneArena, infos));
					target.setOnMouseExited(new MouseExitedMonsterEventHandler(target, monsterInField, paneArena, infos));
					
					for (Monster monster : game.getMonsterList()) {

						if ((int) monster.getX()/getGridWidth() == j && (int) monster.getY()/getGridHeight() == i) {
							if (!monsterInField) {
								infos.setText(monster.getType() + " HP: " + monster.getHp());
								infos.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
								infos.setStyle("-fx-border-color: black;");
								
								if (target.getLayoutX()>= 400) {
									infos.setLayoutX(target.getLayoutX()-50);
								} else {
									infos.setLayoutX(target.getLayoutX()+30);	
								}
								
								if (target.getLayoutY()==0) {
									infos.setLayoutY(target.getLayoutY()+30);
								} else if (target.getLayoutY()>= 400){
									infos.setLayoutY(target.getLayoutY()-10);
								} else {
									infos.setLayoutY(target.getLayoutY()-10);	
								}
								infos.setMouseTransparent(true);
								monsterInField = true;
							}
							else {
								infos.setText(infos.getText() + "\n" + monster.getType() + " HP: " + monster.getHp());
								if (target.getLayoutY()== 440){
									infos.setLayoutY(target.getLayoutY()-10*nMonster);
								}
							}
								
							nMonster += 1;
							target.setOnMouseEntered(new MouseEnteredMonsterEventHandler(target, monsterInField, paneArena, infos));
							target.setOnMouseExited(new MouseExitedMonsterEventHandler(target, monsterInField, paneArena, infos));
							
						}
						
					}
				}
				

			}
		
		
		
	}
	
	/**
	 * Update money in the window and in the game
	 * @param money New amount of Resources
	 */
	public void updateMoney(int money) {
		game.setResources(game.getResources()+money);
		String nbAsStr = String.valueOf(game.getResources());
		labelMoney.setText(nbAsStr);
	}
	
	/**
	 * Add a new line in the window
	 * @param i x_position of start of line
	 * @param j y_position of start of line
	 * @param k x_position of end of line
	 * @param l y_position of end of line
	 */
	public void addLine(int i, int j, int k, int l) {
		lineList.add(new Line(i, j, k, l));
	}
	
	/**
	 * Update a line list to show them
	 */
	public void updateLineList() {
		for (Line line : lineList) {
			paneArena.getChildren().addAll(line);
		}
	}
	
	/**
	 * Remove all lines of the window
	 */
	public void removeLines() {
		for (Line line : lineList) {
			paneArena.getChildren().remove(line);
		}
		lineList.clear();
	}
	
	/**
	 * Add a tower to the game after drag and drop
	 * @param tower Tower to be added
	 * @param lab Label to add the tower
	 * @return true if tower was added
	 */
	public boolean addTower(BasicTower tower, Label lab) {
		if (game.getResources()<100) return false;
		updateMoney(-100);
		game.buildTower(tower);
		Circle circle = new Circle(lab.getLayoutX()+20, lab.getLayoutY()+20,tower.getMaxRange(), Color.web("rgba(0%,0%,0%,0.3)"));
		Circle circle2 = new Circle(lab.getLayoutX()+20, lab.getLayoutY()+20,tower.getMinRange(), Color.web("rgba(100%,0%,0%,0.45)"));
		Label infos = new Label(tower.getType() + "#" + tower.getLevel()
								+ "\nRange " + tower.getMaxRange());
		infos.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		infos.setStyle("-fx-border-color: black;");
		infos.setLayoutX(lab.getLayoutX()-20);
		if (tower.getY()==0) {
			infos.setLayoutY(lab.getLayoutY()+30);
		}else {
			infos.setLayoutY(lab.getLayoutY()-30);	
		}
		circle.setMouseTransparent(true);
		circle2.setMouseTransparent(true);
		infos.setMouseTransparent(true);
		lab.setOnMouseEntered(new MouseEnteredEventHandler(lab, paneArena, tower, game, circle, circle2, infos));
		lab.setOnMouseExited(new MouseExitedEventHandler(lab, paneArena, tower, game, circle, circle2, infos));
		lab.setOnMouseClicked(new ClickEventHandler(lab, paneArena, tower, game, this));
		return true;
	}
	
	/**
	 * Upgrade the chosen tower
	 * @param tower Tower to be upgraded
	 */
	public void upgradeTower(BasicTower tower) {
		if (game.getResources()>=100) {
			tower.upgradeTower();
			updateMoney(-100);
			System.out.println(tower.getType()+ " tower is being upgraded");
		}else {
			System.out.println("not enough resource to upgrade " + tower.getType() +" tower");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Not enough money!");
			alert.showAndWait();
		}
	}
	
	/**
	 * Destroy the chosen tower
	 * @param tower Tower to be destroyed
	 * @param source Label where tower is in
	 */
	public void destroyTower(BasicTower tower, Label source) {
		int level = tower.getLevel();
        game.destroyTower(tower.getX(), tower.getY()); 
        updateMoney(50*level);
        source.setGraphic(null);
	}
	
	/**
	 * Add buttons to the tower (destroy and upgrade)
	 * @param b1 Button one to be added
	 * @param b2 Button two to be added
	 */
	public void addButtons(Button b1, Button b2) {
		but1 = b1;
		but2 = b2;
	}
	
	/**
	 * Remove the buttons from the tower
	 */
	public void removeButtons() {
		paneArena.getChildren().removeAll(but1, but2);
	}
	
	/**
	 * getter for the game
	 * @return game
	 */
	public Game getGame() {
		return game;
	}
	
	
	/**
	 * A function that create the Arena at the beginning
	 */
	@FXML
	public void createArena() {
		Image image = new Image(getClass().getResourceAsStream("/basicTower.png"), 40, 40, false, false);
		basicTowerImg.setGraphic(new ImageView(image));
		image = new Image(getClass().getResourceAsStream("/iceTower.png"), 40, 40, false, false);
		iceTowerImg.setGraphic(new ImageView(image));
		image = new Image(getClass().getResourceAsStream("/laserTower.png"), 40, 40, false, false);
		laserTowerImg.setGraphic(new ImageView(image));
		image = new Image(getClass().getResourceAsStream("/catapult.png"), 40, 40, false, false);
		catapultImg.setGraphic(new ImageView(image));
		game = new Game(START_RESOURCES, this);
		String nbAsStr = String.valueOf(START_RESOURCES);
		labelMoney.setText(nbAsStr);
		if (grids[0][0] != null)
			return; // created already
		for (int i = 0; i < MAX_V_NUM_GRID; i++)
			for (int j = 0; j < MAX_H_NUM_GRID; j++) {
				Label newLabel = new Label();
				if (j % 2 == 0 || i == ((j + 1) / 2 % 2) * (MAX_V_NUM_GRID - 1))
					newLabel.setBackground(
							new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				else
					newLabel.setBackground(
							new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
				newLabel.setLayoutX(j * getGridWidth()); newLabel.setLayoutY(i * getGridHeight());
				newLabel.setMinWidth(getGridWidth()); newLabel.setMaxWidth(getGridWidth());
				newLabel.setMinHeight(getGridHeight()); newLabel.setMaxHeight(getGridHeight());
				newLabel.setStyle("-fx-border-color: black;");
				grids[i][j] = newLabel;
				newLabel.setOnMouseClicked(new EventHandler<MouseEvent>(){
		            @Override
		            public void handle(MouseEvent event) {
		            	paneArena.getChildren().removeAll(but1, but2);
		            }
		        });
				paneArena.getChildren().addAll(newLabel);
			}
		image = new Image(getClass().getResourceAsStream("/start.png"), 40, 40, false, false);
		grids[0][0].setGraphic(new ImageView(image));
		image = new Image(getClass().getResourceAsStream("/end.png"), 40, 40, false, false);
		grids[0][MAX_V_NUM_GRID-1].setGraphic(new ImageView(image));
		setDragAndDrop();
	}

	/**
	 * set drag and drop for towers to the green grids
	 */
	private void setDragAndDrop() {
		Label source1 = labelBasicTower;
		Label source2 = labelIceTower;
		Label source3 = labelCatapult;
		Label source4 = labelLaserTower;
		source1.setOnDragDetected(new DragEventHandler(source1));
		source2.setOnDragDetected(new DragEventHandler(source2));
		source3.setOnDragDetected(new DragEventHandler(source3));
		source4.setOnDragDetected(new DragEventHandler(source4));
		for (int i = 0; i < MAX_V_NUM_GRID; i++) {
			for (int j = 0; j < MAX_H_NUM_GRID; j++) {
				Label target = grids[i][j];
				Color color = (Color)target.getBackground().getFills().get(0).getFill();
				if(color == Color.GREEN) {
					target.setOnDragDropped(new DragDroppedEventHandler(this));
					target.setOnDragOver(new EventHandler<DragEvent>() {
						public void handle(DragEvent event) {
							if (event.getGestureSource() != target && event.getDragboard().hasString()
									&& game.getTower((int)target.getLayoutX()/getGridWidth(),(int)target.getLayoutY()/getGridHeight())==null){
								event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
							}
							event.consume();
						}
					});
					target.setOnDragEntered(new EventHandler<DragEvent>() {
						public void handle(DragEvent event) {
							if (event.getGestureSource() != target && event.getDragboard().hasString()
									&& game.getTower((int)target.getLayoutX()/getGridWidth(),(int)target.getLayoutY()/getGridHeight())==null) {
								target.setStyle("-fx-border-color: blue;");							
							}
							event.consume();
						}
					});
					target.setOnDragExited((event) -> {
						target.setStyle("-fx-border-color: black;");
						event.consume();
					});
				}
			}
		}
	}
}

/**
 * Class for Mouse Enter Event on the tower
 *
 */
class MouseEnteredEventHandler implements EventHandler<MouseEvent>{
	private Label source;
	private AnchorPane paneArena;
	private BasicTower tower;
	private Game game;
	private Circle circle;
	private Circle circle2;
	private Label infos;
	
	/**
	 * Constructor
	 * @param e Label to add to
	 * @param pane paneArena
	 * @param tower Tower which is placed on
	 * @param game Game where it is placed
	 * @param circle Shows Maximum Range 
	 * @param circle2 Shows Minimum Range
	 * @param infos Info of Tower
	 */
	public MouseEnteredEventHandler(Label e, AnchorPane pane, BasicTower tower, Game game, Circle circle, Circle circle2, Label infos) {
		this.source = e;
		this.paneArena = pane;
		this.tower = tower;
		this.game = game;
		this.circle = circle;
		this.circle2 =circle2;
		this.infos = infos;
	}
	
	/**
	 * Handler for mouse enter event on the tower
	 * @param event Event that occurred
	 */
	@Override 
	public void handle(MouseEvent event) {
		if (!game.isTower(tower)) {
			source.removeEventHandler(MouseEvent.MOUSE_ENTERED, this);
			return;
		}
		if (!paneArena.getChildren().contains(circle)) {
			infos.setText(tower.getType() + "#" + tower.getLevel()
			+ "\nRange " + tower.getMaxRange());
			paneArena.getChildren().addAll(circle, circle2, infos);
		}
	}
}

/**
 * Class for Mouse Exited Event on a tower
 */
class MouseExitedEventHandler implements EventHandler<MouseEvent>{
	private Label source;
	private AnchorPane paneArena;
	private BasicTower tower;
	private Game game;
	private Circle circle;
	private Circle circle2;
	private Label infos;
	
	/**
	 * Constructor
	 * @param e Label to add to
	 * @param pane paneArena
	 * @param tower Tower which is placed on
	 * @param game Game where it is placed
	 * @param circle Shows Maximum Range 
	 * @param circle2 Shows Minimum Range
	 * @param infos Info of Tower
	 */
	public MouseExitedEventHandler(Label e, AnchorPane pane, BasicTower tower, Game game, Circle circle, Circle circle2, Label infos) {
		this.source = e;
		this.paneArena = pane;
		this.tower = tower;
		this.game = game;
		this.circle = circle;
		this.circle2 =circle2;
		this.infos = infos;
	}
	
	/**
	 * Handler for mouse exit event on the tower
	 * @param event Event that occurred
	 */
	@Override 
	public void handle(MouseEvent event) {
		if (!game.isTower(tower)) {
			source.removeEventHandler(MouseEvent.MOUSE_EXITED, this);
			return;
		}
		paneArena.getChildren().removeAll(circle, circle2, infos);
	}
}

/**
 * Class for mouse enter event on a monster
 *
 */
class MouseEnteredMonsterEventHandler implements EventHandler<MouseEvent>{
	private Label source;
	private AnchorPane paneArena;
	private boolean monsterInField;
	private Label infos;
	
	/**
	 * Constructor
	 * @param source Label to add to
	 * @param monsterInField Boolean if monster is in grid
	 * @param pane AnchorPane
	 * @param infos Monster Info
	 */
	public MouseEnteredMonsterEventHandler(Label source, boolean monsterInField, AnchorPane pane, Label infos) {
		this.source = source;
		this.paneArena = pane;
		this.monsterInField = monsterInField;
		this.infos = infos;
	}
	
	/**
	 * Handler for mouse enter event on the monster
	 * @param event Event that occurred
	 */
	@Override 
	public void handle(MouseEvent event) {
		if (!monsterInField) { 
			source.removeEventHandler(MouseEvent.MOUSE_ENTERED, this);
			return;
		}
		paneArena.getChildren().addAll(infos);
	}
}

/**
 * Class for mouse exited event on a monster
 *
 */
class MouseExitedMonsterEventHandler implements EventHandler<MouseEvent>{
	private Label source;
	private AnchorPane paneArena;
	private boolean monsterInField;
	private Label infos;
	
	/**
	 * Constructor
	 * @param source Label to add to
	 * @param monsterInField Boolean if monster is in field
	 * @param pane AnchorPane
	 * @param infos Monster Info
	 */
	public MouseExitedMonsterEventHandler(Label source, boolean monsterInField, AnchorPane pane, Label infos) {
		this.source = source;
		this.paneArena = pane;
		this.monsterInField = monsterInField;
		this.infos = infos;
	}
	
	/**
	 * Handler for mouse exit event on the monster
	 * @param event Event that occurred
	 */
	@Override 
	public void handle(MouseEvent event) {
		if (!monsterInField) {
			source.removeEventHandler(MouseEvent.MOUSE_EXITED, this);
			return;
		}
		paneArena.getChildren().removeAll(infos);
	}
}


/**
 * Class for click event on a tower
 */
class ClickEventHandler implements EventHandler<MouseEvent> {
	private Label source;
	private Circle circle;
	private Button buttonUpgrade;
	private Button buttonDestroy;
	private AnchorPane paneArena;
	private BasicTower tower;
	private Game game;
	private MyController mc;
	
	/**
	 * Constructor
	 * @param e Label to add to
	 * @param pane AnchorPane
	 * @param tower Tower that is placed in grid
	 * @param game Game where it is placed
	 * @param mc MyController
	 */
	public ClickEventHandler(Label e, AnchorPane pane, BasicTower tower, Game game, MyController mc) {
		this.source = e;
		this.paneArena = pane;
		this.tower = tower;
		this.game = game;
		this.mc = mc;
		circle = new Circle(source.getLayoutX()+20, source.getLayoutY()+20,20, Color.web("rgba(0,0,100%,0.5)"));
		circle.setMouseTransparent(true);
		buttonUpgrade = new Button("Upgrade");
		buttonDestroy = new Button("Destroy ");
		if (source.getLayoutX()<400) {
			buttonUpgrade.setLayoutX(source.getLayoutX()+50);
			buttonDestroy.setLayoutX(source.getLayoutX()+50);
				
		}else {
			buttonUpgrade.setLayoutX(source.getLayoutX()-50);
			buttonDestroy.setLayoutX(source.getLayoutX()-50);
		}
		buttonUpgrade.setLayoutY(source.getLayoutY()-10);
		buttonDestroy.setLayoutY(source.getLayoutY()+20);
		EventHandler<ActionEvent> eventUpgrade = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                mc.upgradeTower(tower); 
                paneArena.getChildren().removeAll(buttonUpgrade, buttonDestroy);
            } 
        }; 
        buttonUpgrade.setOnAction(eventUpgrade); 
        EventHandler<ActionEvent> eventDestroy = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	mc.destroyTower(tower, source);
                paneArena.getChildren().removeAll(buttonUpgrade, buttonDestroy);
            } 
        }; 
        buttonDestroy.setOnAction(eventDestroy); 
	}
	
	/**
	 * Handler for click on a tower (add buttons)
	 * @param event Event that occurred
	 */
	@Override
	public void handle(MouseEvent event) {
		if (!game.isTower(tower)) {
			source.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
			return;
		}
    	if (!paneArena.getChildren().contains(buttonUpgrade)) {
			mc.removeButtons();
			paneArena.getChildren().addAll(buttonUpgrade, buttonDestroy);
			mc.addButtons(buttonUpgrade, buttonDestroy);
		}else {
			paneArena.getChildren().removeAll(buttonUpgrade, buttonDestroy);
		}
	}
}

/**
 * Class for drag event of towers
 *
 */
class DragEventHandler implements EventHandler<MouseEvent> {
	private Label source;
	/**
	 * Constructor
	 * @param e Label to set
	 */
	public DragEventHandler(Label e) {
		source = e;
	}

	/**
	 * Handler for dragging tower
	 * @param event Event that occurred
	 */
	@Override
	public void handle(MouseEvent event) {
		Dragboard db = source.startDragAndDrop(TransferMode.ANY);
		ClipboardContent content = new ClipboardContent();
		content.putString(source.getText());
		db.setContent(content);
		event.consume();
	}
}

/**
 * Class for dropped towers event
 *
 */
class DragDroppedEventHandler implements EventHandler<DragEvent> {
	private MyController mc;
	/**
	 * Constructor
	 * @param mc MyController
	 */
	public DragDroppedEventHandler(MyController mc){
		this.mc = mc;
	}
	enum ValidatorType {
	    BASIC {
	        public BasicTower create(int x, int y) {return new BasicTower(x,y);}
	    },
	    ICE {
	        public BasicTower create(int x, int y) {return new IceTower(x, y);}
	    },
	    CATAPULT {
	        public BasicTower create(int x, int y) {return new CatapultTower(x, y);}
	    },
	    LASER {
	        public BasicTower create(int x, int y) {return new LaserTower(x, y);}
	    };
	    public BasicTower create(int x, int y) {return null;}
	}
	
	/**
	 * New  instance of a tower
	 * @param validatorType Factory for creating Tower
	 * @param x x_position
	 * @param y y_position
	 * @return tower
	 */
	public BasicTower newInstance(ValidatorType validatorType, int x, int y) {
	    return validatorType.create(x, y);
	}
	
	/**
	 * Change string to the upper case string for the enum
	 * @param str String partly lower case
	 * @return string UPPER case
	 */
	public String changeToEnum(String str) {
		String newstr = "";
		int i=0;
		while (i<str.length() && str.charAt(i) !=' ') {
			newstr+=str.charAt(i);
			i++;
		}
		return newstr.toUpperCase();
	}
	
	/**
	 * Handler for towers dropped on a green grid (adding a new tower)
	 * @param event Event that occurred
	 */
	@Override
	public void handle(DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasString()) {
			//set coordinates of tower as the middle of the grid that it is in:
			int gridX = (int)((Label) event.getGestureTarget()).getLayoutX()/MyController.getGridWidth();
			int gridY = (int)((Label) event.getGestureTarget()).getLayoutY()/MyController.getGridHeight();
			
			if (mc.getGame().getTower(((int) (gridX+0.5)*MyController.getGridWidth()), ((int) (gridY+0.5)*MyController.getGridHeight())) == null) {
				BasicTower newTower = newInstance(ValidatorType.valueOf(changeToEnum(db.getString())), 
						((int) (gridX+0.5)*MyController.getGridWidth()), 
						((int) (gridY+0.5)*MyController.getGridHeight()));	//position needs to be in pixels!
				
				if (mc.addTower(newTower, ((Label) event.getGestureTarget()))) {
					Image image = new Image(getClass().getResourceAsStream(newTower.getImg()), 40, 40, false, false);
					((Label) event.getGestureTarget()).setGraphic(new ImageView(image));
					success = true;
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("Not enough money!");
					alert.showAndWait();
				}

			}
			
		}
		event.setDropCompleted(success);
		event.consume();
	}
}