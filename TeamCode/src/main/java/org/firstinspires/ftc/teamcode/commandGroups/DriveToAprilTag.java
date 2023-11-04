package org.firstinspires.ftc.teamcode.commandGroups;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;

import org.firstinspires.ftc.teamcode.commands.DriveToPose;
import org.firstinspires.ftc.teamcode.commands.UpdateOdoFromAprilTag;
import org.firstinspires.ftc.teamcode.constants.ImportantPositions;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OdometrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;

public class DriveToAprilTag extends SequentialCommandGroup {
	public DriveToAprilTag(DriveSubsystem d, OdometrySubsystem o, TelemetrySubsystem t, VisionSubsystem v, int tagID){
		addCommands(
				new UpdateOdoFromAprilTag(o,v, tagID),
				new DriveToPose(d,o,t, new Pose2d( ImportantPositions.AprilTags.get(tagID).getX()-20,ImportantPositions.AprilTags.get(tagID).getY(), new Rotation2d())),
				new UpdateOdoFromAprilTag(o,v, tagID),
				new DriveToPose(d,o,t, new Pose2d( ImportantPositions.AprilTags.get(tagID).getX()-10,ImportantPositions.AprilTags.get(tagID).getY(), new Rotation2d()))

		);
	}
}
