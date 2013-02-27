package com.example.drawonme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MyDrawingView extends View implements OnTouchListener {

	public MyDrawingView(Context context) {
		super(context);
		setOnTouchListener(this);
	}

	public MyDrawingView(Context context, AttributeSet attrs) {
		super(context,attrs);
		setOnTouchListener(this);
	}

	public MyDrawingView(Context context, AttributeSet attrs, int defStyle) {
		super(context,attrs,defStyle);
		setOnTouchListener(this);
	}
	
	
	@Override
	protected void onDraw (Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		//paint.setARGB(a, r, g, b);
		
		canvas.drawCircle(x, y, 20, paint);		
	}

	float x = 0;
	float y = 0;
	
	public void addToXandY(int xval, int yval) {
		x = x + xval;
		y = y + yval;
		
		if (x < 0) x = 0;
		if (x > getWidth()) x = getWidth();
		if (y < 0) y = 0;
		if (y > getHeight()) y = getHeight();
		
		invalidate();
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.v("onTouch","I am here");
		int action = event.getAction();			
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				Log.v("MOTION","Action Down");
				x = event.getX();
				y = event.getY();
				invalidate();
				break;
			case MotionEvent.ACTION_CANCEL:
				Log.v("MOTION","Action Cancel");
				break;
			case MotionEvent.ACTION_UP:
				Log.v("MOTION","Action Up");
				break;
			case MotionEvent.ACTION_MOVE:
				Log.v("MOTION","Action Move");
				x = event.getX();
				y = event.getY();
				invalidate();
				break;
			default:
				break;
		}
		return true;
	}
}
