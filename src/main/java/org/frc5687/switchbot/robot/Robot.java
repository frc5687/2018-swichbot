package org.frc5687.switchbot.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.frc5687.switchbot.robot.subsystems.Arm;
import org.frc5687.switchbot.robot.subsystems.DriveTrain;
import org.frc5687.switchbot.robot.subsystems.Pincer;
import org.frc5687.switchbot.robot.subsystems.Shifter;
import org.frc5687.switchbot.robot.utils.PDP;

public class Robot extends TimedRobot {

    private static Robot _instance;

    private DriveTrain _drivetrain;
    private Pincer _pincer;
    private Arm _arm;
    private Shifter _shifter;

    private OI _oi;
    private PDP _pdp;

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

        _pdp = new PDP();
        _oi = new OI();
        _drivetrain = new DriveTrain(_instance);
        _pincer = new Pincer(_instance);
        _arm = new Arm(_instance);
        _shifter = new Shifter(_instance);

        _oi.initializeButtons(_instance);
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

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        updateDashboard();
    }

    @Override
    public void disabledPeriodic() {
        updateDashboard();
    }

    public void updateDashboard() {
        _pdp.updateDashboard();
        _arm.updateDashboard();
    }

    public OI getOI() { return _oi; }
    public DriveTrain getDriveTrain() { return _drivetrain; }
    public Pincer getPincer() { return _pincer; }
    public PDP getPDP() { return _pdp; }
    public Arm getArm() { return _arm; }
    public Shifter getShifter() { return _shifter; }
}
