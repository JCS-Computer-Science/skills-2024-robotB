package org.firstinspires.ftc.teamcode.commands.drive;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;

import org.firstinspires.ftc.teamcode.DriveConstants;
import org.firstinspires.ftc.teamcode.constants.AutoConstants;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OdometrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;
import org.firstinspires.ftc.teamcode.util.Convert;

public class HoldPose extends CommandBase {
    private PIDFController xController;
    private PIDFController yController;
    private PIDFController thetaController;

    private DriveSubsystem drive;
    private OdometrySubsystem odometry;
    private TelemetrySubsystem telemetry;
    private Pose2d destination;

    public HoldPose(DriveSubsystem driveSubsystem, OdometrySubsystem odometrySubsystem, TelemetrySubsystem telemetrySubsystem, Pose2d destination) {
        this.drive = driveSubsystem;
        this.odometry = odometrySubsystem;
        this.telemetry = telemetrySubsystem;

        if (destination == null) destination = new Pose2d(0, 0, new Rotation2d(0));
        this.destination = destination;

        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        xController = new PIDFController(AutoConstants.xPID.kP, AutoConstants.xPID.kI, AutoConstants.xPID.kD, AutoConstants.xPID.kF);
        xController.setTolerance(AutoConstants.xPID.tolerance);
        xController.setSetPoint(destination.getX());

        yController = new PIDFController(AutoConstants.yPID.kP, AutoConstants.yPID.kI, AutoConstants.yPID.kD, AutoConstants.yPID.kF);
        yController.setTolerance(AutoConstants.yPID.tolerance);
        yController.setSetPoint(destination.getY());

        thetaController = new PIDFController(AutoConstants.thetaPID.kP, AutoConstants.thetaPID.kI, AutoConstants.thetaPID.kD, AutoConstants.thetaPID.kF);
        thetaController.setTolerance(AutoConstants.thetaPID.tolerance);
        thetaController.setSetPoint(destination.getHeading());
    }

    @Override
    public void execute() {
        if (DriveConstants.DEBUG) {
            xController.setPIDF(AutoConstants.xPID.kP, AutoConstants.xPID.kI, AutoConstants.xPID.kD, AutoConstants.xPID.kF);
            yController.setPIDF(AutoConstants.yPID.kP, AutoConstants.yPID.kI, AutoConstants.yPID.kD, AutoConstants.yPID.kF);
            thetaController.setPIDF(AutoConstants.thetaPID.kP, AutoConstants.thetaPID.kI, AutoConstants.thetaPID.kD, AutoConstants.thetaPID.kF);

            xController.setTolerance(AutoConstants.xPID.tolerance);
            yController.setTolerance(AutoConstants.yPID.tolerance);
            thetaController.setTolerance(AutoConstants.thetaPID.tolerance);
        }

        telemetry.addLine("\nDrive To pose");
        telemetry.addData("Target", String.format("%6.1f %6.1f %6.1f", destination.getX(), destination.getY(), Convert.radiansToDegrees(destination.getHeading())));

        telemetry.addData("xErr", xController.getPositionError());
        telemetry.addData("yErr", yController.getPositionError());
        telemetry.addData("thetaErr", Convert.radiansToDegrees(thetaController.getPositionError()));

        drive.driveFieldCentric(
                yController.calculate(odometry.getPose().getY()),
                xController.calculate(odometry.getPose().getX()),
                thetaController.calculate(odometry.getPose().getHeading()),
                odometry.getPose().getHeading(),
                true
        );
    }

    public void setNewDestination(Pose2d destination) {
        this.destination = destination;

        xController.setSetPoint(destination.getX());
        yController.setSetPoint(destination.getY());
        thetaController.setSetPoint(destination.getHeading());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
