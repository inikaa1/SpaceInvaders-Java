/**
 * The changes made to this class include the following: -
 * difficulty level has been added and a new function has been created called setDiffLevel which sets the difficulty level for the model.
 * a new scene has been created to run the beginning part of the application where the user can select the difficulty level
 */

package invaders;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;
import invaders.Singleton.DifficultyLevel;
import javafx.scene.control.Label;

import java.util.Map;

public class App extends Application {

    // variable difficulty level
    private DifficultyLevel selectedDifficultyLevel;
    // create a new scene
    private Scene difficultyScene;
    private GameEngine model;
    private GameWindow window;

    //function for setting relevent difficulty level
    public void SetDiffLevel(DifficultyLevel selectedDifficultyLevel) {
        this.selectedDifficultyLevel = selectedDifficultyLevel;
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        //label for user guidence on what to do
        Label label = new Label("Press E for Easy, M for Medium, H for Hard");
        // creating the new scene
        difficultyScene = new Scene(label, 600, 800);

        // displaying the scene
        primaryStage.setTitle("Pre Window");
        primaryStage.setScene(difficultyScene);

        primaryStage.show();

        //keybaord input handler for easy, medium and hard
        difficultyScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // e for easy
                if (event.getCode().equals(KeyCode.E)) {
                    System.out.println("Easy");
                    SetDiffLevel(DifficultyLevel.Easy());
                    model = new GameEngine(selectedDifficultyLevel);
                    window = new GameWindow(model);
                }

                else if (event.getCode().equals(KeyCode.M)) {
                    // m for medium
                    System.out.println("Medium");
                    SetDiffLevel(DifficultyLevel.Medium());
                    model = new GameEngine(selectedDifficultyLevel);
                    window = new GameWindow(model);
                }

                else if (event.getCode().equals(KeyCode.H)) {
                    // setting h for hard
                    System.out.println("Hard");
                    SetDiffLevel(DifficultyLevel.Hard());
                    model = new GameEngine(selectedDifficultyLevel);
                    window = new GameWindow(model);
                }

                // running original game window after keybaord input
                window.run();

                primaryStage.setTitle("Space Invaders");
                primaryStage.setScene(window.getScene());
                primaryStage.show();
            }
        });
    }
}
