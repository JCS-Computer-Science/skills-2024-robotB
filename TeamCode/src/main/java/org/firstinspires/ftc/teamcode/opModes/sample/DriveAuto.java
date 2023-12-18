package org.firstinspires.ftc.teamcode.opModes.sample;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.commands.drive.DriveToPose;
import org.firstinspires.ftc.teamcode.opModes.base.AutoOpMode;

@Disabled
@Autonomous(name = "Autonomous", group = "Sample")
public class DriveAuto extends AutoOpMode {
	@Override
	public void setup() {
		schedule(
			new SequentialCommandGroup(
				// Move forward 12 inches
				new DriveToPose(driveSubsystem, odometrySubsystem, telemetrySubsystem, new Pose2d(12, 0, Rotation2d.fromDegrees(0)))),
				// Turn 90 degrees
				new DriveToPose(driveSubsystem, odometrySubsystem, telemetrySubsystem, new Pose2d(odometrySubsystem.getPose().getX(), odometrySubsystem.getPose().getY(), Rotation2d.fromDegrees(90)))
		);
	}
}
