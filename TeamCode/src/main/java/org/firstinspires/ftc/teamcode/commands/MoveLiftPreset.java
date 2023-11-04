package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;

public class MoveLiftPreset extends CommandBase {
	private final LiftSubsystem liftSubsystem;
	private final GripperSubsystem gripperSubsystem;
	private LiftSubsystem.LIFT_POSITIONS preset;
	public MoveLiftPreset(LiftSubsystem liftSubsystem, LiftSubsystem.LIFT_POSITIONS preset, GripperSubsystem gripperSubsystem){
		this.liftSubsystem = liftSubsystem;
		this.gripperSubsystem = gripperSubsystem;
		this.preset = preset;
		addRequirements(liftSubsystem);
	}
	 @Override
	 public void initialize() {
		if(preset.inches< LiftSubsystem.LIFT_POSITIONS.TILT_SAFE.inches){
			gripperSubsystem.setGripperTilt(gripperSubsystem.tiltPositionDown);
		}
		liftSubsystem.gotoPreset(preset);
	 }
	@Override
	public void execute() {
	}

	@Override
	public boolean isFinished() {
		return (liftSubsystem.liftMotor.getCurrentPosition() <= liftSubsystem.liftMotor.getTargetPosition()+5)&&(liftSubsystem.liftMotor.getCurrentPosition() >= liftSubsystem.liftMotor.getTargetPosition()-5);
	}

}
