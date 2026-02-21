package com.example.sierpinskitriangle;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SierpinskiApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Canvas canvas = new Canvas(100,100);
        Group group = new Group(canvas);

        Scene scene = new Scene(group, 320, 240);
        stage.setScene(scene);
        stage.show();
    }
}
