package application;

import java.io.File;
import java.util.Random;
import java.util.regex.Pattern;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.FileChooser.ExtensionFilter;

public class CookieClicker extends Application {

	Image img;
	ImageView imview;
	Label lblCount;
	Button btnClick;
	Button colorDialog;
	int counter = 0;
	

	public CookieClicker() {
		lblCount = new Label("Click the cookie!");
		btnClick = new Button("");
		colorDialog = new Button("Pick the color");
		  
        

		try {
			img = new Image("./Assets/cookie.png");
			imview = new ImageView(img);
		} catch (Exception e) {
			System.err.println("Error loading the image");
		}
		
	}

	public void init() {
		imview.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("Cookie clicked");
			//Instantiating TranslateTransition class   
	        ScaleTransition translate = new ScaleTransition();    
	        translate.setByX(0.15);  
	        translate.setByY(0.15);    
	        translate.setDuration(Duration.millis(50));  
	        translate.setCycleCount(2);   
	        translate.setAutoReverse(true);  
	        translate.setNode(imview);  
			translate.play(); 
			RotateTransition rotateTransition = new RotateTransition(Duration.millis(25), imview);
			Random rand = new Random();
			if(rand.nextInt(10)>=8) {
				//Rotate by 200 degree
		    rotateTransition.setByAngle(360);
		    rotateTransition.play();
			}
		    
			counter++;
			lblCount.setText("You clicked " + counter + " times!");
			event.consume();
		});
		
		colorDialog.setOnAction(event -> showColorDialog());
	}
	
	public void showColorDialog() {
		Stage dialogStage = new Stage();
		BorderPane bpDialog = new BorderPane();
		ColorSelector cs = new ColorSelector();
		
		
		// set title, set default width & height
		dialogStage.setTitle("Color picker");
		dialogStage.setWidth(500);
		dialogStage.setHeight(400);

		// add padding
		bpDialog.setPadding(new Insets(30, 100, 50, 100));
		// top, right, bottom, left or just one number for all
		

		// create components
		TextField txtfNumber = new TextField();
		Button btnCancel = new Button("Cancel");
		Button btnOk = new Button("Ok");

		// manage size of the ok button
		btnOk.setMinSize(80, 0);

		// add components to the layout
		
		bpDialog.setTop(cs);
		bpDialog.setBottom(btnOk);
		bpDialog.setAlignment(btnOk, Pos.BOTTOM_RIGHT);
		bpDialog.setBottom(btnCancel);
		bpDialog.setAlignment(btnCancel, Pos.BOTTOM_RIGHT);

		// event handling
		btnCancel.setOnAction(buttonclicked -> dialogStage.close());
		btnOk.setOnAction(buttonclicked -> {
			bpDialog.setStyle("-fx-background-color: #" + cs.getHex()+";");
			dialogStage.close();
		});
		
		// create scene for the dialog
		Scene s = new Scene(bpDialog);

		// set the scene
		dialogStage.setScene(s);

		// show the stage
		dialogStage.show();
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Cookie Clicker");
		primaryStage.setWidth(500);
		primaryStage.setHeight(500);
		// layout for the dialog
		BorderPane bpMain = new BorderPane();
		HBox countBox = new HBox();

		// add padding
		bpMain.setPadding(new Insets(20));
		// top, right, bottom, left or just one number for all
		bpMain.setMargin(countBox, new Insets(30));
		countBox.setAlignment(Pos.BASELINE_CENTER);
		countBox.setSpacing(20);
		
		imview.setPreserveRatio(true);
		imview.fitWidthProperty().bind(primaryStage.widthProperty().divide(1.5));

		// add components to the layout
		
		countBox.getChildren().add(lblCount);
		bpMain.setBottom(countBox);
		countBox.setAlignment(Pos.BASELINE_CENTER);
		bpMain.setTop(colorDialog);
		colorDialog.setAlignment(Pos.TOP_CENTER);
		bpMain.setCenter(imview);

		// spacing
		
			
		// add sublayouts to the main layout

		Scene s = new Scene(bpMain);

		primaryStage.setScene(s);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

}
