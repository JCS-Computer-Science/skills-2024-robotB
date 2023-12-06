package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class GripperSubsystem extends SubsystemBase {
	private final TelemetrySubsystem t;
	private ServoEx gripperTilt0, gripperTilt1, gripper, pusher, setter, sweep;

	public double tiltPositionDown = 0;
	public double tiltPositionUp = 1.0;
	public boolean isTilted=false;
	public boolean isGripped=false;
	private double grabberPosition = 1.0;
	private double pusherPosition = 1.0;
	public GripperSubsystem(@NonNull HardwareMap hardwareMap, TelemetrySubsystem t) {
		this.t = t;

		gripperTilt0 = new SimpleServo(hardwareMap, "gripperTilt0", 40, 270);
		gripperTilt1 = new SimpleServo(hardwareMap, "gripperTilt1", 40, 270);
		gripper = new SimpleServo(hardwareMap, "gripper", 0, 40);
		pusher = new SimpleServo(hardwareMap, "pusher", 0, 270);
		setter = new SimpleServo(hardwareMap, "setter", 0, 180);

		sweep = new SimpleServo(hardwareMap, "sweep", 0, 180);

		setter.setPosition(0);

		gripper.setInverted(true);
		gripper.setPosition(1.0);

		gripperTilt0.setInverted(true);

		pusher.setInverted(true);
		pusher.setPosition(1.0);
	}

	public void toggleSetter(){
		if (setter.getPosition() == 0) {
			setter.setPosition(0.1);
		} else {
			setter.setPosition(0);
		}
	}

	public void toggleTilt() {
		if (isTilted) {
			setGripperTilt(tiltPositionDown);
		} else {
			setGripperTilt(tiltPositionUp);

		}
		isTilted=!isTilted;
	}

	public void toggleGripper() {
		if (isGripped) {
//			setGripper(0.75);
			gripper.setPosition(0);
		} else {
//			setGripper(1.0);
			gripper.setPosition(1);
		}
		isGripped=!isGripped;
	}

	public void togglePusher() {
		if (pusher.getPosition() == 0.0) {
			setPusher(1.0);
		} else {
			setPusher(0.0);
		}
	}

	public void toggleSweep() {
		if (sweep.getPosition() == 0.0) {
			sweep.setPosition(1.0);
		} else {
			sweep.setPosition(0.0);
		}
	}

	public void setGripperTilt(double position) {
		gripperTilt0.setPosition(position);
		gripperTilt1.setPosition(position);
	}

	public void setGripper(double position) {
		gripper.setPosition(position);
	}

	public void setPusher(double position) {
		pusher.setPosition(position);
	}


	@Override
	public void periodic() {
		t.addLine("\nGripper Subsystem");
		t.addData("Gripper Tilt 0", gripperTilt0.getPosition());
		t.addData("Gripper Tilt 1", gripperTilt1.getPosition());
		t.addData("Gripper", gripper.getPosition());
		t.addData("Pusher", pusher.getPosition());
		t.addData("Sweep", sweep.getPosition());
	}
}
