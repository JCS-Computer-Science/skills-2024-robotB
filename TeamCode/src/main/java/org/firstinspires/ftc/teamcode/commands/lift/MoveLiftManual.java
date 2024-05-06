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
		int targetTicks;
		if(toolOp.getButton(GamepadKeys.Button.RIGHT_BUMPER)){
			targetTicks = liftSubsystem.liftMotor.getTargetPosition()+liftSubsystem.ticksFromInches(0.1);
		}
		else if(toolOp.getButton(GamepadKeys.Button.LEFT_BUMPER)){
			targetTicks = liftSubsystem.liftMotor.getTargetPosition()-liftSubsystem.ticksFromInches(0.1);
		}
		else{
			targetTicks = liftSubsystem.liftMotor.getTargetPosition();
		}
		liftSubsystem.setPosition(targetTicks);
	}

}
