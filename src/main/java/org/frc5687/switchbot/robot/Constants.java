package org.frc5687.switchbot.robot;

public class Constants {
    public static final int CYCLES_PER_SECOND = 50;

    public static class DriveTrain {
        public static final double DEADBAND = 0.01;
        public static final double SENSITIVITY_LOW_GEAR = 0.1;
        public static final double SENSITIVITY_HIGH_GEAR = 0.3;
        public static final double ROTATION_SENSITIVITY = 0.3;

        public static final double HIGH_POW = 1.0;
        public static final double LOW_POW = -HIGH_POW;

        public static final boolean LEFT_MOTORS_INVERTED = false;
        public static final boolean RIGHT_MOTORS_INVERTED = true;
    }
    public class Shifter {
        public static final long STOP_MOTOR_TIME = 60;
        public static final long SHIFT_TIME = 60;

        public static final double SHIFT_UP_THRESHOLD = 50; // in inches per second graTODO tune
        public static final double SHIFT_DOWN_THRESHOLD = 40; // in inches per second TODO tune

        public static final long AUTO_WAIT_PERIOD = 500;
        public static final long MANUAL_WAIT_PERIOD = 3000;
    }

    public static class Arm {
        public static final double CURRENT_CAP = 40;
        public static final long TIMEOUT_CAP = 100;
        public static final double DEADBAND = 0.1;
        public static final double SENSITIVITY = 0.75;

        public static final double FRONT_FLAT = 110.0;
        public static final double FRONT_SWITCH = 30.0;
        public static final double UP = 0;
        public static final double BACK_SWITCH = -30.0;
        public static final double BACK_FLAT = -100.0;

        public static final double ANGLE_MIN = -110;
        public static final double ANGLE_MAX = 110;
        public static final double POT_MIN = 0.065;
        public static final double POT_MAX = 0.695;

        public static final double SPEED_MAX = 1.0;
    }

    public static class Intake {
        public static final double HOLD_SPEED = -0.2;
        public static final double INTAKE_SPEED = -0.75;
        public static final double EJECT_SPEED = 1.0;
        public static final long EJECT_TIME_MILLIS = 500;
    }

    public static class OI {
        public static final double AXIS_BUTTON_THRESHHOLD = 0.2;
    }

    public static class Encoders {

        public static final boolean REVERSED = true; //TODO change to new robot specifications
        public static final int SAMPLES_TO_AVERAGE = 20;
        public static final int PULSES_PER_ROTATION = 4096; // 1024 in quad mode. talon is 4096.

        public class WheelDiameter {
            public static final double INCHES = 6;
            public static final double METERS = 0.1524;
        }
        public static final double GEAR_RATIO = 3.0;
        public class DistancePerRotation {
            public static final double INCHES = Math.PI * WheelDiameter.INCHES * GEAR_RATIO;
            public static final double METERS = Math.PI * WheelDiameter.METERS;
        }

        public class DistancePerPulse {
            public static final double INCHES = DistancePerRotation.INCHES / PULSES_PER_ROTATION;
        }

        public static final double SCALAR_RATIO = 8;
        public static final double MAX_PERIOD = 5;

        public class Track {
            public static final double INCHES = 24;
            public static final double METERS = 0.6096;
        }

        public static final double INCHES_PER_PULSE = DistancePerPulse.INCHES;

    }

    public class Auto {
        public static final double MIN_IMU_ANGLE = -180;
        public static final double MAX_IMU_ANGLE = 180;

        public static final double MAX_PITCH = 20.0;
        public static final double MAX_ROLL = 20.0;

        public class Align {

            public static final double SPEED = 1.0;

            public static final double kP = 0.04;
            public static final double kI = 0.002;
            public static final double kD = 0.4;
            public static final double TOLERANCE = .5; // 0.5
            public static final double MINIMUM_SPEED = 0.2;
            public static final double MAX_OUTPUT = 0;
            /*
             *time the angle must be on target for to be considered steady
             */
            public static final double STEADY_TIME = 60;

        }

        public class Drive {

            public static final double SPEED = 1.0;

            public static final double MIN_SPEED = 0.25;

            public class MaxVel {
                public static final double MPS = 2.33; // Meters Per Second
                public static final double IPS = 130; // Inches Per Second
            }

            public class MaxAcceleration {
                public static final double METERS = 1.5; // Meters Per Second Squared
                public static final double INCHES = 80.0;
            }

            public class MaxJerk {
                public static final double METERS = 6.0; // Meters Per Second Cubed
                public static final double INCHES = 200.0;
            }

            public static final long STEADY_TIME = 100;
            public static final long ALIGN_STEADY_TIME = 100;

            public class TrajectoryFollowing {
                public class Talon {
                    public static final double kP = 0.3; // Talon doesn't use kP
                    public static final double kI = 0.001;//02;
                    public static final double kD = 0.0;
                    public static final double kF = 0.35; // 0.28 works well
                }

                public class Cheese {
                    public static final double kP = 8;//1.06;//0.001;//1.70;//0.80;
                    public static final double kI = 0.0;
                    public static final double kD = 0.1;//.3;
                    public static final double kT = 0.4; // Used for turning correction. -0.68 works well
                    public class kV {
                        public static final double MPS = 1.0 / MaxVel.MPS;
                        public static final double IPS = 1.0;// / MaxVel.IPS;
                    }
                    public class kA {
                        public static final double METERS = 1.0 / MaxAcceleration.METERS;
                        public static final double INCHES = 1.0;//1.0 / MaxAcceleration.INCHES;
                    }
                }
            }

            public class IRPID {
                public static final double kP = 0.05;
                public static final double kI = 0.00;
                public static final double kD = 0.03;
                public static final double TOLERANCE = .5;

                /**
                 * a in the voltage-to-distance equation distance = a * voltage ^ b
                 */
                public static final double TRANSFORM_COEFFICIENT = 27.385;
                /**
                 * b in the voltage-to-distance equation distance = a * voltage ^ b
                 */
                public static final double TRANSFORM_POWER = -1.203;
            }

            public class EncoderPID {
                public static final double kP = 6;//1.06;//0.001;//1.70;//0.80;
                public static final double kI = 0.0;
                public static final double kD = 0.0;//.3;
                public static final double kT = 4; // Used for turning correction
                public class kV {
                    public static final double MPS = 1.0 / MaxVel.MPS;
                    public static final double IPS = 1.0;// / MaxVel.IPS;
                }
                public class kA {
                    public static final double METERS = 1.0 / MaxAcceleration.METERS;
                    public static final double INCHES = 0.0;//1.0 / MaxAcceleration.INCHES;
                }
                public static final double TOLERANCE = 1;
            }

            public class AnglePID {
                public static final double kP = 0.4;
                public static final double kI = 0.006;
                public static final double kD = 0.09;
                public class kV {
                    public static final double MPS = 1.0 / MaxVel.MPS;
                    public static final double IPS = 1.0 / MaxVel.IPS;
                }
                public static final double PATH_TURN = 0.4; // 1.0
                public static final double MAX_DIFFERENCE = 0.4;
                public static final double TOLERANCE = .5;
            }

        }

    }
    public class AutoChooser {
        public static final int LEFT = -1;
        public static final int RIGHT = 1;

        public class Position {
            public static final int FAR_LEFT = 1;
            public static final int MID_LEFT = 2;
            public static final int CENTER_LEFT = 3;
            public static final int CENTER_RIGHT = 4;
            public static final int NEAR_RIGHT = 5;
            public static final int FAR_RIGHT = 6;
        }
    }

    public class RotarySwitch {
        public static final double TOLERANCE = 0.02;
    }


}
