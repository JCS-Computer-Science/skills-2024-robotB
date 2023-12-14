package org.firstinspires.ftc.teamcode.commands.lift;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;

public class MoveLiftPreset extends CommandBase {
	private final LiftSubsystem liftSubsystem;
	private final LiftSubsystem.LIFT_POSITIONS preset;
	public MoveLiftPreset(LiftSubsystem liftSubsystem, LiftSubsystem.LIFT_POSITIONS preset){
		this.liftSubsystem = liftSubsystem;
		this.preset = preset;
		addRequirements(liftSubsystem);
	}
	 @Override
	 public void initialize() {
		liftSubsystem.gotoPreset(preset);
	 }

	@Override
	public boolean isFinished() {
		return (liftSubsystem.liftMotor.getCurrentPosition() <= liftSubsystem.liftMotor.getTargetPosition()+5)&&(liftSubsystem.liftMotor.getCurrentPosition() >= liftSubsystem.liftMotor.getTargetPosition()-5);
	}

}
