package org.firstinspires.ftc.teamcode.commands.launcher;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem;

public class LauncherServoToggle extends CommandBase {
	private final LauncherSubsystem subsystem;
	public LauncherServoToggle(LauncherSubsystem subsystem) {
		this.subsystem = subsystem;
		addRequirements(subsystem);
	}

	@Override
	public void initialize() {
		subsystem.toggleServo();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
