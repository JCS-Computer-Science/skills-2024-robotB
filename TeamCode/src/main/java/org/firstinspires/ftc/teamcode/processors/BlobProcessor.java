package org.firstinspires.ftc.teamcode.processors;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;


import java.util.concurrent.atomic.AtomicReference;
import org.firstinspires.ftc.robotcore.external.function.Continuation;


public class BlobProcessor implements VisionProcessor, CameraStreamSource {
	public Rect rectLeft = new Rect(100, 240, 80, 80);
	public Rect rectMiddle = new Rect(320, 240, 80, 80);
	public Rect rectRight = new Rect(534, 240, 80, 80);
	Selected selection = Selected.NONE;

	Mat submat = new Mat();
	Mat hsvMat = new Mat();

	private final AtomicReference<Bitmap> lastFrame =
			new AtomicReference<>(Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565));

	@Override
	public void getFrameBitmap(Continuation<? extends Consumer<Bitmap>> continuation) {
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
	private void convertRect(int width, int height, CameraCalibration calibration, Rect rect) {

		// Adjust the rectangle to the calibration size
		rect.x = (int) (rect.x * calibration.getSize().getWidth() / width);
		rect.y = (int) (rect.y * calibration.getSize().getHeight() / height);
		rect.width = (int) (rect.width * calibration.getSize().getWidth() / width);
		rect.height = (int) (rect.height * calibration.getSize().getHeight() / height);

		// Make sure the rectangle is within the calibration size
		rect.x = Math.max(0, Math.min(rect.x, (int) calibration.getSize().getWidth() - 1));
		rect.y = Math.max(0, Math.min(rect.y, (int) calibration.getSize().getHeight() - 1));
		rect.width = Math.max(1, Math.min(rect.width, (int) calibration.getSize().getWidth() - rect.x));
		rect.height = Math.max(1, Math.min(rect.height, (int) calibration.getSize().getHeight() - rect.y));

		// Offset the reference point to the center of the rectangle
		rect.x -= rect.width / 2;
		rect.y -= rect.height / 2;


	}

	@Override
	public Object processFrame(Mat frame, long captureTimeNanos) {
		Bitmap b = Bitmap.createBitmap(frame.width(), frame.height(), Bitmap.Config.RGB_565);
		Utils.matToBitmap(frame, b);
		lastFrame.set(b);

		Imgproc.cvtColor(frame, hsvMat, Imgproc.COLOR_RGB2HSV);

		double satRectLeft = getAvgSaturation(hsvMat, rectLeft);
		double satRectMiddle = getAvgSaturation(hsvMat, rectMiddle);
		double satRectRight = getAvgSaturation(hsvMat, rectRight);

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
		switch (selection) {
			case LEFT:
				canvas.drawRect(drawRectangleLeft, selectedPaint);
				canvas.drawRect(drawRectangleMiddle, nonSelectedPaint);
				canvas.drawRect(drawRectangleRight, nonSelectedPaint);
				break;
			case MIDDLE:
				canvas.drawRect(drawRectangleLeft, nonSelectedPaint);
				canvas.drawRect(drawRectangleMiddle, selectedPaint);
				canvas.drawRect(drawRectangleRight, nonSelectedPaint);
				break;
			case RIGHT:
				canvas.drawRect(drawRectangleLeft, nonSelectedPaint);
				canvas.drawRect(drawRectangleMiddle, nonSelectedPaint);
				canvas.drawRect(drawRectangleRight, selectedPaint);
				break;
			case NONE:
				canvas.drawRect(drawRectangleLeft, nonSelectedPaint);
				canvas.drawRect(drawRectangleMiddle, nonSelectedPaint);
				canvas.drawRect(drawRectangleRight, nonSelectedPaint);
				break;
		}
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

