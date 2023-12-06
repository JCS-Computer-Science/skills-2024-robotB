package org.firstinspires.ftc.teamcode.commands.lift;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;

public class SpeedLimit extends CommandBase {

	private final LiftSubsystem ls;
	private final DriveSubsystem d;

	public SpeedLimit(DriveSubsystem d, LiftSubsystem ls) {
		this.ls = ls;
		this.d = d;
	}

	@Override
	public void execute() {
		if (ls.liftMotor.getCurrentPosition() > ls.ticksFromInches(LiftSubsystem.LIFT_POSITIONS.TILT_SAFE.ordinal())) {
			d.drive.setMaxSpeed(0.5);
		} else {
			d.drive.setMaxSpeed(0.8);
		}
	}
}
