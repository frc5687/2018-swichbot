package org.frc5687.switchbot.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.frc5687.switchbot.robot.Constants;
import org.frc5687.switchbot.robot.subsystems.Pincer;

/**
 * Created by Ben Bernard on 6/5/2018.
 */
public class Eject extends Command {
    private Pincer _pincer;
    private long _endtime = 0;

    public Eject(Pincer pincer) {
        _pincer = pincer;
        requires(_pincer);
    }

    @Override
    protected void initialize() {

        _endtime = System.currentTimeMillis() + Constants.Intake.EJECT_TIME_MILLIS;
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() > _endtime;
    }

    @Override
    protected void execute() {
        _pincer.setIntakeState(Pincer.IntakeState.EJECT);
        _pincer.runIntake();
    }

    @Override
    protected void end() {
        _pincer.setIntakeState(Pincer.IntakeState.HOLD);
    }
}
