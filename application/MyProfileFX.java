//TODO: Student Name - Student Number
//Note: TODO comments are to provide helpful hints only, make sure to implement all tasks as per specification.

package application;

//Standard JavaFX Imports
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
//imports for controls
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
//layouts
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
//file imports
import java.io.File;
//geometry
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class MyProfileFX extends Application {
	BorderPane bpMain; // Main layout of window, needs to be global so we can change the background
	// TODO: Declare components that require class scope

	// TODO: Instantiate components
	public MyProfileFX() {

	}

	// TODO: Event Handling for main UI
	@Override
	public void init() {
		// TODO: Handle events for Choose button

		// TODO: Handle events for UpdateProfile button

		// TODO: Handle events for Customise button
	}

	// TODO: Show System Dialog to choose a profile photo (.png only)
	private void showPictureDialog() {
		// TODO: Show System dialog (filter only .png images)
		// TODO: Get Image chosen by user
		// TODO: Show chosen image in main UI if not null
	}

	// TODO: Show Custom Dialog to choose background color
	private void showColorPicker() {
		// TODO: Setup a new Stage for the dialog

		// TODO: Create components for the dialog

		// TODO: Event handling for dialog

		// TODO: Create and Manage Scene & Layouts for dialog

		// TODO: Show the dialog

	}

	// TODO: show a dialog to update user details
	private void showDialog() {
		// TODO: Set up the new Stage

		// TODO: Create components for the dialog

		// TODO: Event handling for dialog

		// TODO: Create and Manage Scene & Layouts for dialog

		// TODO: Validation on user input

		// TODO: Show the dialog
	}

	// TODO: Set up the main UI window & scene
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO: set up main window

		// TODO: manage containers
		bpMain = new BorderPane(); // main container (background to be changed)

		// TODO: bind sizes of ImageView and Button to 25% of window size

		// TODO: set default background of main UI to last 6 digits of your student
		// number

		// TODO: show the window

	}

	// TODO: Launch the application
	public static void main(String[] args) {

	}

	// ---- You do not need to change this method, just use as needed ---
	public void changeBackground(String hexcode) {
		// change background color of main layout
		bpMain.setStyle("-fx-background-color:\n" + "            linear-gradient(" + "#" + hexcode + ", #FFFFFF);");
	}
}
