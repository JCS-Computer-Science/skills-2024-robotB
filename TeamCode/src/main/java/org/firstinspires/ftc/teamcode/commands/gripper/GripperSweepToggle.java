package org.firstinspires.ftc.teamcode.commands.gripper;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;

public class GripperSweepToggle extends CommandBase {
	private final GripperSubsystem subsystem;
	public GripperSweepToggle(GripperSubsystem subsystem) {
		this.subsystem = subsystem;
		addRequirements(subsystem);
	}

	@Override
	public void initialize() {
		subsystem.toggleSweep();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
