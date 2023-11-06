package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class WinchSubsystem extends SubsystemBase {
	private TelemetrySubsystem t;
	private DcMotorEx winchMotor;
	public WinchSubsystem(@NonNull HardwareMap hMap, TelemetrySubsystem t) {
		this.t = t;
		winchMotor = hMap.get(DcMotorEx.class, "winchMotor");

		winchMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
	}

	public void setPower(double power) {
		winchMotor.setPower(power);
	}
}
