/**
 *Designed and created by Pulkit Ghai
*
*@author Pulkit Ghai
*Dated: 10/07/2019
*A university project for subject Advance Programming Practices
*
*/
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import application.Main;
import database.Database;
import exceptionHandler.HandlerException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller class for the game.This class maps user gestures to actions and
 * response with validated moves.
 * 
 * @author Pulkit Ghai
 * @param <E>
 *
 */
public class BattleController<E> {
	ShipsPlacer placer = new ShipsPlacer();
	String ShipName = null;
	String ShipDirection = null;
	int shipLength = 0;
	int fixShiplength = 0;
	Database db = new Database();
	boolean active = false;
	Player1 p1 = null;
	Player2 p2 = null;
	HashMap<String, Integer> RotateRecord = new HashMap<String, Integer>();
	Timeline clock = new Timeline();
	static String Opponent = "";
	static int mins = 0, secs = 0, millis = 0;

	static int playerScore = 0;
	public ArrayList<String> p1Hit = new ArrayList<String>();
	public ArrayList<String> p1Miss = new ArrayList<String>();

	public static Player2 obj1;
	public static Player1 obj;
	public static Database dbObj;
	Map<String, Integer> timerScore = new HashMap();
	@FXML
	private AnchorPane firstScreen;
	@FXML
	private Button loadButton;
	@FXML
	private AnchorPane GameScreen;

	@FXML
	private SplitPane MainGamePane;

	@FXML
	private SplitPane containerPane;

	@FXML
	private ImageView battleShipImg;

	@FXML
	private ImageView carrierImg;

	@FXML
	private ImageView destroyerImg;

	@FXML
	private ImageView submarineImg;

	@FXML
	private ImageView cruiserImg;

	@FXML
	private Label MyShipLabel;

	@FXML
	private GridPane oceanGridWrappper1;

	@FXML
	private Text displayScreenPlayer1;

	@FXML
	private Label EnemyCarrier;

	@FXML
	private Label EnemyBattleShip;

	@FXML
	private Label EnemyCruiser;

	@FXML
	private Label EnemyDestroyer;

	@FXML
	private Label EnemySubMarine;

	@FXML
	private GridPane oceanGridWrappper2;

	@FXML
	private Label MyShipLabel1;

	@FXML
	private Text displayScreen;

	@FXML
	private Button PlayerVsPlayer;

	@FXML
	private Button PlayerVsAI;

	@FXML
	private Label score;

	@FXML
	private Label timer;

	@FXML
	private AnchorPane NetworkingWaitPane;

	@FXML
	private Label waitingLabel;

	@FXML
	private Button continueButton;

	@FXML
	private Button RequestOpponent;

	@FXML
	private AnchorPane GameModePane;

	@FXML
	private Button handleNormalMode;

	@FXML
	private SplitPane ResultsPane;

	@FXML
	private SplitPane MainGamePane1;

	@FXML
	private Label ResultsLabel;

	@FXML
	private Label finalScore;

	@FXML
	private AnchorPane SaveAndLoadPane;

	@FXML
	private Button saveGame;

	Node shipDraggingNode = null;

	// ---------------------Modes of the game---------------------//
	@FXML
	void handlerSalvaMode(MouseEvent event) {

		GameModePane.setVisible(false);
		Database.Mode = "SALVO";
		if (Opponent.equalsIgnoreCase("Player")) {
			this.NetworkingWaitPane.setVisible(true);
		} else {
			GameScreen.setVisible(true);
		}
		initClock();
	}

	@FXML
	void handleNormalMode(MouseEvent event) {
		GameScreen.setVisible(true);
		GameModePane.setVisible(false);
		Database.Mode = "REGULAR";
		if (Opponent.equalsIgnoreCase("Player")) {
			GameScreen.setVisible(false);
			this.NetworkingWaitPane.setVisible(true);
		} else {
			GameScreen.setVisible(true);
		}
		initClock();
	}

	// --------------------Game mode ends ---------------------//
	/// ---------------------Timer --------------------
	private void reset() {
		mins = 0;
		secs = 0;
		millis = 0;
		timer.setText("00:00:000");
	}

	private void calculateScore() {
		// TODO Auto-generated method stub

		if (secs <= 2 && mins == 0) {
			playerScore = playerScore + 10;
			score.setText("SCORE: " + Integer.toString(playerScore));
		} else if (secs <= 4 && mins == 0) {
			playerScore = playerScore + 8;
			score.setText("SCORE: " + Integer.toString(playerScore));
		} else if (secs <= 6 && mins == 0) {
			playerScore = playerScore + 6;
			score.setText("SCORE: " + Integer.toString(playerScore));
		} else if (secs <= 8 && mins == 0) {
			playerScore = playerScore + 4;
			score.setText("SCORE: " + Integer.toString(playerScore));
		} else if (secs <= 10 && mins == 0) {
			playerScore = playerScore + 2;
			score.setText("SCORE: " + Integer.toString(playerScore));
		} else {
			playerScore = playerScore + 1;
			score.setText("SCORE: " + Integer.toString(playerScore));
		}

	}

