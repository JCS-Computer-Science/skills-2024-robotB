package org.firstinspires.ftc.teamcode.opModes.Auto;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commandGroups.DriveByBlob;
import org.firstinspires.ftc.teamcode.commands.depositor.DepositorClose;
import org.firstinspires.ftc.teamcode.commands.depositor.DepositorOpen;
import org.firstinspires.ftc.teamcode.commands.gripper.GripperGrabberToggle;
import org.firstinspires.ftc.teamcode.commands.util.Wait;
import org.firstinspires.ftc.teamcode.opModes.base.AutoOpMode;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;

@Autonomous(name = "OnlyPropDrop", group = "Autonomous", preselectTeleOp = "Manual Control")
public class OnlyPropDrop extends AutoOpMode {
    private VisionSubsystem v;

    @Override
    public void setup() {
        v = new VisionSubsystem(hardwareMap, telemetrySubsystem);

        liftSubsystem.resetEncoder();
        schedule(new SequentialCommandGroup(
                new GripperGrabberToggle(gripperSubsystem),
//                new MoveLiftPreset(liftSubsystem,LiftSubsystem.LIFT_POSITIONS.TILT_SAFE, gripperSubsystem),
                new DriveByBlob(d,o,t, v, depositor),
                new DepositorOpen(depositor),
                new Wait(1.0),
                new DepositorClose(depositor)

//
        ));
//
    }
}
