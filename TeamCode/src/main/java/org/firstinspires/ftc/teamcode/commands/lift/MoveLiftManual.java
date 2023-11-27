package org.firstinspires.ftc.teamcode.commands.lift;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;

public class MoveLiftManual extends CommandBase {
	private final LiftSubsystem liftSubsystem;
	private final GripperSubsystem gripperSubsystem;
	private final GamepadEx toolOp;
	public MoveLiftManual(LiftSubsystem liftSubsystem, GamepadEx toolOp, GripperSubsystem gripperSubsystem){
		this.liftSubsystem = liftSubsystem;
		this.gripperSubsystem = gripperSubsystem;
		this.toolOp = toolOp;
		addRequirements(liftSubsystem);
	}

	@Override
	public void execute() {
		int targetTicks=new Double(liftSubsystem.liftMotor.getTargetPosition() + (toolOp.getLeftY() * 10)).intValue();
		if(targetTicks< liftSubsystem.ticksFromInches(LiftSubsystem.LIFT_POSITIONS.TILT_SAFE.inches)){
			gripperSubsystem.setGripperTilt(gripperSubsystem.tiltPositionDown);
		}
		liftSubsystem.setPosition(targetTicks);
	}

}