	private void initClock() {

		Timeline clock = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
			change(timer);
		}), new KeyFrame(javafx.util.Duration.millis(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();

	}

	private void change(Label timer) {
		// TODO Auto-generated method stub
		if (millis == 1000) {
			secs++;
			millis = 0;
		}
		if (secs == 60) {
			mins++;
			secs = 0;
		}
		timer.setText((((mins / 10) == 0) ? "0" : "") + mins + ":" + (((secs / 10) == 0) ? "0" : "") + secs + ":"
				+ (((millis / 10) == 0) ? "00" : (((millis / 100) == 0) ? "0" : "")) + millis++);

	}

	// -------------------------------timer ends---------------------
	@FXML
	void onRotate(MouseEvent event) {
		int RotateDegree = RotateRecord.get(((Node) event.getSource()).getId().toString());
		String shipDirection = "+x"; // default direction of ships
		((Node) event.getSource()).setRotate(((Node) event.getSource()).getRotate() + 90);
		RotateDegree = RotateDegree + 90;
		if (RotateDegree == 360 || RotateDegree == 0) {
			RotateDegree = 0;
			shipDirection = "+x";
		} else if (RotateDegree == 90) {
			shipDirection = "+y";
		} else if (RotateDegree == 180) {
			shipDirection = "-x";
		} else if (RotateDegree == 270) {
			shipDirection = "-y";
		}
		ShipDirection = shipDirection;
		System.out.println(shipDirection);
		RotateRecord.put(((Node) event.getSource()).getId().toString(), RotateDegree);
		Database.shipDirection.put(((Node) event.getSource()).getId().toString(), ShipDirection);

	}

	@FXML
	void handlerOnDragDetected(MouseEvent event) {
		String shipDragging = ((Node) event.getSource()).getId().toString();
		shipDraggingNode = (Node) event.getSource();
		ShipName = shipDragging;
		System.out.println(shipDragging);
		oceanGridWrappper1.setDisable(false);
		Dragboard db = ((Node) event.getSource()).startDragAndDrop(TransferMode.ANY);
		ClipboardContent cb = new ClipboardContent();
		cb.putImage(((ImageView) event.getSource()).getImage());
		db.setContent(cb);
		event.consume();

	}

	@FXML
	void handlerOnDragOver(DragEvent event) {
		event.acceptTransferModes(TransferMode.ANY);
	}

	@FXML
	void handlerOnDragDropped(DragEvent event) {
		String[] coordinates = ((Labeled) event.getSource()).getText().split("");
		if (ShipDirection == null) {
			ShipDirection = "+x";// default direction of all ships
		}
//		db.validateDrag(coordinates, ShipDirection, ShipName);
		if (db.validateDrag(coordinates, ShipDirection, ShipName)) {
			Database.shipDirection.put(ShipName, ShipDirection);
			placer.ShipsPlacer(ShipName, coordinates, ShipDirection, oceanGridWrappper1, p1);
			shipDraggingNode.setDisable(true);
			ShipDirection = "+x";
		}

	}

	@FXML
	void onExit(ActionEvent event) {
		this.SaveAndLoadPane.setVisible(true);

	}

	@FXML
	void onContinue(ActionEvent event) {

	}

	@FXML
	void onPlay(ActionEvent event) {
		String musicFile = "C:/Users/admin/eclipse-workspace/BattleShip/mp3/03_Battle in the Jungle.mp3"; // For example

		Media sound = new Media(new File(musicFile).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				mediaPlayer.seek(Duration.ZERO);
			}
		});
		mediaPlayer.setVolume(0.5);
		mediaPlayer.play();

