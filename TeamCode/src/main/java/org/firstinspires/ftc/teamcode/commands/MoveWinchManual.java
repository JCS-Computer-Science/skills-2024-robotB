package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.WinchSubsystem;

public class MoveWinchManual extends CommandBase {
	private final WinchSubsystem winchSubsystem;
	private final GamepadEx toolOp;
	public MoveWinchManual(WinchSubsystem w, GamepadEx toolOp) {
		winchSubsystem = w;
		this.toolOp = toolOp;
		addRequirements(w);
	}

	public void execute() {
		winchSubsystem.setPower(toolOp.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-toolOp.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
	}
}
