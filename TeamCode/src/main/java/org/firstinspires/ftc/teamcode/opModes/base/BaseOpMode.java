package org.firstinspires.ftc.teamcode.opModes.base;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.DepositorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LauncherSubsystem;
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
 * @see GripperSubsystem
 * @see LiftSubsystem
 * @see LauncherSubsystem
 */
public abstract class BaseOpMode extends CommandOpMode {
	protected DriveSubsystem driveSubsystem, d;
	protected OdometrySubsystem odometrySubsystem, o;
	protected TelemetrySubsystem telemetrySubsystem, t;
	protected GripperSubsystem gripperSubsystem, gripper;
	protected LauncherSubsystem launcherSubsystem, launcher;
	protected LiftSubsystem liftSubsystem, lift;
	protected DepositorSubsystem depositerSubsystem, depositer;
	protected HardwareMap hMap;
	/**
	 * This method is called when the opmode is initialized.
	 */
	public abstract void additionalConfig();

	@Override
	public void initialize() {
		hMap = hardwareMap;

		//      Subsystems
		telemetrySubsystem = new TelemetrySubsystem(telemetry);
		driveSubsystem = new DriveSubsystem(hMap, telemetrySubsystem);

		liftSubsystem = new LiftSubsystem(hMap, telemetrySubsystem);
		launcherSubsystem = new LauncherSubsystem(hMap, telemetrySubsystem);
		gripperSubsystem = new GripperSubsystem(hMap, telemetrySubsystem);

		depositerSubsystem=new DepositorSubsystem(hMap);

		odometrySubsystem = new OdometrySubsystem(hMap, telemetrySubsystem);
		odometrySubsystem.resetEncoders();

		// 	Aliases
		d = driveSubsystem;
		o = odometrySubsystem;
		t = telemetrySubsystem;
		gripper = gripperSubsystem;
		launcher = launcherSubsystem;
		lift = liftSubsystem;
		depositer=depositerSubsystem;


		// Initializations
		gripper.setGripper(0);

		additionalConfig();
	}
}
