package edu.bloomu.lightsout;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This JavaFx Application is a GUI representing the game LightsOut.
 *
 * @author Lennox Haynes
 */

public class LightsOutGUI extends Application {

    private static final LightsOut game = new LightsOut();
    private final Button[][] buttons = new Button[game.ROWS][game.COLS]; // Game Grid

    @Override
    public void start(Stage primaryStage) {

        GridPane root = new GridPane();
        Scene scene = new Scene(root);

        // Stores the amount of moves the user have made in the LightsOutGame.
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setStyle("-fx-border-color: GRAY; -fx-border-width: 2");
        flowPane.setPadding(new Insets(15));

        // Displays the amount of moves the player has made during the game
        Label moveAmount = new Label("Move Count: " + game.getMoves());
        moveAmount.setPrefWidth(flowPane.getMaxWidth());
        moveAmount.setStyle("-fx-font-size: 24px; -fx-font-weight:bold; ");
        flowPane.getChildren().add(moveAmount);

        /*
        Each button (Lights) appears as Black (OFF) and Yellow (ON).
        Clicking on a button causes it and all neighboring buttons light to toggle
        between on and off.
         */
        GridPane gridPane = new GridPane();

        // Create the buttons and register event handlers.
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new Button(); // Creates a button
                buttons[i][j].setPrefSize(120, 120); // Size of button
                // Handler for button
                int row = i;
                int column = j;
                buttons[i][j].setOnAction(event -> {
                    game.move(row, column); // Moves

                    setLights(); // Updates the GUI to the current state of the game Grid
                    moveAmount.setText("Move Count: " + game.getMoves());

                    // Displays a message when the game is over
                    if (!game.lightsOut()) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("GAME OVER");
                        alert.setHeaderText("You have solved the puzzle!");
                        alert.setContentText("Goodbye!");
                        primaryStage.setAlwaysOnTop(false);
                        alert.showAndWait();
                        Platform.exit();
                    }
                });
                gridPane.add(buttons[i][j], i, j); // Adds the button to the GridPane
            }
        }
        setLights(); // Styles the buttons

        // Adds everything to the scene
        root.add(gridPane, 0, 0);
        root.add(flowPane, 0, 1);

        primaryStage.setScene(scene);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("LightsOut!");
        primaryStage.show();

    }

    /**
     * Sets the color of each button depending on the corresponding boolean value:
     * - Yellow: If true
     * - Black: If False
     */
    private void setLights() {
        // Border of the Lights
        String style = "-fx-border-color: GRAY; -fx-border-width: 1;";

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                if (game.lightAt(i, j)) {
                    // Sets Color when Light is on
                    buttons[i][j].setStyle(style + "-fx-background-color: Yellow");
                }
                else {
                    // Sets color when Light is off
                    buttons[i][j].setStyle(style + "-fx-background-color: Black");
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
