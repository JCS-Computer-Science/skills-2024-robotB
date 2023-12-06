package org.firstinspires.ftc.teamcode.opModes.base;

/**
 * Base class for Autonomous opmodes
 * @author Eric Singer
 * @see BaseOpMode
 */
public abstract class AutoOpMode extends BaseOpMode {
    public abstract void setup();

    @Override
    public void additionalConfig() {
        driveSubsystem.drive.setMaxSpeed(0.5);
        setup();
    }
}
