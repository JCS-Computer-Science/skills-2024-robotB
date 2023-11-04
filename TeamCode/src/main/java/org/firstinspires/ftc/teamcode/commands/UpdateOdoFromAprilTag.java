package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;

import org.firstinspires.ftc.teamcode.subsystems.OdometrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class UpdateOdoFromAprilTag extends CommandBase {
	private OdometrySubsystem o;
	private VisionSubsystem v;
	private int tagID;
	private Pose2d tagPose;
	private boolean isFound;
	private int count = 0;
	public UpdateOdoFromAprilTag(OdometrySubsystem o, VisionSubsystem v, int tagID) {
		this.isFound=false;
		this.count=0;
		this.o = o;
		this.v = v;
		this.tagID = tagID;
	}

	@Override
	public void execute() {
		List<AprilTagDetection> currentDetections = v.getDetections();

		for (AprilTagDetection tag : currentDetections) {
			Rotation2d yawRotation = Rotation2d.fromDegrees(tag.ftcPose.yaw);

			tagPose = new Pose2d(
				-tag.ftcPose.range*yawRotation.getSin(), 
				-tag.ftcPose.y* yawRotation.getCos(),
				Rotation2d.fromDegrees(-tag.ftcPose.yaw)
			);
			o.updatePoseFromAprilTag(tagPose, tag.id);

			count++;
			if (count > 5) {
				isFound = true;
			}
		};
	}

	@Override
	public boolean isFinished() {
		return isFound;
	}

	@Override
	public void end(boolean interrupted) {
		if (!interrupted) {
//			o.updatePoseFromAprilTag(tagPose, tagID);
		}
	}
}
