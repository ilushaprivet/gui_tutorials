//Custom Control - ColorSelector 
// Copy this file into your project, use to create a new ColorSelector object for your UI
// You only need to edit line 19 of this file

package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorSelector extends VBox {
  
  private String defaultColor = "106798"; //TODO: change to last 6 digits of your student number
	
  //declare variables that require class scope
  private int redColor, blueColor, greenColor;
  private Color c;
  private String currColors;
  Rectangle colorPreview;

  //Constructor
  public ColorSelector() {
    //instantiate variables
    redColor = 0;
    blueColor = 0;
    greenColor = 0;
    currColors = "";

    // Create sliders for adjusting red, green, and blue values
    Slider redSlider = new Slider(0, 255, 0);
    Slider greenSlider = new Slider(0, 255, 0);
    Slider blueSlider = new Slider(0, 255, 0);

    // Create labels for the sliders
    Label redLabel = new Label("Red:");
    Label greenLabel = new Label("Green:");
    Label blueLabel = new Label("Blue:");

    // Create a preview rectangle to display the selected color
    colorPreview = new Rectangle(100, 100);
    colorPreview.setStroke(Color.BLACK);
    
    // TODO: set default color to last 6 digits of your student number
    c = Color.web(defaultColor);
    colorPreview.setFill(c);
    currColors = "#" + defaultColor;
    
    // ----- Event Handling -----
    
    // Adding Listener to red slider
    redSlider.valueProperty().addListener(
        new ChangeListener<Number>() {
        public void changed(ObservableValue <? extends Number >
                  observable, Number oldValue, Number newValue)
        {
            System.out.println("red: " + newValue.intValue()); //debug
            
            //Set new color
            redColor = newValue.intValue();
            c = Color.rgb(redColor, greenColor, blueColor);
            colorPreview.setFill(c);
            
            //update string for label
            currColors = "rgb:" + redColor + "," + greenColor + "," + blueColor;
        }
    });
    
    // Adding Listener to green slider
    greenSlider.valueProperty().addListener(
        new ChangeListener<Number>() {
        public void changed(ObservableValue <? extends Number >
                  observable, Number oldValue, Number newValue)
        {
            System.out.println("green: " + newValue.intValue());//debug
            
            //Set new color
            greenColor = newValue.intValue();
            c = Color.rgb(redColor, greenColor, blueColor);
            colorPreview.setFill(c);
            
            //update string for label
            currColors = "rgb:" + redColor + "," + greenColor + "," + blueColor; 
        }
    });
    
    // Adding Listener to blue slider
    blueSlider.valueProperty().addListener(
        new ChangeListener<Number>() {
        public void changed(ObservableValue <? extends Number >
                  observable, Number oldValue, Number newValue)
        {
            System.out.println("blue: " + newValue.intValue());//debug
            
            //Set new color
            blueColor = newValue.intValue();
            c = Color.rgb(redColor, greenColor, blueColor);
            colorPreview.setFill(c);
            
            //update string for label
            currColors = "rgb:" + redColor + "," + greenColor + "," + blueColor; 
        }
    });
    
    // ----- Layout -----

    // Create an HBox to hold the red slider and label
    HBox redBox = new HBox(redLabel, redSlider);
    redBox.setSpacing(10);

    // Create an HBox to hold the green slider and label
    HBox greenBox = new HBox(greenLabel, greenSlider);
    greenBox.setSpacing(10);

    // Create an HBox to hold the blue slider and label
    HBox blueBox = new HBox(blueLabel, blueSlider);
    blueBox.setSpacing(10);

    // Add the color preview and sliders to the VBox
    getChildren().addAll(colorPreview, redBox, greenBox, blueBox);
    setSpacing(10);
    setPadding(new Insets(10));
  }
  
  // ---------- METHODS ---------- 
  
  // ------ getters ------ 
  public String getColorString() {
	  String s = currColors;
	  return s;
  }
  public int getRedValue() {
	  int r = redColor;
	  return r;
  }
  public int getBlueValue() {
	  int b = blueColor;
	  return b;
  }
  public int getGreenValue() {
	  int g = greenColor;
	  return g;
  }
  public Color getColor() {
	  Color color = c;
	  return color;
  }
  
  public String getHex() {
		String s = "#000000";
		if (!(c.toString().substring(2).isBlank())) {
			s = c.toString().substring(2);
		}
		return s;
  }
  
  // ------ setters ------ 
  public void setRed(int c) {
	  redColor = c;
	  updateColors();
  }
  public void setGreen(int c) {
	  greenColor = c;
	  updateColors();
  }
  public void setBlue(int c) {
	  blueColor = c;
	  updateColors();
  }
  public void setColors(int r, int g, int b) {
	  updateColors(r,g,b);
  }
  
  // ------ private internal methods ------ 
  private void updateColors() {
	  colorPreview.setFill(Color.rgb(redColor, greenColor, blueColor));
  }
  private void updateColors(int r, int g, int b) {
	  redColor = r;
	  greenColor = g;
	  blueColor = b;
	  colorPreview.setFill(Color.rgb(redColor, greenColor, blueColor));
  }
}
