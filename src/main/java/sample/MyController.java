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
import javafx.scene.paint.Color;
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
	private int resources;
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
		resources-=100;
		String nbAsStr = String.valueOf(resources);
		labelMoney.setText(nbAsStr);
		game.nextframe();
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
		this.resources = START_RESOURCES;
		game = new Game(resources);
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
					target.setOnDragDropped(new DragDroppedEventHandler());
					target.setOnDragOver(new EventHandler<DragEvent>() {
						public void handle(DragEvent event) {
							if (event.getGestureSource() != target && event.getDragboard().hasString()) {
								event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
							}
							event.consume();
						}
					});
			
					target.setOnDragEntered(new EventHandler<DragEvent>() {
						public void handle(DragEvent event) {
							if (event.getGestureSource() != target && event.getDragboard().hasString()) {
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
			//TODO : add this tower to the towerlist of game
			//add events to this tower
			Image image = new Image(getClass().getResourceAsStream(newTower.getImg()), 40, 40, false, false);
			((Label) event.getGestureTarget()).setGraphic(new ImageView(image));
			success = true;
		}
		event.setDropCompleted(success);
		event.consume();

	}
}
