package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;

/**
 * All this command does is print out if it ran or not
 */
public class RunCheck extends CommandBase {
    private String text;
    private TelemetrySubsystem t;
    public RunCheck(String text, TelemetrySubsystem t) {
        this.text = text;
        this.t = t;
    }

    public void execute() {
        System.out.println(text);
        t.addLine(text);
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
