package org.firstinspires.ftc.teamcode.commands.drive;

import com.arcrobotics.ftclib.command.CommandBase;


import org.firstinspires.ftc.teamcode.subsystems.TankDriveSubsystem;

import java.util.function.DoubleSupplier;

/**
 * Drive the robot using the left joystick for translation and the right joystick for rotation.
 *

 */
public class TeleOpDrive extends CommandBase {
	private final TankDriveSubsystem driveSubsystem;
	private final DoubleSupplier forward;

	private final DoubleSupplier rotation;

	/**
	 * Drive the robot using the left joystick for translation and the right joystick for rotation.
	 * @param driveSubsystem
	 * @param forward

	 * @param rotation

	 *
	 * @author Eric Singer
	 */
	public TeleOpDrive(TankDriveSubsystem driveSubsystem, DoubleSupplier forward, DoubleSupplier rotation) {
		this.driveSubsystem = driveSubsystem;

		this.forward = forward;

		this.rotation = rotation;

		addRequirements(driveSubsystem);
	}

	@Override
	public void execute() {
		driveSubsystem.driveArcade(
				-forward.getAsDouble(), -rotation.getAsDouble()

		);

	}
}
