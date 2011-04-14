package org.game.Battleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Grid extends View {
    private final float xStart;
    private final float yStart;
    private final float xEnd;
    private final float yEnd;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    float xt, yt, xi, yi;
    public Grid(Context context, float x1, float y1, float x2, float y2) {
        super(context);
        mPaint.setColor(0xFF000000);
        this.xStart = x1;
        this.yStart = y1;
        this.xEnd = x2;
        this.yEnd = y2;
        xt = yt = 20;
        xi= yi = 10;
    }
    
    public void onDraw(Canvas canvas) {
        Paint lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lPaint.setColor(0xFF00FFFF);
        canvas.drawRect(xStart, yStart, xEnd, yEnd, lPaint);
        canvas.drawLine(xStart/2, yStart, xStart/2, yEnd, mPaint);
        lPaint.setColor(0xFFFFFFFF);
        canvas.drawRect(xi, yi, xt, yt, lPaint);
        for(int i =20; i<xEnd; i=i+20)
        {
        	canvas.drawLine(i, yStart, i, yEnd, lPaint);
        }
        for(int i =20; i<yEnd; i=i+20)
        {
        	canvas.drawLine(xStart, i, xEnd, i, lPaint);
        }
    }
    public void setVals(float x1,float y1,float x2,float y2)
    {
    	xi = x1;
    	yi= y1;
    	xt = x2;
    	yt = y2;
    }
}
