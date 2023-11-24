package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DepositorSubsystem extends SubsystemBase {
	private final SimpleServo depositorServo;
	private boolean isOpen = false;
	public DepositorSubsystem(@NonNull HardwareMap hardwareMap) {
		depositorServo = new SimpleServo(hardwareMap, "depositorServo", 0, 180);
	}

	public void open() {
		depositorServo.setPosition(1);
	}

	public void close() {
		depositorServo.setPosition(0);
	}
}
