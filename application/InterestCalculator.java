package application;

import javafx.scene.Scene;
import javafx.application.Application;
import javafx.stage.Stage;

//Imports for components in this application.
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

//Support for date entry.
import javafx.scene.control.DatePicker;

//Icons etc.
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

//Layout, containers etc.
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

//Support for quitting.
import javafx.application.Platform;

//Date handling.
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

//Currency output formatting.
import java.text.NumberFormat;
import java.util.Locale;

//Alerts...
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InterestCalculator extends Application {
	// Components that need class scope.
	Label lblCapital, lblInterestRate, lblInvTerm;
	TextField txtfCapital, txtfInterestRate, txtfInvTerm;
	Button btnQuit, btnCalculate, btnDialog;
	CheckBox chkSimple, chkCompound;
	TextArea txtMain;

	public InterestCalculator() {
		// Instantiate components with 'new'.
		lblCapital = new Label("Capital:");

		lblInterestRate = new Label("Interest rate:");
		lblInvTerm = new Label("Investment term (yrs):");

		txtfCapital = new TextField();
		txtfInterestRate = new TextField();
		txtfInvTerm = new TextField();

		btnQuit = new Button("Quit");
		btnCalculate = new Button("Calculate");
		btnDialog = new Button("...");

		// Set button sizes.
		btnQuit.setMinWidth(80);
		btnCalculate.setMinWidth(80);

		chkSimple = new CheckBox("Simple interest");
		chkCompound = new CheckBox("Compound interest");

		txtMain = new TextArea();

	}// constructor()

	@Override
	public void init() {
		// Event handling: Respond to program events.

		// clicking btnQUit should quit the application.
		btnQuit.setOnAction(event -> Platform.exit());

		btnDialog.setOnAction(event -> customDialog());

		btnCalculate.setOnAction(event -> showInterestAnalysis());

	}

	private void showInterestAnalysis() {
		double capital = 0, intRate = 0, years = 0;
		// get use data
		try {
			capital = Double.parseDouble(txtfCapital.getText());
			intRate = Double.parseDouble(txtfInterestRate.getText());
			years = Double.parseDouble(txtfInvTerm.getText());
		} catch (Exception e) {
			System.err.print("Missing data " + e.toString());
		}
		// check the checkboxes
		if (chkSimple.isSelected()) {
			showSimpleInterest(capital, intRate, years);
		}

		if (chkCompound.isSelected()) {
			showCompoundInterest(capital, intRate, years);
		}
	}

	private void showSimpleInterest(double capital, double intRate, double years) {
		double interest = 0, increasedCapital, interestAmount = getSimpleInterest(capital, intRate, years);
		increasedCapital = capital + interestAmount;
		NumberFormat currFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
		String analysisString = "Simple Interest: \nYears: "+years+"\nInitial Capital: "+currFormat.format(capital)+"\nInterest earned: "+currFormat.format(interestAmount)+"\nFinal Amount: "+currFormat.format(increasedCapital);
		txtMain.appendText(analysisString);
	}

	private double getSimpleInterest(double capital, double intRate, double years) {
		double interest = 0;
		interest = capital*(intRate/100)*years;
		return interest;
	};

	private void showCompoundInterest(double capital, double intRate, double years) {
		double interest = 0, increasedCapital, interestAmount = getCompoundInterest(capital, intRate, years);
		increasedCapital = capital + interestAmount;
		NumberFormat currFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
		String analysisString = "Compound Interest: \nYears: "+years+"\nInitial Capital: "+currFormat.format(capital)+"\nInterest earned: "+currFormat.format(interestAmount)+"\nFinal Amount: "+currFormat.format(increasedCapital);
		txtMain.appendText(analysisString);
	}

	private double getCompoundInterest(double capital, double intRate, double years) {
		double interest = 0;
		for(int i=0;i<years;i++) {
			interest += (capital+interest)*(intRate/100);
		}
		return interest;
	}

	public void customDialog() {
		Stage dialogStage = new Stage();

		// set title, set default width & height
		dialogStage.setTitle("Dialog");
		dialogStage.setWidth(300);
		dialogStage.setHeight(250);

		// layout for the dialog
		VBox vbDialog = new VBox();

		// add padding
		vbDialog.setPadding(new Insets(20));
		vbDialog.setSpacing(30);
		vbDialog.setAlignment(Pos.CENTER_LEFT);

		// top, right, bottom, left or just one number for all

		// create components
		Label lblStartDate = new Label("Start date:");
		DatePicker datePickerStart = new DatePicker();
		Label lblEndDate = new Label("Start date:");
		DatePicker datePickerEnd = new DatePicker();
		Button btnCancel = new Button("Cancel");
		Button btnOk = new Button("Ok");

		// subcontainers
		VBox vbDateStart = new VBox();
		vbDateStart.getChildren().addAll(lblStartDate, datePickerStart);
		vbDateStart.setSpacing(10);
		vbDateStart.setAlignment(Pos.CENTER_LEFT);

		VBox vbDateEnd = new VBox();
		vbDateEnd.getChildren().addAll(lblEndDate, datePickerEnd);
		vbDateEnd.setSpacing(10);
		vbDateEnd.setAlignment(Pos.CENTER_LEFT);

		HBox hbButtons = new HBox();
		hbButtons.getChildren().addAll(btnCancel, btnOk);
		hbButtons.setSpacing(10);
		hbButtons.setAlignment(Pos.CENTER_RIGHT);

		vbDialog.getChildren().addAll(vbDateStart, vbDateEnd, hbButtons);

		// event handling
		btnCancel.setOnAction(event -> dialogStage.close());
		btnOk.setOnAction(event -> {
			LocalDate date1 = datePickerStart.getValue();
			LocalDate date2 = datePickerEnd.getValue();
			Period period = Period.between(date1, date2);
			txtfInvTerm.setText(Integer.toString(period.getYears()));
			dialogStage.close();
		});

		// Create a scene.
		Scene s = new Scene(vbDialog);

		// Apply a stylesheet ("intrcalc_style.css")
		s.getStylesheets().add("./Assets/intercalc_style.css");

		// Set the scene.
		dialogStage.setScene(s);

		// Show the stage.
		dialogStage.show();

	};

	@Override
	public void start(Stage primaryStage) throws Exception {
		// User interface construction.
		// Set the title.
		primaryStage.setTitle("Interest Calculator v1.0");

		// Add an appropriate icon.
		primaryStage.getIcons().add(new Image("./Assets/ledger.png"));

		// Set the width and height
		primaryStage.setWidth(500);
		primaryStage.setHeight(500);

		// Create a layout.
		VBox vbMain = new VBox(); // Main container.
		vbMain.setPadding(new Insets(10));
		vbMain.setSpacing(10);

		GridPane gp = new GridPane(); // Contains textfields, labels, a button and checkboxes.
		// gridpane spacing
		gp.setHgap(10);
		gp.setVgap(10);

		HBox hbButtons = new HBox();// Just contains the Quit and Calculate buttons.
		hbButtons.setSpacing(10);

		// Put the gp into the main container.
		vbMain.getChildren().add(gp);

		// Add components to the layout.
		gp.add(lblCapital, 0, 0);
		gp.add(txtfCapital, 1, 0);

		gp.add(lblInterestRate, 0, 1);
		gp.add(txtfInterestRate, 1, 1);

		gp.add(lblInvTerm, 0, 2);
		gp.add(txtfInvTerm, 1, 2);
		gp.add(btnDialog, 2, 2);

		gp.add(chkSimple, 1, 3);
		gp.add(chkCompound, 1, 4);

		vbMain.getChildren().add(txtMain);

		// Add the buttons to the buttons hbox.
		hbButtons.getChildren().addAll(btnQuit, btnCalculate);

		// Now, add the button box to the main container.
		vbMain.getChildren().add(hbButtons);
		hbButtons.setAlignment(Pos.BASELINE_RIGHT);

		// Create a scene.
		Scene s = new Scene(vbMain);

		// Set the scene.
		primaryStage.setScene(s);

		// Apply a stylesheet ("intrcalc_style.css")
		s.getStylesheets().add("./Assets/intercalc_style.css");

		// Show the stage.
		primaryStage.show();

	}// start()

	public static void main(String[] args) {
		// Launch the application.
		launch(args);

	}// main()

}// class