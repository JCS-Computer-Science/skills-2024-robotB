package org.firstinspires.ftc.teamcode.opModes.sample;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.commandGroups.DriveByBlob;
import org.firstinspires.ftc.teamcode.opModes.base.AutoOpMode;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;

@Disabled
@Autonomous(name = "Drive By Vision", group = "Sample")
public class DriveByVision extends AutoOpMode {
	@Override
	public void setup() {
		VisionSubsystem visionSubsystem = new VisionSubsystem(hMap, telemetrySubsystem);
		schedule(
				new DriveByBlob(driveSubsystem, odometrySubsystem, telemetrySubsystem, visionSubsystem, liftSubsystem)
		);
	}
}
