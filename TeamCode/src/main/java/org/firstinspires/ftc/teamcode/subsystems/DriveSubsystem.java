package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.constants.AutoConstants;
import org.firstinspires.ftc.teamcode.constants.MotorConstants;

public class DriveSubsystem extends SubsystemBase {
    private final MotorEx frontLeft, frontRight, backLeft, backRight;
    private final MecanumDrive drive;
    private final TelemetrySubsystem t;

    private final PIDFController xController;
    private final PIDFController yController;
    private final PIDFController thetaController;

    public DriveSubsystem(@NonNull HardwareMap hardwareMap, TelemetrySubsystem telemetrySubsystem) {
        this.t = telemetrySubsystem;
//        Motors
        frontLeft = new MotorEx(hardwareMap, "frontLeft");
        frontRight = new MotorEx(hardwareMap, "frontRight");
        backLeft = new MotorEx(hardwareMap, "backLeft");
        backRight = new MotorEx(hardwareMap, "backRight");

        frontLeft.setFeedforwardCoefficients(MotorConstants.frontLeft.kS, MotorConstants.frontLeft.kV, MotorConstants.frontLeft.kA);
        frontRight.setFeedforwardCoefficients(MotorConstants.frontRight.kS, MotorConstants.frontRight.kV, MotorConstants.frontRight.kA);
        backLeft.setFeedforwardCoefficients(MotorConstants.backLeft.kS, MotorConstants.backLeft.kV, MotorConstants.backLeft.kA);
        backRight.setFeedforwardCoefficients(MotorConstants.backRight.kS, MotorConstants.backRight.kV, MotorConstants.backRight.kA);

        frontLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

//        Drivetrain
        drive = new MecanumDrive(
                frontLeft,
                frontRight,
                backLeft,
                backRight
        );

//      PID Controllers
        xController = new PIDFController(AutoConstants.xPID.kP, AutoConstants.xPID.kI, AutoConstants.xPID.kD, AutoConstants.xPID.kF);
        xController.setTolerance(AutoConstants.xPID.tolerance);

        yController = new PIDFController(AutoConstants.yPID.kP, AutoConstants.yPID.kI, AutoConstants.yPID.kD, AutoConstants.yPID.kF);
        yController.setTolerance(AutoConstants.yPID.tolerance);

        thetaController = new PIDFController(AutoConstants.thetaPID.kP, AutoConstants.thetaPID.kI, AutoConstants.thetaPID.kD, AutoConstants.thetaPID.kF);
        thetaController.setTolerance(AutoConstants.thetaPID.tolerance);
    }

    /**
     * Set the targets in 3D space for the robot to move to.
     * @param x the x coordinate of the target
     * @param y the y coordinate of the target
     * @param theta the angle of the target
     */
    public void setSetPoint(double x, double y, double theta) {
        xController.setSetPoint(x);
        yController.setSetPoint(y);
        thetaController.setSetPoint(theta);
    }

    /**
     * Set our target while maintaining our heading.
     * @param x the x coordinate of the target
     * @param y the y coordinate of the target
     */
    public void setSetPoint(double x, double y) {
        setSetPoint(x, y, thetaController.getSetPoint());
    }

    /**
     * Rotate to target Heading
     * @param theta the angle of the target
     */
    public void setSetPoint(double theta) {
        setSetPoint(xController.getSetPoint(), yController.getSetPoint(), theta);
    }

    /**
     * Set our target from a Transform2d
     * @param pose
     */
    public void setSetPoint(Transform2d pose) {
        setSetPoint(pose.getTranslation().getX(), pose.getTranslation().getY(), pose.getRotation().getDegrees());
    }

    public void driveToSetPoint(OdometrySubsystem o) {

        t.addData("xErr", xController.getPositionError());
        t.addData("yErr", yController.getPositionError());
        t.addData("thetaErr", thetaController.getPositionError());

        driveFieldCentric(
                yController.calculate(o.getPose().getY()),
                xController.calculate(o.getPose().getX()),
                thetaController.calculate(o.getPose().getHeading()),
                o.getPose().getHeading(),
                true
        );
    }

