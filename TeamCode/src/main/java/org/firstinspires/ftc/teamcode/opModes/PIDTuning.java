package org.firstinspires.ftc.teamcode.opModes;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.drive.HoldPose;
import org.firstinspires.ftc.teamcode.opModes.base.AutoOpMode;

@Autonomous(name = "PID Tuning", group = "Test")
public class PIDTuning extends AutoOpMode {

	@Override
	public void setup() {
		schedule(
//				new DriveToPose(d, o, t, new Pose2d(10, 0, Rotation2d.fromDegrees(0)))
		new HoldPose(d, o, t, new Pose2d(-10, 0, Rotation2d.fromDegrees(0)))
		);

//		schedule(new SequentialCommandGroup(
//				new DriveToPose(d,o,t,new Pose2d(10,0,Rotation2d.fromDegrees(0))),
//				new Wait(1.0),
//				new DriveToPose(d,o,t,new Pose2d(10,5,Rotation2d.fromDegrees(0))),
//				new Wait(1.0),
//				new DriveToPose(d,o,t,new Pose2d(10,-5,Rotation2d.fromDegrees(0))),
//				new Wait(1.0),
//				new DriveToPose(d,o,t,new Pose2d(10,0,Rotation2d.fromDegrees(0))),
//				new Wait(1.0),
//				new DriveToPose(d,o,t,new Pose2d(0,0,Rotation2d.fromDegrees(0)))
//		));
	}
}
