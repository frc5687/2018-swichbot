package org.frc5687.switchbot.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
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

    public Pincer(Robot robot) {
        _robot = robot;
        _leftSolenoid = new DoubleSolenoid(RobotMap.PCM.LEFT_PINCER_OPEN, RobotMap.PCM.LEFT_PINCER_CLOSE);
        _rightSolenoid = new DoubleSolenoid(RobotMap.PCM.RIGHT_PINCER_OPEN, RobotMap.PCM.RIGHT_PINCER_CLOSE);
    }


    public void open() {
        _leftSolenoid.set(DoubleSolenoid.Value.kForward);
        _rightSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void close() {
        _leftSolenoid.set(DoubleSolenoid.Value.kReverse);
        _rightSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new HoldPincer(this));
    }
}
