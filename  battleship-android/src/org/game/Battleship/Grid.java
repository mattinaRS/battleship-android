package org.game.Battleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

public class Grid extends View {
	private static final String TAG = "Sudoku" ;
	private final GameBoard gameboard;
	private float width; // width of one tile
	private float height; // height of one tile
	private int selX; // X index of selection
	private int selY; // Y index of selection
	private final Rect selRect = new Rect();
	
    public Grid(Context context) {
    	super(context);
    	this.gameboard = (GameBoard) context;
    	setFocusable(true);
    	setFocusableInTouchMode(true);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) 
    {
	    width = w / 8f;
	    height = h / 16f;
	    getRect(selX, selY, selRect);
	    Log.d(TAG, "onSizeChanged: width " + width + ", height "
	    + height);
	    super.onSizeChanged(w, h, oldw, oldh);
	}
    
    private void getRect(int x, int y, Rect rect) 
    {
    	rect.set((int) (x * width), (int) (y * height), (int) (x
    			* width + width), (int) (y * height + height));
    }
    
    @Override
    protected void onDraw(Canvas canvas) 
    {
	    // Draw the background...
	    Paint background = new Paint();
	    background.setColor(getResources().getColor(
	    R.color.puzzle_background));
	    canvas.drawRect(0, 0, getWidth(), getHeight(), background);
	    // Draw the board...
	    // Draw the numbers...
	    Paint dark = new Paint();
	    dark.setColor(getResources().getColor(R.color.puzzle_dark));    
	    // Draw the hints...
	    // Draw the selection...
	    Paint hilite = new Paint();
	    hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
	    Paint light = new Paint();
	    light.setColor(getResources().getColor(R.color.puzzle_light));
	    // Draw the minor grid lines
	    for (int i = 0; i < 16; i++) 
	    {
	    	canvas.drawLine(0, i * height, getWidth(), i * height,
	    			light);
	    	canvas.drawLine(0, i * height + 1, getWidth(), i * height
	    			+ 1, hilite);
	    }
	    for (int i = 0; i < 8; i++) 
	    {
	    	canvas.drawLine(i * width, 0, i * width, getHeight(),
	    			light);
		    canvas.drawLine(i * width + 1, 0, i * width + 1,
		    getHeight(), hilite);
	    }

	    canvas.drawLine(0, 8 * height, getWidth(), 8 * height, dark);
		canvas.drawLine(0, 8 * height + 1, getWidth(), 8 * height + 1, hilite);
//		canvas.drawLine(10 * width, 0, 10 * width, getHeight(), dark);
//		canvas.drawLine(10 * width + 1, 0, 10 * width + 1, getHeight(), hilite);
    }
}