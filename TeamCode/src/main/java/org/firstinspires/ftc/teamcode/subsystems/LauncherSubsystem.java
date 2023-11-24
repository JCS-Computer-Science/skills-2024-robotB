package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.checkerframework.checker.index.qual.LTEqLengthOf;

public class LauncherSubsystem extends SubsystemBase {
	private DcMotor launcherMotor;
	private ServoEx launcherServo;
	private boolean servoState=false;
	private final TelemetrySubsystem t;
	public LauncherSubsystem(@NonNull HardwareMap hardwareMap, TelemetrySubsystem t) {
		 launcherMotor = hardwareMap.get(DcMotor.class, "launcherMotor");
		 launcherServo = new SimpleServo(hardwareMap,"launcherServo",0,180);
		 this.t = t;
		 launcherServo.setPosition(0.5);
	}

	public void setPower(double power) {
		launcherMotor.setPower(power);
	}

	public void toggle(){
		if(launcherMotor.getPower() == 0){
			setPower(-1);
		} else {
			setPower(0);
		}
	}

	public void toggleServo(){
		if(launcherServo.getPosition()==0){
			launcherServo.setPosition(0.5);
		}else{
			launcherServo.setPosition(0);
		}
	}

	@Override
	public void periodic() {
		t.addData("Launcher state", launcherMotor.getPower() > 0 ? "On" : "Off");
	}
}
