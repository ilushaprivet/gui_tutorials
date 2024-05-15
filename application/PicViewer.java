package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class PicViewer extends Application {

	Image img;
	ImageView imview;
	Label lblPath;
	Button btnChoose;

	public PicViewer() {
		
		lblPath = new Label("Image Path");
		btnChoose = new Button("Choose picture");
		
		try {
			img = new Image("./Assets/logo1.png");
			imview = new ImageView(img);
		}
		catch(Exception e){
			System.err.println("Error loading the image");
		}
		
	}
	
	public void init() {
		btnChoose.setOnAction(event ->{
			FileChooser fc = new FileChooser();
			fc.setTitle("Open Image File");
			fc.getExtensionFilters().add(new ExtensionFilter("PNG only", "*.png"));
			File sf = fc.showOpenDialog(null);
			
			if(sf != null) {
				lblPath.setText(sf.getPath().toString());
				try {
					img = new Image(sf.toURI().toString());
					imview.setImage(img);
					
				}
				catch(Exception e){
					System.out.println("Error opening the text file");
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {


		primaryStage.setTitle("PicViewer Tutorial");
		primaryStage.setWidth(500);
		primaryStage.setHeight(500);

		// layout for the dialog
		BorderPane bpMain = new BorderPane();
		HBox hbButtons = new HBox();

		// add padding
		bpMain.setPadding(new Insets(20));
		// top, right, bottom, left or just one number for all
		bpMain.setMargin(hbButtons, new Insets(30));
		hbButtons.setAlignment(Pos.BASELINE_RIGHT);
		hbButtons.setSpacing(20);

		imview.setPreserveRatio(true);
		imview.fitWidthProperty().bind(primaryStage.widthProperty().divide(2));
		// manage size of the ok button
		btnChoose.setMinSize(80, 0);
		lblPath.setMinSize(150, 0);

		// add components to the layout

		hbButtons.getChildren().addAll(lblPath, btnChoose);
		bpMain.setBottom(hbButtons);
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
