package org.firstinspires.ftc.teamcode.commandGroups;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SelectCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;

import org.firstinspires.ftc.teamcode.commands.BlobDetect;
import org.firstinspires.ftc.teamcode.commands.DepositorClose;
import org.firstinspires.ftc.teamcode.commands.DepositorOpen;
import org.firstinspires.ftc.teamcode.commands.DriveToPose;
import org.firstinspires.ftc.teamcode.commands.Wait;
import org.firstinspires.ftc.teamcode.processors.BlobProcessor;
import org.firstinspires.ftc.teamcode.subsystems.DepositorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OdometrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;

import java.util.HashMap;

public class DriveByBlob extends SequentialCommandGroup {
	private Double forwardTravel = -18.5;
	private Double sideOffset = 10.5;
	private Double frontOffset = -10.0;
	public DriveByBlob(DriveSubsystem d, OdometrySubsystem o, TelemetrySubsystem t, VisionSubsystem v, DepositorSubsystem ds) {
		addCommands(
				new BlobDetect(v),
				new DriveToPose(d, o, t, new Pose2d(forwardTravel,0, Rotation2d.fromDegrees(0)), 3.0),
				new Wait(1.0),
				new SelectCommand(
						new HashMap<Object, Command>() {{
							put(BlobProcessor.Selected.LEFT, new DriveToPose(d, o, t, new Pose2d(forwardTravel,-sideOffset, Rotation2d.fromDegrees(0)),3.0));
							put(BlobProcessor.Selected.MIDDLE, new DriveToPose(d, o, t, new Pose2d(forwardTravel+frontOffset,0,Rotation2d.fromDegrees(0)),3.0));
							put(BlobProcessor.Selected.RIGHT, new DriveToPose(d, o, t, new Pose2d(forwardTravel,sideOffset,Rotation2d.fromDegrees(0)),3.0));
						}},
						// the selector
						v::getBlob
				),
				new DepositorOpen(ds),
				new Wait(1.0),
				new DepositorClose(ds)
		);
	}
}
