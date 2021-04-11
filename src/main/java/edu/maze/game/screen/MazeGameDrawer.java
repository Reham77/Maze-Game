package edu.maze.game.screen;

import edu.maze.game.bot.BotPlay;
import edu.maze.game.builder.DFSMazeBuilder;
import edu.maze.game.builder.KruskalMazeBuilder;
import edu.maze.game.builder.MazeBuilder;
import edu.maze.game.builder.PrimMazeBuilder;
import edu.maze.game.entity.Board;
import edu.maze.game.entity.Cell;
import edu.maze.game.screen.HomePageScene.Algorithm;
import edu.maze.game.screen.HomePageScene.GameMode;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public abstract class MazeGameDrawer {
    private final int rows;
    private final int cols;
    private final GameMode gameMode;
    protected TranslateTransition translateTransition;
    protected int distance;
    protected Board board;

    public MazeGameDrawer(int rows, int cols, GameMode gameMode, Algorithm algorithm) {
        distance = getDistance(rows);
        this.rows = rows;
        this.cols = cols;
        this.gameMode = gameMode;
        board = generateBoard(algorithm);
    }

    private Board generateBoard(Algorithm algorithm) {
        MazeBuilder builder;
        switch (algorithm) {
            case DFS:
                builder = new DFSMazeBuilder(rows, cols);
                break;
            case PRIM:
                builder = new PrimMazeBuilder(rows, cols);
                break;
            case KRUSKAL:
            default:
                builder = new KruskalMazeBuilder(rows, cols);
                break;
        }
        return builder.build();
    }

    private int getDistance(int rows) {
        if (rows == 8)
            return 90;
        else if (rows == 14)
            return 50;
        else
            return 35;
    }

    private GridPane createMazeGrid() {
        GridPane gridPane = new GridPane();

        for (int x = 0; x <= rows; x++) {
            for (int y = 0; y <= cols; y++) {
                Group group = new Group();
                Line horizontal = new Line(0, 0, distance, 0);
                if (board.isWallRemoved(new Cell(x, y), Board.UP) || y == rows) {
                    horizontal.setStyle("-fx-stroke: #ff0000 ; -fx-stroke-width: 5");
                    horizontal.setOpacity(0);
                } else {
                    horizontal.setStyle("-fx-stroke: #0d3b12 ; -fx-stroke-width: 5");
                }

                Line vertical = new Line(0, 0, 0, distance);
                if (board.isWallRemoved(new Cell(x, y), Board.LEFT) || x == cols) {
                    vertical.setStyle("-fx-stroke: #ff0000 ; -fx-stroke-width: 5");
                    vertical.setOpacity(0);
                } else {
                    vertical.setStyle("-fx-stroke: #022d26 ; -fx-stroke-width: 5");
                }

                group.getChildren().addAll(vertical, horizontal);
                gridPane.add(group, y, x);
            }
        }
        return gridPane;
    }

    private ImageView getMouseImage() {
        Image image = new Image("file:src/main/resources/mouse.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setX((distance / 10) + 5);
        imageView.setY((distance / 10) + 5);
        imageView.setFitHeight(distance + 5);
        imageView.setFitWidth(distance + 5);
        imageView.setPreserveRatio(true);
        imageView.setFocusTraversable(true);

        return imageView;
    }

    private Button createExitButton(Stage stage) {
        Button exitButton = new Button("I Quit");
        exitButton.setMaxWidth(150);
        exitButton.setFocusTraversable(false);
        exitButton.setOnAction(event -> {
            stage.setScene(new HomePageScene().createHomePageScene(stage));
        });
        return exitButton;
    }

    protected void createAlertBox(Stage stage) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //to block any interactions with any other windows

        window.setTitle("Congratulations");
        VBox vBox = new VBox();
        Label label = new Label("You Won !");
        Button button = new Button("Play Again");
        button.setOnAction(event -> {
            window.close();
            stage.setScene(new HomePageScene().createHomePageScene(stage));
        });
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, button);
        Scene scene = new Scene(vBox , 300 , 150);
        window.setScene(scene);
        window.show();
    }

    protected abstract void sceneOnPressAction(Scene scene);

    protected abstract void moveBot(List<Integer> directionsList, Stage stage);

    public Scene create(Stage stage) {
        stage.setTitle("Maze Game");

        Group group = new Group();

        GridPane gridPane = createMazeGrid();
        ImageView imageView = getMouseImage();
        translateTransition = new TranslateTransition(Duration.millis(200), imageView);

        Scene scene = new Scene(group, 940, 780, Color.rgb(255, 250, 255));

        if (gameMode == GameMode.PLAY)
            sceneOnPressAction(scene);
        else {
            BotPlay play = new BotPlay(rows, cols);
            List<Integer> directionsList = play.getPath(board);
            moveBot(directionsList, stage);
        }

        Button exitButton = createExitButton(stage);

        Group buttons = new Group();
        buttons.getChildren().add(exitButton);
        gridPane.add(buttons, cols + 2, rows / 3, 1, 3);

        group.getChildren().addAll(imageView, gridPane);
        //         stage.setResizable(false);
        scene.getStylesheets().add("style.css");
        return scene;
    }
}
