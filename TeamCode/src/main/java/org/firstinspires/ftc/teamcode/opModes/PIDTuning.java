package org.firstinspires.ftc.teamcode.opModes;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.commands.drive.DriveToPose;
import org.firstinspires.ftc.teamcode.commands.drive.HoldPose;
import org.firstinspires.ftc.teamcode.opModes.base.AutoOpMode;

import java.util.function.BooleanSupplier;

@Autonomous(name = "PID Tuning", group = "Test")
public class PIDTuning extends AutoOpMode {

	private BooleanSupplier mode;

	@Override
	public void setup() {
		schedule(
				new SequentialCommandGroup(
					new DriveToPose(d, o, t, new Pose2d(0, 0, Rotation2d.fromDegrees(o.getPose().getHeading() + Math.PI/2))),
					new WaitCommand(1000)
				).perpetually()
		);
	}
}
