package org.piotr;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class PomodoroTimer {
    private Timeline timeline;

    private int[] timerDurationsList = {100,100,1500,300,1500,300,1500,900};
    private int currentTimerIndex = 0;

    private final int timelineCycleDurationInMillis = 50;

    private int elapsedSeconds = 0;

    private final IntegerProperty secondsRemaining = new SimpleIntegerProperty();

    public IntegerProperty secondsRemainingProperty() {
        return secondsRemaining;
    }

    void initializeTimer() {
        timeline = new Timeline(new KeyFrame(Duration.millis(timelineCycleDurationInMillis), event -> updateElapsedSeconds()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        secondsRemaining.setValue(timerDurationsList[currentTimerIndex]);
    }

    public boolean isRunning() {return timeline.getStatus() == Animation.Status.RUNNING;}

    public void stopTimer() {
        timeline.stop();
    }

    public void startTimer() {
        timeline.play();
    }

    private void updateElapsedSeconds() {
        if (isRunning()) {
            elapsedSeconds++;

            int currentTimerSeconds = timerDurationsList[currentTimerIndex];
            int remainingSeconds = currentTimerSeconds - elapsedSeconds;

            secondsRemaining.setValue(remainingSeconds);

            if (remainingSeconds <= 0) {
                incrementCurrentTimerIndex();
                elapsedSeconds = 0;
                secondsRemaining.setValue(timerDurationsList[currentTimerIndex]);
            }
        }
    }

    private void incrementCurrentTimerIndex() {
        currentTimerIndex = (currentTimerIndex + 1) % timerDurationsList.length;
    }

}
