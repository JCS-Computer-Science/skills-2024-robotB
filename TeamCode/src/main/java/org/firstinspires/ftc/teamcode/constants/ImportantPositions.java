package org.firstinspires.ftc.teamcode.constants;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.util.LUT;

public class ImportantPositions {
	public static final LUT<Integer, Pose2d> AprilTags = new LUT<Integer, Pose2d>()
	{{
//		Blue Backdrop
		add(1, new Pose2d(61.875, 41.375, Rotation2d.fromDegrees(90)));
		add(2, new Pose2d(61.875, 35.375, Rotation2d.fromDegrees(90)));
		add(3, new Pose2d(61.875, 29.375, Rotation2d.fromDegrees(90)));

//		Red Backdrop
		add(6, new Pose2d(61.875, -41.375, Rotation2d.fromDegrees(90)));
		add(5, new Pose2d(61.875, -35.375, Rotation2d.fromDegrees(90)));
		add(4, new Pose2d(61.875, -29.375, Rotation2d.fromDegrees(90)));
	}};
}
