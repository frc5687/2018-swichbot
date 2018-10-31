package org.frc5687.switchbot.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.DistanceFollower;
import jaci.pathfinder.followers.EncoderFollower;
import org.frc5687.switchbot.robot.Constants;
import org.frc5687.switchbot.robot.subsystems.DriveTrain;

public class AutoDrivePath extends Command {
    private double _distance;
    private double _speed;
    private Trajectory _trajectory;
    private DistanceFollower _follower;

    private DriveTrain _driveTrain;
    private AHRS _imu;

    private PIDController _angleController;
    private PIDListener _anglePID;
    private double kPangle = .001;
    private double kIangle = .0001;
    private double kDangle = .001;

    public AutoDrivePath(DriveTrain driveTrain, AHRS imu, double distance, double speed) {

        _driveTrain = driveTrain;
        _speed = speed;

        Waypoint[] points = new Waypoint[] {
                new Waypoint(0, 0, 0),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
                new Waypoint(0, distance, 0),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
        };

        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, speed, 2.0, 60.0);
        Trajectory trajectory = Pathfinder.generate(points, config);

    }

    @Override
    protected void initialize() {
        _follower = new DistanceFollower(_trajectory);
        _follower.configurePIDVA(1.0, 0.0, 0.0, 1 / _speed, 0);

        _anglePID = new PIDListener();
        _angleController = new PIDController(kPangle, kIangle, kDangle, _imu, _anglePID, 0.01);
        _angleController.setInputRange(Constants.Auto.MIN_IMU_ANGLE, Constants.Auto.MAX_IMU_ANGLE);
        double maxSpeed = _speed * Constants.Auto.Drive.AnglePID.MAX_DIFFERENCE;
        SmartDashboard.putNumber("AutoDrive/angleMaxSpeed", maxSpeed);
        SmartDashboard.putNumber("AutoDrive/setPoint", _driveTrain.getYaw());
        _angleController.setOutputRange(-maxSpeed, maxSpeed);
        _angleController.setContinuous();

        // If an angle is supplied, use that as our setpoint.  Otherwise get the current heading and stick to it!
        _angleController.setSetpoint(_driveTrain.getYaw());
        _angleController.enable();
    }

    @Override
    protected void execute() {
        double speed = _follower.calculate((int)_driveTrain.getLeftTicks());
        double angleFactor = _anglePID.get();

        _driveTrain.setPower(speed + angleFactor, speed - angleFactor, true);
    }

    @Override
    protected boolean isFinished() {
        return _follower.isFinished();
    }


    private class PIDListener implements PIDOutput {

        private double value;

        public double get() {
            return value;
        }

        @Override
        public void pidWrite(double output) {
            value = output;
        }

    }

}
