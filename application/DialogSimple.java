//DialogSimple
// Application with MenuBar and shows two types of Dialogs
// Template - complete TOOD tasks

package application;

import java.util.regex.Pattern;

//Standard JavaFX imports
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//imports for components
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
//imports for menus
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
// imports for images
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//imports for layout
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//imports for geometry etc
import javafx.geometry.Insets;
import javafx.geometry.Pos;

//support for quitting the Application
import javafx.application.Platform;

public class DialogSimple extends Application {

	// declare components that require class scope
	MenuBar mbMain;
	Menu mnuFile, mnuHelp;
	MenuItem miShowDialog;
	MenuItem miAbout;
	TextArea txtMain;

	// constructor
	public DialogSimple() {

		// instantiate components
		mbMain = new MenuBar();
		mnuFile = new Menu("File");
		mnuHelp = new Menu("Help");
		miShowDialog = new MenuItem("Show Dialog");
		miAbout = new MenuItem("About");
		txtMain = new TextArea();

	}

	// event handling
	@Override
	public void init() {

		// handle events for miAbout
		miAbout.setOnAction(event -> showDialog());

		miShowDialog.setOnAction(event -> showCustomDialog());
	}

	// show a custom dialog
	public void showCustomDialog() {
		Stage dialogStage = new Stage();

		// set title, set default width & height
		dialogStage.setTitle("My custom dialog");
		dialogStage.setWidth(500);
		dialogStage.setHeight(200);

		// layout for the dialog
		VBox vbDialog = new VBox();

		// add padding
		vbDialog.setPadding(new Insets(30, 100, 50, 100));
		// top, right, bottom, left or just one number for all

		// subcontainers
		GridPane gpDialog = new GridPane();
		HBox hbButtons = new HBox();

		// create components
		Label lblName = new Label("Full name:");
		TextField txtfName = new TextField();
		Label lblNumber = new Label("Student number:");
		TextField txtfNumber = new TextField();
		Button btnCancel = new Button("Cancel");
		Button btnOk = new Button("Ok");

		// manage size of the ok button
		btnOk.setMinSize(80, 0);

		// add components to the layout
		gpDialog.add(lblName, 0, 0);
		gpDialog.add(txtfName, 1, 0);
		gpDialog.add(lblNumber, 0, 1);
		gpDialog.add(txtfNumber, 1, 1);
		hbButtons.getChildren().addAll(btnOk, btnCancel);

		// spacing
		gpDialog.setVgap(10);
		gpDialog.setHgap(20);
		vbDialog.setSpacing(10);
		hbButtons.setSpacing(10);

		// add sublayouts to the main layout
		vbDialog.getChildren().addAll(gpDialog, hbButtons);

		// event handling
		btnCancel.setOnAction(buttonclicked -> dialogStage.close());
		btnOk.setOnAction(buttonclicked -> {
			System.out.println("Ok was clicked!");

			// get the text from the textfield
			String username = txtfName.getText();

			// get text from the student no
			String studentnum = txtfNumber.getText();

			// validation for name i.e. Ilia Ermolov
			String pattern = "[A-Z]{1}[a-z]{1,20}\s[A-Z]{1}[a-z]{1,20}"; // regex

			// validation for number i.e. GCD12345678
			String patternnumber = "[0-9]{7,10}"; // regex

			// double conditional
			if (!Pattern.matches(pattern, username)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Wrong name pattern");
				alert.setHeaderText("Please provide a valid user name");
				alert.setContentText(
						"Make sure your name:\n- Starts with a capital letter\n- The rest letters are lowercase\n- Has the space between the name and surname");

				alert.showAndWait();
			} else if (!Pattern.matches(patternnumber, studentnum)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Wrong number pattern");
				alert.setHeaderText("Please provide a valid student number");
				alert.setContentText("Make sure your number:\n- Only contains numbers 0-9\n- Is 7 to 10 numbers long");

				alert.showAndWait();
			}

			else {
				txtMain.setText("Hello " + username + "!\nYour number is " + studentnum);
				dialogStage.close();
			}
		});

		// create scene for the dialog
		Scene s = new Scene(vbDialog);

		// set the scene
		dialogStage.setScene(s);

		// show the stage
		dialogStage.show();

	}

	// show simple prebuilt dialog
	public void showDialog() {

		// use alert
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About dialog simple");
		alert.setHeaderText("Just a simple dialog application");
		alert.setContentText("DialogSimple is a copyright HDC organisation 2023, ilia ermolov");

		try {

			// add custom image before alert
			Image img = new Image("./Assets/logo1.png");
			ImageView imv = new ImageView(img);

			// manage image size
			imv.setFitWidth(80);
			imv.setPreserveRatio(true);

			// set the image of the alert to imageview
			alert.setGraphic(imv);

		} catch (Exception error) {
			System.err.println("Image not found");
			error.printStackTrace();

		}

		alert.showAndWait();
	}

	// window setup
	@Override
	public void start(Stage primaryStage) throws Exception {

		// set default width and height of window
		primaryStage.setWidth(615);
		primaryStage.setHeight(300);

		// set title to 'DialogSimple'
		primaryStage.setTitle("DialogSimple");

		// create main layout
		BorderPane bpMain = new BorderPane();

		// add menus to the menubar (mnufile + mnuhelp)
		mbMain.getMenus().addAll(mnuFile, mnuHelp);

		// add menuitems to the menus
		mnuFile.getItems().add(miShowDialog);
		mnuHelp.getItems().add(miAbout);

		// add components to the layout
		bpMain.setTop(mbMain);
		bpMain.setCenter(txtMain);

		// Create a Scene - takes a root layout
		Scene scene = new Scene(bpMain);

		// Set the Scene
		primaryStage.setScene(scene);

		// show the window
		primaryStage.show();
	}

	// launch the application
	public static void main(String[] args) {
		launch(args);

	}
}