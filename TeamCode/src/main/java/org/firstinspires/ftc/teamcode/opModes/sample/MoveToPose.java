package org.firstinspires.ftc.teamcode.opModes.sample;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.commands.drive.DriveToPose;
import org.firstinspires.ftc.teamcode.opModes.base.AutoOpMode;

@Disabled
@Autonomous(name = "Move To Pose", group = "Sample")
public class MoveToPose extends AutoOpMode {
	@Override
	public void setup() {
		schedule(
				// Move forward 12 inches
				new DriveToPose(driveSubsystem, odometrySubsystem, telemetrySubsystem, new Pose2d(12, 0, Rotation2d.fromDegrees(0))));
	}
}
