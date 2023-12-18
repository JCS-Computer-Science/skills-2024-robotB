package org.firstinspires.ftc.teamcode.opModes.base;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Base class for Autonomous opmodes
 * @author Eric Singer
 * @see BaseOpMode
 */
@Autonomous(preselectTeleOp = "Manual Control")
public abstract class AutoOpMode extends BaseOpMode {
    @Override
    public void initialize() {
        super.initialize();

        driveSubsystem.drive.setMaxSpeed(0.5);

        setup();
    }
}
