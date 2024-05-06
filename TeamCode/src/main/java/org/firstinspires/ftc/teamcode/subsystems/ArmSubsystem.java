package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmSubsystem extends SubsystemBase {
	private final double maxPower=0.8;
	private DcMotorEx armMotor;
	public ArmSubsystem(HardwareMap hmap){
		armMotor = hmap.get(DcMotorEx.class, "arm");
		armMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
	}

	public void moveArm(double power){
		armMotor.setPower(power*maxPower);
	}

}
