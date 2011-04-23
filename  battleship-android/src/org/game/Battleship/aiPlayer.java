package org.game.Battleship;

import java.util.Random;

import android.graphics.Point;
import android.util.Log;

public class aiPlayer extends AbstractAI{
	private int[][] mhsf = new int[10][10]; //aiGrid
	
	//Player grid with ships laid down....maybe provided to us after
	//player has laid the ships.  Filled with true or false at every
	//location a ship square exists.  So comp can compare against
	//when making the attack. Player's shipPlacement.
	//private boolean[][] hitOrMiss=new boolean[10][10];
	private boolean hitOrMiss;
	private Point point = new Point();
	//Remembers previous turn (hit or miss)
	//holds 1 for ai to attack around area, 0 to make a random search and attack
	private int lastShot = 0;
	
	//Coordinates of last shot
	private int lastShotCoordX;
	private int lastShotCoordY;
	
	//Checks if computer hit ship or not
	private boolean chit = false;	
	
	//row and column for comp attack
	private int r,c;
	
	//Max number of hits required by comp to win
	//final private int MAX_HITS = 17;
	
	//To keep track of hits that have been made
	private int hits = 0;
	
	//Keep track of how many shots for scoring purposes
	private int shots = 0;
	
	//10x10 grid to place ships, free space (0), occupied(1)
	private int[][] shipMap = new int [10][10];
	private int[] shipFive_x = {1, 5, 1, 1, 1};
	private int[] shipFive_y = {0, 0, 0, 1, 2};
	private int[] shipFive_dir = {0, 0, 1, 1, 1};

	private int[] shipFour_x = {7, 8, 9, 5, 6};
	private int[] shipFour_y = {2, 1, 3, 5, 6};
	private int[] shipFour_dir = {1, 1, 1, 0, 0};

	private int[] shipThree_x = {0, 9, 5, 3, 6};
	private int[] shipThree_y = {7, 7, 7, 9, 7};
	private int[] shipThree_dir = {1, 1, 1, 0, 0};

	private int[] shipThreeTwo_x = {2, 6, 4, 3, 3};
	private int[] shipThreeTwo_y = {6, 7, 4, 3, 7};
	private int[] shipThreeTwo_dir = {0, 0, 0, 1, 1};

	private int[] shipTwo_x = {2, 4, 4, 6, 9};
	private int[] shipTwo_y = {1, 1, 2, 1, 1};
	private int[] shipTwo_dir = {0, 0, 1, 1, 1};
		
	//Getting if comp hit is true or false
	public boolean getChit()
	{
		return this.chit;	
	}
	//Setting comp hit to true or false
	public void setChit(boolean value)
	{
		this.chit = value;	
	}
	
	//Get info about location
	public int getMHSF(int x, int y)
	{
		return this.mhsf[x][y];				
	}	
	
	//Set info about location
	public void setMHSF(int x,int y,int z)
	{
		this.mhsf[x][y]=z;			
	}

	//row  selected by comp to attack
	public int getR()
	{
		return this.r;	
	}	
	
	//column selected by comp to attack
	public int getC()
	{
		return this.c;	
	}
	
	//setting row for comp to attack
	public void setR(int r)
	{
		this.r = r;	
	}
	
	//setting column for comp to attack
	public void setC(int c)
	{
		this.c = c;	
	}

	//Set X coordinate of current HIT
	public void setLastShotX(int x)
	{
		this.lastShotCoordX = x;
	}
	//Set Y coordinate of current HIT
	public void setLastShotY(int y)
	{
		this.lastShotCoordY = y;
	}
	//Get X coordinate of last HIT
	public int getLastShotX()
	{
		return this.lastShotCoordX;
	}
	//Get Y coordinate of last HIT
	public int getLastShotY()
	{
		return this.lastShotCoordY;
	}
	
	//Increment the number of HITS made by comp by 1
	public void setHits()
	{
		this.hits+=1;	
	}
	//Get number of HITS made by comp
	public int getHits()
	{
		return this.hits;	
	}		
	//Increment number of shots done by comp by 1
	public void setShots()
	{
		this.shots+=1;
	}
	//Get total number of shots for score calculation purposes
	public int getShots()
	{
		return this.shots;	
	}			
	
	//Sees if chosen position by AI is valid or not
	public boolean isValid(int x, int y)
	{			
		if ((x<0)||(y<0)||(x>9)||(y>9))
			return false;	
		else
			return true;		
	}

 	//Sees if the attack should be made by validating position and making
	//sure that it is a free space.
	public boolean isPlausible(int x, int y)
	{
		if ((isValid(x,y))&&(this.getMHSF(x,y)==3))
			return true;
		else
			return false;
	}

	//Checks if any of the surrounding points are plausible
	public Point isSurrounded(int x, int y)
	{
		if (this.isPlausible(x+1,y)) {
			point.x = x+1;
			point.y = y;
			return point;
		}			
		else if (this.isPlausible(x-1,y)) {
			point.x = x-1;
			point.y = y;
			return point;
		}	
		else if (this.isPlausible(x,y+1)) {
			point.x = x;
			point.y = y+1;
			return point;
		}	
		else if (this.isPlausible(x,y-1)) {
			point.x = x;
			point.y = y-1;
			return point;
		}	
		else {
			lastShot = 0;
			attackGrid();
			return null;
		}
	}

	//This method produces x and y coordinates for the AI to attack
	//within the 10x10 grid.
	public Point attackGrid()
	{
		Random rand = new Random();
		int x = rand.nextInt(10);
		int y = rand.nextInt(10);
		if (isPlausible(x, y))
		{
			point.x = x;
			point.y = y;
			
			return point;
		}
		else
		{
			attackGrid();
			return null;
		}
	}

