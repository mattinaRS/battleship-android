package org.game.Battleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Grid extends View{
    private final float xStart;
    private final float yStart;
    private final float xEnd;
    private final float yEnd;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Grid(Context context, float x1, float y1, float x2, float y2) {
        super(context);
        mPaint.setColor(0xFF000000);
        this.xStart = x1;
        this.yStart = y1;
        this.xEnd = x2;
        this.yEnd = y2;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lPaint.setColor(0xFF00FFFF);
        canvas.drawRect(xStart, yStart, xEnd, yEnd, lPaint);
    	canvas.drawLine(xStart/2, yStart, xStart/2, yEnd, mPaint);
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
}
