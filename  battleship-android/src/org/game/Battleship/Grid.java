package org.game.Battleship;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class Grid extends View 
{
  	public enum Direction 
  	{
  		NORTH, EAST, SOUTH, WEST
  	}
	private static final String TAG = "BattleShip" ;
	private final GameBoard gameboard;
	private final int numrows = 10;
	private final int numcols = 10;
	private final int numships = 5;
	private boolean deploy_phase = true;
	private float width; // width of one tile
	private float height; // height of one tile
	private int selX; // X index of selection
	private int selY; // Y index of selection
	private int[][] pgrid;
	private final Rect selRect = new Rect();
	private final List<Point> HiCoord = new ArrayList<Point>(1);	
	private final List<Ships> ships = new ArrayList<Ships>(numships);	
	private final List<Rect> HiList = new ArrayList<Rect>(1);
	private final List<Rect> HitMiss = new ArrayList<Rect>(100);
	private final int[] target = new int[100];
	int turns;
	private int currSelect;
    public Grid(Context context) {
    	super(context);
    	turns = 0;
    	this.gameboard = (GameBoard) context;
    	HiList.add(new Rect());
    	HiCoord.add(new Point(-1,-1));
    	ships.add(new Ships(1, 0, 0+10, 5));
    	ships.add(new Ships(2, 1, 6+10, 2));
    	ships.add(new Ships(3, 5, 1+10, 3));
    	ships.add(new Ships(4,4, 4+10, 3));
    	ships.add(new Ships(5, 7, 2+10, 4));
    	deploy_phase = true;
    	currSelect = 0;
    	setFocusable(true);
    	setFocusableInTouchMode(true);
    }
    
    public int[][] getPgrid()
    {
    	
    	pgrid = new int[10][10];
    	int x,y,size;
    	int ordx, ordy;
    	Direction d;
    	for(int i =0; i< numships; i++)
    	{
    		x = ships.get(i).getX();
    		y = ships.get(i).getY();
    		ordy = y - 10;
    		ordx = x;
    		Log.d("ORDY", Integer.toString(ordy));
    		Log.d("ORDX", Integer.toString(ordx));
    		size = ships.get(i).getSize();
    		d = ships.get(i).getDirection();
   			switch(d)
   			{
   			case NORTH:
   	    		for(int j =0; j<size; j++)
   	    		{
   	    			Log.d("NORTH", Integer.toString(size));
   	    			pgrid[ordx][ordy] = ships.get(i).getName();
   	    			ordy++;
   	    		}
   				break;
   			case EAST:
   	    		for(int j =0; j<size; j++)
   	    		{
   	    			Log.d("EAST", Integer.toString(size));
   	    			pgrid[ordx][ordy] = ships.get(i).getName();
   	    			ordx++;
   	    		}
   				break;
   			}
    	}
    	return (pgrid);
    }
    
    public void updateUIonattk(Point p)
    {
    	Log.d("UI", "UpdatUi called");
    	int x, y;
    	x = p.x;
    	y = p.y + 10;
    	Log.d("y", Integer.toString(y));
		Rect r  = new Rect();
    	if(pgrid[p.x][p.y] == 0)
    	{
        	Log.d("UI", "MISS");
    		getRect(x,y, r);
    		HitMiss.add(r);
    		target[HitMiss.size()] = 0;
    		invalidate(r);
    	}
    	else
    	{
        	Log.d("UI", "HIT");
    		getRect(x,y, r);
    		HitMiss.add(r);    		
    		target[HitMiss.size()] = 1;
    		invalidate(r);
    	}
    }
    
    public Point updateaigrid(int[][] aigrid)
    {
    	Point p = new Point();
    	p = HiCoord.get(0);
    	HiList.get(0).setEmpty();
   		HiCoord.get(0).set(-1, -1);
   		return p;
    }
    
    public boolean isDeploy_phase() {
		return deploy_phase;
	}

	public void setDeploy_phase(boolean deploy_phase) {
		this.deploy_phase = deploy_phase;
	}

	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) 
    {
	    width = w / 10f;
	    height = h / 20f;
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
	    R.color.battleship_background));
	    canvas.drawRect(0, 0, getWidth(), getHeight(), background);
	    // Draw the board...
	    // Draw the numbers...
	    Paint hit = new Paint();
	    Paint miss = new Paint();
	    hit.setColor(getResources().getColor(R.color.battleship_hit));
	    miss.setColor(getResources().getColor(R.color.battleship_miss));
	    Paint dark = new Paint();
	    Paint ShipBorder = new Paint();
	    Paint ShipColor =new Paint();
	    dark.setColor(getResources().getColor(R.color.battleship_dark));    
	    ShipColor.setColor(getResources().getColor(R.color.battleship_dark));    
	    ShipBorder.setColor(getResources().getColor(R.color.battleship_black));    
	    Paint hilite = new Paint();
	    hilite.setColor(getResources().getColor(R.color.battleship_hilite));
	    Paint light = new Paint();
	    light.setColor(getResources().getColor(R.color.battleship_light));
	    for (int i = 0; i < 2*numrows; i++) 
	    {
	    	canvas.drawLine(0, i * height, getWidth(), i * height,
	    			light);
	    	canvas.drawLine(0, i * height + 1, getWidth(), i * height
	    			+ 1, hilite);
	    }
	    for (int i = 0; i <(numcols); i++) 
	    {
	    	canvas.drawLine(i * width, 0, i * width, getHeight(),
	    			light);
		    canvas.drawLine(i * width + 1, 0, i * width + 1,
		    getHeight(), hilite);
	    }

	    canvas.drawLine(0, numrows * height, getWidth(), numrows * height, dark);
		canvas.drawLine(0, numrows * height + 1, getWidth(), numrows * height + 1, hilite);
