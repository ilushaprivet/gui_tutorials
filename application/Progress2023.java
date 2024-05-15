package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

//Components in this application.
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
//Imports for layout.
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//Geometry
import javafx.geometry.Insets;
import javafx.geometry.Pos;

//This is to support an icon for the application.
import javafx.scene.image.Image;



public class Progress2023 extends Application {
	
	//Declare components requiring class scope.
	ProgressBar progBar;
	
	ProgressIndicator progInd;
	
	Label lblProgress;
	
	Button btnIncrease, btnDecrease;
	


	public Progress2023() {
		progBar = new ProgressBar();
		progInd = new ProgressIndicator();
		
		btnIncrease = new Button("Increase");
		btnDecrease = new Button("Decrease");
		
		lblProgress = new Label("Progress");
		

		
		
	}//constructor()
	
	@Override
	public void init() {
		
		//Event handling...
		btnIncrease.setOnAction(event -> increaseProgress());
		btnDecrease.setOnAction(event -> decreaseProgress());
		

		
	}
	
	private void increaseProgress() {
		System.out.println("Clicked Increase");
		double progress = progBar.getProgress();
		System.out.println("progress: "+progress);
		progress += 0.05;
		if(progress>1)
			progress=1;
		if(progress<0)
			progress=0;
		
		//change the color of the bar
		if(progress<0.4) {
			progBar.setStyle("-fx-accent: blue");
			progInd.setStyle("-fx-accent: blue");
			}
		else if(progress<0.7) {
			progBar.setStyle("-fx-accent: green");
			progInd.setStyle("-fx-accent: green");
			}
		else if(progress>0.7) {
			progBar.setStyle("-fx-accent: red");
			progInd.setStyle("-fx-accent: red");
			}
		
		progBar.setProgress(progress);
		progInd.setProgress(progress);
		
		
	}
	
	private void decreaseProgress() {
		System.out.println("Clicked Decrease");
		double progress = progBar.getProgress();
		System.out.println("progress: "+progress);
		progress -= 0.05;
		if(progress>1)
			progress=1;
		if(progress<0)
			progress=0;
		
		//change the color of the bar
				if(progress<0.4) {
					progBar.setStyle("-fx-accent: blue");
					progInd.setStyle("-fx-accent: blue");
					}
				else if(progress<0.7) {
					progBar.setStyle("-fx-accent: green");
					progInd.setStyle("-fx-accent: green");
					}
				else if(progress>0.7) {
					progBar.setStyle("-fx-accent: red");
					progInd.setStyle("-fx-accent: red");
					}
				
				
		progBar.setProgress(progress);
		progInd.setProgress(progress);
		
		
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Progress 2023");
		primaryStage.setWidth(500);
		primaryStage.setHeight(400);

		BorderPane bpMain = new BorderPane();

		HBox hbProgress = new HBox();
		hbProgress.getChildren().addAll(progBar, progInd);
		
		HBox hbButtons = new HBox();
		hbButtons.getChildren().addAll(btnDecrease, btnIncrease);
		hbButtons.setAlignment(Pos.TOP_RIGHT);
		hbButtons.setSpacing(30);
		hbButtons.setPadding(new Insets(50));
		
		bpMain.setBottom(hbButtons);
		bpMain.setCenter(hbProgress);
		bpMain.setTop(lblProgress);
		
		bpMain.setPadding(new Insets(30));
		
		progBar.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.8));
		
		Scene s = new Scene (bpMain);
		
		primaryStage.setScene(s);
		
		//Show the stage.
		primaryStage.show();
		
	}//start()


	public static void main(String[] args) {
		// Launch the application
		launch(args);
		
	}//main()

}//class