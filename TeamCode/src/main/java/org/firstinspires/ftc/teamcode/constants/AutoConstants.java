package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.dashboard.config.Config;

public class AutoConstants {
    @Config
    public static class xPID {
        public static double kP = 0.1;
        public static double kI = 0.0;
        public static double kD = 0.00;
        public static double kF = 0.0;
        public static double tolerance = 1.0;
    }

    @Config
    public static class yPID {
        public static double kP = 0.2;
        public static double kI = 0.0;
        public static double kD = 0.0;
        public static double kF = 0.0;
        public static double tolerance = 1.0;
    }

    @Config
    public static class thetaPID {
        public static double kP = 2;
        public static double kI = 0.0;
        public static double kD = 0.1;
        public static double kF = 0.0;
        public static double tolerance = 1.0;
    }
}
