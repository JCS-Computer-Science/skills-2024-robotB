package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.SquareToAprilTag;
import org.firstinspires.ftc.teamcode.commands.UpdateOdoFromAprilTag;
import org.firstinspires.ftc.teamcode.opModes.base.AutoOpMode;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;

@Autonomous(name="Validate April Tags", group = "Testing")
public class ValidateAprilTags extends AutoOpMode {
	private VisionSubsystem v;
	@Override
	public void setup() {
		this.v = new VisionSubsystem(hardwareMap, t);

		schedule(
				new SquareToAprilTag(d, o, v, t, 2),
				new UpdateOdoFromAprilTag(o, v, 2)
		);
	}
}
