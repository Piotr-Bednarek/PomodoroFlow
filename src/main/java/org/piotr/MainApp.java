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
    private Scene scene;

    private Label elapsedSecondsLabel =  new Label();

    private PomodoroTimer pomodoroTimer =  new PomodoroTimer();


    @Override
    public void start(Stage stage) {

        pomodoroTimer.initializeTimer();

        pomodoroTimer.secondsRemainingProperty().addListener((observable, oldValue, newValue) -> {
            updateLabel(newValue.intValue());
        });

        updateLabel(pomodoroTimer.secondsRemainingProperty().get());

        startStopButton = new Button("Start Pomodoro");
        startStopButton.setOnAction(event -> {toggleStartStop();});

        VBox root = new VBox(10,elapsedSecondsLabel, startStopButton);
        root.setAlignment(Pos.CENTER);

        scene = new Scene(root, 400, 400);

        stage.setTitle("Pomodoro Flow");
        stage.setScene(scene);
        stage.show();
    }

    private void updateLabel(int remainingSeconds) {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        elapsedSecondsLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void toggleStartStop() {
        if (!pomodoroTimer.isRunning()) {
            pomodoroTimer.startTimer();
            startStopButton.setText("Stop Pomodoro");
        } else {
            pomodoroTimer.stopTimer();
            startStopButton.setText("Start Pomodoro");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
