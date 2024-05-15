package application;
	
//Standard javafx imports
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
//components in this app
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
//imports for layouts
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;			
import javafx.scene.layout.HBox;
//Insetting and alignment
import javafx.geometry.Insets;
import javafx.geometry.Pos;
//Quitting the app
import javafx.application.Platform;
//Imports for file handling support
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
//Image for the icon
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//Imports for alerts
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ContactManager extends Application {
	//Components that need class scope.
	Label lblNames, lblEmail, lblAddress;
	TextField txtfEmail;
	TextArea txtAddr;
	Button btnClose, btnAbout;
	ListView <String> lvNames;

	public ContactManager() {
		//Instantiate components with 'new'
		lblNames = new Label("Names:");
		lblEmail = new Label("Email:");
		lblAddress = new Label("Address:");
		
		txtfEmail = new TextField();
		txtAddr = new TextArea();
		
		//disable them for user
		txtfEmail.setDisable(true);
		txtAddr.setDisable(true);
		
		btnClose = new Button("Close");
		btnClose.setMinWidth(60);
		
		btnAbout = new Button("About");
		btnAbout.setMinWidth(60);	
		
		lvNames = new ListView<String>();
		
		//hardcoded input values
		//lvNames.getItems().addAll("Smith Peter","Gorman Michael","Kelly Margaret");
		
		
		//read from file
		readContactNames("./assets/contacts.csv");
		
	}
	
	private void readContactNames(String contactsFile) {
		try {
			String line;
			
			BufferedReader buf = new BufferedReader(new FileReader(contactsFile));
			while((line = buf.readLine()) != null) {
				String[] contactDataArray = new String[3];
				contactDataArray = line.split(":");
				lvNames.getItems().add(contactDataArray[0]);
				
				
			}
			buf.close();
			
		} catch(Exception e) {
			System.out.println("Error reading "+contactsFile);
		}
	}
	
	@Override
	public void init() {
		//Event handling...
		btnClose.setOnAction(event -> Platform.exit());
		btnAbout.setOnAction(event -> showAbout());
		
		lvNames.setOnMousePressed(event -> {
			String selectedName = lvNames.getSelectionModel().getSelectedItem().toString();
			System.out.println(selectedName);
			
			try {
				BufferedReader buf = new BufferedReader(new FileReader("./assets/contacts.csv"));
				String line;
				while((line = buf.readLine()) != null) {
					String[] readLine = new String[3];
					readLine = line.split(":");
					if(readLine[0].equals(selectedName)) {
						System.out.println("Found "+selectedName);
						txtfEmail.setText(readLine[1]);
						txtAddr.setText(readLine[2]);
					}
				}
			}
			catch(Exception e){
				System.err.println("Error reading the file");
				e.printStackTrace();
			}
		});
		
	}
	
	public void showAbout() {

		// use alert
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About Contact Manager");
		alert.setHeaderText("Just a simple contact manager application");
		alert.setContentText("Contact MAnager is a copyright HDC organisation 2023, ilia ermolov 3106798");

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
		//Set the title
		primaryStage.setTitle("ContactManager V1.0.0");
		try {
			primaryStage.getIcons().add(new Image("file:assets/ledger.png"));
		}
		catch(Exception e) {
			System.out.println("Error writing the text file");
			e.printStackTrace();
		}
		
		
		//Set the width and height.
		primaryStage.setWidth(800);
		primaryStage.setHeight(500);
		
		//bind width of the list to 1/3 of the window
		lvNames.minWidthProperty().bind(primaryStage.widthProperty().divide(3));
				
		//Create a layout
		VBox vbMain = new VBox(); // our main container
		
		//Set the spacing
		vbMain.setSpacing(10);
		vbMain.setPadding(new Insets(10));
		
		//Sublayout Gridpane
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		
		//Sublayout HBox for buttons
		HBox hbButtons = new HBox();
		hbButtons.setSpacing(10);
		hbButtons.getChildren().addAll(btnAbout,btnClose);
		hbButtons.setAlignment(Pos.BASELINE_RIGHT);
		
		//Add components to the gridpane
		gp.add(lblNames, 0, 0);
		gp.add(lblEmail, 1, 0);
		gp.add(txtfEmail, 1, 1);
		gp.add(lblAddress, 1, 2);
		gp.add(txtAddr, 1, 3);
		gp.add(lvNames, 0, 1, 1, 3);
		
		//add subcontainers to main layout
		vbMain.getChildren().add(gp);
		vbMain.getChildren().add(hbButtons);
				
		//Create a scene
		Scene s = new Scene(vbMain);
		
		//TODO: apply a stylesheet
		//s.getStylesheets().add("application/cm_style.css");
		
		//Set the scene
		primaryStage.setScene(s);
		
		//Show the stage
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		//Launch the application.
		launch();
	}
}