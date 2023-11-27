package org.firstinspires.ftc.teamcode.commands.depositor;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DepositorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;

public class DepositorOpen extends CommandBase {
	private final DepositorSubsystem subsystem;
	public DepositorOpen(DepositorSubsystem subsystem) {
		this.subsystem = subsystem;
		addRequirements(subsystem);
	}

	@Override
	public void initialize() {
		subsystem.open();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
