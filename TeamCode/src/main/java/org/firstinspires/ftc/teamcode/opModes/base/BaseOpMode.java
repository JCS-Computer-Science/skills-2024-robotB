package org.firstinspires.ftc.teamcode.opModes.base;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OdometrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;

/**
 * Generic OpMode base that can be extended by {@link TeleOpMode} and {@link AutoOpMode}.
 *
 * @author Eric Singer
 *
 * @see DriveSubsystem
 * @see OdometrySubsystem
 * @see TelemetrySubsystem
 * @see LiftSubsystem
 */
public abstract class BaseOpMode extends CommandOpMode {
	protected DriveSubsystem driveSubsystem, d;
	protected OdometrySubsystem odometrySubsystem, o;
	protected TelemetrySubsystem telemetrySubsystem, t;
	protected LiftSubsystem liftSubsystem, lift;
	protected HardwareMap hMap;

	public abstract void setup();

	@Override
	public void initialize() {
		hMap = hardwareMap;

		//      Subsystems
		telemetrySubsystem = new TelemetrySubsystem(telemetry);
		driveSubsystem = new DriveSubsystem(hMap, telemetrySubsystem);

		liftSubsystem = new LiftSubsystem(hMap, telemetrySubsystem);

		odometrySubsystem = new OdometrySubsystem(hMap, telemetrySubsystem);
		odometrySubsystem.resetEncoders();

		// 	Aliases
		d = driveSubsystem;
		o = odometrySubsystem;
		t = telemetrySubsystem;
		lift = liftSubsystem;
	}
}
