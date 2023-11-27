package org.firstinspires.ftc.teamcode.commands.launcher;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem;

public class ToggleLauncherMotors extends CommandBase {
	private LauncherSubsystem subsystem;
	public ToggleLauncherMotors(LauncherSubsystem subsystem) {
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
