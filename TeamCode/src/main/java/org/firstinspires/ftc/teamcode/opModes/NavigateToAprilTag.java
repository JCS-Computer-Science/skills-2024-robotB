package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commandGroups.DriveToAprilTag;
import org.firstinspires.ftc.teamcode.opModes.base.AutoOpMode;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;

@Autonomous(name="Drive to April Tag", group = "Testing")
public class NavigateToAprilTag extends AutoOpMode {
	private VisionSubsystem visionSubsystem, v;
	@Override
	public void setup() {
		visionSubsystem = new VisionSubsystem(hardwareMap, telemetrySubsystem);
		v = visionSubsystem;

		schedule(new DriveToAprilTag(d,o,t, v,2));
	}
}
