package org.firstinspires.ftc.teamcode.commandGroups;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.commands.gripper.GripperGrabberToggle;
import org.firstinspires.ftc.teamcode.commands.gripper.GripperPusherToggle;
import org.firstinspires.ftc.teamcode.commands.gripper.GripperSweepToggle;
import org.firstinspires.ftc.teamcode.commands.gripper.GripperTiltToggle;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;

public class GrabPixel extends SequentialCommandGroup {

	public GrabPixel(GripperSubsystem gripperSubsystem) {
	}
}
