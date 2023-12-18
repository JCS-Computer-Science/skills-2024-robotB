package org.firstinspires.ftc.teamcode.commands.drive;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OdometrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;

public class DriveToRelativePose extends DriveToPose {
	/**
	 * Drive to a pose that is relative to the current pose
	 * @param d {@link DriveSubsystem}
	 * @param o {@link OdometrySubsystem}
	 * @param t {@link TelemetrySubsystem}
	 * @param newPose Coordinates relative to the current pose
	 */
	public DriveToRelativePose(@NonNull  DriveSubsystem d, @NonNull OdometrySubsystem o, @NonNull TelemetrySubsystem t, @NonNull Pose2d newPose) {
		// break newPose into its components based on current heading
		super(d, o, t, new Pose2d(
				newPose.getX() * Math.cos(o.getPose().getHeading()) - newPose.getY() * Math.sin(o.getPose().getHeading()),
				newPose.getX() * Math.sin(o.getPose().getHeading()) + newPose.getY() * Math.cos(o.getPose().getHeading()),
				o.getPose().getRotation().plus(newPose.getRotation())
		));
	}
}
