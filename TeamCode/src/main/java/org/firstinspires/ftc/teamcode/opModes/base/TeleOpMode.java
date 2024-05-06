package org.firstinspires.ftc.teamcode.opModes.base;

import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.arm.MoveArm;
import org.firstinspires.ftc.teamcode.commands.drive.TeleOpDrive;
import org.firstinspires.ftc.teamcode.commands.lift.MoveLiftManual;
import org.firstinspires.ftc.teamcode.commands.lift.MoveLiftPreset;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;

/**
 * Base class for TeleOp opmodes comes preloaded with the following subsystems:
 *
 * @author Eric Singer
 * @see BaseOpMode
 */
public abstract class TeleOpMode extends BaseOpMode {

    protected GamepadEx driver;
    //protected GamepadEx toolOp;

    @Override
    public void initialize() {
        super.initialize();
//      Gamepads
        driver = new GamepadEx(gamepad1);
        //toolOp = new GamepadEx(gamepad2);

//      Drive Subsystem Controls
        driveSubsystem.setDefaultCommand(new TeleOpDrive(driveSubsystem,()->driver.getLeftY(),()->driver.getRightX())        );
        driveSubsystem.drive.setMaxSpeed(0.8);

//      Lift Subsystem Controls
        GamepadButton toolUp = new GamepadButton(driver, GamepadKeys.Button.DPAD_UP);
        GamepadButton toolMiddle = new GamepadButton(driver, GamepadKeys.Button.DPAD_LEFT);
        GamepadButton toolDown = new GamepadButton(driver, GamepadKeys.Button.DPAD_DOWN);

        toolUp.whenPressed(new MoveLiftPreset(liftSubsystem, LiftSubsystem.LIFT_POSITIONS.TOP));
        toolMiddle.whenPressed(new MoveLiftPreset(liftSubsystem, LiftSubsystem.LIFT_POSITIONS.MIDDLE));
        toolDown.whenPressed(new MoveLiftPreset(liftSubsystem, LiftSubsystem.LIFT_POSITIONS.BOTTOM));


        liftSubsystem.setDefaultCommand(new MoveLiftManual(liftSubsystem, driver));
        armSubsystem.setDefaultCommand(new MoveArm(armSubsystem, driver));

        setup();
    }
}
