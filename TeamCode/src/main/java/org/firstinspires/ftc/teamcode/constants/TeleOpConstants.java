package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.dashboard.config.Config;

public class TeleOpConstants {
    @Config
    public static class thetaPID {
        public static double kP = 0.1;
        public static double kI = 0.0;
        public static double kD = 0.0;
        public static double kF = 0.0;
        public static double tolerance = 0.0;
    }
}
