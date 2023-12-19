package org.firstinspires.ftc.teamcode.processors;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;


public class BlobProcessor implements VisionProcessor, CameraStreamSource {
	public Rect rectLeft = new Rect(50, 100, 100, 200);
	public Rect rectMiddle = new Rect(320, 50, 200, 100);
	public Rect rectRight = new Rect(590, 100, 100, 200);
	Selected selection = Selected.NONE;

	Mat submat = new Mat();
	Mat hsvMat = new Mat();

	private final AtomicReference<Bitmap> lastFrame =
			new AtomicReference<>(Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565));

	@Override
	public void getFrameBitmap(@NonNull Continuation<? extends Consumer<Bitmap>> continuation) {
		continuation.dispatch(bitmapConsumer -> bitmapConsumer.accept(lastFrame.get()));
	}

	@Override
	public void init(int width, int height, CameraCalibration calibration) {
		lastFrame.set(Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565));

		convertRect(width, height, calibration, rectLeft);
		convertRect(width, height, calibration, rectMiddle);
		convertRect(width, height, calibration, rectRight);
	}

	/**
	 * @param width Width of the frame
	 * @param height Height of the frame
	 * @param calibration Camera calibration
	 * @param rect Rectangle to convert
	 */
	private void convertRect(int width, int height, @NonNull CameraCalibration calibration, @NonNull Rect rect) {
		// Adjust the rectangle to the calibration size
		rect.x = (int) (rect.x * calibration.getSize().getWidth() / width);
		rect.y = (int) (rect.y * calibration.getSize().getHeight() / height);
		rect.width = (int) (rect.width * calibration.getSize().getWidth() / width);
		rect.height = (int) (rect.height * calibration.getSize().getHeight() / height);

		// Offset the reference point to the center of the rectangle
		rect.x -= rect.width / 2;
		rect.y -= rect.height / 2;

		// Make sure the rectangle is within the calibration size
		rect.x = Math.max(0, Math.min(rect.x, (int) calibration.getSize().getWidth() - 1));
		rect.y = Math.max(0, Math.min(rect.y, (int) calibration.getSize().getHeight() - 1));
		rect.width = Math.max(1, Math.min(rect.width, (int) calibration.getSize().getWidth() - rect.x));
		rect.height = Math.max(1, Math.min(rect.height, (int) calibration.getSize().getHeight() - rect.y));
	}

	@Override
	public Object processFrame(Mat frame, long captureTimeNanos) {
		Bitmap b = Bitmap.createBitmap(frame.width(), frame.height(), Bitmap.Config.RGB_565);
		Utils.matToBitmap(frame, b);

		Imgproc.cvtColor(frame, hsvMat, Imgproc.COLOR_RGB2HSV);

		double satRectLeft = getAvgSaturation(hsvMat, rectLeft);
		double satRectMiddle = getAvgSaturation(hsvMat, rectMiddle);
		double satRectRight = getAvgSaturation(hsvMat, rectRight);

		lastFrame.set(b);

		if ((satRectLeft > satRectMiddle) && (satRectLeft > satRectRight)) {
			return Selected.LEFT;
		} else if ((satRectMiddle > satRectLeft) && (satRectMiddle > satRectRight)) {
			return Selected.MIDDLE;
		}
		return Selected.RIGHT;
	}

	protected double getAvgSaturation(Mat input, Rect rect) {
		submat = input.submat(rect);
		Scalar color = Core.mean(submat);
		return color.val[1];
	}

	private android.graphics.Rect makeGraphicsRect(Rect rect, float scaleBmpPxToCanvasPx) {
		int left = Math.round(rect.x * scaleBmpPxToCanvasPx);
		int top = Math.round(rect.y * scaleBmpPxToCanvasPx);
		int right = left + Math.round(rect.width * scaleBmpPxToCanvasPx);
		int bottom = top+ Math.round(rect.height * scaleBmpPxToCanvasPx);

		return new android.graphics.Rect(left,top,right,bottom);
	}

	@Override
	public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
		Paint selectedPaint = new Paint();
		selectedPaint.setColor(Color.RED);
		selectedPaint.setStyle(Paint.Style.STROKE);
		selectedPaint.setStrokeWidth(scaleCanvasDensity * 4);

		Paint nonSelectedPaint = new Paint(selectedPaint);
		nonSelectedPaint.setColor(Color.GREEN);

		android.graphics.Rect drawRectangleLeft = makeGraphicsRect(rectLeft, scaleBmpPxToCanvasPx);
		android.graphics.Rect drawRectangleMiddle = makeGraphicsRect(rectMiddle, scaleBmpPxToCanvasPx);
		android.graphics.Rect drawRectangleRight = makeGraphicsRect(rectRight, scaleBmpPxToCanvasPx);

		selection = (Selected) userContext;
		Boolean[] boxes = new Boolean[3];
		switch (selection) {
			case LEFT:
				boxes[0] = true;
				break;
			case MIDDLE:
				boxes[1] = true;
				break;
			case RIGHT:
				boxes[2] = true;
				break;
			default:
				Arrays.fill(boxes, false);
		}

		canvas.drawRect(drawRectangleLeft, boxes[0] ? selectedPaint : nonSelectedPaint);
		canvas.drawRect(drawRectangleMiddle, boxes[1] ? selectedPaint : nonSelectedPaint);
		canvas.drawRect(drawRectangleRight, boxes[2] ? selectedPaint : nonSelectedPaint);
	}

	public Selected getSelection() {
		return selection;
	}

	public enum Selected {
		NONE,
		LEFT,
		MIDDLE,
		RIGHT
	 }
}

