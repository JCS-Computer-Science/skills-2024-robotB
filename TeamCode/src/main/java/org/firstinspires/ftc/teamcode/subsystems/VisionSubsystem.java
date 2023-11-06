package org.firstinspires.ftc.teamcode.subsystems;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.DriveConstants;
import org.firstinspires.ftc.teamcode.processors.BlobProcessor;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;


public class VisionSubsystem extends SubsystemBase {
    private AprilTagProcessor aprilTag;
    private TfodProcessor tfod;
    private BlobProcessor blob;
    private VisionPortal visionPortal;
    private TelemetrySubsystem telemetry;

    public VisionSubsystem(@NonNull HardwareMap hardwareMap, TelemetrySubsystem telemetry) {
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();
        tfod = TfodProcessor.easyCreateWithDefaults();
        blob = new BlobProcessor();
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag, tfod, blob);
        this.telemetry = telemetry;

        visionPortal.setProcessorEnabled(aprilTag, false);
        visionPortal.setProcessorEnabled(tfod, false);
    }

    public void telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                telemetry.addLine(String.format("META", detection.metadata.fieldPosition.getData()));

            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));

            }
        }   // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");
    }

    public void telemetryTfod() {
        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", String.format("%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100));
            telemetry.addData("- Position", String.format("%.0f / %.0f", x, y));
            telemetry.addData("- Size", String.format("%.0f x %.0f", recognition.getWidth(), recognition.getHeight()));
        }
    }

    public void telemetryBlob() {
        telemetry.addData("Blob Selection", blob.getSelection());
    }

    public void enableTfod() {
        visionPortal.setProcessorEnabled(tfod, true);
    }

    public void disableTfod() {
        visionPortal.setProcessorEnabled(tfod, false);
    }

    public void enableAprilTag() {
        visionPortal.setProcessorEnabled(aprilTag, true);
    }

    public void disableAprilTag() {
        visionPortal.setProcessorEnabled(aprilTag, false);
    }

    /**
     * Stop the streaming session. This is an asynchronous call which does not take effect
     * immediately. If you call {@link #resumeStreaming()} before the operation is complete,
     * it will SYNCHRONOUSLY await completion of the stop command
     *
     * Stopping the streaming session is a good way to save computational resources if there may
     * be long (e.g. 10+ second) periods of match play in which vision processing is not required.
     * When streaming is stopped, no new image data is acquired from the camera and any attached
     * {@link VisionProcessor}s will lie dormant until such time as {@link #resumeStreaming()} is called.
     *
     * Stopping and starting the stream can take a second or two, and thus is not advised for use
     * cases where instantaneously enabling/disabling vision processing is required.
     */
    public void stopStreaming() {
        visionPortal.stopStreaming();
    }

    /**
     * Resume the streaming session if previously stopped by {@link #stopStreaming()}. This is
     * an asynchronous call which does not take effect immediately. If you call {@link #stopStreaming()}
     * before the operation is complete, it will SYNCHRONOUSLY await completion of the resume command.
     *
     * See notes about use case on {@link #stopStreaming()}
     */
    public void resumeStreaming() {
        visionPortal.resumeStreaming();
    }

    @Override
    public void periodic() {
        if (DriveConstants.DEBUG) {
            telemetryAprilTag();
            telemetryTfod();
            telemetryBlob();
        }
    }

    public List<AprilTagDetection> getDetections() {
        return aprilTag.getDetections();
    }
}
