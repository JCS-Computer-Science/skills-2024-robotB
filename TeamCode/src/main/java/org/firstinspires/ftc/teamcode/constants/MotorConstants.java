package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.dashboard.config.Config;

public class MotorConstants {
    public static double winch_diameter = 35.0;
    public static double lift_diameter = 38.2;
    @Config
    public static class frontLeft {
        public static double kS = 0.0;
        public static double kV = 0.0;
        public static double kA = 0.0;
    }
    @Config
    public static class frontRight {
        public static double kS = 0.0;
        public static double kV = 0.0;
        public static double kA = 0.0;
    }
    @Config
    public static class backLeft {
        public static double kS = 0.0;
        public static double kV = 0.0;
        public static double kA = 0.0;
    }
    @Config
    public static class backRight {
        public static double kS = 0.0;
        public static double kV = 0.0;
        public static double kA = 0.0;
    }
}
