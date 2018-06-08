package org.frc5687.switchbot.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.frc5687.switchbot.robot.Constants;
import org.frc5687.switchbot.robot.Robot;
import org.frc5687.switchbot.robot.RobotMap;
import org.frc5687.switchbot.robot.commands.HoldPincer;

/**
 * Created by Ben Bernard on 6/5/2018.
 */
public class Pincer extends Subsystem {
    private Robot _robot;
    private DoubleSolenoid _leftSolenoid;
    private DoubleSolenoid _rightSolenoid;
    private VictorSP _intakeMotor;
    private IntakeState _intakeState = IntakeState.HOLD;

    public Pincer(Robot robot) {
        _robot = robot;
        _leftSolenoid = new DoubleSolenoid(RobotMap.PCM.LEFT_PINCER_OPEN, RobotMap.PCM.LEFT_PINCER_CLOSE);
        _rightSolenoid = new DoubleSolenoid(RobotMap.PCM.RIGHT_PINCER_OPEN, RobotMap.PCM.RIGHT_PINCER_CLOSE);
        _intakeMotor = new VictorSP(RobotMap.PWM.INTAKE_MOTOR);
    }


    public void open() {
        _leftSolenoid.set(DoubleSolenoid.Value.kForward);
        _rightSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void close() {
        _leftSolenoid.set(DoubleSolenoid.Value.kReverse);
        _rightSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void runIntake() {
        switch(_intakeState) {
            case HOLD:
                runIntake(Constants.Intake.HOLD_SPEED);
                break;
            case INTAKE:
                runIntake(Constants.Intake.INTAKE_SPEED);
                break;
            case EJECT:
                runIntake(Constants.Intake.EJECT_SPEED);
                break;
            default:
                runIntake(0);
        }
    }

    public void runIntake(double speed) {
        _intakeMotor.set(-speed);
    }


    public void setIntakeState(IntakeState intakeState) {
        _intakeState = intakeState;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new HoldPincer(this));
    }


    public enum IntakeState {
        HOLD(0),
        INTAKE(1),
        EJECT(2);

        private int _value;

        IntakeState(int value) {
            this._value = value;
        }

        public int getValue() {
            return _value;
        }

    }

}
