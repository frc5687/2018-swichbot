package org.frc5687.switchbot.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.frc5687.switchbot.robot.commands.ClosePincer;
import org.frc5687.switchbot.robot.commands.OpenPincer;
import org.frc5687.switchbot.robot.utils.Gamepad;

import static org.frc5687.switchbot.robot.utils.Helpers.applyDeadband;
import static org.frc5687.switchbot.robot.utils.Helpers.applySensitivityFactor;

public class OI {

    private Gamepad _driverGamepad;
    private Gamepad _operatorGamepad;

    private JoystickButton _driverLeftBumper;
    private JoystickButton _driverRightBumper;
    private JoystickButton _operatorLeftBumper;
    private JoystickButton _operatorRightBumper;


    public OI() {
        _driverGamepad = new Gamepad(0);
        _operatorGamepad = new Gamepad(1);

        _driverLeftBumper = new JoystickButton(_driverGamepad, Gamepad.Buttons.LEFT_BUMPER.getNumber());
        _driverRightBumper = new JoystickButton(_driverGamepad, Gamepad.Buttons.RIGHT_BUMPER.getNumber());
        _operatorLeftBumper = new JoystickButton(_operatorGamepad, Gamepad.Buttons.LEFT_BUMPER.getNumber());
        _operatorRightBumper = new JoystickButton(_operatorGamepad, Gamepad.Buttons.RIGHT_BUMPER.getNumber());


    }

    public void initializeButtons(Robot robot) {
        _driverLeftBumper.whenPressed(new OpenPincer(robot.getPincer()));
        _driverRightBumper.whenPressed(new ClosePincer(robot.getPincer()));
        _operatorLeftBumper.whenPressed(new OpenPincer(robot.getPincer()));
        _operatorRightBumper.whenPressed(new ClosePincer(robot.getPincer()));
    }

    public double getDriveSpeed() {
        double speed = -getSpeedFromAxis(_driverGamepad, 1);
        speed = applyDeadband(speed, Constants.DriveTrain.DEADBAND);
        return applySensitivityFactor(speed, Constants.DriveTrain.SENSITIVITY);

    }

    public double getDriveRotation() {
        double speed = getSpeedFromAxis(_driverGamepad, 0);
        speed = applyDeadband(speed, Constants.DriveTrain.DEADBAND);
        return applySensitivityFactor(speed, Constants.DriveTrain.SENSITIVITY);

    }

    private double getSpeedFromAxis(Joystick gamepad, int axisNumber) {
        return gamepad.getRawAxis(axisNumber);
    }


    public double getArmSpeed() {
        double speed = -getSpeedFromAxis(_driverGamepad, 5);
        speed = applyDeadband(speed, Constants.Arm.DEADBAND);
        return applySensitivityFactor(speed, Constants.Arm.SENSITIVITY);
    }
}
