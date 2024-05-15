//Name student number

package application;

//default fx libraries
import javafx.application.Application;
// input

import javafx.stage.Stage;
//imports for layout
import javafx.scene.layout.GridPane;
//imports for geometry
import javafx.geometry.Insets;
//controls
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//terminating
import javafx.application.Platform;
import javafx.event.EventHandler;

public class Simple extends Application {
	//components
	Label lblName;
	Label lblMessage;
	TextField txtfName;
	Button btnCancel, btnOk;
	
	//constructor
	public Simple() {
		lblName = new Label("Enter name:");
		lblMessage = new Label();
		txtfName = new TextField();
		btnCancel = new Button("Cancel");
		btnOk = new Button("Ok");
		
		//manage button sizes (equal size)
		btnCancel.setMinWidth(80);
		btnOk.setMinWidth(80);

	}
	
	//event handling
	@Override
	public void init() {
		
		//event on cancel
		btnCancel.setOnAction(event -> Platform.exit());
		btnOk.setOnAction(event -> {
			//debug
			System.out.print("Ok pressed");
			//end result (get the name and greet with a name)
			lblMessage.setText("Hello, "+txtfName.getText());
		});
		
		//making enter work as an ok button
		txtfName.setOnKeyPressed((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {
			 
		    @Override
		    public void handle(KeyEvent event) {
		        if(event.getCode().equals(KeyCode.ENTER)) {
		        	System.out.print("Enter pressed");
					//end result (get the name and greet with a name)
					lblMessage.setText("Hello, "+txtfName.getText());
		        }
		    }
		});
		
	}
	

	//window setup, layouts, etc
	@Override
	public void start(Stage primaryStage) throws Exception {
		//window title
		primaryStage.setTitle("A Simple Application");
		
		//set default w and h
		primaryStage.setWidth(615);
		primaryStage.setHeight(300);
		
		//create layout
		GridPane gpMain = new GridPane();
		
		//add controls to the layout
		gpMain.add(lblName, 0, 0);
		gpMain.add(txtfName,1, 0, 2, 1); //starts at col 1 row 0, takes up to 2 cols and 1 row
		gpMain.add(lblMessage, 0, 1, 3, 1);
		gpMain.add(btnCancel, 1, 2);
		gpMain.add(btnOk, 2, 2);
		
		//spacing (space between elements) and padding (space around the scene)
		//Insets myPadding = new Insets(30); in case we need it more than 1 time
		gpMain.setPadding(new Insets(90,200,150,150)); //top, right, bottom, left or just one number for all
		
		gpMain.setVgap(5);
		gpMain.setHgap(10);
		
		//create scene
		Scene s = new Scene(gpMain);
		
		
		// add scene to the primary stage
		primaryStage.setScene(s);
		
		// show the stage
		primaryStage.show();
	}

	//launch
	public static void main(String[] args) {
		launch(args);

	}

}
