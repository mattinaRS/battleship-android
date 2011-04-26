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
import android.widget.Button;

public class Grid extends View 
{
  	public enum Direction 
  	{
  		NORTH, EAST, SOUTH, WEST
  	}
	private static final String TAG = "BattleShip" ;
	private final GameBoard gameboard;
	private Button end;
	private final int numrows = 10;
	private final int numcols = 10;
	private final int numships = 5;
	private boolean deploy_phase = true;
	private float width; // width of one tile
	private float height; // height of one tile
	private int selX; // X index of selection
	private int selY; // Y index of selection
	private int[][] pgrid, aigrid;
	Point pattack = new Point();
	private final Rect selRect = new Rect();
	private final List<Point> HiCoord = new ArrayList<Point>(1);	
	private final List<Ships> ships = new ArrayList<Ships>(numships);	
	private final List<Ships> aidestroyed = new ArrayList<Ships>(numships);	
	private final List<Ships> pldestroyed = new ArrayList<Ships>(numships);	
	private final List<Rect> HiList = new ArrayList<Rect>(1);
	private final List<Rect> HitMiss = new ArrayList<Rect>(100);
	private final List<Integer> target = new ArrayList<Integer>(100);
	private final List<Rect> HitMissAI = new ArrayList<Rect>(100);
	private final List<Integer> targetAI = new ArrayList<Integer>(100);
	private Point p = new Point();
	private int currSelect;
    public Grid(Context context) {
    	super(context);
    	this.gameboard = (GameBoard) context;
    	end = gameboard.getButtonEnd();
    	aigrid = new int[10][10];
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
    
    public int[][] getAigrid() {
		return aigrid;
	}

	public void setAigrid(int[][] grid) {
		this.aigrid = grid;
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
    		size = ships.get(i).getSize();
    		d = ships.get(i).getDirection();
   			switch(d)
   			{
   			case NORTH:
   	    		for(int j =0; j<size; j++)
   	    		{
   	    			pgrid[ordx][ordy] = ships.get(i).getName();
   	    			ordy++;
   	    		}
   				break;
   			case EAST:
   	    		for(int j =0; j<size; j++)
   	    		{
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
    	int x, y;
    	x = p.x;
    	y = p.y + 10;
		Rect r  = new Rect();
		Rect sel = new Rect();
    	if(pgrid[p.x][p.y] == 0)
    	{
    		getRect(x,y, r);
    		HitMiss.add(r);
    		target.add(0);
    		invalidate(r);
    	}
    	else
    	{
    		getRect(x,y, r);
    		HitMiss.add(r);    		
    		target.add(1);
    		invalidate(r);
    	}
//    	Log.d("Y", Integer.toString(pattack.y));
//    	Log.d("X", Integer.toString(pattack.x));
//    	Log.d("aigrid", Integer.toString(aigrid[pattack.x][pattack.y]));
//    	Log.d("aigrid", gridtoString(aigrid, 10, 10));
    	if(aigrid[pattack.x][pattack.y] == 0)
    	{
    		Point point = new Point(HiCoord.get(0));
        	HiList.get(0).setEmpty();
       		HiCoord.get(0).set(-1, -1);
    		getRect(point.x,point.y, sel);
    		HitMissAI.add(sel);
    		targetAI.add(0);
    		invalidate(sel);
    	}
    	else
    	{
//    		Log.d("aigrid", "found ship");
    		Point point = new Point(HiCoord.get(0));
        	HiList.get(0).setEmpty();
       		HiCoord.get(0).set(-1, -1);
    		getRect(point.x,point.y, sel);
    		HitMissAI.add(sel);    		
    		targetAI.add(1);
    		invalidate(sel);
 //   		Log.d("aigrid", "found ship ends");
    	}
    }
    
    public Point updateaigrid(int[][] aigrid)
    {
    	Point p = new Point(pattack);
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
		for(int i =0; i<numships;i++)
		{
			Rect r = ships.get(i).getHull();
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
//			Log.d("aigrid", "in draw");
			canvas.drawRect(HiList.get(0), dark);
			Paint selected = new Paint();
			selected.setColor(getResources().getColor(
			R.color.battleship_black));
			selected.setStyle(style);
			canvas.drawRect(selRect, selected);
			Rect r = new Rect();
			Rect sel = new Rect();
			for(int i=0; i<HitMiss.size(); i++)
			{
				r = HitMiss.get(i);
				if(target.get(i) ==0)
				{
					canvas.drawRect(r, miss);
				}
				else
				{
					canvas.drawRect(r, hit);
				}					
			}

//			Log.d("aigrid", "updating ai ui");
			for(int i=0; i<HitMissAI.size(); i++)
			{
//				Log.d("aigrid", "updating ai ui");
				sel = HitMissAI.get(i);
				if(targetAI.get(i)==0)
				{
					canvas.drawRect(sel, miss);
				}
				else
				{
//					Log.d("aigrid", "updating hit");
					canvas.drawRect(sel, hit);
				}
					
			}
			
		}
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
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
	    		case KeyEvent.KEYCODE_W:
	    			Log.d("Key", "W");
			    	invalidate((ships.get(currSelect)).getHull());
		    		(ships.get(currSelect)).animate(Direction.NORTH);
			    	invalidate((ships.get(currSelect)).getHull());
	    			break;
	    		case KeyEvent.KEYCODE_D:
	    			Log.d("Key", "D");
			    	invalidate((ships.get(currSelect)).getHull());
		    		(ships.get(currSelect)).animate(Direction.EAST);
			    	invalidate((ships.get(currSelect)).getHull());
	    			break;
		    	case KeyEvent.KEYCODE_DPAD_UP:
			    	invalidate((ships.get(currSelect)).getHull());
		    		(ships.get(currSelect)).move(Direction.NORTH);	    		
			    	invalidate((ships.get(currSelect)).getHull());
		    		break;
			    case KeyEvent.KEYCODE_DPAD_DOWN:
			    	invalidate((ships.get(currSelect)).getHull());
		    		(ships.get(currSelect)).move(Direction.SOUTH);	    		
			    	invalidate((ships.get(currSelect)).getHull());
			    	break;
			    case KeyEvent.KEYCODE_DPAD_LEFT:
			    	invalidate((ships.get(currSelect)).getHull());
		    		(ships.get(currSelect)).move(Direction.WEST);	    		
			    	invalidate((ships.get(currSelect)).getHull());
			    	break;
			    case KeyEvent.KEYCODE_DPAD_RIGHT:
			    	invalidate((ships.get(currSelect)).getHull());
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
    	selX = Math.min(Math.max(x, 0), numrows -1);
    	selY = Math.min(Math.max(y, 0), numcols - 1);
//    	Log.d("Selx",Integer.toString(selX));
//    	Log.d("Sely",Integer.toString(selY));    	
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
//  			Log.d(TAG, Integer.toString(selX));
   			HiCoord.set(0, new Point(-1, -1));
   	    	getRect(-1, -1, HiList.get(0));
   			invalidate(HiList.get(0));
   			end.setEnabled(false);
   			return;
   		}
    	if(((HiCoord.get(0)).x == -1)) 
    	{
    		HiCoord.set(0, new Point(selX,selY));
    	   	getRect(selX, selY, HiList.get(0));
    	   	invalidate(HiList.get(0));
        	pattack.x = selY;
        	pattack.y = selX;
   			end.setEnabled(true);
    		return;    			
    	}
    }
    
    public Point getpAttk()
    {
    	return new Point(pattack);
    }
	
	private String gridtoString(int[][] arr, int r, int c)
	{
		String str = new String();
		for(int i=0; i<r; i++)
		{
			for(int j =0; j<c; j++)
			{
				str += Integer.toString(arr[i][j]);
				str += " ";
			}
			str += "\n";				
		}
		return str;
	}

}