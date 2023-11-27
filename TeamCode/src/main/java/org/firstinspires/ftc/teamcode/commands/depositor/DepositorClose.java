package org.firstinspires.ftc.teamcode.commands.depositor;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DepositorSubsystem;

public class DepositorClose extends CommandBase {
	private final DepositorSubsystem subsystem;
	public DepositorClose(DepositorSubsystem subsystem) {
		this.subsystem = subsystem;
		addRequirements(subsystem);
	}

	@Override
	public void initialize() {
		subsystem.close();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
