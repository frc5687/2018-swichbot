package org.frc5687.switchbot.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.frc5687.switchbot.robot.OI;
import org.frc5687.switchbot.robot.subsystems.DriveTrain;

/**
 * Created by Ben Bernard on 6/4/2018.
 */
public class TankDrive extends Command {

    private OI _oi;
    private DriveTrain _driveTrain;

    public TankDrive(DriveTrain driveTrain, OI oi) {
        _driveTrain = driveTrain;
        _oi = oi;
        requires(_driveTrain);
    }


 @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {
        double leftSpeed = _oi.getLeftSpeed();
        double rightSpeed = _oi.getRightSpeed();

        _driveTrain.tankDrive(leftSpeed, rightSpeed, false);
    }
}
