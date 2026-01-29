package org.piotr;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineCap;


public class Clock {
    private final double RADIUS = 100;
    private final Arc progressArc = new Arc(RADIUS, RADIUS, RADIUS, RADIUS, 90, 0);
    private final Circle baseCircle = new Circle(RADIUS);
    private final Label remainingTimeLabel = new Label();
    public StackPane clockContainer;

    public Clock() {
        initializeClock();
    }


    private void initializeClock() {
        Pane canvas = new Pane();

        remainingTimeLabel.setStyle(
                "-fx-font-size: 42px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #34495e;" +
                "-fx-font-family: 'Roboto', 'Segoe UI Light';"
        );


        baseCircle.setCenterX(RADIUS);
        baseCircle.setCenterY(RADIUS);
        baseCircle.setFill(Color.TRANSPARENT);
        baseCircle.setStroke(Color.LIGHTGRAY);
        baseCircle.setStrokeWidth(20);

        progressArc.setType(ArcType.OPEN);
        progressArc.setStroke(Color.BLUE);
        progressArc.setStrokeWidth(25);
        progressArc.setStrokeLineCap(StrokeLineCap.ROUND);
        progressArc.setFill(Color.TRANSPARENT);

        canvas.setPrefSize(RADIUS * 2, RADIUS * 2);
        canvas.setMaxSize(RADIUS * 2, RADIUS * 2);
        canvas.getChildren().addAll(baseCircle, progressArc);
        clockContainer = new StackPane(canvas, remainingTimeLabel);
        clockContainer.setAlignment(Pos.CENTER);
    }

    public void updateClock(int remainingSeconds, int totalSeconds) {
        double progress = 1.0 - ((double) remainingSeconds / totalSeconds);

        if (progress == 0 ) progress = 0.0001;

        progressArc.setLength(-360 * progress);

        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        remainingTimeLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
}
