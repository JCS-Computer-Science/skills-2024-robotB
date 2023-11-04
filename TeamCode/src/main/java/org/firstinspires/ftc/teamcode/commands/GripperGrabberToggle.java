package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;

public class GripperGrabberToggle extends CommandBase {
	private final GripperSubsystem subsystem;
	public GripperGrabberToggle(GripperSubsystem subsystem) {
		this.subsystem = subsystem;
		addRequirements(subsystem);
	}

	@Override
	public void initialize() {
		subsystem.toggleGripper();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