    /**
     * Update all PIDF controllers from the constants file.
     * @see AutoConstants
     */
    public void updatePIDF() {
        xController.setPIDF(AutoConstants.xPID.kP, AutoConstants.xPID.kI, AutoConstants.xPID.kD, AutoConstants.xPID.kF);
        yController.setPIDF(AutoConstants.yPID.kP, AutoConstants.yPID.kI, AutoConstants.yPID.kD, AutoConstants.yPID.kF);
        thetaController.setPIDF(AutoConstants.thetaPID.kP, AutoConstants.thetaPID.kI, AutoConstants.thetaPID.kD, AutoConstants.thetaPID.kF);
        xController.setTolerance(AutoConstants.xPID.tolerance);
        yController.setTolerance(AutoConstants.yPID.tolerance);
        thetaController.setTolerance(AutoConstants.thetaPID.tolerance);
    }

    /**
     * Drives the robot from the perspective of the robot itself rather than that
     * of the driver.
     *
     * @param strafeSpeed  the horizontal speed of the robot, derived from input
     * @param forwardSpeed the vertical speed of the robot, derived from input
     * @param turnSpeed    the turn speed of the robot, derived from input
     * @param squareInputs Square joystick inputs for finer control
     */
    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double turnSpeed, boolean squareInputs) {
        // For a robot centric model, the input of (0,1,0) for (leftX, leftY, rightX)
        // will move the robot in the direction of its current heading. Every movement
        // is relative to the frame of the robot itself.
        //
        //                 (0,1,0)
        //                   /
        //                  /
        //           ______/_____
        //          /           /
        //         /           /
        //        /___________/
        //           ____________
        //          /  (0,0,1)  /
        //         /     ↻     /
        //        /___________/

        drive.driveRobotCentric(
                strafeSpeed,
                forwardSpeed,
                -turnSpeed,
                squareInputs
        );
    }

    /**
     * Drives the robot from the perspective of the robot itself rather than that
     * of the driver.
     *
     * @param strafeSpeed  the horizontal speed of the robot, derived from input
     * @param forwardSpeed the vertical speed of the robot, derived from input
     * @param turnSpeed    the turn speed of the robot, derived from input
     */
    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double turnSpeed) {
        driveRobotCentric(strafeSpeed, forwardSpeed, turnSpeed, false);
    }

    /**
     * Drives the robot from the perspective of the driver. No matter the orientation of the
     * robot, pushing forward on the drive stick will always drive the robot away
     * from the driver.
     *
     * @param strafeSpeed  the horizontal speed of the robot
     * @param forwardSpeed the vertical speed of the robot
     * @param turnSpeed    the turn speed of the robot
     * @param heading    the heading of the robot
     * @param squareInputs whether to square the inputs to decrease sensitivity at low speeds
     */
    public void driveFieldCentric(double strafeSpeed, double forwardSpeed,
                                  double turnSpeed, double heading, boolean squareInputs) {
        // Below is a model for how field centric will drive when given the inputs
        // for (leftX, leftY, rightX). As you can see, for (0,1,0), it will travel forward
        // regardless of the heading. For (1,0,0) it will strafe right (ref to the 0 heading)
        // regardless of the heading.
        //
        //                   heading
        //                     /
        //            (0,1,0) /
        //               |   /
        //               |  /
        //            ___|_/_____
        //          /           /
        //         /           / ---------- (1,0,0)
        //        /__________ /

        drive.driveFieldCentric(
                strafeSpeed,
                forwardSpeed,
                turnSpeed,
                heading,
                squareInputs
        );
    }

    /**
     * Drives the robot from the perspective of the driver. No matter the orientation of the
     * robot, pushing forward on the drive stick will always drive the robot away
     * from the driver.
     *
     * @param strafeSpeed       the horizontal speed of the robot, derived from input
     * @param forwardSpeed       the vertical speed of the robot, derived from input
     * @param turnSpeed    the turn speed of the robot, derived from input
     * @param heading    the heading of the robot
     */
    public void driveFieldCentric(double strafeSpeed, double forwardSpeed,
                                  double turnSpeed, double heading) {
        driveFieldCentric(strafeSpeed, forwardSpeed, turnSpeed, heading, false);
    }
}
