package org.firstinspires.ftc.teamcode.commands.vision;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.processors.BlobProcessor;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;

public class BlobDetect extends CommandBase {
	private final VisionSubsystem v;
	private final boolean isDebug;

	public BlobDetect(VisionSubsystem v, boolean debug) {
		this.v = v;
		this.isDebug = debug;
	}
	public BlobDetect(VisionSubsystem v) {
		this.v = v;
		this.isDebug=false;
	}

	@Override
	public void execute() {
		v.DetectedBlob = v.getBlob();
	}

	@Override
	public boolean isFinished() {
		return !isDebug && v.DetectedBlob != BlobProcessor.Selected.NONE;
	}

	@Override
	public void end(boolean interrupted) {
		v.disableBlobs();
	}
}
