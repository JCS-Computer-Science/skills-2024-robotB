package org.firstinspires.ftc.teamcode.commands.gripper;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;

public class GripperPusherToggle extends CommandBase {
	private final GripperSubsystem subsystem;
	public GripperPusherToggle(GripperSubsystem subsystem) {
		this.subsystem = subsystem;
		addRequirements(subsystem);
	}

	@Override
	public void initialize() {
		subsystem.togglePusher();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
