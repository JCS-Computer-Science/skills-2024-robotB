package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.geometry.Rotation2d;

import org.firstinspires.ftc.teamcode.constants.TeleOpConstants;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OdometrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;

import java.util.function.DoubleSupplier;

/**
 * Drive the robot using the left joystick for translation and the right joystick for rotation.
 *
 * @see DriveSubsystem
 */
public class TeleOpDriveAutoHeading extends CommandBase {
    private final DriveSubsystem driveSubsystem;
    private final OdometrySubsystem odometrySubsystem;
    private final TelemetrySubsystem telemetrySubsystem;

    private final DoubleSupplier forward;
    private final DoubleSupplier strafe;
    private final DoubleSupplier rotation;

    private PIDFController thetaController;
    private Rotation2d targetAngle;

    /**
     * Drive the robot using the left joystick for translation and the right joystick for rotation.
     * @param driveSubsystem
     * @param odometrySubsystem
     * @param telemetrySubsystem
     * @param forward
     * @param strafe
     * @param rotation
     * @see DriveSubsystem
     *
     * @author Eric Singer
     */
    public TeleOpDriveAutoHeading(DriveSubsystem driveSubsystem, OdometrySubsystem odometrySubsystem, TelemetrySubsystem telemetrySubsystem, DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier rotation) {
        this.driveSubsystem = driveSubsystem;
        this.odometrySubsystem = odometrySubsystem;
        this.telemetrySubsystem = telemetrySubsystem;

        this.forward = forward;
        this.strafe = strafe;
        this.rotation = rotation;

        this.targetAngle = new Rotation2d(odometrySubsystem.getPose().getHeading());


//        based on the input of driver.getRightX move the rotation target by that amount
        thetaController = new PIDFController(TeleOpConstants.thetaPID.kP, TeleOpConstants.thetaPID.kI, TeleOpConstants.thetaPID.kD, TeleOpConstants.thetaPID.kF);

        thetaController.setTolerance(TeleOpConstants.thetaPID.tolerance);

        addRequirements(driveSubsystem, telemetrySubsystem, odometrySubsystem);
    }

    @Override
    public void execute() {
        targetAngle = targetAngle.plus(Rotation2d.fromDegrees(rotation.getAsDouble() * 0.7));

        telemetrySubsystem.addData("Target Angle", targetAngle.getDegrees());
        telemetrySubsystem.addData("Current Angle", odometrySubsystem.getPose().getRotation().getDegrees());
        telemetrySubsystem.addData("Error", thetaController.getPositionError());
        driveSubsystem.driveRobotCentric(
            forward.getAsDouble(),
            strafe.getAsDouble(),
            thetaController.calculate(0,targetAngle.minus(odometrySubsystem.getPose().getRotation()).getDegrees()),
                true
        );

    }
}
