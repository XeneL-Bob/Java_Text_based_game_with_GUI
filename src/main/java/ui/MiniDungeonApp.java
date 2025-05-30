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

import java.net.URL;

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
        root.setAlignment(Pos.TOP_CENTER);

        // Background image
        try {
            URL bgUrl = getClass().getResource("/images/background.png");
            if (bgUrl != null) {
                BackgroundImage bgImage = new BackgroundImage(
                        new Image(bgUrl.toExternalForm(), 800, 600, false, true),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT
                );
                root.setBackground(new Background(bgImage));
            }
        } catch (Exception e) {
            System.out.println("Background image not found.");
        }

        // Stats and hearts
        HBox statsBox = new HBox(20);
        heartBox = new HBox(5);
        scoreLabel = new Label();
        stepLabel = new Label();
        statsBox.getChildren().addAll(new Label("HP:"), heartBox, scoreLabel, stepLabel);
        root.getChildren().add(statsBox);

        // Map grid
        mapGrid = new GridPane();
        mapGrid.setHgap(5);
        mapGrid.setVgap(5);
        updateMap();
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

        // Log area
        logArea = new TextArea();
        logArea.setPrefRowCount(5);
        logArea.setEditable(false);
        root.getChildren().add(logArea);

        updateStats();

        primaryStage.setScene(new Scene(root));
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
        updateHearts();
        scoreLabel.setText("Score: " + engine.getPlayer().getScore());
        stepLabel.setText("Steps: " + engine.getPlayer().getSteps());
    }

    private void updateHearts() {
        heartBox.getChildren().clear();
        int hp = engine.getPlayer().getHP();
        for (int i = 0; i < hp; i++) {
            ImageView heart = new ImageView(safeLoad("/images/heart.png"));
            heart.setFitHeight(20);
            heart.setFitWidth(20);
            heartBox.getChildren().add(heart);
        }
    }

    private void updateMap() {
        mapGrid.getChildren().clear();
        char[][] map = engine.getMap().getGrid();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                char symbol = map[row][col];
                Image image;

                switch (symbol) {
                    case 'P': image = safeLoad("/images/player.png"); break;
                    case 'M': image = safeLoad("/images/mutantmelee.png"); break;
                    case 'R': image = safeLoad("/images/mutantranged.png"); break;
                    case 'T': image = safeLoad("/images/trap.png"); break;
                    case 'G': image = safeLoad("/images/gold.png"); break;
                    case 'L': image = safeLoad("/images/ladder.png"); break;
                    case 'E': image = safeLoad("/images/emptytile.png"); break;
                    case 'W': image = safeLoad("/images/Wall.png"); break;
                    case 'H': image = safeLoad("/images/heart.png"); break;
                    default:  image = safeLoad("/images/emptytile.png"); break;
                }

                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(32);
                imageView.setFitHeight(32);
                mapGrid.add(imageView, col, row);
            }
        }
    }

    private Image safeLoad(String path) {
        URL url = getClass().getResource(path);
        if (url == null) {
            System.err.println("Missing resource: " + path);
            return new Image(getClass().getResource("/images/emptytile.png").toExternalForm());
        }
        return new Image(url.toExternalForm());
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
