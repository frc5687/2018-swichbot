package org.frc5687.switchbot.robot;

public class Constants {
    public static final int CYCLES_PER_SECOND = 50;

    public static class DriveTrain {
        public static final double DEADBAND = 0.1;
        public static final double SENSITIVITY = 0.75;

        public static final double HIGH_POW = 1.0;
        public static final double LOW_POW = -HIGH_POW;

        public static final boolean LEFT_MOTORS_INVERTED = false;
        public static final boolean RIGHT_MOTORS_INVERTED = true;
    }
    public class Shifter {
        public static final long STOP_MOTOR_TIME = 60;
        public static final long SHIFT_TIME = 60;

        public static final double SHIFT_UP_THRESHOLD = 50; // in inches per second TODO tune
        public static final double SHIFT_DOWN_THRESHOLD = 40; // in inches per second TODO tune

        public static final long AUTO_WAIT_PERIOD = 500;
        public static final long MANUAL_WAIT_PERIOD = 3000;
    }

    public static class Arm {
        public static final double CURRENT_CAP = 40;
        public static final long TIMEOUT_CAP = 100;
        public static final double DEADBAND = 0.1;
        public static final double SENSITIVITY = 0.75;
    }

    public static class Intake {
        public static final double HOLD_SPEED = -0.2;
        public static final double INTAKE_SPEED = -1.0;
        public static final double EJECT_SPEED = 1.0;
    }
}
