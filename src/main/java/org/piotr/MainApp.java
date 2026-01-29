package org.piotr;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Button startStopButton;

    private final PomodoroTimer pomodoroTimer = new PomodoroTimer();

    private final Clock clock = new Clock();
    @Override
    public void start(Stage stage) {
        pomodoroTimer.initializeTimer();

        startStopButton = new Button("Start Pomodoro");
        startStopButton.setOnAction(event -> toggleStartStop());

        pomodoroTimer.secondsRemainingProperty().addListener((obs, oldVal, newVal) -> updateUI(newVal.intValue()));

        updateUI(pomodoroTimer.secondsRemainingProperty().get());

        VBox root = new VBox(20, clock.clockContainer, startStopButton);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30;");

        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Pomodoro Flow");
        stage.setScene(scene);
        stage.show();
    }

    private void updateUI(int remainingSeconds) {
        clock.updateClock(remainingSeconds, pomodoroTimer.getCurrentTimerDuration());
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