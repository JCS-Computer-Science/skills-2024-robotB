package org.firstinspires.ftc.teamcode.opModes.base;

import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.GripperGrabberToggle;
import org.firstinspires.ftc.teamcode.commands.GripperPusherToggle;
import org.firstinspires.ftc.teamcode.commands.GripperTiltToggle;
import org.firstinspires.ftc.teamcode.commands.LauncherServoToggle;
import org.firstinspires.ftc.teamcode.commands.MoveLiftManual;
import org.firstinspires.ftc.teamcode.commands.MoveLiftPreset;
import org.firstinspires.ftc.teamcode.commands.SetterToggle;
import org.firstinspires.ftc.teamcode.commands.TeleOpDrive;
import org.firstinspires.ftc.teamcode.commands.ToggleMotors;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;

/**
 * Base class for TeleOp opmodes comes preloaded with the following subsystems:
 *
 * @author Eric Singer
 * @see BaseOpMode
 */
public abstract class TeleOpMode extends BaseOpMode {

    protected GamepadEx driver;
    protected GamepadEx toolOp;

    /**
     * This method is called when the opmode is initialized.
     */
    public abstract void setup();

    @Override
    public void additionalConfig() {

//      Gamepads
        driver = new GamepadEx(gamepad1);
        toolOp = new GamepadEx(gamepad2);

//      Drive Subsystem Controls
        driveSubsystem.setDefaultCommand(new TeleOpDrive(driveSubsystem, driver::getLeftX, driver::getLeftY, driver::getRightX));

//      Lift Subsystem Controls
        GamepadButton toolUp = new GamepadButton(toolOp, GamepadKeys.Button.DPAD_UP);
        GamepadButton toolMiddle = new GamepadButton(toolOp, GamepadKeys.Button.DPAD_LEFT);
        GamepadButton toolDown = new GamepadButton(toolOp, GamepadKeys.Button.DPAD_DOWN);

        toolUp.whenPressed(new MoveLiftPreset(liftSubsystem, LiftSubsystem.LIFT_POSITIONS.HANG, gripperSubsystem));
        toolMiddle.whenPressed(new MoveLiftPreset(liftSubsystem, LiftSubsystem.LIFT_POSITIONS.MIDDLE, gripperSubsystem));
        toolDown.whenPressed(new MoveLiftPreset(liftSubsystem, LiftSubsystem.LIFT_POSITIONS.START, gripperSubsystem));

        liftSubsystem.setDefaultCommand(new MoveLiftManual(liftSubsystem, toolOp, gripperSubsystem));

//       Launcher Controls
        GamepadButton toolY = new GamepadButton(toolOp, GamepadKeys.Button.Y);
        GamepadButton toolRBumper = new GamepadButton(toolOp, GamepadKeys.Button.RIGHT_BUMPER);

        toolY.whenPressed(new ToggleMotors(launcherSubsystem));
        toolRBumper.whenPressed(new LauncherServoToggle(launcherSubsystem));

//       Gripper Subsystem Controls
        GamepadButton toolA = new GamepadButton(toolOp, GamepadKeys.Button.A);
        GamepadButton toolB = new GamepadButton(toolOp, GamepadKeys.Button.B);
        GamepadButton toolX = new GamepadButton(toolOp, GamepadKeys.Button.X);
        GamepadButton toolLBumper = new GamepadButton(toolOp, GamepadKeys.Button.LEFT_BUMPER);

        toolA.whenPressed(new GripperGrabberToggle(gripperSubsystem));
        toolB.whenPressed(new GripperTiltToggle(gripperSubsystem));
        toolX.whenPressed(new GripperPusherToggle(gripperSubsystem));
        toolLBumper.whenPressed(new SetterToggle(gripperSubsystem));


//       Everything else
        setup();

    }
}
