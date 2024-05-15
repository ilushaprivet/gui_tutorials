package application;

//Standard javafx imports.
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import javafx.scene.Scene;

//Imports for components in this application.
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ProgressBar;

//Imports for layout.
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class ProgIndTask23 extends Application {

	// Declare variables/components that need class scope.
	Label lblProgress;

	ProgressIndicator progInd;

	Button btnStart, btnCancel;
	
	Task <Void> task;

	public ProgIndTask23() {
		// Instantiate components with 'new'.
		lblProgress = new Label("Progress:");

		progInd = new ProgressIndicator(0);

		progInd.setStyle("-fx-accent: green;");

		btnStart = new Button("Start");
		btnCancel = new Button("Cancel");

		// Manage button sizes.
		btnStart.setMinWidth(60);
		btnCancel.setMinWidth(60);
		
		btnCancel.setDisable(true);

	}// constructor()

	@Override
	public void init() {
		// handle events on the start button
		btnStart.setOnAction(event -> startTask());

		//handle events on the cancel button
		btnCancel.setOnAction(event -> cancelTask());

	}// init()
	
	private void startTask() {
		btnStart.setDisable(true);
		btnCancel.setDisable(false);
		task = new Task<Void>() {
			@Override
			public Void call() throws InterruptedException{
				//functionality
				final long max = 1000000000;
				System.out.println("Thread running, task started");
				
				//loop to simulate
				for(long i=1; i <= max; i++) {
					if(isCancelled()) {
						updateProgress(0, max);
						break;
					}
					//update the progress
					updateProgress(i, max);
				}
				return null;
			}
		};
		
		new Thread(task).start();
		
		progInd.progressProperty().bind(task.progressProperty());
	}
	
	private void cancelTask() {
		btnCancel.setDisable(true);
		btnStart.setDisable(false);
		task.cancel();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("ProgIndTask23");
		primaryStage.setWidth(400);
		primaryStage.setHeight(300);
		
		//Create a main VBoxlayout, center align, customise spacing and padding
		VBox vbMain = new VBox();
		vbMain.setAlignment(Pos.CENTER);
		vbMain.setSpacing(30);
		vbMain.setPadding(new Insets(40));

		

		//Add components to the layout.
		HBox hbButtons = new HBox();
		hbButtons.getChildren().addAll(btnStart, btnCancel);
		hbButtons.setAlignment(Pos.CENTER);
		hbButtons.setSpacing(30);

		
		vbMain.getChildren().addAll(lblProgress, progInd, hbButtons);

		Scene s = new Scene(vbMain);

		primaryStage.setScene(s);

		// Show the stage.
		primaryStage.show();

	}// start()

	public static void main(String[] args) {
		// Launch the application.
		launch(args);

	}// main()

}// class