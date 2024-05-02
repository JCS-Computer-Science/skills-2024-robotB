package org.firstinspires.ftc.teamcode.commands.lift;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;

public class MoveLiftManual extends CommandBase {
	private final LiftSubsystem liftSubsystem;
	private final GamepadEx toolOp;
	public MoveLiftManual(LiftSubsystem liftSubsystem, GamepadEx toolOp){
		this.liftSubsystem = liftSubsystem;
		this.toolOp = toolOp;
		addRequirements(liftSubsystem);
	}

	@Override
	public void execute() {
		int targetTicks = (int)Math.round(liftSubsystem.liftMotor.getTargetPosition() + ((toolOp.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-toolOp.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) )* liftSubsystem.ticksFromInches(0.1)));
		liftSubsystem.setPosition(targetTicks);
	}

}
