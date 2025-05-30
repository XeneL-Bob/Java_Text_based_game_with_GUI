// JavaFX GUI Entry Point: MiniDungeonApp.java

package ui;

import core.*;
import utils.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MiniDungeonApp extends Application {

    private GameEngine engine;
    private TextArea logArea;
    private Label scoreLabel, stepLabel;
    private GridPane mapGrid;
    private HBox heartBox;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("MiniDungeon Game");
        engine = new GameEngine();

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Top Info Panel
        HBox statsBox = new HBox(20);
        heartBox = new HBox(2);
        scoreLabel = new Label();
        stepLabel = new Label();
        statsBox.getChildren().addAll(heartBox, scoreLabel, stepLabel);
        root.getChildren().add(statsBox);

        // Map
        mapGrid = new GridPane();
        mapGrid.setHgap(1);
        mapGrid.setVgap(1);
        root.getChildren().add(mapGrid);

        // Movement Buttons
        HBox controlBox = new HBox(10);
        controlBox.setAlignment(Pos.CENTER);

        Button up = new Button("↑");
        Button down = new Button("↓");
        Button left = new Button("←");
        Button right = new Button("→");

        up.setOnAction(e -> makeMove("u"));
        down.setOnAction(e -> makeMove("d"));
        left.setOnAction(e -> makeMove("l"));
        right.setOnAction(e -> makeMove("r"));

        controlBox.getChildren().addAll(left, up, down, right);
        root.getChildren().add(controlBox);

        // Action Buttons
        HBox actionBox = new HBox(10);
        Button saveBtn = new Button("Save");
        Button loadBtn = new Button("Load");
        Button helpBtn = new Button("Help");
        Button topBtn = new Button("Top Scores");

        saveBtn.setOnAction(e -> SaveLoadManager.save(engine));
        loadBtn.setOnAction(e -> {
            GameEngine loaded = SaveLoadManager.load();
            if (loaded != null) {
                engine = loaded;
                updateMap();
                updateStats();
                log("Game loaded successfully.");
            }
        });
        helpBtn.setOnAction(e -> log("Use arrows to move. Reach the ladder to advance. Avoid traps and mutants."));
        topBtn.setOnAction(e -> ScoreManager.displayTopScores());

        actionBox.getChildren().addAll(saveBtn, loadBtn, helpBtn, topBtn);
        root.getChildren().add(actionBox);

        // Log Area
        logArea = new TextArea();
        logArea.setPrefRowCount(5);
        logArea.setEditable(false);
        root.getChildren().add(logArea);

        updateStats();
        updateMap();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void makeMove(String dir) {
        String result = engine.movePlayer(dir);
        updateMap();
        updateStats();
        log(result);

        if (engine.isGameOver()) {
            if (engine.isGameWon()) {
                log("YOU WIN!");
                ScoreManager.tryAddScore(engine.getPlayer().getScore());
            } else {
                log("Game Over. You lose.");
            }
        }
    }

    private void updateStats() {
        updateHearts(engine.getPlayer().getHP());
        scoreLabel.setText("Score: " + engine.getPlayer().getScore());
        stepLabel.setText("Steps: " + engine.getPlayer().getSteps());
    }

    private void updateHearts(int hp) {
        heartBox.getChildren().clear();
        Image heartImage = new Image(getClass().getResource("/images/heart.png").toExternalForm(), 20, 20, true, true);
        for (int i = 0; i < hp; i++) {
            heartBox.getChildren().add(new ImageView(heartImage));
        }
    }

    private void updateMap() {
        mapGrid.getChildren().clear();
        char[][] map = engine.getMap().getGrid();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                String imageFile = switch (map[row][col]) {
                    case 'P' -> "/images/player.png";
                    case 'G' -> "/images/gold.png";
                    case 'T' -> "/images/trap.png";
                    case 'M' -> "/images/mutantmelee.png";
                    case 'R' -> "/images/mutantranged.png";
                    case 'L' -> "/images/ladder.png";
                    case 'E' -> "/images/entrytile.png";
                    case '#' -> "/images/Wall.png";
                    default -> "/images/emptytile.png";
                };

                ImageView tile = new ImageView(new Image(getClass().getResource(imageFile).toExternalForm(), 32, 32, true, true));
                mapGrid.add(tile, col, row);
            }
        }
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