//		canvas.drawRect(HiList.get(0), dark);
		for(int i =0; i<numships;i++)
		{
			Rect r = ships.get(i).getHull();
			Log.d("Ship", r.toString());
			ships.get(i).setHeight(height);
			ships.get(i).setWidth(width);
			ships.get(i).setRect(ships.get(i).getX(), ships.get(i).getY());
			canvas.drawRect((ships.get(i)).getHull(), ShipColor);
		}
		if(deploy_phase == true)
		{
			ShipBorder.setStyle(style);
			canvas.drawRect((ships.get(currSelect)).getHull(), ShipBorder);
		}
		if(deploy_phase == false)
		{
			canvas.drawRect(HiList.get(0), dark);
			Log.d(TAG, "selrect =" + selRect);
			Paint selected = new Paint();
			selected.setColor(getResources().getColor(
			R.color.battleship_selected));
			canvas.drawRect(selRect, selected);
			Rect r = new Rect();
			Log.d("Hit", Integer.toString(HitMiss.size()));
			for(int i=0; i<HitMiss.size(); i++)
			{
				r = HitMiss.get(i);
				Log.d(TAG, r.toString());
				if(target[i] ==0)
				{
					canvas.drawRect(r, miss);
				}
				else
				{
					canvas.drawRect(r, hit);
				}
					
			}
			
		}
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
	    Log.d(TAG, "onKeyDown: keycode=" + keyCode + ", event="
	    + event);
	    if(deploy_phase == false)
	    {
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
		    }
	    }
	    else
	    {
	    	switch(keyCode)
	    	{
		    	case KeyEvent.KEYCODE_DPAD_UP:
			    	invalidate((ships.get(currSelect)).getHull());
		    		(ships.get(currSelect)).animate(Direction.NORTH);
		    		(ships.get(currSelect)).move(Direction.NORTH);	    		
			    	invalidate((ships.get(currSelect)).getHull());
		    		break;
			    case KeyEvent.KEYCODE_DPAD_DOWN:
			    	invalidate((ships.get(currSelect)).getHull());
		    		(ships.get(currSelect)).animate(Direction.SOUTH);
		    		(ships.get(currSelect)).move(Direction.SOUTH);	    		
			    	invalidate((ships.get(currSelect)).getHull());
			    	break;
			    case KeyEvent.KEYCODE_DPAD_LEFT:
			    	invalidate((ships.get(currSelect)).getHull());
		    		(ships.get(currSelect)).animate(Direction.WEST);
		    		(ships.get(currSelect)).move(Direction.WEST);	    		
			    	invalidate((ships.get(currSelect)).getHull());
			    	break;
			    case KeyEvent.KEYCODE_DPAD_RIGHT:
			    	invalidate((ships.get(currSelect)).getHull());
		    		(ships.get(currSelect)).animate(Direction.EAST);
		    		(ships.get(currSelect)).move(Direction.EAST);	    		
			    	invalidate((ships.get(currSelect)).getHull());
			    	break;
			    case KeyEvent.KEYCODE_TAB:
			    	switchShip();
				    break;
				default:
				   	return super.onKeyDown(keyCode, event);	    	
	    	}
	    }
	    return true;
    }
    
    private void select(int x, int y) 
    {
    	invalidate(selRect);
    	selX = Math.min(Math.max(x, 0), 2*numrows);
    	selY = Math.min(Math.max(y, 0), numcols);
    	getRect(selX, selY, selRect);
    	invalidate(selRect);
    }

    private void switchShip() 
    {
    	invalidate((ships.get(currSelect)).getHull());
    	if(currSelect == numships-1)
    	{
    		currSelect = 0;
    	}
    	else
    	{
    		currSelect++;
    	}
    	invalidate((ships.get(currSelect)).getHull());
    }
    
    private void highlight() 
    {
    	invalidate(selRect);
   		if((selX == (HiCoord.get(0)).x) && (selY == (HiCoord.get(0)).y) ) 
   		{
   			Log.d(TAG, Integer.toString(selX));
   			HiCoord.set(0, new Point(-1, -1));
   	    	getRect(-1, -1, HiList.get(0));
   			invalidate(HiList.get(0));
   			return;
   		}
    	if(((HiCoord.get(0)).x == -1)) 
    	{
    		HiCoord.set(0, new Point(selX,selY));
    	   	getRect(selX, selY, HiList.get(0));
    	   	invalidate(HiList.get(0));
    		return;    			
    	}
    }

	public int[][] getPlayerGrid() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void updateplayergrid(Point p) 
	{
		// TODO Auto-generated method stub
		
	}
}