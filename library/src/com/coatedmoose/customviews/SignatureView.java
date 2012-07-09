/**
 * 
 */
package com.coatedmoose.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * A simple view to capture a path traced onto the screen. Initially intended to be used to captures signatures.
 * 
 * @author Andrew Crichton
 * @version 0.1
 */
public class SignatureView extends View {

	//private static final String TAG = "SignatureView";


	private Path mPath;
	private Paint mPaint;
	private Bitmap mBitmap;
	private Canvas mCanvas;

	private float curX, curY;

	private static final int TOUCH_TOLERANCE = 4;
    private static final int STROKE_WIDTH = 2;

	public SignatureView(Context context) {
		super(context);
		init();
	}
	public SignatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public SignatureView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setFocusable(true);
		mPath = new Path();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(STROKE_WIDTH);
	}

	/**
	 * Set the color of the signature. 
	 * @param color the hex representation of the desired color, most likely an instance of Color.*
	 */
	public void setSigColor(int color) {
		mPaint.setColor(color);
	}

	/**
	 * Set the color of the signature. For simpler option just us setSigColor(int color).
	 * @param a alpha value
	 * @param r red value
	 * @param g green value
	 * @param b blue value\
	 */
	public void setSigColor(int a, int r, int g, int b) {
		mPaint.setARGB(a, r, g, b);
	}

	public void clearSig() {
		if (mCanvas != null) {
			mCanvas.drawColor(Color.WHITE);
			mCanvas.drawPaint(mPaint);
			mPath.reset();
			invalidate();
		}
	}

    public Bitmap getImage() {
        return this.mBitmap;
    }

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		int bitW = mBitmap != null ? mBitmap.getWidth() : 0;
		int bitH = mBitmap != null ? mBitmap.getWidth() : 0;

		// If the width and height of the bitmap are bigger than the
		// new defined size, then keep the excess bitmap and return
		// (Part of the backing bitmap will be clipped off, but it
		// will still exist)
		if (bitW >= w && bitH >= h) {
			return;
		}

		if (bitW < w) bitW = w;
		if (bitH < h) bitH = h;

		// create a new bitmap and canvas for the new size
		Bitmap newBitmap = Bitmap.createBitmap(bitW, bitH, Bitmap.Config.RGB_565);
		Canvas newCanvas = new Canvas();
		newCanvas.setBitmap(newBitmap);
		// If the old bitmap exists, redraw it onto the new bitmap
		if (mBitmap != null) {
			newCanvas.drawBitmap(mBitmap, 0, 0, null);
		}
		// Replace the old bitmap and canvas with the new one
		mBitmap = newBitmap;
		mCanvas = newCanvas;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(mBitmap, 0, 0, mPaint);
		canvas.drawPath(mPath, mPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchDown(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			touchMove(x, y);
			break;
		case MotionEvent.ACTION_UP:
			touchUp();
			break;
		}
		invalidate();
		return true;
	}


	/**----------------------------------------------------------
	 * Private methods
	 **---------------------------------------------------------*/

	private void touchDown(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		curX = x;
		curY = y;
	}

	private void touchMove(float x, float y) {
		float dx = Math.abs(x - curX);
		float dy = Math.abs(y - curY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(curX, curY, (x + curX)/2, (y + curY)/2);
			curX = x;
			curY = y;
		}
	}

	private void touchUp() {
		mPath.lineTo(curX, curY);
		mCanvas.drawPath(mPath, mPaint);
		mPath.reset();
	}
}
