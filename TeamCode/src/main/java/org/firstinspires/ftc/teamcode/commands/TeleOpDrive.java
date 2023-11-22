package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OdometrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;

import java.util.function.DoubleSupplier;

/**
 * Drive the robot using the left joystick for translation and the right joystick for rotation.
 *
 * @see DriveSubsystem
 */
public class TeleOpDrive extends CommandBase {
	private final DriveSubsystem driveSubsystem;
	private final DoubleSupplier forward;
	private final DoubleSupplier strafe;
	private final DoubleSupplier rotation;


	/**
	 * Drive the robot using the left joystick for translation and the right joystick for rotation.
	 * @param driveSubsystem
	 * @param forward
	 * @param strafe
	 * @param rotation
	 * @see DriveSubsystem
	 *
	 * @author Eric Singer
	 */
	public TeleOpDrive(DriveSubsystem driveSubsystem, DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier rotation) {
		this.driveSubsystem = driveSubsystem;

		this.forward = forward;
		this.strafe = strafe;
		this.rotation = rotation;

		addRequirements(driveSubsystem);
	}

	@Override
	public void execute() {
		driveSubsystem.driveRobotCentric(
				-forward.getAsDouble(),
				strafe.getAsDouble(),
				-rotation.getAsDouble(),
				true
		);

	}
}
