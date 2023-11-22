package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

//import org.firstinspires.ftc.teamcode.constants.TeleOpConstants;

@Config
public class DriveConstants {
    public static boolean DEBUG = false;

//  Start REV encoder
    public static double TICKS_PER_REV = 8192;
    public static double WHEEL_DIAMETER = 1.38; // inches
    public static double DISTANCE_PER_PULSE = WHEEL_DIAMETER * Math.PI / TICKS_PER_REV;
    public static double CENTER_WHEEL_OFFSET =-7; // inches
//  End REV encoder

    public static double TRACK_WIDTH = 12; // inches

    public static double MAX_VELOCITY = 1.5;
    public static double MAX_ACCELERATION = 1.5;

    public static double B = 2.0;
    public static double ZETA = 0.7;

}
