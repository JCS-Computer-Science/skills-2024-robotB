package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.processors.BlobProcessor;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;

public class BlobDetect extends CommandBase {
	private VisionSubsystem v;
	public BlobDetect(VisionSubsystem v) {
		this.v = v;
	}

	@Override
	public void execute() {
		v.DetectedBlob = v.getBlob();
	}

	@Override
	public boolean isFinished() {
		return v.DetectedBlob != BlobProcessor.Selected.NONE;
	}

	@Override
	public void end(boolean interrupted) {
		v.disableBlobs();
	}
}
