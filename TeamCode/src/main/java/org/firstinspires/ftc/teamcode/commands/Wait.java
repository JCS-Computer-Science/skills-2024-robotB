package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

public class Wait extends CommandBase {
	private Timing.Timer timer;

	/**
	 * Waits for a specified amount of time
	 * @param t the time to wait in seconds
	 */
	public Wait(Double t) {
		timer = new Timing.Timer(t.longValue());
	}

	@Override
	public void initialize() {
		timer.start();
	}

	@Override
	public boolean isFinished(){
		return timer.done();
	}
}
