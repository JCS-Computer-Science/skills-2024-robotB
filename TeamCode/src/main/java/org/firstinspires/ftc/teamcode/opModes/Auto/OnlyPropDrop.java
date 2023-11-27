package org.firstinspires.ftc.teamcode.opModes.Auto;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commandGroups.DriveByBlob;
import org.firstinspires.ftc.teamcode.commands.DepositorClose;
import org.firstinspires.ftc.teamcode.commands.DepositorOpen;
import org.firstinspires.ftc.teamcode.commands.GripperGrabberToggle;
import org.firstinspires.ftc.teamcode.commands.MoveLiftPreset;
import org.firstinspires.ftc.teamcode.commands.TimedDrive;
import org.firstinspires.ftc.teamcode.commands.Wait;
import org.firstinspires.ftc.teamcode.opModes.base.AutoOpMode;
import org.firstinspires.ftc.teamcode.subsystems.DepositorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;
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
                new DriveByBlob(d,o,t, v, depositer),
                new DepositorOpen(depositer),
                new Wait(1.0),
                new DepositorClose(depositer)

//
        ));
//
    }
}
