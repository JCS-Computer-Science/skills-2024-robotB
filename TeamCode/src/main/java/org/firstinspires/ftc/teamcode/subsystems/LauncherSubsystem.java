package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.checkerframework.checker.index.qual.LTEqLengthOf;

public class LauncherSubsystem extends SubsystemBase {
	private DcMotor launcherMotor;
	private final TelemetrySubsystem t;
	public LauncherSubsystem(@NonNull HardwareMap hardwareMap, TelemetrySubsystem t) {
		 launcherMotor = hardwareMap.get(DcMotor.class, "launcherMotor");

		 this.t = t;
	}

	public void setPower(double power) {
		launcherMotor.setPower(power);
	}

	public void toggle(){
		if(launcherMotor.getPower() == 0){
			setPower(1);
		} else {
			setPower(0);
		}
	}

	@Override
	public void periodic() {
		t.addData("Launcher state", launcherMotor.getPower() > 0 ? "On" : "Off");
	}
}
