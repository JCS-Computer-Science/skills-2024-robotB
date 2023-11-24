package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

public class Wait extends CommandBase {
	private Timing.Timer timer;
	private long t;

	/**
	 * Waits for a specified amount of time
	 * @param t the time to wait in seconds
	 */
	public Wait(Double t) {


		this.t=t.longValue();

	}

	@Override
	public void initialize() {
		timer = new Timing.Timer(t);

	}

	@Override
	public void execute(){
		if(!timer.isTimerOn()){
			timer.start();
		}
	}

	@Override
	public boolean isFinished(){
		return timer.done();
	}
}
