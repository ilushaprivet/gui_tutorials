package application;
//imports for managing the currency, price and fil reading
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
//import for managing the confirmation alert
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PizzaVendingFX extends Application {

	// Components that need class scope.
	Label lblCreate, lblStatus, lblTotal;
	Button btnAdd, btnRemove, btnClear, btnPay;
	//two listviews (menu and cart)
	ListView<String> lvMenu, lvCart;
	//a progress bar for cooking
	ProgressBar progBar;
	//task for progressing the bar
	Task <Void> task;
	//a variable "total" is global to keep track of the total order price
	double total = 0;
	//a formatting variable for currency, set global because we need to operate with it in several methods
	Locale currency = new Locale.Builder().setLanguage("ga").setRegion("IE").build();
	NumberFormat currFormat = NumberFormat.getCurrencyInstance(currency);

	public PizzaVendingFX() {
		// Instantiating labels'
		lblCreate = new Label("Pizza Vending Machine - Create Order");
		lblStatus = new Label("No pizza selected!");
		lblTotal = new Label("Total: none");
		//progressbar
		progBar = new ProgressBar();
		//buttons
		btnAdd = new Button("Add to Order");
		btnRemove = new Button("Remove from Order");
		btnClear = new Button("Clear Order");
		btnPay = new Button("Pay");
		btnPay.setMinWidth(60);
		//listviews
		lvMenu = new ListView<String>();
		lvCart = new ListView<String>();
		// read menu from file
		readMenu("./assets/pizzamenu.csv");
	}

	private void readMenu(String contactsFile) {
		try {
			String line;			
			BufferedReader buf = new BufferedReader(new FileReader(contactsFile));
			while ((line = buf.readLine()) != null) {
				String[] itemDataArray = new String[2];
				itemDataArray = line.split(";");
				lvMenu.getItems().add(itemDataArray[0]+" - "+currFormat.format(Double.parseDouble(itemDataArray[1])));
			}
			buf.close();

		} catch (Exception e) {
			System.out.println("Error reading " + contactsFile+ e.toString());
		}
	}

	@Override
	public void init() {
		// Event handling...
		btnAdd.setOnAction(event -> addItem());
		btnRemove.setOnAction(event -> removeItem());
		btnClear.setOnAction(event -> clearOrder());
		//conditional for checking whether the cart is empty
		//before showing the payment dialog
		btnPay.setOnAction(event -> {
			if(lvCart.getItems().isEmpty())
				lblStatus.setText("You cart is empty, add items!");
			else
				showPaymentDialog();
		});
		
		//2 listeners to deselect each listview whenever the other one is active
		lvMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
		    if (newVal != null) {
		        lvCart.getSelectionModel().clearSelection();
		    }
		});
		lvCart.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
		    if (newVal != null) {
		        lvMenu.getSelectionModel().clearSelection();
		    }
		});
		

	}
	
	private void showPaymentDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Payment");
		alert.setHeaderText("Please confirm your payment");
		alert.setContentText("Total: "+currFormat.format(total));
		
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

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
		    // User clicked OK button
		    lblStatus.setText("Preparing your order...");
		    letItCook();
		} else {
		    // User clicked Cancel button or closed the dialog
		    // Do nothing
		}
	}

	private void addItem() {
		if(lvMenu.getSelectionModel().getSelectedIndex()==-1) {
			lblStatus.setText("No item selected to add!");
		}
		else {
			total+=getPrice(lvMenu.getSelectionModel().getSelectedItem());
			lvCart.getItems().add(lvMenu.getSelectionModel().getSelectedItem());
			lblTotal.setText("Total: "+currFormat.format(total));
			lblStatus.setText("Item added!");
		}
	}
	
	private void removeItem(){
		if(lvCart.getSelectionModel().getSelectedIndex()==-1) {
			lblStatus.setText("No item selected to remove!");
		}
		else {
			total-=getPrice(lvCart.getSelectionModel().getSelectedItem());
			lvCart.getItems().remove(lvCart.getSelectionModel().getSelectedItem());
			lblTotal.setText("Total: "+currFormat.format(total));
			lblStatus.setText("Item removed!");
		}
	}
	
	private void clearOrder() {
		total=0;
		lvCart.getItems().clear();
		lblTotal.setText("Total: none");
	}
	
	//method for getting the price of an item
	//i guess there should be a more reliable way, e.g. to
	//index all items and just reference to their price by index
	//but i think my way is easier
	private double getPrice(String menuItem) {
		String[] itemDataArray = new String[2];
		itemDataArray = menuItem.split("- ");
		try {
			return currFormat.parse(itemDataArray[1]).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private void letItCook() {
		//disable all the buttons and listviews - user won't need any of them
		//for the cooking time (too late to change anything)
		btnAdd.setDisable(true);
		btnRemove.setDisable(true);
		btnClear.setDisable(true);
		btnPay.setDisable(true);
		lvMenu.setDisable(true);
		lvCart.setDisable(true);
		//make the progress bar appear
		progBar.setVisible(true);
		//the "cooking" (can be any operation)
		task = new Task<Void>() {
			@Override
			public Void call() throws InterruptedException{
				//functionality
				final long max = 100000000;
				System.out.println("Thread running, task started");
				
				//loop to simulate
				for(long i=1; i <= max; i++) {
					//update the progress
					updateProgress(i, max);
				}
				return null;
			}
		};
		task.setOnSucceeded(event -> {
		    // Code to run when task is finished
			btnAdd.setDisable(false);
			btnRemove.setDisable(false);
			btnClear.setDisable(false);
			btnPay.setDisable(false);
			lvMenu.setDisable(false);
			lvCart.setDisable(false);
			progBar.setVisible(false);
			//change status
			lblStatus.setText("Pizza ready! Come again!");
			//use earlier established method to clear order
			clearOrder();
		});
		task.setOnFailed(event -> {
		    // handle failure
		    Throwable exception = task.getException();
		    btnAdd.setDisable(false);
			btnRemove.setDisable(false);
			btnClear.setDisable(false);
			btnPay.setDisable(false);
			lvMenu.setDisable(false);
			lvCart.setDisable(false);
			progBar.setVisible(false);
			//change status
			lblStatus.setText("Something went wrong! Please retry");
		});
		//start the thread
		new Thread(task).start();
		//bind the bar progress to task progress
		progBar.progressProperty().bind(task.progressProperty());
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Set the title
		primaryStage.setTitle("Add to your order and press Pay to bake pizza");
		try {
			primaryStage.getIcons().add(new Image("file:assets/ledger.png"));
		} catch (Exception e) {
			System.out.println("Error reading image file");
			e.printStackTrace();
		}

		// Set the width and height.
		primaryStage.setWidth(700);
		primaryStage.setHeight(600);

		// bind width of the list to 1/3 of the window
		lvMenu.minWidthProperty().bind(primaryStage.widthProperty().divide(3));
		lvCart.minWidthProperty().bind(primaryStage.widthProperty().divide(3));
		// Create a layout
		BorderPane bpMain = new BorderPane(); // our main container

		// Set the spacing
		bpMain.setPadding(new Insets(10));

		// Sublayout Gridpane
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);

		// Sublayout HBox for buttons
		HBox hbButtons = new HBox();
		hbButtons.setSpacing(10);
		hbButtons.getChildren().addAll(lblStatus, progBar, lblTotal, btnPay);
		hbButtons.setAlignment(Pos.CENTER);
		//make progress bar invisible by default
		progBar.setVisible(false);

		VBox vbButtons = new VBox();
		vbButtons.setSpacing(10);
		btnAdd.minWidthProperty().bind(primaryStage.widthProperty().divide(4));
		btnRemove.minWidthProperty().bind(primaryStage.widthProperty().divide(4));
		btnClear.minWidthProperty().bind(primaryStage.widthProperty().divide(4));
		vbButtons.getChildren().addAll(btnAdd, btnRemove, btnClear);
		vbButtons.minWidthProperty().bind(primaryStage.widthProperty().divide(4));

		// Add components to the gridpane
		gp.add(lblCreate, 0, 0);
		gp.add(lvMenu, 0, 1, 1, 3);
		gp.add(vbButtons, 1, 1, 1, 3);
		gp.add(lvCart, 2, 1, 1, 3);

		// add subcontainers to main layout
		bpMain.setCenter(gp);
		bpMain.setBottom(hbButtons);

		// Create a scene
		Scene s = new Scene(bpMain);

		// TODO: apply a stylesheet
		// s.getStylesheets().add("application/cm_style.css");

		// Set the scene
		primaryStage.setScene(s);

		// Show the stage
		primaryStage.show();
	}

	public static void main(String[] args) {
		// Launch the application.
		launch();
	}

}
