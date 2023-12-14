package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Vector2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TelemetrySubsystem extends SubsystemBase {
	private final MultipleTelemetry t;
	private final FtcDashboard dashboard;
	private final TelemetryPacket p;
	public TelemetrySubsystem(Telemetry telemetry){
		this.dashboard = FtcDashboard.getInstance();
		this.p = new TelemetryPacket();
		this.t = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

	}

	public void addData(String label, Object value) {
		t.addData(label, value);
	}
	public void addLine(String line){
		t.addLine(line);
	}

	@Override
	public void periodic() {
		t.update();
	}
}
