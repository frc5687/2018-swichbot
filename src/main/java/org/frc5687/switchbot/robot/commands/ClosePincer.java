package org.frc5687.switchbot.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.frc5687.switchbot.robot.subsystems.Pincer;

/**
 * Created by Ben Bernard on 6/5/2018.
 */
public class ClosePincer extends Command {
    private Pincer _pincer;

    public ClosePincer(Pincer pincer) {
        _pincer = pincer;
        requires(_pincer);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {
        _pincer.close();
    }
}
