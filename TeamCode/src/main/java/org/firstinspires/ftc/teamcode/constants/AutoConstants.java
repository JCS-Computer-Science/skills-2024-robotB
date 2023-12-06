package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.geometry.Rotation2d;

public class AutoConstants {
    @Config
    public static class xPID {
        public static double kP = 0.06;
        public static double kI = 0.2;
        public static double kD = 0.0;
        public static double kF = 0.0;
        public static double tolerance = 1.0;
    }

    @Config
    public static class yPID {
        public static double kP = 0.10;
        public static double kI = 0.25;
        public static double kD = 0.0;
        public static double kF = 0.0;
        public static double tolerance = 1.0;
    }

    @Config
    public static class thetaPID {
        public static double kP = 3.5;
        public static double kI = 0.001;
        public static double kD = 0.02;
        public static double kF = 0.0;
        public static double tolerance = Rotation2d.fromDegrees(1.0).getRadians();
    }
}
