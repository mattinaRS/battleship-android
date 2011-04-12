package org.game.Battleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Ship extends View {
    private final float x;
    private final float y;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Ship(Context context, float x, float y) {
        super(context);
        mPaint.setColor(0xFFFF0000);
        this.x = x;
        this.y = y;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(x, y, x+10, y+10, mPaint);
    }
}
