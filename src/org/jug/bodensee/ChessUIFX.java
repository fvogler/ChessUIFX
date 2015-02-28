/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jug.bodensee;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author MICHELB
 */
public class ChessUIFX extends Application {
    private int style = 0;
    private Stage stage;
    
    private final Map<Integer, String> figures = new HashMap<Integer, String>();
    
//&#9812; = ♔
//&#9813; = ♕
//&#9814; = ♖
//&#9815; = ♗
//&#9816; = ♘
//&#9817; = ♙
//&#9818; = ♚
//&#9819; = ♛
//&#9820; = ♜
//&#9821; = ♝
//&#9822; = ♞
//&#9823; = ♟

    public ChessUIFX() {
        figures.put(0, "♜");
        figures.put(1, "♘");
        figures.put(2, "♗");
        figures.put(3, "♕");
        figures.put(4, "♔");
        figures.put(5, "♗");
        figures.put(6, "♘");
        figures.put(7, "♜");
        IntStream.range(8, 16).forEach(i -> figures.put(i, "♙"));  //weisser Bauer
        
        IntStream.range(48, 56).forEach(i -> figures.put(i, "♟")); //schwarzer Bauer
        figures.put(56, "♜");
        figures.put(57, "♞");
        figures.put(58, "♝");
        figures.put(59, "♛");
        figures.put(60, "♚");
        figures.put(61, "♝");
        figures.put(62, "♞");
        figures.put(63, "♜");
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.initStyle(StageStyle.DECORATED);
        double width = 80f;
        double heigth = 80f;
        StackPane root = new StackPane();
        Group group = new Group();
        root.getChildren().add(group);
        Scene scene = new Scene(root, width * 8, heigth * 8);
        
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Rectangle rectangle = new Rectangle(heigth, width);
                rectangle.setLayoutX(rectangle.getWidth() * x);
                rectangle.setLayoutY(rectangle.getHeight()* y);
                Color background = ((x+1 * y+1)%2 == 0 ? Color.GREY:Color.WHITE);
                rectangle.setFill(background);
                
                rectangle.widthProperty().bind(Bindings.divide(scene.widthProperty(), 8d));
                rectangle.heightProperty().bind(Bindings.divide(scene.heightProperty(), 8d));

                StackPane stackPane = new StackPane(rectangle);
                
                stackPane.layoutXProperty().bind(Bindings.multiply(x, Bindings.divide(scene.widthProperty(), 8d)));
                stackPane.layoutYProperty().bind(Bindings.multiply(y, Bindings.divide(scene.heightProperty(), 8d)));
                
                //find Figure
                String figure = figures.get(x + 8*y);
                if (null != figure) {
                    final Text text = new Text(figure);
                    text.setStyle("-fx-font-size:40");
                    
                    text.setOnMouseClicked(e -> {
                        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), text);
                        rotateTransition.setFromAngle(0d);
                        rotateTransition.setByAngle(360d);
                        rotateTransition.play();
                            });
                    
                    stackPane.getChildren().add(text);
                }

                group.getChildren().add(stackPane);
            }
        }
        
        primaryStage.setTitle("JUG Bodensee ChessUIFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
