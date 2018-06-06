package org.frc5687.switchbot.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import jdk.management.resource.internal.inst.AbstractInterruptibleChannelRMHooks;
import org.frc5687.switchbot.robot.OI;
import org.frc5687.switchbot.robot.subsystems.Arm;

/**
 * Created by Ben Bernard on 6/5/2018.
 */
public class DriveArm extends Command {

    private Arm _arm;
    private OI _oi;

    public DriveArm(Arm arm,  OI oi) {
        _arm = arm;
        _oi = oi;
        requires(_arm);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {
        // Get the base speed from the throttle
        double speed = _oi.getArmSpeed();

        _arm.drive(speed);
    }
}
