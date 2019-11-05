package sample;

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

import general.*;
import towers.*;

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
	//private static final int ARENA_WIDTH = 480;
	//private static final int ARENA_HEIGHT = 480;
	private static final int GRID_WIDTH = 40;
	private static final int GRID_HEIGHT = 40;
	private static final int MAX_H_NUM_GRID = 12;
	private static final int MAX_V_NUM_GRID = 12;
	private static final int START_RESOURCES = 1500;
	private Label grids[][] = new Label[MAX_V_NUM_GRID][MAX_H_NUM_GRID]; // the grids on arena
	private Game game;
	
	
	public static int getGridWidth() {
		return GRID_WIDTH;
	}

	public static int getGridHeight() {
		return GRID_HEIGHT;
	}

	@FXML
	private void nextFrame() {
		String nbAsStr = String.valueOf(game.getResources());
		labelMoney.setText(nbAsStr);
		game.nextframe();
	}
	
	public void updateMoney(int money) {
		game.setResources(game.getResources()+money);
		String nbAsStr = String.valueOf(game.getResources());
		labelMoney.setText(nbAsStr);
	}
	
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
	
	public void upgradeTower(BasicTower tower) {
		if (game.getResources()>=100) {
			tower.upgradeTower();
			updateMoney(-100);
			System.out.println(tower.getType()+ " tower is being upgraded");
		}else {
			System.out.println("not enough resource to upgrade " + tower.getType() +" tower");
			//dialogbox
		}
	}
	
	public void destroyTower(BasicTower tower, Label source) {
		int level = tower.getLevel();
        game.destroyTower(tower.getX(), tower.getY()); 
        updateMoney(50*level);
        source.setGraphic(null);
	}
	
	/**
	 * A function that create the Arena
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
		game = new Game(START_RESOURCES);
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
				paneArena.getChildren().addAll(newLabel);
			}
		setDragAndDrop();
	}

	/**
	 * set drag and drop for towers
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


class MouseEnteredEventHandler implements EventHandler<MouseEvent>{
	private Label source;
	private AnchorPane paneArena;
	private BasicTower tower;
	private Game game;
	private Circle circle;
	private Circle circle2;
	private Label infos;
	
	public MouseEnteredEventHandler(Label e, AnchorPane pane, BasicTower tower, Game game, Circle circle, Circle circle2, Label infos) {
		this.source = e;
		this.paneArena = pane;
		this.tower = tower;
		this.game = game;
		this.circle = circle;
		this.circle2 =circle2;
		this.infos = infos;
	}
	
	@Override 
	public void handle(MouseEvent event) {
		if (!game.isTower(tower)) {
			source.removeEventHandler(MouseEvent.MOUSE_ENTERED, this);
			return;
		}
		if (!paneArena.getChildren().contains(circle)) {
			System.out.println(tower.getLevel());
			infos.setText(tower.getType() + "#" + tower.getLevel()
			+ "\nRange " + tower.getMaxRange());
			paneArena.getChildren().addAll(circle, circle2, infos);
		}
	}
}


class MouseExitedEventHandler implements EventHandler<MouseEvent>{
	private Label source;
	private AnchorPane paneArena;
	private BasicTower tower;
	private Game game;
	private Circle circle;
	private Circle circle2;
	private Label infos;
	
	public MouseExitedEventHandler(Label e, AnchorPane pane, BasicTower tower, Game game, Circle circle, Circle circle2, Label infos) {
		this.source = e;
		this.paneArena = pane;
		this.tower = tower;
		this.game = game;
		this.circle = circle;
		this.circle2 =circle2;
		this.infos = infos;
	}
	
	@Override 
	public void handle(MouseEvent event) {
		if (!game.isTower(tower)) {
			source.removeEventHandler(MouseEvent.MOUSE_EXITED, this);
			return;
		}
		paneArena.getChildren().removeAll(circle, circle2, infos);
	}
}


class ClickEventHandler implements EventHandler<MouseEvent> {
	private Label source;
	private Circle circle;
	private Button buttonUpgrade;
	private Button buttonDestroy;
	private AnchorPane paneArena;
	private BasicTower tower;
	private Game game;
	private MyController mc;
	
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
		buttonUpgrade.setLayoutX(source.getLayoutX()+50);
		buttonUpgrade.setLayoutY(source.getLayoutY()-10);
		buttonDestroy.setLayoutX(source.getLayoutX()+50);
		buttonDestroy.setLayoutY(source.getLayoutY()+30);
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
	
	@Override
	public void handle(MouseEvent event) {
		if (!game.isTower(tower)) {
			source.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
			return;
		}
    	if (!paneArena.getChildren().contains(buttonUpgrade)) {
			paneArena.getChildren().addAll(buttonUpgrade, buttonDestroy);
		}else {
			paneArena.getChildren().removeAll(buttonUpgrade, buttonDestroy);
		}
	}
}

class DragEventHandler implements EventHandler<MouseEvent> {
	private Label source;
	public DragEventHandler(Label e) {
		source = e;
	}

	@Override
	public void handle(MouseEvent event) {
		Dragboard db = source.startDragAndDrop(TransferMode.ANY);
		ClipboardContent content = new ClipboardContent();
		content.putString(source.getText());
		db.setContent(content);
		event.consume();
	}
}

class DragDroppedEventHandler implements EventHandler<DragEvent> {
	private MyController mc;
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
	public BasicTower newInstance(ValidatorType validatorType, int x, int y) {
	    return validatorType.create(x, y);
	}
	public String changeToEnum(String str) {
		String newstr = "";
		int i=0;
		while (i<str.length() && str.charAt(i) !=' ') {
			newstr+=str.charAt(i);
			i++;
		}
		return newstr.toUpperCase();
	}
	
	@Override
	public void handle(DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasString()) {
			BasicTower newTower = newInstance(ValidatorType.valueOf(changeToEnum(db.getString())), 
					((int)((Label) event.getGestureTarget()).getLayoutX())/MyController.getGridWidth(), 
					((int)((Label) event.getGestureTarget()).getLayoutY())/MyController.getGridHeight());
			if (mc.addTower(newTower, ((Label) event.getGestureTarget()))) {
				Image image = new Image(getClass().getResourceAsStream(newTower.getImg()), 40, 40, false, false);
				((Label) event.getGestureTarget()).setGraphic(new ImageView(image));
				success = true;
			}else {
				//dialog box
			}
		}
		event.setDropCompleted(success);
		event.consume();
	}
}