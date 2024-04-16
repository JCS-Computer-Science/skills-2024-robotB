package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.DifferentialDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TankDriveSubsystem extends SubsystemBase {

	private MotorEx leftMotor, rightMotor;
	public final DifferentialDrive drive;

	private TelemetrySubsystem telemetry;

	public TankDriveSubsystem(HardwareMap hmap, TelemetrySubsystem telemetry) {
		this.leftMotor = new MotorEx(hmap, "leftMotor");
		this.rightMotor = new MotorEx(hmap, "rightMotor");
		this.telemetry = telemetry;

		this.leftMotor.setInverted(true);
		this.leftMotor.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		this.rightMotor.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

		this.drive = new DifferentialDrive(leftMotor, rightMotor);

	}

	public void driveArcade(double forwardSpeed, double turnSpeed) {
		drive.arcadeDrive(forwardSpeed, turnSpeed);
	}

}
