package org.frc5687.switchbot.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc5687.switchbot.robot.commands.*;
import org.frc5687.switchbot.robot.subsystems.DriveTrain;
import org.frc5687.switchbot.robot.subsystems.Shifter;
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

    private Button _driverStartButton;
    private Button _driverBackButton;

    private Button _operatorYButton;
    private Button _operatorXButton;
    private Button _operatorBButton;
    private Button _operatorAButton;

    private Button _driverYButton;
    private Button _driverXButton;
    private Button _driverBButton;
    private Button _driverAButton;

    private Shifter.Gear _gear = Shifter.Gear.LOW;

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

        _driverStartButton = new JoystickButton(_operatorGamepad, Gamepad.Buttons.START.getNumber());
        _driverBackButton = new JoystickButton(_operatorGamepad, Gamepad.Buttons.BACK.getNumber());


        _operatorYButton = new JoystickButton(_operatorGamepad, Gamepad.Buttons.Y.getNumber());
        _operatorXButton = new JoystickButton(_operatorGamepad, Gamepad.Buttons.X.getNumber());
        _operatorAButton = new JoystickButton(_operatorGamepad, Gamepad.Buttons.A.getNumber());
        _operatorBButton = new JoystickButton(_operatorGamepad, Gamepad.Buttons.B.getNumber());


        _driverYButton = new JoystickButton(_driverGamepad, Gamepad.Buttons.Y.getNumber());
        _driverXButton = new JoystickButton(_driverGamepad, Gamepad.Buttons.X.getNumber());
        _driverAButton = new JoystickButton(_driverGamepad, Gamepad.Buttons.A.getNumber());
        _driverBButton = new JoystickButton(_driverGamepad, Gamepad.Buttons.B.getNumber());

    }

    public void initializeButtons(Robot robot) {
        _driverLeftTrigger.whenPressed(new OpenPincer(robot.getPincer()));
        _driverLeftTrigger.whenReleased(new ClosePincer(robot.getPincer()));

        _driverRightTrigger.whenPressed(new Eject(robot.getPincer()));

        _driverLeftBumper.whenPressed(new Shift(robot.getDriveTrain(), robot.getShifter(), Shifter.Gear.HIGH, false));
        _driverRightBumper.whenPressed(new Shift(robot.getDriveTrain(), robot.getShifter(), Shifter.Gear.LOW, false));

        _operatorLeftTrigger.whenPressed(new OpenPincer(robot.getPincer()));
        _operatorLeftTrigger.whenReleased(new ClosePincer(robot.getPincer()));

        _operatorRightTrigger.whenPressed(new Eject(robot.getPincer()));

        //_operatorLeftBumper.whenPressed(new SwitchDriveMode(robot.getDriveTrain(), DriveTrain.DriveMode.TANK));
        //_operatorRightBumper.whenPressed(new SwitchDriveMode(robot.getDriveTrain(), DriveTrain.DriveMode.ARCADE));

        _operatorYButton.whenPressed(new MoveArmToSetpoint(robot.getArm(), this, Constants.Arm.UP));
        _operatorXButton.whenPressed(new MoveArmToSetpoint(robot.getArm(), this, Constants.Arm.FRONT_SWITCH));
        _operatorBButton.whenPressed(new MoveArmToSetpoint(robot.getArm(), this, Constants.Arm.BACK_SWITCH));
        _operatorAButton.whenPressed(new MoveArmToSetpoint(robot.getArm(), this, Constants.Arm.FRONT_FLAT));

        _driverYButton.whenPressed(new MoveArmToSetpoint(robot.getArm(), this, Constants.Arm.UP));
        _driverXButton.whenPressed(new MoveArmToSetpoint(robot.getArm(), this, Constants.Arm.FRONT_SWITCH));
        _driverBButton.whenPressed(new MoveArmToSetpoint(robot.getArm(), this, Constants.Arm.BACK_SWITCH));
        _driverAButton.whenPressed(new MoveArmToSetpoint(robot.getArm(), this, Constants.Arm.FRONT_FLAT));

    }

    public double getDriveSpeed() {
        double speed = -getSpeedFromAxis(_driverGamepad, Gamepad.Axes.LEFT_Y.getNumber());
        speed = applyDeadband(speed, Constants.DriveTrain.DEADBAND);
        return applySensitivityFactor(speed, Constants.DriveTrain.SENSITIVITY_LOW_GEAR);

    }

    public double getDriveRotation() {
        double speed = getSpeedFromAxis(_driverGamepad, Gamepad.Axes.RIGHT_X.getNumber());
        speed = applyDeadband(speed, Constants.DriveTrain.DEADBAND);
        return applySensitivityFactor(speed, Constants.DriveTrain.ROTATION_SENSITIVITY);
    }

    public double getLeftSpeed() {
        double speed = -getSpeedFromAxis(_driverGamepad, Gamepad.Axes.LEFT_Y.getNumber());
        speed = Helpers.applyDeadband(speed, Constants.DriveTrain.DEADBAND);
        SmartDashboard.putNumber("DriveTrain/LeftRaw", speed);
        double sensitivity = _gear == Shifter.Gear.LOW ? Constants.DriveTrain.SENSITIVITY_LOW_GEAR : Constants.DriveTrain.SENSITIVITY_HIGH_GEAR;
        speed = Helpers.applySensitivityFactor(speed, sensitivity);
        SmartDashboard.putNumber("DriveTrain/LeftScaled", speed);
        return speed;
    }


    public double getRightSpeed() {
        double speed = -getSpeedFromAxis(_driverGamepad, Gamepad.Axes.RIGHT_Y.getNumber());
        speed = Helpers.applyDeadband(speed, Constants.DriveTrain.DEADBAND);
        SmartDashboard.putNumber("DriveTrain/RightRaw", speed);
        double sensitivity = _gear == Shifter.Gear.LOW ? Constants.DriveTrain.SENSITIVITY_LOW_GEAR : Constants.DriveTrain.SENSITIVITY_HIGH_GEAR;
        speed = Helpers.applySensitivityFactor(speed, sensitivity);
        SmartDashboard.putNumber("DriveTrain/RightScaled", speed);
        return speed;
    }


    protected double getSpeedFromAxis(Joystick gamepad, int axisNumber) {
        return gamepad.getRawAxis(axisNumber);
    }


    public double getArmSpeed() {
        double speed = -getSpeedFromAxis(_operatorGamepad, 5) * Constants.Arm.SPEED_MAX;
        speed = applyDeadband(speed, Constants.Arm.DEADBAND);
        return applySensitivityFactor(speed, Constants.Arm.SENSITIVITY);
    }
}
