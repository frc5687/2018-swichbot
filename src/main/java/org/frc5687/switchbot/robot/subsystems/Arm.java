package org.frc5687.switchbot.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.frc5687.switchbot.robot.Constants;
import org.frc5687.switchbot.robot.Robot;
import org.frc5687.switchbot.robot.RobotMap;
import org.frc5687.switchbot.robot.commands.DriveArm;
import org.frc5687.switchbot.robot.utils.PDP;

/**
 * Created by Ben Bernard on 6/5/2018.
 */
public class Arm extends Subsystem {
    private Robot _robot;
    private VictorSP _motor;
    private PDP _pdp;

    private int _direction = 0;
    private boolean _atFrontLimit = false;
    private boolean _atRearLimit = false;

    private long _capTimeout = 0;
    private int _capDirection = 0;

    public Arm(Robot robot) {
        _robot = robot;
        _motor = new VictorSP(RobotMap.PWM.ARM_MOTOR);
        _pdp = robot.getPDP();
    }


    public void drive(double speed) {
        _direction = (int)Math.copySign(1, speed);

        // See if we are drawing too much power...
        if (_pdp.getCurrent(RobotMap.PDP.ARM_VICTOR) > Constants.Arm.CURRENT_CAP) {
            // If this is the start of an excess draw condition, record it
            if (_capTimeout==0) {
                _capTimeout = System.currentTimeMillis() + Constants.Arm.TIMEOUT_CAP;
                _capDirection = _direction;
            } else if (_capDirection !=_direction) {
                // If the direction has changed, reset!
                _capTimeout = 0;
                _capDirection = 0;
            } else if (System.currentTimeMillis() > Constants.Arm.TIMEOUT_CAP) {
                // Timeout exceeded...
                _atFrontLimit  = _direction>0;
                _atRearLimit = _direction<0;
            }
        } else {
            // Overdraw condition ended
            _capTimeout = 0;
            _capDirection = 0;
        }

        if (speed > 0 && atFrontLimit()) {
            speed = 0;
        } else if (speed < 0 && atRearLimit()) {
            speed = 0;
        }

        _motor.set(speed);
    }

    public boolean atFrontLimit() {
        return _atFrontLimit;
    }

    public boolean atRearLimit() {
        return _atRearLimit;
    }


    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveArm(this, _robot.getOI()));
    }
}
