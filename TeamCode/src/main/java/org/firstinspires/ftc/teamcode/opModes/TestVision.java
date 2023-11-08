package org.firstinspires.ftc.teamcode.opModes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SelectCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.BlobDetect;
import org.firstinspires.ftc.teamcode.commands.RunCheck;
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
				new BlobDetect(v),
				new SelectCommand(
					// the first parameter is a map of commands
					new HashMap<Object, Command>() {{
						put(BlobProcessor.Selected.LEFT, new RunCheck("Left", telemetrySubsystem));
						put(BlobProcessor.Selected.MIDDLE, new RunCheck("Middle", telemetrySubsystem));
						put(BlobProcessor.Selected.RIGHT, new RunCheck("Right", telemetrySubsystem));
					}},
					// the selector
					() -> v.DetectedBlob
				)
		);
	}
}
