package org.piotr;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Time;

public class MainApp extends Application {
    private Button startStopButton;
    private Boolean isRunning = false;
    private Scene scene;

    private int timelineCycleDurationInMillis = 50;
    private Timeline timeline;

    private int elapsedSeconds = 0;

    private int[] timerDurationsList = {100,100,1500,300,1500,300,1500,900};
    private int currentTimerIndex = 0;

    private Label elapsedSecondsLabel;


    @Override
    public void start(Stage stage) {
        timeline = new Timeline(new KeyFrame(Duration.millis(timelineCycleDurationInMillis), event -> updateElapsedSeconds()));
        timeline.setCycleCount(Timeline.INDEFINITE);

        startStopButton = new Button("Start Pomodoro");
        startStopButton.setOnAction(event -> {toggleStartStop();});

        elapsedSecondsLabel = new Label(String.valueOf(elapsedSeconds));

        VBox root = new VBox(10,elapsedSecondsLabel, startStopButton);
        root.setAlignment(Pos.CENTER);

        scene = new Scene(root, 400, 400);

        stage.setTitle("Pomodoro Flow");
        stage.setScene(scene);
        stage.show();
    }

    private void toggleStartStop() {
        if (!isRunning) {
            isRunning = true;
            startStopButton.setText("Stop Pomodoro");
            startTimer();
        } else if (isRunning) {
            isRunning = false;
            startStopButton.setText("Start Pomodoro");
            stopTimer();
        }
    }

    private void startTimer() {
        timeline.play();
    }

    private void stopTimer() {
        timeline.stop();
    }

    private void updateElapsedSeconds() {
        if (isRunning) {
            elapsedSeconds++;

            int currentTimerSeconds = timerDurationsList[currentTimerIndex];
            int remainingSeconds = currentTimerSeconds - elapsedSeconds;

            if (remainingSeconds <= 0) {
                incrementCurrentTimerIndex();
                elapsedSeconds = 0;
            } else {
                int minutes = remainingSeconds / 60;
                int seconds = remainingSeconds % 60;
                elapsedSecondsLabel.setText(String.format("%02d:%02d", minutes, seconds));
            }
        }
    }

    private void incrementCurrentTimerIndex() {
        currentTimerIndex++;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
