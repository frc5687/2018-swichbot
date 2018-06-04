package org.frc5687.switchbot.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends TimedRobot {

    private static Robot _instance;

    public Robot() {
    }

    @Override
    public void startCompetition() {
        super.startCompetition();
    }

    @Override
    public void robotInit() {
        _instance = this;
        setPeriod(1 / Constants.CYCLES_PER_SECOND);
        LiveWindow.disableAllTelemetry();
    }

    @Override
    protected void loopFunc() {
        try {
            super.loopFunc();
        } catch (Throwable throwable) {
            DriverStation.reportError("Unhandled exception: " + throwable.toString(), throwable.getStackTrace());
            System.exit(1);
        }
    }


}
