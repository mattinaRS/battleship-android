package org.game.Battleship;

import org.game.Battleship.Grid.Direction;

import android.graphics.Rect;

public class Ships
{
	private Direction direction = Direction.NORTH;
	private int name;
	int ix,iy, size;  
	Rect hull;
	float height, width;
	
	public Ships(int n, int x, int y, int sz )
	{
		name = n;
		hull = new Rect();
		ix = x;
		iy =y;
		size = sz;
	}
	
	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setRect(int x, int y)
	{
		if(direction == Direction.NORTH)
		{
	    	hull.set((int) (x * width), (int) (y * height), (int) (x
	    			* width + width), (int) (y*height + height*size));    
		}
		else if(direction == Direction.SOUTH)
		{
	    	hull.set((int) (x * width), (int) (y * height), (int) (x
	    			* width + width), (int) (y*height - height*size ));    
		}
		else if(direction == Direction.EAST)
		{
	    	hull.set((int) (x * width), (int) (y * height), (int) (x
	    			* width + width*size), (int) (y*height + height));    
		}
		else
		{
	    	hull.set((int) (x * width), (int) (y * height), (int) (x
	    			* width - width*size), (int) (y*height + height));    
		}
	}
	
	public void move(Direction d)
	{
   	    switch (d) 
   	    {
    	      case NORTH:
    	    	  iy--;
    	    	  direction = Direction.NORTH;
    	    	  setRect(ix,iy);
    	          break;
    	      case SOUTH:
    	    	  iy++;
    	    	  setRect(ix,iy);
    	        break;
    	      case EAST:
    	    	  ix++;
    	    	  direction = Direction.EAST;
    	    	  setRect(ix,iy);
    	        break;
    	      case WEST:
    	    	  ix--;
    	    	  setRect(ix,iy);
    	    	  break;
    	 }    		
	}
	public Direction getDirection()
	{
		return this.direction;
	}
	
	public int getName() {
		return name;
	}

	public void animate(Direction d)
	{
		
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
