package org.frc5687.switchbot.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc5687.switchbot.robot.Constants;
import org.frc5687.switchbot.robot.Robot;
import org.frc5687.switchbot.robot.RobotMap;
import org.frc5687.switchbot.robot.commands.ArcadeDrive;
import sun.util.resources.cldr.or.CalendarData_or_IN;

import static org.frc5687.switchbot.robot.utils.Helpers.limit;

/**
 * Created by Ben Bernard on 6/4/2018.
 */
public class DriveTrain extends Subsystem{
    // Add the objects for the motor controllers

    // Left side needs one TalonSRX master and two VictorSPX followers
    TalonSRX _leftMaster;
    VictorSPX _leftFollowerA;
    VictorSPX _leftFollowerB;

    // And right side needs one TalonSRX master and two VictorSPX followers
    TalonSRX _rightMaster;
    VictorSPX _rightFollowerA;
    VictorSPX _rightFollowerB;

    private Robot _robot;

    public DriveTrain(Robot robot) {
        _robot = robot;

        // Motor Initialization
        _leftMaster = new TalonSRX(RobotMap.CAN.LEFT_MASTER);
        _leftFollowerA = new VictorSPX(RobotMap.CAN.LEFT_FOLLOWER_A);
        _leftFollowerB = new VictorSPX(RobotMap.CAN.LEFT_FOLLOWER_B);

        _rightMaster = new TalonSRX(RobotMap.CAN.RIGHT_MASTER);
        _rightFollowerA = new VictorSPX(RobotMap.CAN.RIGHT_FOLLOWER_A);
        _rightFollowerB = new VictorSPX(RobotMap.CAN.RIGHT_FOLLOWER_B);
        
        // Setup followers to follow their master
        _leftFollowerA.follow(_leftMaster);
        _leftFollowerB.follow(_leftMaster);
        
        _rightFollowerA.follow(_rightMaster);
        _rightFollowerB.follow(_rightMaster);

        // Setup motors
        _leftMaster.configPeakOutputForward(Constants.DriveTrain.HIGH_POW, 0);
        _leftFollowerA.configPeakOutputForward(Constants.DriveTrain.HIGH_POW, 0);
        _leftFollowerB.configPeakOutputForward(Constants.DriveTrain.HIGH_POW, 0);

        _rightMaster.configPeakOutputForward(Constants.DriveTrain.HIGH_POW, 0);
        _rightFollowerA.configPeakOutputForward(Constants.DriveTrain.HIGH_POW, 0);
        _rightFollowerB.configPeakOutputForward(Constants.DriveTrain.HIGH_POW, 0);

        _leftMaster.configPeakOutputReverse(Constants.DriveTrain.LOW_POW, 0);
        _leftFollowerA.configPeakOutputReverse(Constants.DriveTrain.LOW_POW, 0);
        _leftFollowerB.configPeakOutputReverse(Constants.DriveTrain.LOW_POW, 0);

        _rightMaster.configPeakOutputReverse(Constants.DriveTrain.LOW_POW, 0);
        _rightFollowerA.configPeakOutputReverse(Constants.DriveTrain.LOW_POW, 0);
        _rightFollowerB.configPeakOutputReverse(Constants.DriveTrain.LOW_POW, 0);


        _leftMaster.configNominalOutputForward(0.0, 0);
        _leftFollowerA.configNominalOutputForward(0.0, 0);
        _leftFollowerB.configNominalOutputForward(0.0, 0);
        _rightMaster.configNominalOutputForward(0.0, 0);
        _rightFollowerA.configNominalOutputForward(0.0, 0);
        _rightFollowerB.configNominalOutputForward(0.0, 0);

        _leftMaster.configNominalOutputReverse(0.0, 0);
        _leftFollowerA.configNominalOutputReverse(0.0, 0);
        _leftFollowerB.configNominalOutputReverse(0.0, 0);
        _rightMaster.configNominalOutputReverse(0.0, 0);
        _rightFollowerA.configNominalOutputReverse(0.0, 0);
        _rightFollowerB.configNominalOutputReverse(0.0, 0);

        _leftMaster.setInverted(Constants.DriveTrain.LEFT_MOTORS_INVERTED);
        _leftFollowerA.setInverted(Constants.DriveTrain.LEFT_MOTORS_INVERTED);
        _leftFollowerB.setInverted(Constants.DriveTrain.LEFT_MOTORS_INVERTED);
        _rightMaster.setInverted(Constants.DriveTrain.RIGHT_MOTORS_INVERTED);
        _rightFollowerA.setInverted(Constants.DriveTrain.RIGHT_MOTORS_INVERTED);
        _rightFollowerB.setInverted(Constants.DriveTrain.RIGHT_MOTORS_INVERTED);

        // Configure the encoders
        _leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        _rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        resetDriveEncoders();


    }


    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ArcadeDrive(this, _robot.getOI()));
    }

    public void setPower(double leftSpeed, double rightSpeed) {
        try {
            _leftMaster.set(ControlMode.PercentOutput, leftSpeed);
            _rightMaster.set(ControlMode.PercentOutput, rightSpeed);
        } catch (Exception e) {
            DriverStation.reportError("DriveTrain.setPower exception: " + e.toString(), false);
        }
        SmartDashboard.putNumber("DriveTrain/Speed/Right", rightSpeed);
        SmartDashboard.putNumber("DriveTrain/Speed/Left", leftSpeed);
    }


    public void resetDriveEncoders() {
        try {
            _leftMaster.setSelectedSensorPosition(0,0,0);
            _rightMaster.setSelectedSensorPosition(0, 0, 0);
        } catch (Exception e) {
            DriverStation.reportError("DriveTrain.resetDriveEncoders exception. I suppose this is really bad. : " + e.toString(), false);
        }
    }


    public void arcadeDrive(double speed, double rotation) {
        SmartDashboard.putNumber("DriveTrain/Speed", speed);
        SmartDashboard.putNumber("DriveTrain/Rotation", rotation);

        speed = limit(speed);

        rotation = limit(rotation);

        // Square the inputs (while preserving the sign) to increase fine control
        // while permitting full power.
        speed = Math.copySign(speed * speed, speed);
        rotation = Math.copySign(rotation * rotation, rotation);

        double leftMotorOutput;
        double rightMotorOutput;

        double maxInput = Math.copySign(Math.max(Math.abs(speed), Math.abs(rotation)), speed);

        if (speed >= 0.0) {
            // First quadrant, else second quadrant
            if (rotation >= 0.0) {
                leftMotorOutput = maxInput;
                rightMotorOutput = speed - rotation;
            } else {
                leftMotorOutput = speed + rotation;
                rightMotorOutput = maxInput;
            }
        } else {
            // Third quadrant, else fourth quadrant
            if (rotation >= 0.0) {
                leftMotorOutput = speed + rotation;
                rightMotorOutput = maxInput;
            } else {
                leftMotorOutput = maxInput;
                rightMotorOutput = speed - rotation;
            }
        }

        _leftMaster.set(ControlMode.PercentOutput, limit(leftMotorOutput) * Constants.DriveTrain.HIGH_POW);
        _rightMaster.set(ControlMode.PercentOutput, limit(rightMotorOutput) * Constants.DriveTrain.HIGH_POW);
    }

    public void tankDrive(double leftSpeed, double rightSpeed, boolean overrideCaps) {
        double leftMotorOutput = leftSpeed;
        double rightMotorOutput = rightSpeed;

        _leftMaster.set(ControlMode.PercentOutput, limit(leftMotorOutput) * Constants.DriveTrain.HIGH_POW);
        _rightMaster.set(ControlMode.PercentOutput, limit(rightMotorOutput) * Constants.DriveTrain.HIGH_POW);
    }


    public double getLeftSpeed() {
        return _leftMaster.getMotorOutputPercent() / Constants.DriveTrain.HIGH_POW;
    }

    public double getRightSpeed() {
        return _rightMaster.getMotorOutputPercent() / Constants.DriveTrain.HIGH_POW;
    }

}
