// Package Deceleration
package com.jackljones.www;

// Imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Main Class Entry Point To Program
public class BookRecordSystem extends Application {

	// To run: --module-path "Modules\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml
	
	// See user-guide for more details
	
	
    /*
     * Description - This is the entry point to the program
     */


    // Main Methods
    public static void main(String[] args) {
        // Starts Application
        launch(args);
    }

    // GUI Entry Point
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Manages the Scenes
        SceneBuilder sceneBuilder = new SceneBuilder(primaryStage);

        primaryStage.setTitle("Book Record System");
        primaryStage.setResizable(false);

        // Default Width and Height
        int width = 300;
        int height = 400;

        primaryStage.setWidth(width);
        primaryStage.setHeight(height);

        // Set to home scene
        Scene sceneHome = sceneBuilder.getSceneHome();

        // Set Stage
        primaryStage.setScene(sceneHome);
        primaryStage.show();

    }
}
