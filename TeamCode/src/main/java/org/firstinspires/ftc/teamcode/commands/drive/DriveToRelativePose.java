package org.firstinspires.ftc.teamcode.commands.drive;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OdometrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;

public class DriveToRelativePose extends DriveToPose {
	public DriveToRelativePose(DriveSubsystem d, OdometrySubsystem o, TelemetrySubsystem t, Pose2d newPose) {
		// break newPose into its components based on current heading
		super(d, o, t, new Pose2d(
				newPose.getX() * Math.cos(o.getPose().getHeading()) - newPose.getY() * Math.sin(o.getPose().getHeading()),
				newPose.getX() * Math.sin(o.getPose().getHeading()) + newPose.getY() * Math.cos(o.getPose().getHeading()),
				o.getPose().getRotation().plus(newPose.getRotation())
		));
	}
}
