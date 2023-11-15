package org.firstinspires.ftc.teamcode.opModes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SelectCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.BlobDetect;
import org.firstinspires.ftc.teamcode.commands.DriveToPose;
import org.firstinspires.ftc.teamcode.opModes.base.AutoOpMode;
import org.firstinspires.ftc.teamcode.processors.BlobProcessor;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;

import java.util.HashMap;

@Autonomous(name = "Test Vision", group = "Test")
public class TestVision extends AutoOpMode {
	@Override
	public void setup() {
		VisionSubsystem v = new VisionSubsystem(hardwareMap, telemetrySubsystem);
		schedule(
			new SequentialCommandGroup(
				new BlobDetect(v),
				new SelectCommand(
					new HashMap<Object, Command>() {{
						put(BlobProcessor.Selected.LEFT, new DriveToPose(d, o, t, new Pose2d(-10,0,Rotation2d.fromDegrees(0))));
						put(BlobProcessor.Selected.MIDDLE, new DriveToPose(d, o, t, new Pose2d(0,10,Rotation2d.fromDegrees(0))));
						put(BlobProcessor.Selected.RIGHT, new DriveToPose(d, o, t, new Pose2d(10,0,Rotation2d.fromDegrees(0))));
					}},
					// the selector
					v::getBlob
				)
			)
		);
	}
}
