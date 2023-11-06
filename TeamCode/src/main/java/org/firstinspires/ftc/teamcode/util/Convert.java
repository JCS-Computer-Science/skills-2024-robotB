package org.firstinspires.ftc.teamcode.util;

public class Convert {
	public static double inchToMeters(double inches) {
		return inches * 0.0254;
	}
	public static double feetToMeters(double feet) {
		return feet * 0.3048;
	}
	public static double ticksToInches(double ticks) {
		return ticks * 0.00393701;
	}
	public static double degreesToRadians(double degrees) {
		return degrees * 0.0174533;
	}
	public static double radiansToDegrees(double radians) {
		return radians * 57.2958;
	}
	public static double mmToInches(double mm) {
		return mm * 0.0393701;
	}
}
