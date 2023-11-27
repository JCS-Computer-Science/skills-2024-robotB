package org.firstinspires.ftc.teamcode.opModes.Auto;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.gripper.GripperGrabberToggle;
import org.firstinspires.ftc.teamcode.commands.lift.MoveLiftPreset;
import org.firstinspires.ftc.teamcode.commands.drive.TimedDrive;
import org.firstinspires.ftc.teamcode.opModes.base.AutoOpMode;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;

@Autonomous(name = "DriveFwd", group = "Autonomous", preselectTeleOp = "Manual Control")
public class DriveFwd extends AutoOpMode {
    @Override
    public void setup() {
        liftSubsystem.resetEncoder();
        schedule(new SequentialCommandGroup(
                new GripperGrabberToggle(gripperSubsystem),
                new MoveLiftPreset(liftSubsystem,LiftSubsystem.LIFT_POSITIONS.TILT_SAFE, gripperSubsystem)),
                new TimedDrive(driveSubsystem,0.5,0.0,0.0,6.0)
//        ,new GripperGrabberToggle(gripperSubsystem)
        );
//        schedule(new TimedDrive(driveSubsystem,0.5,0.0,0.0,2.0));
    }
}
