package org.frc5687.switchbot.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.frc5687.switchbot.robot.commands.ClosePincer;
import org.frc5687.switchbot.robot.commands.Eject;
import org.frc5687.switchbot.robot.commands.OpenPincer;
import org.frc5687.switchbot.robot.utils.AxisButton;
import org.frc5687.switchbot.robot.utils.Gamepad;
import org.frc5687.switchbot.robot.utils.Helpers;

import static org.frc5687.switchbot.robot.utils.Helpers.applyDeadband;
import static org.frc5687.switchbot.robot.utils.Helpers.applySensitivityFactor;

public class OI {

    protected Gamepad _driverGamepad;
    protected Gamepad _operatorGamepad;

    private Button _driverLeftBumper;
    private Button _driverRightBumper;
    private Button _operatorLeftBumper;
    private Button _operatorRightBumper;

    private Button _operatorLeftTrigger;
    private Button _operatorRightTrigger;

    private Button _driverLeftTrigger;
    private Button _driverRightTrigger;


    public OI() {
        _driverGamepad = new Gamepad(0);
        _operatorGamepad = new Gamepad(1);

        _driverLeftBumper = new JoystickButton(_driverGamepad, Gamepad.Buttons.LEFT_BUMPER.getNumber());
        _driverRightBumper = new JoystickButton(_driverGamepad, Gamepad.Buttons.RIGHT_BUMPER.getNumber());

        _operatorLeftBumper = new JoystickButton(_operatorGamepad, Gamepad.Buttons.LEFT_BUMPER.getNumber());
        _operatorRightBumper = new JoystickButton(_operatorGamepad, Gamepad.Buttons.RIGHT_BUMPER.getNumber());

        _driverLeftTrigger = new AxisButton(_driverGamepad, Gamepad.Axes.LEFT_TRIGGER.getNumber(), Constants.OI.AXIS_BUTTON_THRESHHOLD);
        _driverRightTrigger = new AxisButton(_driverGamepad, Gamepad.Axes.RIGHT_TRIGGER.getNumber(), Constants.OI.AXIS_BUTTON_THRESHHOLD);

        _operatorLeftTrigger = new AxisButton(_operatorGamepad, Gamepad.Axes.LEFT_TRIGGER.getNumber(), Constants.OI.AXIS_BUTTON_THRESHHOLD);
        _operatorRightTrigger = new AxisButton(_operatorGamepad, Gamepad.Axes.RIGHT_TRIGGER.getNumber(), Constants.OI.AXIS_BUTTON_THRESHHOLD);


    }

    public void initializeButtons(Robot robot) {
        _driverLeftTrigger.whenPressed(new OpenPincer(robot.getPincer()));
        _driverLeftTrigger.whenReleased(new ClosePincer(robot.getPincer()));

        _driverRightTrigger.whenPressed(new Eject(robot.getPincer()));

        _operatorLeftTrigger.whenPressed(new OpenPincer(robot.getPincer()));
        _operatorLeftTrigger.whenReleased(new ClosePincer(robot.getPincer()));

        _operatorRightTrigger.whenPressed(new Eject(robot.getPincer()));
    }

    public double getDriveSpeed() {
        double speed = -getSpeedFromAxis(_driverGamepad, Gamepad.Axes.LEFT_Y.getNumber());
        speed = applyDeadband(speed, Constants.DriveTrain.DEADBAND);
        return applySensitivityFactor(speed, Constants.DriveTrain.SENSITIVITY);

    }

    public double getDriveRotation() {
        double speed = getSpeedFromAxis(_driverGamepad, Gamepad.Axes.LEFT_X.getNumber());
        speed = applyDeadband(speed, Constants.DriveTrain.DEADBAND);
        return applySensitivityFactor(speed, Constants.DriveTrain.ROTATION_SENSITIVITY);
    }

    public double getLeftSpeed() {
        double speed = -getSpeedFromAxis(_driverGamepad, Gamepad.Axes.LEFT_Y.getNumber());
        speed = Helpers.applyDeadband(speed, Constants.DriveTrain.DEADBAND);
        return Helpers.applySensitivityFactor(speed, Constants.DriveTrain.SENSITIVITY);
    }


    public double getRightSpeed() {
        double speed = -getSpeedFromAxis(_driverGamepad, Gamepad.Axes.RIGHT_Y.getNumber());
        speed = Helpers.applyDeadband(speed, Constants.DriveTrain.DEADBAND);
        return Helpers.applySensitivityFactor(speed, Constants.DriveTrain.SENSITIVITY);
    }


    protected double getSpeedFromAxis(Joystick gamepad, int axisNumber) {
        return gamepad.getRawAxis(axisNumber);
    }


    public double getArmSpeed() {
        double speed = -getSpeedFromAxis(_operatorGamepad, 5);
        speed = applyDeadband(speed, Constants.Arm.DEADBAND);
        return applySensitivityFactor(speed, Constants.Arm.SENSITIVITY);
    }
}
