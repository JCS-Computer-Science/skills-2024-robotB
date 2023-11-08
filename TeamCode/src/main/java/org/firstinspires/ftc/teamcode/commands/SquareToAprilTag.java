package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OdometrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class SquareToAprilTag extends CommandBase {
	private DriveSubsystem d;
	private OdometrySubsystem o;
	private TelemetrySubsystem t;
	private VisionSubsystem v;

	final double DESIRED_DISTANCE = 12.0; //  this is how close the camera should get to the target (inches)

	//  Set the GAIN constants to control the relationship between the measured position error, and how much power is
	//  applied to the drive motors to correct the error.
	//  Drive = Error * Gain    Make these values smaller for smoother control, or larger for a more aggressive response.
	final double SPEED_GAIN  =  0.02  ;   //  Forward Speed Control "Gain". eg: Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
	final double STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  eg: Ramp up to 25% power at a 25 degree Yaw error.   (0.25 / 25.0)
	final double TURN_GAIN   =  0.01  ;   //  Turn Control "Gain".  eg: Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)

	final double MAX_AUTO_SPEED = 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
	final double MAX_AUTO_STRAFE= 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
	final double MAX_AUTO_TURN  = 0.3;   //  Clip the turn speed to this max value (adjust for your robot)

	private int DESIRED_TAG_ID = -1;     // Choose the tag you want to approach or set to -1 for ANY tag.
	private boolean targetFound = false;
	private AprilTagDetection desiredTag = null;

	public SquareToAprilTag(DriveSubsystem d, OdometrySubsystem o, VisionSubsystem v, TelemetrySubsystem t, int tagID) {
		this.d = d;
		this.o = o;
		this.t = t;
		this.v = v;
		this.DESIRED_TAG_ID = tagID;

		addRequirements(d, o, t, v);
	}

	@Override
	public void execute() {
		List<AprilTagDetection> currentDetections = v.getAprilTags();

		for (AprilTagDetection detection : currentDetections) {
			// Look to see if we have size info on this tag.
			if (detection.metadata != null) {
				//  Check to see if we want to track towards this tag.
				if ((DESIRED_TAG_ID < 0) || (detection.id == DESIRED_TAG_ID)) {
					// Yes, we want to use this tag.
					targetFound = true;
					desiredTag = detection;
					break;  // don't look any further.
				} else {
					// This tag is in the library, but we do not want to track it right now.
					t.addData("Skipping", String.format("Tag ID %d is not desired", detection.id));
				}
			} else {
				// This tag is NOT in the library, so we don't have enough information to track to it.
				t.addData("Unknown", String.format("Tag ID %d is not in TagLibrary", detection.id));
			}
		}

		// Tell the driver what we see, and what to do.
		if (targetFound) {
			t.addData("\n>","Navigating to Target\n");
			t.addData("Found", String.format("ID %d (%s)", desiredTag.id, desiredTag.metadata.name));
			t.addData("Range",  String.format("%5.1f inches", desiredTag.ftcPose.range));
			t.addData("Bearing", String.format("%3.0f degrees", desiredTag.ftcPose.bearing));
			t.addData("Yaw",String.format("%3.0f degrees", desiredTag.ftcPose.yaw));


			// Determine heading, range and Yaw (tag image rotation) error so we can use them to control the robot automatically.
			double  rangeError      = (desiredTag.ftcPose.range - DESIRED_DISTANCE);
			double  headingError    = desiredTag.ftcPose.bearing;
			double  yawError        = desiredTag.ftcPose.yaw;

			// Use the speed and turn "gains" to calculate how we want the robot to move.
			double drive  = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
			double turn   = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
			double strafe = Range.clip(-yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);

			//  Send the desired motion to the drive subsystem.
			d.driveRobotCentric(strafe, drive, turn, false);
		} else {
			t.addData("\n>","Drive using joysticks to find valid target\n");
		}


	}
}
