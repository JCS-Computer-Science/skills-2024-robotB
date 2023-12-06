package org.firstinspires.ftc.teamcode.opModes.base;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.gripper.GripperGrabberToggle;
import org.firstinspires.ftc.teamcode.commands.gripper.GripperPusherToggle;
import org.firstinspires.ftc.teamcode.commands.gripper.GripperSweepToggle;
import org.firstinspires.ftc.teamcode.commands.gripper.GripperTiltToggle;
import org.firstinspires.ftc.teamcode.commands.launcher.LauncherServoToggle;
import org.firstinspires.ftc.teamcode.commands.lift.MoveLiftManual;
import org.firstinspires.ftc.teamcode.commands.lift.MoveLiftPreset;
import org.firstinspires.ftc.teamcode.commands.SetterToggle;
import org.firstinspires.ftc.teamcode.commands.drive.TeleOpDrive;
import org.firstinspires.ftc.teamcode.commands.launcher.ToggleLauncherMotors;
import org.firstinspires.ftc.teamcode.commands.lift.SpeedLimit;
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
        driveSubsystem.drive.setMaxSpeed(0.8);

//      Lift Subsystem Controls
        GamepadButton toolUp = new GamepadButton(toolOp, GamepadKeys.Button.DPAD_UP);
        GamepadButton toolMiddle = new GamepadButton(toolOp, GamepadKeys.Button.DPAD_LEFT);
        GamepadButton toolDown = new GamepadButton(toolOp, GamepadKeys.Button.DPAD_DOWN);

        toolUp.whenPressed(new MoveLiftPreset(liftSubsystem, LiftSubsystem.LIFT_POSITIONS.HANG, gripperSubsystem));
        toolMiddle.whenPressed(new MoveLiftPreset(liftSubsystem, LiftSubsystem.LIFT_POSITIONS.MIDDLE, gripperSubsystem));
        toolDown.whenPressed(new MoveLiftPreset(liftSubsystem, LiftSubsystem.LIFT_POSITIONS.START, gripperSubsystem));

        liftSubsystem.setDefaultCommand(new MoveLiftManual(liftSubsystem, toolOp, gripperSubsystem));

        // With speed limit
//        liftSubsystem.setDefaultCommand(new ParallelCommandGroup(
//                new MoveLiftManual(liftSubsystem, toolOp, gripperSubsystem),
//                new SpeedLimit(driveSubsystem, liftSubsystem)
//        ));

//       Launcher Controls
        GamepadButton toolSTART = new GamepadButton(toolOp, GamepadKeys.Button.START);
        GamepadButton toolRBumper = new GamepadButton(toolOp, GamepadKeys.Button.RIGHT_BUMPER);

        toolSTART.whenPressed(new ToggleLauncherMotors(launcherSubsystem));
        toolRBumper.whenPressed(new LauncherServoToggle(launcherSubsystem));

//       Gripper Subsystem Controls
        GamepadButton toolA = new GamepadButton(toolOp, GamepadKeys.Button.A);
        GamepadButton toolB = new GamepadButton(toolOp, GamepadKeys.Button.B);
        GamepadButton toolX = new GamepadButton(toolOp, GamepadKeys.Button.X);
        GamepadButton toolY = new GamepadButton(toolOp, GamepadKeys.Button.Y);
        GamepadButton toolLBumper = new GamepadButton(toolOp, GamepadKeys.Button.LEFT_BUMPER);

        toolA.whenPressed(new GripperGrabberToggle(gripperSubsystem));
        toolB.whenPressed(new GripperTiltToggle(gripperSubsystem));
        toolX.whenPressed(new GripperPusherToggle(gripperSubsystem));
        toolY.whenPressed(new GripperSweepToggle(gripperSubsystem));
        toolLBumper.whenPressed(new SetterToggle(gripperSubsystem));

//       Everything else
        setup();

    }
}
