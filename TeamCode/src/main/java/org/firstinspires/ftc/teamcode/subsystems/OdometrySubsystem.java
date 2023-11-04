package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.hardware.motors.Motor.Encoder;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.kinematics.HolonomicOdometry;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.DriveConstants;
import org.firstinspires.ftc.teamcode.constants.ImportantPositions;
import org.firstinspires.ftc.teamcode.util.Convert;

import java.util.Objects;

public class OdometrySubsystem extends SubsystemBase {

    private final HolonomicOdometry m_odometry;
    private final TelemetrySubsystem telemetry;
    private final Encoder leftEncoder, rightEncoder, centerEncoder;

    public OdometrySubsystem(@NonNull HardwareMap hardwareMap, TelemetrySubsystem telemetrySubsystem) {
        this(hardwareMap,telemetrySubsystem, new Pose2d(0,0,new Rotation2d(0)));

    }

    public OdometrySubsystem(HardwareMap hardwareMap, TelemetrySubsystem telemetrySubsystem, Pose2d initialPose) {

        // This is the built-in IMU in the REV hub.
        // The orientation of the hub is important.
        // Below is a model of the REV Hub and the
        // orientation axes for the IMU.
        //
        //                           | Z axis
        //                           |
        //     (Motor Port Side)     |   / X axis
        //                       ____|__/____
        //          Y axis     / *   | /    /|   (IO Side)
        //          _________ /______|/    //      I2C
        //                   /___________ //     Digital
        //                  |____________|/      Analog
        //
        //                 (Servo Port Side)
        // taken from roadrunner quickstart

        telemetry = telemetrySubsystem;
        leftEncoder = new MotorEx(hardwareMap, "frontLeft").encoder;
        rightEncoder = new MotorEx(hardwareMap, "backLeft").encoder;
        centerEncoder = new MotorEx(hardwareMap, "backRight").encoder;

        leftEncoder.setDistancePerPulse(DriveConstants.DISTANCE_PER_PULSE);
        rightEncoder.setDistancePerPulse(DriveConstants.DISTANCE_PER_PULSE);
        centerEncoder.setDistancePerPulse(DriveConstants.DISTANCE_PER_PULSE);

        leftEncoder.setDirection(MotorEx.Direction.REVERSE);
        centerEncoder.setDirection(MotorEx.Direction.REVERSE);

        m_odometry = new HolonomicOdometry(
                rightEncoder::getDistance,
                leftEncoder::getDistance,
                centerEncoder::getDistance,
                DriveConstants.TRACK_WIDTH,
                DriveConstants.CENTER_WHEEL_OFFSET
        );

        m_odometry.updatePose(initialPose);

    }
    public void updatePoseFromAprilTag(Pose2d poseFromTag, int tagID) {
        Pose2d tagPose= ImportantPositions.AprilTags.get(tagID);
        double newPoseX = poseFromTag.getY()*tagPose.getRotation().getSin()+poseFromTag.getX()*tagPose.getRotation().getCos() + tagPose.getX();
        double newPoseY = -poseFromTag.getX()*tagPose.getRotation().getSin()+poseFromTag.getY()*tagPose.getRotation().getCos() + tagPose.getY();
        Rotation2d newPoseRot = tagPose.getRotation().times(-1).rotateBy(poseFromTag.getRotation()).rotateBy(Rotation2d.fromDegrees(90));
        Pose2d newPose = new Pose2d(newPoseX, newPoseY, newPoseRot);
        m_odometry.updatePose(newPose);
    }
    public Pose2d getPose() {
        return m_odometry.getPose();
    }

    @Override
    public void periodic() {
        m_odometry.updatePose();
        telemetry.addLine("\nOdometry");
        telemetry.addData("x", m_odometry.getPose().getX());
        telemetry.addData("y", m_odometry.getPose().getY());
        telemetry.addData("theta", Convert.radiansToDegrees(m_odometry.getPose().getHeading()));
    }

    public void setPose(Pose2d pose) {
        m_odometry.updatePose(pose);
    }
    
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
        centerEncoder.reset();
    }
}