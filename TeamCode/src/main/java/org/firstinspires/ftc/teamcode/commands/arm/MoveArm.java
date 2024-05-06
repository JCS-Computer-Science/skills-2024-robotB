package org.firstinspires.ftc.teamcode.commands.arm;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

public class MoveArm extends CommandBase {
	private final ArmSubsystem armSubsystem;
	private final GamepadEx gamepad;

	public MoveArm(ArmSubsystem armSubsystem, GamepadEx gamepad){
		this.armSubsystem = armSubsystem;
		this.gamepad = gamepad;
		addRequirements(armSubsystem);
	}

	@Override
	public void execute() {
		armSubsystem.moveArm(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
//		if(gamepad.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
//			armSubsystem.moveArm(1);
//		} else if(gamepad.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
//			armSubsystem.moveArm(-1);
//		} else {
//			armSubsystem.moveArm(0);
//		}
	}

}
