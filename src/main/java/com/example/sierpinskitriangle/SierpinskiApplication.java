package com.example.sierpinskitriangle;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SierpinskiApplication extends Application {

    private int w, h;
    private int s;//longitud de los lados del triangulo

    private int limit;
    private int counter;

    private Point2D[] triangle;
    //private ArrayList <Point2D> points;
    private Point2D lastPoint;

    /*OPTIMIZACIÓN: en lugar de renderizar cada vez todos los puntos que se han generado hasta el momento, se guarda solo el último
    * punto y se dibuja. En render se evita limpiar el canvas para dejar todos los puntos dibujados anteriormente y que en cada
    * llamada solo se dibuje el último punto encima de lo que ya había, en lugar de dibujarlos todos cada vez y disminuir el rendimiento*/

    @Override
    public void start(Stage stage) throws IOException {
        w = 800;
        h = 800;
        s = 600;

        limit = 10000;
        counter = 0;

        Canvas canvas = new Canvas(w,h);
        GraphicsContext g = canvas.getGraphicsContext2D();

        Pane pane = new Pane(canvas);
        Scene scene = new Scene(pane, w, h);
        stage.setScene(scene);
        stage.show();

        loop(g);
    }

    //loop de update/render
    private void loop(GraphicsContext g) {
        load();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
                render(g);
            }
        };

        timer.start();
    }

    private void load() {
        //crea el triángulo y el arreglo de puntos
        triangle = generateEquilateralTriangle(s);
        //points = new ArrayList<>();

        //genera el primer punto aleatoriamente
        lastPoint = getRandomPointInTriangle();
    }

    private Point2D getRandomPointInTriangle() {
        double r1 = Math.random();
        double r2 = Math.random();

        Point2D A = triangle[0];
        Point2D B = triangle[1];
        Point2D C = triangle[2];

        double px = (1 - Math.sqrt(r1)) * A.getX() + (Math.sqrt(r1) * (1-r2)) * B.getX() + (r2 * Math.sqrt(r1)) * C.getX();
        double py = (1 - Math.sqrt(r1)) * A.getY() + (Math.sqrt(r1) * (1-r2)) * B.getY() + (r2 * Math.sqrt(r1)) * C.getY();

        return new Point2D(px, py);
    }

    private void update() {
        if (counter > limit) return;

        /*por cada iteración selecciona una esquina del triángulo aleatoriamente
        y obtiene el punto medio entre ella y el anterior punto creado*/
        Point2D random = getRandomPoint();
        //Point2D last = points.get(points.size() - 1);
        //points.add(getMidpoint(random, last));
        lastPoint = getMidpoint(random, lastPoint);

        counter++;
    }

    private Point2D getRandomPoint() {
        int p = (int) (Math.random() * 3);
        return triangle[p];
    }

    private Point2D getMidpoint(Point2D p1, Point2D p2) {
        return new Point2D(
                (p1.getX() + p2.getX()) / 2,
                (p1.getY() + p2.getY()) / 2
        );
    }

    private void render(GraphicsContext g) {
        //g.clearRect(0, 0, w, h);

        g.setStroke(Color.BLACK);
        g.setFill(new Color(0.5, 0, 1, 1));

        double[] xPoints = {triangle[0].getX(), triangle[1].getX(), triangle[2].getX()};
        double[] yPoints = {triangle[0].getY(), triangle[1].getY(), triangle[2].getY()};

        g.strokePolygon(xPoints, yPoints, 3);
        renderPoints(g, 1);

        //dibuja el indicador de que ya llegó al limite
        if (counter > limit) {
            g.setFill(Color.RED);
            g.fillRect(20, 20, 20, 20);
        }
    }

    private void renderPoints(GraphicsContext g, double width) {
        /*for (Point2D p: points) {
            g.fillRect(
                    p.getX() - width / 2,
                    p.getY() - width / 2,
                    width, width);
        }*/

        g.fillRect(
                lastPoint.getX() - width / 2,
                lastPoint.getY() - width / 2,
                width, width);
    }


    public Point2D[] generateEquilateralTriangle(double side) {
        if (side > w || side > h) {
            return new Point2D[] {
                    new Point2D(0,0),
                    new Point2D(0,0),
                    new Point2D(0,0)};
        }

        double height = Math.round( (Math.sqrt(3.0) / 2.0) * side );

        Point2D[] triangle = new Point2D[3];
        triangle[0] = new Point2D((w-side)/2, (h-height)/2 + height);
        triangle[1] = new Point2D((w-side)/2 + side, (h-height)/2 + height);
        triangle[2] = new Point2D((1.0) * w / 2, (h-height)/2 );

        return triangle;
    }
}
