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
	private ServoEx gripperTilt0, gripperTilt1, gripper, pusher;

	public double tiltPositionDown = 0.2;
	public double tiltPositionUp = 0.4;
	private double grabberPosition = 1.0;
	private double pusherPosition = 1.0;
	public GripperSubsystem(@NonNull HardwareMap hardwareMap, TelemetrySubsystem t) {
		this.t = t;

		gripperTilt0 = new SimpleServo(hardwareMap, "gripperTilt0", 0, 180);
		gripperTilt1 = new SimpleServo(hardwareMap, "gripperTilt1", 0, 180);
		gripper = new SimpleServo(hardwareMap, "gripper", 0, 180);
		pusher = new SimpleServo(hardwareMap, "pusher", 0, 270);

		gripperTilt1.setInverted(true);
		pusher.setInverted(true);
	}

	public void toggleTilt() {
		if (gripperTilt0.getPosition() == tiltPositionDown) {
			setGripperTilt(tiltPositionUp);
		} else {
			setGripperTilt(tiltPositionDown);
		}
	}

	public void toggleGripper() {
		if (gripper.getPosition() == 0.0) {
			setGripper(1.0);
		} else {
			setGripper(0.0);
		}
	}

	public void togglePusher() {
		if (pusher.getPosition() == 0.0) {
			setPusher(1.0);
		} else {
			setPusher(0.0);
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
	}
}