	public void isHit(boolean didItHit)
{
	hitOrMiss = didItHit;
	//this.setShots();
	if (hitOrMiss)	//getHitOrMiss can be grid of Human that lets us know if the chosen location is hit or miss
	{
		this.setHits();
		{
			this.setMHSF(point.x,point.y,1);
			this.lastShot = 1;
			this.setLastShotX(point.x);
			this.setLastShotY(point.y);
			this.setChit(true);
		}											
	}
	else	
	{	
		this.setMHSF(point.x,point.y,0);
		this.setChit(false);
	}
}

	public Point aiAttack()
{
	Random rand = new Random();
	Point p = new Point(); 
	p.x = rand.nextInt(10);
	p.y = rand.nextInt(10);
	return p;
	//Guess a target that has not been guessed
	//Start of game or miss on previous turn
/*	if (lastShot == 0)                      		
	{
		/* Checks to see state of grid with hits and misses 
		and returns x,y coordinate to attack a location (random)
		
		return attackGrid();
		//return randomLocation on grid
	}
   else                            					//if lastShot == 1  hit on previous turn with no sunk
   {
	   isSurrounded(this.lastShotCoordX, this.lastShotCoordY);
	   return null;
   }
*/
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//Get info about location
	public int getShipMap(int x, int y)
	{
		return this.shipMap[x][y];				
	}	

	//Set info about location
	public void setShipMap(int x,int y,int z)
	{
		this.shipMap[x][y]=z;			
	}

	public void initializeShips()
	{
		for (int i=0; i<10; i++)
		{
			for (int j=0; j<10; j++)
			{
				shipMap[i][j] = 0;
			}
		}
	}

	public int[][] aiGrid()
	{
		Log.d("AIgrid", "before initialize");
		initializeShips();
		Random rand = new Random();
		int randXYDir = rand.nextInt(5);
		
		int[] shipLength = {5, 4, 3, 1, 2};
		//boolean[] shipDirectory = {shipFive_dir[randXYDir], shipFour_dir[randXYDir], shipThree_dir[randXYDir], shipThreeTwo_dir[randXYDir], shipTwo_dir[randXYDir]};
		Log.d("AIgrid", "before for loop");
		for(int i=0; i<5; i++)
		{
			int x = shipLength[i];
			Log.d("AIgrid Length", Integer.toString(x));
			switch(x) 
			{
				case 5:
					if(shipFive_dir[randXYDir] == 0)
					{
						Log.d("AIgrid randXYDir", Integer.toString(randXYDir));
						Log.d("AIgrid direction", Integer.toString(shipFive_dir[randXYDir]));
						for (int j=0; j<shipLength[i]; j++)
						{
							Log.d("AIgrid", "before shipMap placed");
							shipMap[shipFive_x[randXYDir]++][shipFive_y[randXYDir]] = 5;
							Log.d("AIgrid", "after shipMap Placed");
						}
					}
					else
					{
						for (int j=0; j<shipLength[i]; j++)
						{
							shipMap[shipFive_x[randXYDir]][shipFive_y[randXYDir]++] = 5;
						}
					}
					break;
					
				case 4:
					if(shipFour_dir[randXYDir] == 0)
					{
						Log.d("AIgrid randXYDir four hor", Integer.toString(randXYDir));
						Log.d("AIgrid direction four hor", Integer.toString(shipFive_dir[randXYDir]));
						for (int j=0; j<shipLength[i]; j++)
						{
							shipMap[shipFour_x[randXYDir]++][shipFour_y[randXYDir]] = 4;
						}
					}
					else
					{
						Log.d("AIgrid randXYDir four ver", Integer.toString(randXYDir));
						Log.d("AIgrid direction four ver", Integer.toString(shipFive_dir[randXYDir]));
						for (int j=0; j<shipLength[i]; j++)
						{
							shipMap[shipFour_x[randXYDir]][shipFour_y[randXYDir]++] = 4;
						}
					}
					break;
					
				case 3:
					if(shipThree_dir[randXYDir] == 0)
					{
						for (int j=0; j<shipLength[i]; j++)
						{
							shipMap[shipThree_x[randXYDir]++][shipThree_y[randXYDir]] = 3;
						}
					}
					else
					{
						for (int j=0; j<shipLength[i]; j++)
						{
							shipMap[shipThree_x[randXYDir]][shipThree_y[randXYDir]++] = 3;
						}
					}
					break;
					
				case 1:
					if(shipThreeTwo_dir[randXYDir] == 0)
					{
						for (int j=0; j<(shipLength[i]+2); j++)
						{
							shipMap[shipThreeTwo_x[randXYDir]++][shipThreeTwo_y[randXYDir]] = 32;
						}
					}
					else
					{
						for (int j=0; j<(shipLength[i]+2); j++)
						{
							shipMap[shipThreeTwo_x[randXYDir]][shipThreeTwo_y[randXYDir]++] = 32;
						}
					}
					break;
					
				case 2:
					if(shipTwo_dir[randXYDir] == 0)
					{
						for (int j=0; j<shipLength[i]; j++)
						{
							shipMap[shipTwo_x[randXYDir]++][shipTwo_y[randXYDir]] = 2;
						}
					}
					else
					{
						for (int j=0; j<shipLength[i]; j++)
						{
							shipMap[shipTwo_x[randXYDir]][shipTwo_y[randXYDir]++] = 2;
						}
					}
					break;
			}
			Log.d("AIgrid index", Integer.toString(i));
		}
		Log.d("AIgrid", "before return");		
		return shipMap;
	}	

}
