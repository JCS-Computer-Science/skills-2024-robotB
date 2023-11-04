package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.MoveWinchManual;
import org.firstinspires.ftc.teamcode.opModes.base.TeleOpMode;
import org.firstinspires.ftc.teamcode.subsystems.WinchSubsystem;

@TeleOp(name = "Manual Control", group = "TeleOp")
public class ManualControl extends TeleOpMode {
    @Override
    public void setup() {
        WinchSubsystem winchSubsystem = new WinchSubsystem(hardwareMap, telemetrySubsystem);

        winchSubsystem.setDefaultCommand(new MoveWinchManual(winchSubsystem, toolOp));
    }
}