//		mediaPlayer.play();

		this.firstScreen.setVisible(false);
		GameScreen.setCacheShape(false);
		this.GameScreen.setVisible(true);

		RotateRecord.put("destroyerImg", 0);
		RotateRecord.put("submarineImg", 0);
		RotateRecord.put("battleShipImg", 0);
		RotateRecord.put("carrierImg", 0);
		RotateRecord.put("cruiserImg", 0);
	}

	/*
	 * @FXML void onSelect(ActionEvent event) {
	 * 
	 * if (shipLength != 0) { if (db.addCoordinates(ShipName, fixShiplength,
	 * ((Labeled) event.getSource()).getText(), 9, 9) == true) { ((Node)
	 * event.getSource()).setStyle("fx-background-color: #536878"); shipLength--;
	 * ((Node) event.getSource()).setDisable(true); }
	 * 
	 * String[] xy = ((Labeled) event.getSource()).getText().split(""); int x =
	 * Integer.parseInt(xy[0]); int y = Integer.parseInt(xy[1]);
	 * 
	 * } else {
	 * 
	 * active = false;
	 * 
	 * this.oceanGridWrappper1.setDisable(true); }
	 * 
	 * }
	 */
	/*
	 * @FXML void onSelectPosition(ActionEvent event) {
	 * 
	 * }
	 */

	@FXML
	void startGame(ActionEvent event) {
		this.MyShipLabel.setText("Select ships position");

	}

	@FXML
	void onGameStart(ActionEvent event) {
		Main.relaunch.close();
		System.out.println("Restarting app!");
		playerScore = 0;
		mins = 0;
		secs = 0;
		millis = 0;
		db.clearDatabse();
		Platform.runLater(() -> new Main().start(new Stage()));
	}

	public void gridRepo()

	{
		ObservableList<Node> list = this.oceanGridWrappper1.getChildren();

		int counter = 0;
		for (Node node : list) {

			if (counter < list.size() - 1 && node.toString().split("'").length >= 1
					&& !node.getTypeSelector().equalsIgnoreCase("ImageView")) {
				db.addButtonToMap(node.toString().split("'")[1], node);
				counter++;
			}

		}

	}

	@FXML
	void gamePlayerVsPlayer(ActionEvent event) throws IOException {
		Opponent = "Player";
		if (db.coordinates.size() == 5) {
			this.PlayerVsPlayer.setDisable(true);
			this.PlayerVsAI.setDisable(true);
//			if (true) {
			gridRepo();

			this.firstScreen.setVisible(false);
			this.GameScreen.setVisible(false);
			this.GameModePane.setVisible(true);

		} else {

			// ends here..// } else {
			this.displayScreen.setText("Please select all ships position first.");
		}
	}

	@FXML
	void playWithAI(ActionEvent event) throws IOException {

		Opponent = "Computer";
		if (db.coordinates.size() == 5) {
			this.PlayerVsPlayer.setDisable(true);
this.loadButton.setDisable(true);
			GameScreen.setVisible(false);
			GameModePane.setVisible(true);
			gridRepo();
			p1 = new Player1();
			p2 = new Player2();
			p1.set_player2_object(p2);
			p2.set_player1_object(p1); //

			this.EnemyBattleShip.setVisible(true);
			this.EnemyCarrier.setVisible(true);
			this.EnemyCruiser.setVisible(true);
			this.EnemySubMarine.setVisible(true);
			this.EnemyDestroyer.setVisible(true);
			this.oceanGridWrappper2.setDisable(false);

			((Node) event.getSource()).setDisable(true);
		} else {
			this.displayScreen.setText("Please select all ships position first.");
		}

	}

	@FXML
	void onRequestOpponent(ActionEvent event) throws IOException {
		p1 = Player1.getInstance();
		String response = p1.registerSelf(p1.myHost, p1.port, Database.Mode);
		System.out.println(response);
		if (response.equalsIgnoreCase("challenge")) {
			Database.dice = true;
			this.PlayerVsPlayer.setDisable(true);
			this.PlayerVsAI.setDisable(true);
			this.GameModePane.setVisible(false);
			this.NetworkingWaitPane.setVisible(false);
			this.GameScreen.setVisible(true);
			// Display enemy labels as Sailing this.EnemyBattleShip.setVisible(true);
			this.EnemyCarrier.setVisible(true);
			this.EnemyCruiser.setVisible(true);
			this.EnemySubMarine.setVisible(true);
			this.EnemyDestroyer.setVisible(true);
			this.EnemyBattleShip.setVisible(true);
			this.oceanGridWrappper2.setDisable(false);

		} else if (response.equalsIgnoreCase("wait")) {

			Database.dice = false;
			this.oceanGridWrappper2.setDisable(false);
			this.PlayerVsPlayer.setDisable(true);
			this.PlayerVsAI.setDisable(true);
			this.GameModePane.setVisible(false);
			this.NetworkingWaitPane.setVisible(false);
			this.GameScreen.setVisible(true);
			// Display enemy labels as Sailing this.EnemyBattleShip.setVisible(true);
			this.EnemyCarrier.setVisible(true);
			this.EnemyCruiser.setVisible(true);
			this.EnemySubMarine.setVisible(true);
			this.EnemyDestroyer.setVisible(true);
			this.EnemyBattleShip.setVisible(true);
		} else if (response.equalsIgnoreCase("mismatch")) {
			this.displayScreenPlayer1.setText("Mismatched Mode");
		}

	}

	private void onPlayRegularMode(String[] xy, Node EventNode, String Opponent)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String response[];
		clock.pause();
		System.out.println(Database.dice);
		if (Database.dice) {
			File file = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/explosion.png");
			ImageView iv = new ImageView(file.toURI().toString());
			ImageView iv2 = new ImageView(file.toURI().toString());
			int x = Integer.parseInt(xy[0]);
			int y = Integer.parseInt(xy[1]);

			response = p1.attack(x, y, Opponent).split("#");
			System.out.println("response" + p1.attack(x, y, Opponent));
			System.out.println("p1: " + response[0] + " " + response[1]);
			if (response[0].equals("hit")) {

				String musicFile1 = "C:/Users/admin/eclipse-workspace/BattleShip/mp3/Explosion.mp3"; // For example

				Media soundExplosion = new Media(new File(musicFile1).toURI().toString());
				MediaPlayer mediaPlayer1 = new MediaPlayer(soundExplosion);

				mediaPlayer1.play();
				this.displayScreenPlayer1.setFill(Color.web("#ffffff", 0.8));
				this.displayScreenPlayer1.setText("Yeah!! We hit..");
				oceanGridWrappper2.add(iv, y, x, 1, 1);
				EventNode.setStyle("-fx-background-color:transparent;");
				EventNode.setDisable(true);
				calculateScore();
			} else if (response[0].equals("miss")) {
				this.displayScreenPlayer1.setFill(Color.web("#ff0000", 0.8));
				this.displayScreenPlayer1.setText("Oops!! We miss..");
				EventNode.setStyle("-fx-background-color:transparent;");
				EventNode.setDisable(true);
			} else {

				switch (response[0]) {
				case "destroyer":
					this.displayScreenPlayer1.setFill(Color.web("#00cc2c", 0.8));
					this.displayScreenPlayer1.setText("Yeah!! We hit..");

					this.EnemyDestroyer.setTextFill(Color.web("#ff0000", 0.8));
					this.EnemyDestroyer.setText("Sunk");
					this.oceanGridWrappper2.add(iv, y, x, 1, 1);
					calculateScore();
					p2.sunked.put("destroyer", -1);
//					destroyerImg.setImage(img);
					break;
				case "carrier":
					this.displayScreenPlayer1.setFill(Color.web("#00cc2c", 0.8));
					this.displayScreenPlayer1.setText("Yeah!! We hit..");

					this.EnemyCarrier.setTextFill(Color.web("#ff0000", 0.8));
					this.EnemyCarrier.setText("Sunk");
					this.oceanGridWrappper2.add(iv, y, x, 1, 1);
					p2.sunked.put("carrier", -1);
					calculateScore();
//					carrierImg.setImage(img);
					break;
				case "submarine":
					this.displayScreenPlayer1.setFill(Color.web("#00cc2c", 0.8));
					this.displayScreenPlayer1.setText("Yeah!! We hit..");
					EventNode.setDisable(true);
					EventNode.setStyle("-fx-background-color:#00cc2c;");

					this.EnemySubMarine.setTextFill(Color.web("#ff0000", 0.8));
					this.EnemySubMarine.setText("Sunk");
					this.oceanGridWrappper2.add(iv, y, x, 1, 1);
					p2.sunked.put("submarine", -1);
					calculateScore();
					break;
				case "cruiser":
					this.displayScreenPlayer1.setFill(Color.web("#00cc2c", 0.8));
					this.displayScreenPlayer1.setText("Yeah!! We hit..");

					this.EnemyCruiser.setTextFill(Color.web("#ff0000", 0.8));
					this.EnemyCruiser.setText("Sunk");
					this.oceanGridWrappper2.add(iv, y, x, 1, 1);
					p2.sunked.put("cruiser", -1);
					calculateScore();
					break;
				case "battleship":
					this.displayScreenPlayer1.setFill(Color.web("#00cc2c", 0.8));
					this.displayScreenPlayer1.setText("Yeah!! We hit..");

					this.EnemyBattleShip.setTextFill(Color.web("#ff0000", 0.8));
					this.EnemyBattleShip.setText("Sunk");
					this.oceanGridWrappper2.add(iv, y, x, 1, 1);
					p2.sunked.put("battleship", -1);
					calculateScore();
					break;
				}

				EventNode.setDisable(true);

			}
			Database.dice = false;
			if (!response[1].equalsIgnoreCase("win") && !Opponent.equalsIgnoreCase("Player")) {
				Database.dice = false;
				String[] enemy = p2.attack_AI().split("#");
				if (enemy[0].equals("hit")) {
					// setThe button color as hit

					String musicFile1 = "C:/Users/admin/eclipse-workspace/BattleShip/mp3/Explosion.mp3"; // For example

					Media soundExplosion = new Media(new File(musicFile1).toURI().toString());
					MediaPlayer mediaPlayer1 = new MediaPlayer(soundExplosion);

					mediaPlayer1.play();
					Node node = db.getNode(enemy[2] + enemy[3]);
					oceanGridWrappper1.add(iv2, Integer.parseInt(enemy[3]), Integer.parseInt(enemy[2]), 1, 1);
					this.displayScreen.setFill(Color.web("#ffffff", 0.8));
					this.displayScreen.setText("Enemy hits..");
					reset();
				} else if (enemy[0].equals("miss")) {

					Node node = db.getNode(enemy[2] + enemy[3]);
					node.setStyle("-fx-background-color:#000000;");
					this.displayScreen.setFill(Color.web("#ff0000", 0.8));
					this.displayScreen.setText("Enemy misses..");
					reset();
				} else {
					File sinkfile = new File(
							"C:/Users/admin/eclipse-workspace/BattleShip/image/InsignificantBaggyKingfisher-max-1mb.gif");
					Image img = new Image(sinkfile.toURI().toString());
					switch (enemy[0]) {
					case "destroyer":
						this.displayScreen.setFill(Color.web("#00cc2c", 0.8));
						this.displayScreen.setText("Enemy hits....");
						Node node = db.getNode(enemy[2] + enemy[3]);

						oceanGridWrappper1.add(iv2, Integer.parseInt(enemy[3]), Integer.parseInt(enemy[2]), 1, 1);
						RotateRecord.put("destroyerImg", 0);
						p1.sunked.put("destroyerImg", -1);
//						((Node) event.getSource()).setRotate(((Node) event.getSource()).getRotate() + 90);

						destroyerImg.setImage(img);
						destroyerImg.setRotate(360 - destroyerImg.getRotate());
						attackChancesPlayer1--;
						reset();
						break;
					case "carrier":
						this.displayScreen.setFill(Color.web("#00cc2c", 0.8));
						this.displayScreen.setText("Enemy hits..");
						node = db.getNode(enemy[2] + enemy[3]);

						oceanGridWrappper1.add(iv2, Integer.parseInt(enemy[3]), Integer.parseInt(enemy[2]), 1, 1);

						attackChancesPlayer1--;

						RotateRecord.put("carrierImg", 0);
						p1.sunked.put("carrierImg", -1);
						carrierImg.setImage(img);
						reset();
						break;
					case "submarine":
						this.displayScreen.setFill(Color.web("#00cc2c", 0.8));
						this.displayScreen.setText("Enemy hits..");
						node = db.getNode(enemy[2] + enemy[3]);

						oceanGridWrappper1.add(iv2, Integer.parseInt(enemy[3]), Integer.parseInt(enemy[2]), 1, 1);

						attackChancesPlayer1--;
						RotateRecord.put("submarineImg", 0);
						p1.sunked.put("submarineImg", -1);
						submarineImg.setImage(img);
						submarineImg.setRotate(360 - submarineImg.getRotate());
						reset();
						break;
					case "cruiser":
						this.displayScreen.setFill(Color.web("#00cc2c", 0.8));
						this.displayScreen.setText("Enemy hits..");

						node = db.getNode(enemy[2] + enemy[3]);// node.setStyle("-fx-background-color:#00cc2c;");
						oceanGridWrappper1.add(iv2, Integer.parseInt(enemy[3]), Integer.parseInt(enemy[2]), 1, 1);

						attackChancesPlayer1--;

						RotateRecord.put("cruiserImg", 0);
						p1.sunked.put("cruiserImg", -1);
						cruiserImg.setImage(img);
						cruiserImg.setRotate(360 - cruiserImg.getRotate());
						reset();
						break;
					case "battleship":
						this.displayScreen.setFill(Color.web("#00cc2c", 0.8));
						this.displayScreen.setText("Enemy hits..");

						node = db.getNode(enemy[2] + enemy[3]);
						oceanGridWrappper1.add(iv2, Integer.parseInt(enemy[3]), Integer.parseInt(enemy[2]), 1, 1);

						RotateRecord.put("battleShipImg", 0);
						p1.sunked.put("battleShipImg", -1);
						attackChancesPlayer1--;
						battleShipImg.setImage(img);
						reset();
						break;
					}
					if (enemy[1].equalsIgnoreCase("win")) {
						WinnerDeclared = true;
						/*
						 * this.displayScreenPlayer1.setText("Computer wins");
						 * this.displayScreen.setText("I won");
						 */
						this.GameScreen.setDisable(true);
						this.ResultsPane.setVisible(true);
						this.finalScore.setText("SCORE: " + Integer.toString(playerScore));
						this.ResultsLabel.setText("Computer Wins");
						this.oceanGridWrappper2.setDisable(true);
						Database.dice = false;
						clock.stop();
						timer.setVisible(false);
					}

					EventNode.setDisable(true);

				}
			} else if (response[1].equalsIgnoreCase("win")) {
				WinnerDeclared = true;
				this.GameScreen.setDisable(true);
				this.ResultsPane.setVisible(true);
				this.finalScore.setText("SCORE: " + Integer.toString(playerScore));
				this.ResultsLabel.setText("You Win");
//				this.displayScreenPlayer1.setText("Player1 wins");
				this.PlayerVsAI.setDisable(true);
				this.oceanGridWrappper2.setDisable(true);
				clock.stop();
				timer.setVisible(false);
				this.displayScreen.setText("");
			}

		}
	}

	HashMap<String[], Node> salvoCoordinates = new HashMap<>();

	boolean WinnerDeclared = false; // Variable used in Salvo Mode

	public void onAttackAI(String[] xy, Node EventNode) throws IOException, InterruptedException {
		File file = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/explosion.png");
		ImageView iv = new ImageView(file.toURI().toString());
		ImageView iv2 = new ImageView(file.toURI().toString());
		int x = Integer.parseInt(xy[0]);
		int y = Integer.parseInt(xy[1]);

		String[] response = p1.attack(x, y, Opponent).split("#");

		System.out.println("p1: " + Arrays.toString(response).substring(1, Arrays.toString(response).length() - 1));
		if (response[0].equals("hit")) {

			String musicFile1 = "C:/Users/admin/eclipse-workspace/BattleShip/mp3/Explosion.mp3"; // For example

			Media soundExplosion = new Media(new File(musicFile1).toURI().toString());
			MediaPlayer mediaPlayer1 = new MediaPlayer(soundExplosion);

			mediaPlayer1.play();
			this.displayScreenPlayer1.setFill(Color.web("#ffffff", 0.8));
			this.displayScreenPlayer1.setText("Yeah!! We hit..");
			oceanGridWrappper2.add(iv, y, x, 1, 1);
			EventNode.setDisable(true);
			calculateScore();
		} else if (response[0].equals("miss")) {
			this.displayScreenPlayer1.setFill(Color.web("#ff0000", 0.8));
			this.displayScreenPlayer1.setText("Oops!! We miss..");
//			EventNode.setStyle("-fx-background-color:#000000;");
			EventNode.setDisable(true);
		} else {

			switch (response[0]) {
			case "destroyer":
				this.displayScreenPlayer1.setFill(Color.web("#00cc2c", 0.8));
				this.displayScreenPlayer1.setText("Yeah!! We hit..");

				this.EnemyDestroyer.setTextFill(Color.web("#ff0000", 0.8));
				this.EnemyDestroyer.setText("Sunk");
				this.oceanGridWrappper2.add(iv, y, x, 1, 1);
				p2.sunked.put("destroyer", -1);
				calculateScore();
				attackChancesAI--;

				break;
			case "carrier":
				this.displayScreenPlayer1.setFill(Color.web("#00cc2c", 0.8));
				this.displayScreenPlayer1.setText("Yeah!! We hit..");

				this.EnemyCarrier.setTextFill(Color.web("#ff0000", 0.8));
				this.EnemyCarrier.setText("Sunk");
				this.oceanGridWrappper2.add(iv, y, x, 1, 1);
				p2.sunked.put("carrier", -1);
				calculateScore();
//				carrierImg.setImage(img);
				attackChancesAI--;

				break;
			case "submarine":
				this.displayScreenPlayer1.setFill(Color.web("#00cc2c", 0.8));
				this.displayScreenPlayer1.setText("Yeah!! We hit..");

				this.EnemySubMarine.setTextFill(Color.web("#ff0000", 0.8));
				this.EnemySubMarine.setText("Sunk");
				this.oceanGridWrappper2.add(iv, y, x, 1, 1);
				p2.sunked.put("submarine", -1);
				attackChancesAI--;

				calculateScore();
				break;
			case "cruiser":
				this.displayScreenPlayer1.setFill(Color.web("#00cc2c", 0.8));
				this.displayScreenPlayer1.setText("Yeah!! We hit..");

				this.EnemyCruiser.setTextFill(Color.web("#ff0000", 0.8));
				this.EnemyCruiser.setText("Sunk");
				this.oceanGridWrappper2.add(iv, y, x, 1, 1);
				p2.sunked.put("cruiser", -1);
				attackChancesAI--;

				calculateScore();
				break;
			case "battleship":
				this.displayScreenPlayer1.setFill(Color.web("#00cc2c", 0.8));
				this.displayScreenPlayer1.setText("Yeah!! We hit..");

				this.EnemyBattleShip.setTextFill(Color.web("#ff0000", 0.8));
				this.EnemyBattleShip.setText("Sunk");
				this.oceanGridWrappper2.add(iv, y, x, 1, 1);
				p2.sunked.put("battleship", -1);
				calculateScore();
				attackChancesAI--;

				break;
			}
			EventNode.setDisable(true);

		}
		if (response[1].equalsIgnoreCase("win")) {
			WinnerDeclared = true;
			this.GameScreen.setDisable(true);
			this.ResultsPane.setVisible(true);
			this.finalScore.setText("SCORE: " + Integer.toString(playerScore));
			this.ResultsLabel.setText("You Win");
//			this.displayScreenPlayer1.setText("Player1 wins");
			this.PlayerVsAI.setDisable(true);
			this.oceanGridWrappper2.setDisable(true);
			clock.stop();
			timer.setVisible(false);
			this.displayScreen.setText("");
		} else {
			Database.dice = false;
		}
	}

	public void onAttackPlayer1(Node EventNode) {
		File file = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/explosion.png");
		ImageView iv2 = new ImageView(file.toURI().toString());
		Database.dice = false;
		String[] enemy = p2.attack_AI().split("#");

		if (enemy[0].equals("hit")) {

			String musicFile1 = "C:/Users/admin/eclipse-workspace/BattleShip/mp3/Explosion.mp3"; // For example

			Media soundExplosion = new Media(new File(musicFile1).toURI().toString());
			MediaPlayer mediaPlayer1 = new MediaPlayer(soundExplosion);

			mediaPlayer1.play();
			// setThe button color as hit
			Node node = db.getNode(enemy[2] + enemy[3]);
			oceanGridWrappper1.add(iv2, Integer.parseInt(enemy[3]), Integer.parseInt(enemy[2]), 1, 1);

			this.displayScreen.setFill(Color.web("#ffffff", 0.8));
			this.displayScreen.setText("Enemy hits..");
			reset();
		} else if (enemy[0].equals("miss")) {
			Node node = db.getNode(enemy[2] + enemy[3]);
			node.setStyle("-fx-background-color:#000000;");
			this.displayScreen.setFill(Color.web("#ff0000", 0.8));
			this.displayScreen.setText("Enemy misses..");
			reset();
		} else {
			File sinkfile = new File(
					"C:/Users/admin/eclipse-workspace/BattleShip/image/InsignificantBaggyKingfisher-max-1mb.gif");
			Image img = new Image(sinkfile.toURI().toString());
			System.out.println(enemy[0]);

			switch (enemy[0]) {
			case "destroyer":

				this.displayScreen.setFill(Color.web("#00cc2c", 0.8));
				this.displayScreen.setText("Enemy hits....");
				Node node = db.getNode(enemy[2] + enemy[3]);

				oceanGridWrappper1.add(iv2, Integer.parseInt(enemy[3]), Integer.parseInt(enemy[2]), 1, 1);

				destroyerImg.setImage(img);
				attackChancesPlayer1--;
				p1.sunked.put("destroyer", -1);
				reset();
				break;
			case "carrier":
				this.displayScreen.setFill(Color.web("#00cc2c", 0.8));
				this.displayScreen.setText("Enemy hits..");
				node = db.getNode(enemy[2] + enemy[3]);

				oceanGridWrappper1.add(iv2, Integer.parseInt(enemy[3]), Integer.parseInt(enemy[2]), 1, 1);

				RotateRecord.put("carrierImg", 0);
				p1.sunked.put("carrier", -1);
				carrierImg.setImage(img);
				attackChancesPlayer1--;
				reset();
				break;
			case "submarine":
				this.displayScreen.setFill(Color.web("#00cc2c", 0.8));
				this.displayScreen.setText("Enemy hits..");
				node = db.getNode(enemy[2] + enemy[3]);
//				node.setStyle("-fx-background-color:#00cc2c;");
				oceanGridWrappper1.add(iv2, Integer.parseInt(enemy[3]), Integer.parseInt(enemy[2]), 1, 1);
				RotateRecord.put("submarineImg", 0);

				submarineImg.setImage(img);
				p1.sunked.put("submarine", -1);
				attackChancesPlayer1--;
				reset();
				break;
			case "cruiser":
				this.displayScreen.setFill(Color.web("#00cc2c", 0.8));
				this.displayScreen.setText("Enemy hits..");

				node = db.getNode(enemy[2] + enemy[3]);
//				node.setStyle("-fx-background-color:#00cc2c;");
				oceanGridWrappper1.add(iv2, Integer.parseInt(enemy[3]), Integer.parseInt(enemy[2]), 1, 1);
				// this.cruiserSailing.setTextFill(Color.web("#ff0000", 0.8));
				// this.cruiserSailing.setText("Sunk");
				attackChancesPlayer1--;
				p1.sunked.put("cruiser", -1);
				RotateRecord.put("cruiserImg", 0);
				cruiserImg.setImage(img);
				reset();
				break;
			case "battleship":
				this.displayScreen.setFill(Color.web("#00cc2c", 0.8));
				this.displayScreen.setText("Enemy hits..");

				node = db.getNode(enemy[2] + enemy[3]);
				oceanGridWrappper1.add(iv2, Integer.parseInt(enemy[3]), Integer.parseInt(enemy[2]), 1, 1);
				RotateRecord.put("battleShipImg", 0);
				p1.sunked.put("battleship", -1);
				attackChancesPlayer1--;
				battleShipImg.setImage(img);
				reset();
				break;
			}

			if (enemy[1].equalsIgnoreCase("win")) {
				WinnerDeclared = true;

				this.GameScreen.setDisable(true);
				this.ResultsPane.setVisible(true);
				this.finalScore.setText("SCORE: " + Integer.toString(playerScore));
				this.ResultsLabel.setText("Computer Wins");
				this.oceanGridWrappper2.setDisable(true);
				Database.dice = false;
				clock.stop();
				timer.setVisible(false);
			}
			EventNode.setDisable(true);

		}

	}

	int SalvoCounterAI = 1;
	int SalvoCounterPlayer1 = 1;
	int attackChancesPlayer1 = 5;
	int attackChancesAI = 5;

	@FXML
	void onAttack(ActionEvent event) throws IOException, InterruptedException {

		String[] xy = ((Labeled) event.getSource()).getText().split("");
		Node node = (Node) event.getSource();
		if (Database.Mode.equalsIgnoreCase("REGULAR")) {
			// reset();
			onPlayRegularMode(xy, node, Opponent);
		} else if (Database.Mode.equalsIgnoreCase("SALVO") && Opponent.equalsIgnoreCase("Computer")) {
			// reset();
			((Node) event.getSource()).setDisable(true);
			((Node) event.getSource()).setStyle("-fx-background-color:transparent;");
			if (SalvoCounterAI < attackChancesPlayer1) {

				salvoCoordinates.put(xy, node);
				SalvoCounterAI++;
			} else if (SalvoCounterAI == attackChancesPlayer1) {
				salvoCoordinates.put(xy, node);
				Iterator itr = salvoCoordinates.keySet().iterator();
				while (itr.hasNext() && WinnerDeclared == false) {
					String[] key = (String[]) itr.next();

					onAttackAI(key, node);

				}
				Database.dice = false;
				SalvoCounterAI = 1;
				salvoCoordinates.clear();
				while (SalvoCounterPlayer1 < attackChancesAI || SalvoCounterPlayer1 == attackChancesAI) {
					onAttackPlayer1(node);
					SalvoCounterPlayer1++;
				}

				SalvoCounterPlayer1 = 1;
				System.out.println("Attack chances of Player 1" + attackChancesPlayer1);
				System.out.println("Attack chances of AI 1" + attackChancesAI);

			}
		} else if (Database.Mode.equalsIgnoreCase("SALVO") && Opponent.equalsIgnoreCase("Player")
				&& Database.dice == true) {
			((Node) event.getSource()).setDisable(true);
			((Node) event.getSource()).setStyle("-fx-background-color:transparent;");

			if (SalvoCounterAI < attackChancesAI) {
				System.out.println(SalvoCounterAI);
				salvoCoordinates.put(xy, node);
				SalvoCounterAI++;
			} else if (SalvoCounterAI == attackChancesAI) {
				salvoCoordinates.put(xy, node);
				Iterator itr = salvoCoordinates.keySet().iterator();
				while (itr.hasNext() && WinnerDeclared == false) {
					String[] key = (String[]) itr.next();

					onAttackAI(key, node);
					Database.dice = true;
				}
				if (p1.socket.isClosed()) {
					this.GameScreen.setDisable(true);
					this.ResultsPane.setVisible(true);
					this.finalScore.setText("SCORE: " + Integer.toString(playerScore));
					this.ResultsLabel.setText("Opponent Wins");
					this.oceanGridWrappper2.setDisable(true);
					Database.dice = false;
					clock.stop();
					timer.setVisible(false);
				}
				Database.dice = false;
				SalvoCounterAI = 1;
				salvoCoordinates.clear();
				System.out.println("Attack chances of Player 1 " + attackChancesPlayer1);
				System.out.println("Attack chances of AI 1 " + attackChancesAI);
			}

		}
	}

	@FXML
	void restartGame(ActionEvent event) {
		Main.relaunch.close();
		System.out.println("Restarting app!");

		Platform.runLater(() -> new Main().start(new Stage()));
	}

	private void setTimer(int mins2, int secs2, int millis2) {
		mins = mins2;
		secs = secs2;
		millis = millis2;
	}

	public static void loadShipsData() {
		// TODO Auto-generated method stub
		Database.loadShipsData();
	}

	/*
	 * public void updateFrontEnd(int x, int y) { File file = new
	 * File("C:/Users/admin/eclipse-workspace/BattleShip/image/explosion.png");
	 * ImageView iv2 = new ImageView(file.toURI().toString());
	 * this.oceanGridWrappper1.add(iv2,x,y, 1, 1);
	 * 
	 * // TODO Auto-generated method stub
	 * 
	 * }
	 */

	@FXML
	void onSaveGame(ActionEvent event) {

		try {
			p1.c = Player1.loadShips;

			p1.dir = Database.shipDirection;
			p1.modeLoad = Database.Mode;
			p1.player = Opponent;
			// For Player 1

			System.out.println("p1.player=Opponent" + p1.player);

			System.out.println("siszeeeee" + p1.c.size());
			FileOutputStream f = new FileOutputStream(new File("C:/Users/admin/eclipse-workspace/BattleShip/p1.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(p1);
			o.close();
			f.close();

			// For Player 2
			FileOutputStream f1 = new FileOutputStream(new File("C:/Users/admin/eclipse-workspace/BattleShip/p2.txt"));
			ObjectOutputStream o1 = new ObjectOutputStream(f1);
			o1.writeObject(p2);
			o1.close();
			f1.close();

			// For Score and Timer data

			int saveScore = playerScore;

			int saveMillis = millis;
			int saveSecs = secs;
			int saveMins = mins;
			int salvoPlayerHit = attackChancesAI;
			int salvoAiHit = attackChancesPlayer1;

			Map<String, Integer> timerScore = new HashMap();
			timerScore.put("PlayerScore", saveScore);
			timerScore.put("timerMins", saveMins);
			timerScore.put("timerSecs", saveSecs);
			timerScore.put("timerMillis", saveMillis);

			timerScore.put("salvoPlayer", salvoPlayerHit);
			timerScore.put("salvoAi", salvoAiHit);

			FileOutputStream inscore = new FileOutputStream(
					new File("C:/Users/admin/eclipse-workspace/BattleShip/timerScore.txt"));
			ObjectOutputStream outscore = new ObjectOutputStream(inscore);
			outscore.writeObject(timerScore);
			inscore.close();
			outscore.close();

			// For Database
			FileOutputStream inData = new FileOutputStream(
					new File("C:/Users/admin/eclipse-workspace/BattleShip/data.txt"));
			ObjectOutputStream outData = new ObjectOutputStream(inData);
			outData.writeObject(db);
			inData.close();
			outData.close();

		} catch (Exception e) {
			HandlerException.Handler(e.toString());
			System.out.println("File not found");
		}
		Main.relaunch.close();

	}

	@FXML
	void Exit(ActionEvent event) {
		Main.relaunch.close();
	}

	@FXML
	void onLoad(ActionEvent event) {
		this.loadButton.setDisable(true);
		File file = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/explosion.png");

		try {
			// Loading Player 1

			FileInputStream fileIn = new FileInputStream("C:/Users/admin/eclipse-workspace/BattleShip/p1.txt");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			obj = (Player1) objectIn.readObject();

			fileIn.close();
			objectIn.close();

			// Loading Player 2
			FileInputStream fileIn1 = new FileInputStream("C:/Users/admin/eclipse-workspace/BattleShip/p2.txt");
			ObjectInputStream objectIn1 = new ObjectInputStream(fileIn1);
			obj1 = (Player2) objectIn1.readObject();
			fileIn1.close();
			objectIn1.close();

			System.out.println("load");

			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					System.out.print(obj1.player2[i][j] + " ");
				}

				System.out.println();
			}

			// Loading Database
			FileInputStream inData = new FileInputStream("C:/Users/admin/eclipse-workspace/BattleShip/timerScore.txt");
			ObjectInputStream outData = new ObjectInputStream(inData);
			timerScore = (Map<String, Integer>) outData.readObject();

			// System.out.println(timerScore);
			inData.close();
			outData.close();

		}

		catch (Exception e) {
			e.printStackTrace();
			HandlerException.Handler(e.toString());
			System.out.println("File not found");
		}
		loadPlayerShips(obj);
		loadAiShips(obj1);
		this.p1 = obj;
		this.p2 = obj1;
		// this.db = dbObj;
		p1.set_player2_object(p2);
		p2.set_player1_object(p1);
		this.db = p1.db;

		System.out.println("Coor" + Player1.coordinates);
		System.out.println("Miss Hits:" + p1.p1Miss);
		System.out.println("Miss Hits:" + p1.p1Hit);

		score.setText("SCORE:" + timerScore.get("PlayerScore").toString());

		this.playerScore = timerScore.get("PlayerScore");

		initClock();
		mins = timerScore.get("timerMins");
		secs = timerScore.get("timerSecs");
		millis = timerScore.get("timerMillis");
		setTimer(mins, secs, millis);
		// System.out.println(data.mins+" "+data.secs+" "+ data.millis);

		this.Opponent = obj.player;

		this.attackChancesAI = timerScore.get("salvoPlayer");
		this.attackChancesPlayer1 = timerScore.get("salvoAi");

	}

	private void loadAiShips(Player2 obj1) {
		// TODO Auto-generated method stub
		this.oceanGridWrappper2.setDisable(false);
		this.oceanGridWrappper1.setDisable(false);
		gridRepo();
		File file = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/explosion.png");
		File missFile = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/miss.PNG");

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				if (obj1.player2[i][j] == -1) {
					// miss

					ImageView iv2 = new ImageView(missFile.toURI().toString());
					iv2.setStyle("-fx-background-color:transparent;");
					iv2.setDisable(true);
					oceanGridWrappper2.add(iv2, j, i);

				} else if (obj1.player2[i][j] == 2) {
					// hit
					ImageView iv = new ImageView(file.toURI().toString());
					iv.setStyle("-fx-background-color:transparent;");
					iv.setDisable(true);
					oceanGridWrappper2.add(iv, j, i);

				}

				else if (obj1.player2[i][j] == 1) {
					// ship placed

				}

			}
		}
		
		
		
	
		
		Set set = (Set<E>) obj1.sunked.keySet();
		
		System.out.println("fdfsdfdsf"+obj1.sunked.keySet());
		Iterator itr = set.iterator();
		itr = set.iterator();
		
		while (itr.hasNext()) {
			File sinkfile = new File(
					"C:/Users/admin/eclipse-workspace/BattleShip/image/InsignificantBaggyKingfisher-max-1mb.gif");
			Image img = new Image(sinkfile.toURI().toString());
			String key = (String)itr.next();
			this.EnemyCarrier.setVisible(true);
			this.EnemyDestroyer.setVisible(true);
			this.EnemySubMarine.setVisible(true);
			this.EnemyCruiser.setVisible(true);
			this.EnemyBattleShip.setVisible(true);
			this.EnemyDestroyer.setText("Sailing");
			this.EnemyCarrier.setText("Sailing");
			this.EnemySubMarine.setText("Sailing");
			this.EnemyCruiser.setText("Sailing");
			this.EnemyBattleShip.setText("Sailing");
			if ((Integer)obj1.sunked.get(key) ==  -1) {
				switch (key) {

				case "destroyer":
					
					this.EnemyDestroyer.setTextFill(Color.web("#ff0000", 0.8));
					this.EnemyDestroyer.setText("Sunk");
					break;
				case "carrier":
					this.EnemyCarrier.setTextFill(Color.web("#ff0000", 0.8));
					this.EnemyCarrier.setText("Sunk");
					break;
				case "submarine":
					this.EnemySubMarine.setTextFill(Color.web("#ff0000", 0.8));
					this.EnemySubMarine.setText("Sunk");
					break;
				case "cruiser":
					this.EnemyCruiser.setTextFill(Color.web("#ff0000", 0.8));
					this.EnemyCruiser.setText("Sunk");
					break;
				case "battleship":
					this.EnemyBattleShip.setTextFill(Color.web("#ff0000", 0.8));
					this.EnemyBattleShip.setText("Sunk");
					break;

				}
			}

		}
	}

	private void loadPlayerShips(Player1 obj) {
		// TODO Auto-generated method stub

		File file = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/explosion.png");
		File missFile = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/miss.PNG");
		System.out.println("OBJ siszeeeee" + obj.c.size());
		System.out.println("DIR siszeeeee" + obj.dir.size());

		Database.Mode = obj.modeLoad;
		System.out.println("Mode" + obj.modeLoad);

		Set<E> set = (Set<E>) obj.c.keySet();

		Iterator itr = set.iterator();
		String[] list = null;
		while (itr.hasNext()) {

			String key = (String) itr.next();
			// String dir=(String) direction.get(key);
			System.out.println(key);
			System.out.println(obj.c);
			list = (String[]) ((String) ((ArrayList) obj.c.get(key)).get(0)).split("");
			System.out.println(obj.dir);
			System.out.println(obj.dir.get(key));
			placer.ShipsPlacer(key, list, obj.dir.get(key), oceanGridWrappper1, obj);

		}

		for (String item : obj.p1Miss) {
			String[] arr = item.split("");
			int x = Integer.parseInt(arr[0]);
			int y = Integer.parseInt(arr[1]);

			ImageView iv2 = new ImageView(missFile.toURI().toString());
			iv2.setStyle("-fx-background-color:transparent;");
			iv2.setDisable(true);
			oceanGridWrappper1.add(iv2, y, x);

		}

		for (String item : obj.p1Hit) {
			String[] arr = item.split("");
			int x = Integer.parseInt(arr[0]);
			int y = Integer.parseInt(arr[1]);

			ImageView iv2 = new ImageView(file.toURI().toString());
			iv2.setStyle("-fx-background-color:transparent;");
			iv2.setDisable(true);
			oceanGridWrappper1.add(iv2, y, x);

		}
		for (String item:obj.p1Distroy) {
			String [] arr= item.split("");
			int x= Integer.parseInt(arr[0]);
			int y= Integer.parseInt(arr[1]);

			ImageView iv2 = new ImageView(file.toURI().toString());
			iv2.setStyle("-fx-background-color:transparent;");
			iv2.setDisable(true);
			oceanGridWrappper1.add(iv2, y, x);

		}
		set = (Set<E>) obj.sunked.keySet();

		itr = set.iterator();
		
		while (itr.hasNext()) {
			File sinkfile = new File(
					"C:/Users/admin/eclipse-workspace/BattleShip/image/InsignificantBaggyKingfisher-max-1mb.gif");
			Image img = new Image(sinkfile.toURI().toString());
			String key = (String)itr.next();
			
			if ((Integer)obj.sunked.get(key) ==  -1) {
				switch (key) {

				case "cruiserImg":
					cruiserImg.setImage(img);
					break;
				case "destroyerImg":
					destroyerImg.setImage(img);
					break;
				case "submarineImg":
					submarineImg.setImage(img);
					break;
				case "battleShipImg":
					battleShipImg.setImage(img);
					break;
				case "carrierImg":
					carrierImg.setImage(img);
					break;

				}
			}

		}
	

	}

}
