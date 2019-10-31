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
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
	
	private static final int ARENA_WIDTH = 480;
	private static final int ARENA_HEIGHT = 480;
	private static final int GRID_WIDTH = 40;
	private static final int GRID_HEIGHT = 40;
	private static final int MAX_H_NUM_GRID = 12;
	private static final int MAX_V_NUM_GRID = 12;
	private static final int START_RESOURCES = 1500;
	private int resources;
	private Label grids[][] = new Label[MAX_V_NUM_GRID][MAX_H_NUM_GRID]; // the grids on arena
	private int x = -1, y = 0; // where is my monster
	private static final Game game;
	
	
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
		this.resources = START_RESOURCES;
		this.game = new Game(resources);
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
				newLabel.setLayoutX(j * GRID_WIDTH);
				newLabel.setLayoutY(i * GRID_HEIGHT);
				newLabel.setMinWidth(GRID_WIDTH);
				newLabel.setMaxWidth(GRID_WIDTH);
				newLabel.setMinHeight(GRID_HEIGHT);
				newLabel.setMaxHeight(GRID_HEIGHT);
				newLabel.setStyle("-fx-border-color: black;");
				grids[i][j] = newLabel;
				paneArena.getChildren().addAll(newLabel);
			}

		setDragAndDrop();
	}

	/**
	 * A function that demo how drag and drop works
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
					target.setText("Drop\nHere");
					target.setOnDragDropped(new DragDroppedEventHandler());
			
					// well, you can also write anonymous class or even lambda
					// Anonymous class
					target.setOnDragOver(new EventHandler<DragEvent>() {
						public void handle(DragEvent event) {
							/* data is dragged over the target
							 * accept it only if it is not dragged from the same node and if it has a string data */
							if (event.getGestureSource() != target && event.getDragboard().hasString()) {
								/* allow for both copying and moving, whatever user chooses */
								event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
							}
							event.consume();
						}
					});
			
					target.setOnDragEntered(new EventHandler<DragEvent>() {
						public void handle(DragEvent event) {
							/* the drag-and-drop gesture entered the target 
							 * show to the user that it is an actual gesture target */
							if (event.getGestureSource() != target && event.getDragboard().hasString()) {
								target.setStyle("-fx-border-color: blue;");
							}
							event.consume();
						}
					});
					// lambda
					target.setOnDragExited((event) -> {
						/* mouse moved away, remove the graphical cues */
						target.setStyle("-fx-border-color: black;");
						System.out.println("Exit");
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
	        public BasicTower create(int x, int y) {
	            return new BasicTower(x,y, game);
	        }
	    },
	    ICE {
	        public BasicTower create(int x, int y) {
	            return new IceTower(x, y , game);
	        }
	    },
	    CATAPULT {
	        public BasicTower create(int x, int y) {
	            return new CatapultTower(x, y, game);
	        }
	    },
	    LASER {
	        public BasicTower create(int x, int y) {
	            return new LaserTower(x, y, game);
	        }
	    };
	    public BasicTower create(int x, int y) {
	        return null;
	    }
	}
	public BasicTower newInstance(ValidatorType validatorType, int x, int y) {
	    return validatorType.create(x, y);
	}
	
	@Override
	public void handle(DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;
		System.out.println(db.getString());
		if (db.hasString()) {
			
			//BasicTower newTower = newInstance(BASIC, 2, 2);
			Image image = new Image(getClass().getResourceAsStream("/basicTower.png"), 40, 40, false, false);
			((Label) event.getGestureTarget()).setGraphic(new ImageView(image));
			success = true;
		}
		event.setDropCompleted(success);
		event.consume();

	}
}
