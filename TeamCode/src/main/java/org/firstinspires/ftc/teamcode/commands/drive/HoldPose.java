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

public class HoldPose extends DriveToPose {
    public HoldPose(DriveSubsystem driveSubsystem, OdometrySubsystem odometrySubsystem, TelemetrySubsystem telemetrySubsystem, Pose2d destination) {
        super(driveSubsystem, odometrySubsystem, telemetrySubsystem, destination);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
