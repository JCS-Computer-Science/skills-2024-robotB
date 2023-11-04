package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem;

public class ToggleMotors extends CommandBase {
	private LauncherSubsystem subsystem;
	public ToggleMotors(LauncherSubsystem subsystem) {
		this.subsystem = subsystem;
		addRequirements(subsystem);
	}

	@Override
	public void initialize() {
		subsystem.toggle();
	}

	public boolean isFinished() {
		return true;
	}
}
