package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opModes.base.AutoOpMode;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;

@Autonomous(name = "Test Vision", group = "Test")
public class TestVision extends AutoOpMode {
	private VisionSubsystem v;
	@Override
	public void setup() {
		v = new VisionSubsystem(hardwareMap, telemetrySubsystem);

//		schedule(
//			new DriveByBlob(d,o,t,v, depositor)
//		);
	}
}
