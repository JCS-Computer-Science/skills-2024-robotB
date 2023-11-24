package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem;

public class SetterToggle extends CommandBase {
	private final GripperSubsystem subsystem;
	public SetterToggle(GripperSubsystem subsystem) {
		this.subsystem = subsystem;
		addRequirements(subsystem);
	}

	@Override
	public void initialize() {
		subsystem.toggleSetter();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
