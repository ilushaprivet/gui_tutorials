package application;

//Standard javafx imports.
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.stage.Stage;

//Components in this application: Labels, buttons and textfields.
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.animation.Animation;

//Layout...
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//Geometry
import javafx.geometry.Insets;
import javafx.geometry.Pos;

//Application quitting.
import javafx.application.Platform;

//An image is required to display an icon.
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
//Timing
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

//Java Utils
import java.util.ArrayList;
import java.util.Random;

//custom fonts
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

//file reading
import java.io.FileReader;
import java.io.BufferedReader;

public class WordGuesser extends Application {

	Label lblGuess, lblTime, lblHint;
	TextField txtfGuess;
	Image img;
	ImageView imView;
	Button btnStart;
	
	Timeline tLine;
	int timeLeft = 30;

	ArrayList<String> wordList;
	// the word to guess
	String wordToGuess = "";
	// game state
	Boolean gameOver;

	public WordGuesser() {
		// labels
		lblGuess = new Label("Guess the word I'm thinking of!");
		lblGuess.setStyle("-fx-font: 14px Tahoma;");
		lblTime = new Label("Elapsed Time:");
		lblHint = new Label("Hint:");

		// textfield
		txtfGuess = new TextField();

		// default can't write in txtf yet
		txtfGuess.setDisable(true);
		gameOver = true;

		// image for hint
		img = new Image("./Assets/Guess/hint1.png");
		imView = new ImageView(img);

		// start button
		btnStart = new Button("Start");
		btnStart.setMinWidth(70);
		btnStart.setMinHeight(50);

		// word list
		wordList = new ArrayList<String>();

		// hardcode
		// wordList.add("truck");

		// read words from file
		generateWords("./Assets/Guess/wordlist.csv");

		// debug
		for (String word : wordList) {
			System.out.println(word);
		}
		// custom font
		String font_name = "Comic Sans MS";
		Font font = Font.font(font_name, FontWeight.BOLD, FontPosture.REGULAR, 25);
		// Setting font to the text
		btnStart.setFont(font);
	}

	// read from csv
	private void generateWords(String filename) {
		try {
			String line = "";
			BufferedReader buf = new BufferedReader(new FileReader(filename));
			while ((line = buf.readLine()) != null) {
				wordList.add(line);

			}
			buf.close();

		} catch (Exception e) {
			System.out.println("Error reading " + filename);
		}
	}

	@Override
	public void init() {

		btnStart.setOnAction(event -> {
			txtfGuess.setDisable(false);
			if (gameOver) {
				gameOver = false;
				btnStart.setText("Start");
				System.out.println("Game started!");
				}
			else {
				gameOver = true;
				btnStart.setText("Stop");
				System.out.println("Game over!");
				resetGame();
				}


			wordToGuess = getWord();
			System.out.println("Guess the word: " + wordToGuess);

			lblHint.setText("Hint: " + wordToGuess.length() + " letters");
			
			timeLeft=30;
			lblTime.setText("Time left: ");
			tLine = new Timeline(new KeyFrame(Duration.millis(1000), 
					timerTick -> {
						timeLeft--;
						lblTime.setText("Time Left: " + timeLeft);
						if(timeLeft==0) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setHeaderText("Time is up! Try again. ");
							alert.showAndWait();
							resetGame();
						}
					}));
			tLine.setCycleCount(30);
			tLine.play();
			
		});

		txtfGuess.setOnKeyReleased(event -> {
			if (wordToGuess.equals(txtfGuess.getText())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Congrats! You guessed the word " + wordToGuess);
				resetGame();
				alert.showAndWait();
			} else if (wordToGuess.startsWith(txtfGuess.getText())) {
				txtfGuess.setStyle("-fx-text-inner-color: green;");
			} else {
				txtfGuess.setStyle("-fx-text-inner-color: red;");
			}
			
			if (event.getCode() == KeyCode.ENTER) {
				if (txtfGuess.getText().equals(wordToGuess)) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Congrats! You guessed the word " + wordToGuess);
					resetGame();
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Wrong! Try again");
					alert.showAndWait();
				}

			}
			
				
			});
			
		imView.setOnMousePressed(event ->{
	});

		};
		
		
	private void resetGame() {
		gameOver = true;
		lblHint.setText("Hint: ");
		btnStart.setText("Start");
		wordList.clear();
		generateWords("./Assets/Guess/wordlist.csv");
		txtfGuess.clear();
		txtfGuess.setDisable(true);
		timeLeft = 30;
		lblTime.setText("Time Left: ");
		tLine.stop();
		
	}
	
	private String getWord() {
		Random rand = new Random();
		int randNum = rand.nextInt(wordList.size());

		String randomWord = wordList.get(randNum);
		wordList.remove(randNum);
		return randomWord;

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("WordGuesser");
		primaryStage.setWidth(500);
		primaryStage.setHeight(400);

		BorderPane bpMain = new BorderPane();

		VBox vbGuess = new VBox();
		vbGuess.getChildren().addAll(lblGuess, txtfGuess, imView);
		VBox vbInfo = new VBox();
		vbInfo.getChildren().addAll(lblTime, lblHint);

		HBox hbButtons = new HBox();
		hbButtons.getChildren().add(btnStart);
		hbButtons.setAlignment(Pos.BASELINE_CENTER);

		bpMain.setPadding(new Insets(10));
		vbGuess.setPadding(new Insets(10)); // can do top, right, bottom, left

		bpMain.setCenter(vbGuess);
		bpMain.setBottom(hbButtons);
		bpMain.setRight(vbInfo);

		imView.fitWidthProperty().bind(primaryStage.widthProperty().divide(1.5));
		imView.setPreserveRatio(true);
		txtfGuess.maxWidthProperty().bind(primaryStage.widthProperty().divide(1.5));

		Scene s = new Scene(bpMain);

		primaryStage.setScene(s);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}