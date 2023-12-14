package org.firstinspires.ftc.teamcode.opModes.base;

/**
 * Base class for Autonomous opmodes
 * @author Eric Singer
 * @see BaseOpMode
 */
public abstract class AutoOpMode extends BaseOpMode {
    @Override
    public void initialize() {
        super.initialize();

        driveSubsystem.drive.setMaxSpeed(0.5);

        setup();
    }
}
