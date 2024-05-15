//Template for FXEditor23
//TODO: Complete tasks as required
//TODO: Student name - StudentNumber
package application;


// IO imports
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
//Basic Java Imports
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;
//Imports for layouts
import javafx.scene.layout.BorderPane;
//Imports for Controls
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;

public class FXEditor23 extends Application {
	// Declare components that require class scope
	MenuBar mBar;
	Menu mnuFile, mnuEdit, mnuHelp;
	MenuItem miFileNew, miFileSave, miFileOpen, miFileClose, miFileQuit;
	// TODO: Declare menuItems for Edit: Undo, Redo, Cut, Copy, Paste, Delete,
	// SelectAll
	MenuItem miEditUndo, miEditRedo, miEditCut, miEditCopy, miEditPaste, miEditDelete, miEditSelectAll;
	// TODO: Declare menuItems for Help: Contents, About
	MenuItem miHelpContents, miHelpAbout;

	TextArea txtMain;

	public FXEditor23() {
		// Instantiate components
		mBar = new MenuBar();
		// FileMenu and MenuItems
		mnuFile = new Menu("File");
		miFileNew = new MenuItem("New");
		miFileSave = new MenuItem("Save");
		miFileOpen = new MenuItem("Open");
		miFileClose = new MenuItem("Close"); // save and clear
		miFileQuit = new MenuItem("Quit");
		// Edit
		mnuEdit = new Menu("Edit");
		miEditUndo = new MenuItem("Undo");
		miEditRedo = new MenuItem("Redo");
		miEditCut = new MenuItem("Cut");
		miEditCopy = new MenuItem("Copy");
		miEditPaste = new MenuItem("Paste");
		miEditDelete = new MenuItem("Delete");
		miEditSelectAll = new MenuItem("Select All");
		// Help
		mnuHelp = new Menu("Help");
		miHelpContents = new Menu("Contents");
		miHelpAbout = new MenuItem("About");

		// Text Area to type in
		txtMain = new TextArea();
	}

	// Event handling
	@Override
	public void init() {
		// Handle events on miHelpAbout
		miHelpAbout.setOnAction(event -> showAbout());
		// TODO: Handle events on File Menu
		// quit - first checks whether the file is saved
		miFileQuit.setOnAction(event -> saveAndQuit());
		miFileSave.setOnAction(event -> saveFile());
		// open file option A
		//miFileOpen.setOnAction(event -> openFileA());
		// open file option B
		miFileOpen.setOnAction(event -> openFileB());
		// TODO: Handle events on Edit Menu
		miEditUndo.setOnAction(event -> txtMain.undo());

		// TODO: Edit events: Redo, Cut, Copy, Paste, clear.....

		// TODO Help events: Content (create a custom dialog)

		// Next week: File reading/writing

	}

	// saveandquit method - saves b4 quitting
	private void saveAndQuit() {
		// check if theres anything to save
		if (txtMain.getText().isBlank())
			Platform.exit();
		// prompt the user
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Save before quitting?");
		alert.setHeaderText("Do you want to save your work?");

		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				System.out.println("Saving to file...");
				try {
					// save as a text file
					BufferedWriter buf = new BufferedWriter(new FileWriter("./textfiles/myTextFile.txt"));
					// write the text to the file
					buf.write(txtMain.getText());
					// close the writer
					buf.close();

				} catch (IOException e) {
					System.out.println("Error writing the text file");
					e.printStackTrace();
				}
			} else
				Platform.exit();

		});
		// quit if yes

	}

	private void saveFile() {
		// prompt the user

		System.out.println("Saving to file...");
		
		FileChooser fc = new FileChooser();
		fc.setTitle("Save Text File");
		fc.getExtensionFilters().add(new ExtensionFilter("text only", "*.txt"));
		File sf = fc.showSaveDialog(null);
		
		if(sf != null) {
			System.out.println("A file was chosen." + sf.getPath().toString());
			try {
				// save as a text file
				BufferedWriter buf = new BufferedWriter(new FileWriter(sf.getPath().toString()));
				// write the text to the file
				buf.write(txtMain.getText());
				// close the writer
				buf.close();
				
			}
			catch(Exception e){
				System.out.println("Error opening the text file");
				e.printStackTrace();
			}
		}
	}
	
	//hardcoded openfile
	private void openFileA() {
		try {
			BufferedReader buf = new BufferedReader(new FileReader("./textfiles/myTextFile.txt"));
			String line = "";
			while((line = buf.readLine()) != null) {
				//add the line to the text area
				txtMain.appendText(line+"\n");
			}
			buf.close();
			
		}
		catch(Exception e){
			System.out.println("Error opening the text file");
			e.printStackTrace();
		}
	}
	
	//userchooses openfile
	private void openFileB() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open Text File");
		fc.getExtensionFilters().add(new ExtensionFilter("text only", "*.txt"));
		File sf = fc.showOpenDialog(null);
		
		if(sf != null) {
			System.out.println("A file was chosen." + sf.getPath().toString());
			try {
				BufferedReader buf = new BufferedReader(new FileReader(sf.getPath().toString()));
				String line = "";
				while((line = buf.readLine()) != null) {
					//add the line to the text area
					txtMain.appendText(line+"\n");
				}
				buf.close();
				
			}
			catch(Exception e){
				System.out.println("Error opening the text file");
				e.printStackTrace();
			}
		}
	}

	private void showAbout() {

		// use alert
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About FXEditor23");
		alert.setHeaderText("Just a simple text editor.");
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

	@Override
	public void start(Stage primaryStage) throws Exception {
		// setup window and show it
		primaryStage.setTitle("FXEditor 2023");
		primaryStage.setWidth(600);
		primaryStage.setHeight(500);

		// Create a layout (BorderPane bpMain)
		BorderPane bpMain = new BorderPane();

		// Add File, Help, Edit to MenuBar
		mBar.getMenus().addAll(mnuFile, mnuEdit, mnuHelp);

		// TODO: Add menu items to File Menu
		mnuFile.getItems().addAll(miFileNew, miFileSave, miFileOpen, miFileClose, miFileQuit);

		// TODO: Add menu items to Edit Menu
		mnuEdit.getItems().addAll(miEditUndo, miEditRedo, miEditCut, miEditCopy, miEditPaste, miEditDelete,
				miEditSelectAll);

		// TODO: Add menu items to Help Menu
		mnuHelp.getItems().addAll(miHelpContents, miHelpAbout);

		// Add components to the layout
		bpMain.setTop(mBar);
		bpMain.setCenter(txtMain);

		// Create a Scene (main layout)
		Scene s = new Scene(bpMain);

		// Set the Scene (primaryStage)
		primaryStage.setScene(s);

		// Show the main window
		primaryStage.show();
	}

	public static void main(String[] args) {
		// launch the application
		launch(args);
	}
}