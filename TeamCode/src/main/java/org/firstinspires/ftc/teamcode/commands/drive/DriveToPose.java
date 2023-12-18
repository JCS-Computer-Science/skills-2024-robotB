package org.firstinspires.ftc.teamcode.commands.drive;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.DriveConstants;
import org.firstinspires.ftc.teamcode.constants.AutoConstants;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OdometrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;
import org.firstinspires.ftc.teamcode.util.Convert;
import org.firstinspires.ftc.teamcode.util.PIDFControllerExt;

public class DriveToPose extends CommandBase {
    private PIDFControllerExt xController;
    private PIDFControllerExt yController;
    private PIDFController thetaController;

    private final DriveSubsystem drive;
    private final OdometrySubsystem odometry;
    private final TelemetrySubsystem telemetry;
    private Pose2d destination;

    /**
     * Drive to a pose
     * @param d {@link DriveSubsystem}
     * @param o {@link OdometrySubsystem}
     * @param t {@link TelemetrySubsystem}
     * @param p {@link Pose2d}
     */
    public DriveToPose(@NonNull  DriveSubsystem d, @NonNull OdometrySubsystem o, @NonNull TelemetrySubsystem t, @NonNull Pose2d p) {
        drive = d;
        odometry = o;
        telemetry = t;
        destination = p;

        addRequirements(d);
    }

    /**
     * Drive to a pose with the same x and y, but a different heading
     * @param d {@link DriveSubsystem}
     * @param o {@link OdometrySubsystem}
     * @param t {@link TelemetrySubsystem}
     * @param rot {@link Rotation2d}
     */
    public DriveToPose(DriveSubsystem d, OdometrySubsystem o, TelemetrySubsystem t, Rotation2d rot) {
        this(d,o,t, new Pose2d(o.getPose().getX(), o.getPose().getY(), rot));
    }

    /**
     * @param d {@link DriveSubsystem}
     * @param o {@link OdometrySubsystem}
     * @param t {@link TelemetrySubsystem}
     * @param x x coordinate
     * @param y y coordinate
     * @param heading heading
     */
    public DriveToPose(DriveSubsystem d, OdometrySubsystem o, TelemetrySubsystem t, double x, double y, double heading) {
        this(d,o,t, new Pose2d(x,y,Rotation2d.fromDegrees(heading)));
    }

    /**
     * Maintain current pose
     * @param d {@link DriveSubsystem}
     * @param o {@link OdometrySubsystem}
     * @param t {@link TelemetrySubsystem}
     */
    public DriveToPose(DriveSubsystem d, OdometrySubsystem o, TelemetrySubsystem t) {
        this(d,o,t, new Pose2d(o.getPose().getX(),o.getPose().getY(),o.getPose().getRotation()));
    }

    @Override
    public void initialize() {
        xController = new PIDFControllerExt(AutoConstants.xPID.kP, AutoConstants.xPID.kI, AutoConstants.xPID.kD, AutoConstants.xPID.kF);
        xController.setTolerance(AutoConstants.xPID.tolerance);
        xController.setSetPoint(destination.getX());

        yController = new PIDFControllerExt(AutoConstants.yPID.kP, AutoConstants.yPID.kI, AutoConstants.yPID.kD, AutoConstants.yPID.kF);
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

        double xErr=destination.getX()-odometry.getPose().getX();
        double yErr=destination.getY()-odometry.getPose().getY();

        double robotXErr = Math.cos(odometry.getPose().getHeading())*xErr+Math.sin(odometry.getPose().getHeading())*yErr;
        double robotYErr = -Math.sin(odometry.getPose().getHeading())*xErr+Math.cos(odometry.getPose().getHeading())*yErr;

        drive.driveRobotCentric(
                yController.calculate(robotYErr),
                xController.calculate(robotXErr),
                thetaController.calculate(odometry.getPose().getHeading()),
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
        return (xController.atSetPoint() && yController.atSetPoint() && thetaController.atSetPoint());
    }

    @Override
    public void end(boolean interrupted){
        drive.driveRobotCentric(0.0,0.0,0.0);
    }
}
