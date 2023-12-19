package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TelemetrySubsystem extends SubsystemBase {
	public final MultipleTelemetry t;
	public TelemetrySubsystem(Telemetry telemetry){
		FtcDashboard dashboard = FtcDashboard.getInstance();
		this.t = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
	}

	public Telemetry.Item addData(String s, String s1, Object... objects) {
		return t.addData(s, s1, objects);
	}

	public Telemetry.Item addData(String s, Object o) {
		return t.addData(s, o);
	}

	public <T> Telemetry.Item addData(String s, Func<T> func) {
		return t.addData(s, func);
	}

	public <T> Telemetry.Item addData(String s, String s1, Func<T> func) {
		return t.addData(s, s1, func);
	}

	public boolean removeItem(Telemetry.Item item) {
		return t.removeItem(item);
	}

	public Object addAction(Runnable runnable) {
		// note: this behavior is correct given the current default Telemetry implementation
		return t.addAction(runnable);
	}

	public boolean removeAction(Object o) {
		return t.removeAction(o);
	}


	public String getItemSeparator() {
		return t.getItemSeparator();
	}

	public void setItemSeparator(String s) {
		t.setItemSeparator(s);
	}

	public String getCaptionValueSeparator() {
		return t.getCaptionValueSeparator();
	}

	public void setCaptionValueSeparator(String s) {
		t.setCaptionValueSeparator(s);
	}

	public void setDisplayFormat(Telemetry.DisplayFormat displayFormat) {
		t.setDisplayFormat(displayFormat);
	}

	@Override
	public void periodic() {
		t.update();
	}
}
