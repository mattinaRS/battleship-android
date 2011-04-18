package org.game.Battleship;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

public class Grid extends View {
	private static final String TAG = "BattleShip" ;
	private final GameBoard gameboard;
	private float width; // width of one tile
	private float height; // height of one tile
	private int selX; // X index of selection
	private int selY; // Y index of selection
	private final Rect selRect = new Rect();
	private final List<Point> HiCoord = new ArrayList<Point>(5);	
	private final List<Ship> ships = new ArrayList<Ship>(5);	
	private final List<Rect> HiList = new ArrayList<Rect>(5);
	
    public Grid(Context context) {
    	super(context);
    	this.gameboard = (GameBoard) context;
	    for(int i =0; i< 5; i++)
	    {
	    	HiList.add(new Rect());
	    	HiCoord.add(new Point(-1,-1));
	    }
    	ships.add(new Ship("Carrier", 0, 0+8, 5));
    	ships.add(new Ship("GunBoat", 1, 6+8, 2));
    	ships.add(new Ship("Destroyer", 5, 1+8, 3));
    	ships.add(new Ship("Submarine",4, 4+8, 3));
    	ships.add(new Ship("Battleship", 7, 2+8, 4));
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
    	Style style = Paint.Style.STROKE;
	    Paint background = new Paint();
	    background.setColor(getResources().getColor(
	    R.color.puzzle_background));
	    canvas.drawRect(0, 0, getWidth(), getHeight(), background);
	    // Draw the board...
	    // Draw the numbers...
	    Paint dark = new Paint();
	    Paint ShipBorder = new Paint();
	    Paint ShipColor =new Paint();
	    dark.setColor(getResources().getColor(R.color.puzzle_dark));    
	    ShipColor.setColor(getResources().getColor(R.color.puzzle_white));    
	    ShipBorder.setColor(getResources().getColor(R.color.puzzle_black));    
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
//		canvas.drawRect((3*width+2), (4*height+2), (4*width-2), (5*height-2), ShipColor);
//		canvas.drawLine(10 * width, 0, 10 * width, getHeight(), dark);
//		canvas.drawLine(10 * width + 1, 0, 10 * width + 1, getHeight(), hilite);
		Log.d(TAG, "selrect =" + selRect);
		Paint selected = new Paint();
		selected.setColor(getResources().getColor(
		R.color.puzzle_selected));
		canvas.drawRect(selRect, selected);
		for(int i =0; i<5;i++)
		{
			canvas.drawRect(HiList.get(i), dark);
			Rect r = ships.get(i).getHull();
			Log.d("Ship", r.toString());
			ships.get(i).setRect(ships.get(i).getX(), ships.get(i).getY());
			Rect r1 = ships.get(i).getHull();
			Log.d("Shipafter", r1.toString());
			ShipBorder.setStyle(style);
			canvas.drawRect((ships.get(i)).getHull(), ShipColor);
			canvas.drawRect((ships.get(i)).getHull(), ShipBorder);
		}
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
	    Log.d(TAG, "onKeyDown: keycode=" + keyCode + ", event="
	    + event);
	    switch (keyCode) 
	    {
	    	case KeyEvent.KEYCODE_DPAD_UP:
	    		select(selX, selY - 1);
	    		break;
		    case KeyEvent.KEYCODE_DPAD_DOWN:
		    	select(selX, selY + 1);
		    	break;
		    case KeyEvent.KEYCODE_DPAD_LEFT:
		    	select(selX - 1, selY);
		    	break;
		    case KeyEvent.KEYCODE_DPAD_RIGHT:
		    	select(selX + 1, selY);
		    	break;
		    case KeyEvent.KEYCODE_ENTER:
		    	highlight();
		    	break;
		    default:
		    	return super.onKeyDown(keyCode, event);
	    }
	    return true;
    }
    
    private void select(int x, int y) 
    {
    	invalidate(selRect);
    	selX = Math.min(Math.max(x, 0), 7);
    	selY = Math.min(Math.max(y, 0), 7);
    	getRect(selX, selY, selRect);
    	invalidate(selRect);
    }

    private void highlight() 
    {
    	invalidate(selRect);
    	for(int i=0; i<5;i++)
    	{
    		if((selX == (HiCoord.get(i)).x) && (selY == (HiCoord.get(i)).y) ) 
    		{
    			Log.d(TAG, Integer.toString(selX));
    			HiCoord.set(i, new Point(-1, -1));
    	    	getRect(-1, -1, HiList.get(i));
    			invalidate(HiList.get(i));
    			return;
    		}
    	}
    	for(int i=0; i<5;i++)
    	{
    		if(((HiCoord.get(i)).x == -1)) 
    		{
    			HiCoord.set(i, new Point(selX,selY));
    	    	getRect(selX, selY, HiList.get(i));
    	    	invalidate(HiList.get(i));
    			return;    			
    		}
    	}
    }
    private class Ship
    {
    	private String name;
    	int ix,iy, size;  
    	Rect hull;
    	
    	public Ship(String n, int x, int y, int sz )
    	{
    		name = new String(n);
    		hull = new Rect();
    		ix = x;
    		iy =y;
    		size = sz;
    	}
    	
    	public void setRect(int x, int y)
    	{
        	hull.set((int) (x * width), (int) (y * height), (int) (x
        			* width + width), (int) (y*height +height*size));    
    	}
    	
    	public Rect getHull()
    	{
    		return (new Rect(hull));
    	}
    	
    	public int getX()
    	{
    		return (ix);
    	}
    	public int getY()
    	{
    		return (iy);
    	}
    	public int getSize()
    	{
    		return (size);
    	}
    }
}