package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveSubsystem extends SubsystemBase {
    private final MotorEx frontLeft, frontRight, backLeft, backRight;
    public final MecanumDrive drive;
    private final TelemetrySubsystem t;

    public DriveSubsystem(@NonNull HardwareMap hardwareMap, TelemetrySubsystem telemetrySubsystem) {
        this.t = telemetrySubsystem;
//        Motors
        frontLeft = new MotorEx(hardwareMap, "frontLeft");
        frontRight = new MotorEx(hardwareMap, "frontRight");
        backLeft = new MotorEx(hardwareMap, "backLeft");
        backRight = new MotorEx(hardwareMap, "backRight");

        for (MotorEx motor : new MotorEx[]{frontLeft, frontRight, backLeft, backRight}) {
            motor.setInverted(true);
        }

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
                -strafeSpeed,
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
                -strafeSpeed,
                forwardSpeed,
                -turnSpeed,
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
