package org.frc5687.switchbot.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc5687.switchbot.robot.Constants;
import org.frc5687.switchbot.robot.Robot;

import java.awt.*;

/**
 * Created by Ben Bernard on 2/2/2018.
 */
public class AutoGroup extends CommandGroup {
    public AutoGroup(int position, int switchSide, Robot robot) {
        super();
        int switchFactor = switchSide * (position );


        addSequential(new ClosePincer(robot.getPincer()));


        SmartDashboard.putString("Auto/Mode", "Switch Only");
        switch(switchFactor) {
            case -Constants.AutoChooser.Position.FAR_LEFT: // Position 1, left side
                buildAutoCross(robot);
                break;
            case Constants.AutoChooser.Position.FAR_LEFT:  // Position 1, right side
                buildAutoCross(robot);
                break;
            case -Constants.AutoChooser.Position.MID_LEFT: // Position 2, left side:
                straightSwitch(robot);
                break;
            case Constants.AutoChooser.Position.MID_LEFT: // Position 2, right side
                buildAutoCross(robot);
                break;
            case -Constants.AutoChooser.Position.CENTER: // Position 3, left side
                break;
            case Constants.AutoChooser.Position.CENTER: // Position 3, right side
                break;
            case -Constants.AutoChooser.Position.NEAR_RIGHT: // Position 4, left side
                buildAutoCross(robot);
                break;
            case Constants.AutoChooser.Position.NEAR_RIGHT: // Position 4, right side
                straightSwitch(robot);
                break;
            case -Constants.AutoChooser.Position.MID_RIGHT: // Position 5, left side
            case Constants.AutoChooser.Position.MID_RIGHT: // Position 5, right side
                buildAutoCross(robot);
                break;
            case -Constants.AutoChooser.Position.FAR_RIGHT: // Position 6, left side
                buildAutoCross(robot);
                break;
            case Constants.AutoChooser.Position.FAR_RIGHT: // Position 6, left side
                buildAutoCross(robot);
                break;
            default:
                buildAutoCross(robot);
                break;
        }
    }

    private void buildAutoCross(Robot robot) {
        addSequential(new AutoDrive(robot.getDriveTrain(), robot.getIMU(), 48, .60, true, true, 5000, "AutoCross"));
        return;
    }

    private void straightSwitch(Robot robot) {
        addParallel(new MoveArmToSetpoint(robot.getArm(), null, Constants.Arm.FRONT_SWITCH));
        addSequential(new AutoDrive(robot.getDriveTrain(), robot.getIMU(), 48, .60, true, true, 5000, "StraightSwitch"));
        addSequential(new Eject(robot.getPincer()));
        return;
    }

}