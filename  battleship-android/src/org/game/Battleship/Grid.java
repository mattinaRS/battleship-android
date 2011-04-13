package org.game.Battleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.View.OnClickListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class Grid extends View implements KeyListener, OnClickListener{
    private final float xStart;
    private final float yStart;
    private final float xEnd;
    private final float yEnd;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float xt1,yt1,xt2,yt2;
    public Grid(Context context, float x1, float y1, float x2, float y2) {
        super(context);
        mPaint.setColor(0xFF000000);
        this.xStart = x1;
        this.yStart = y1;
        this.xEnd = x2;
        this.yEnd = y2;
        xt1=10;
        yt1=10;
        xt2=20;
        yt2=20;
    }
    
    public void onDraw(Canvas canvas) {
        Paint lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lPaint.setColor(0xFF00FFFF);
        canvas.drawRect(xStart, yStart, xEnd, yEnd, lPaint);
        canvas.drawLine(xStart/2, yStart, xStart/2, yEnd, mPaint);
        canvas.drawRect(xt1, yt1, xt2,yt2, mPaint);
        lPaint.setColor(0xFFFFFFFF);
        for(int i =10; i<xEnd; i=i+10)
        {
        	canvas.drawLine(i, yStart, i, yEnd, lPaint);
        }
        for(int i =10; i<yEnd; i=i+10)
        {
        	canvas.drawLine(xStart, i, xEnd, i, lPaint);
        }
    }

	@Override
	public void clearMetaKeyState(View arg0, Editable arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInputType() {
		Canvas canvas = new Canvas();
		xt1=40;
		yt1=40;
		xt2=80;
		yt2=80;
		onDraw(canvas);
		return 0;
	}

	@Override
	public boolean onKeyDown(View arg0, Editable arg1, int arg2, KeyEvent arg3) {
		Canvas canvas = new Canvas();
		xt1=40;
		yt1=40;
		xt2=80;
		yt2=80;
		onDraw(canvas);
		Log.d("Canvas", "KeyDown");
		return false;
	}

	@Override
	public boolean onKeyOther(View arg0, Editable arg1, KeyEvent arg2) {
		Canvas canvas = new Canvas();
		xt1=40;
		yt1=40;
		xt2=80;
		yt2=80;
		onDraw(canvas);
		return false;
	}

	@Override
	public boolean onKeyUp(View arg0, Editable arg1, int arg2, KeyEvent arg3) {
		Canvas canvas = new Canvas();
		xt1=40;
		yt1=40;
		xt2=80;
		yt2=80;
		onDraw(canvas);
		return false;
	}

	@Override
	public void onClick(View src) {
		Canvas canvas = new Canvas();
		xt1=40;
		yt1=40;
		xt2=80;
		yt2=80;
		onDraw(canvas);
		// TODO Auto-generated method stub
		
	}
}
