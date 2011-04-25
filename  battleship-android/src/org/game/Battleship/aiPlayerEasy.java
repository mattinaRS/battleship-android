package org.game.Battleship;

import java.util.Random;

import android.graphics.Point;

public class aiPlayerEasy extends AbstractAI {
	
	public aiPlayerEasy()
	{
		for(int i=0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				shipMap[i][j] = 0;
			}
		}
	}
	
	//Grid AI keeps to track hits and misses of Player ship locations
	private int[][] mhf = new int[10][10];
	
	//To determine if current attack was hit or miss
	private boolean hitOrMiss;
	
	private Point point = new Point();
	
	//To keep track of hits that have been made
	private int hits = 0;
	
	//Keep track of how many shots taken for scoring purposes
	private int shots = 0;
	
	//10x10 grid to place ships
	private int[][] shipMap = new int [10][10];
	
	//Predefined ship locations with different direction
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
	
	//Get info about location
	public int getMHF(int x, int y)
	{
		return this.mhf[x][y];				
	}	
	
	//Set info about location
	public void setMHF(int x,int y,int z)
	{
		this.mhf[x][y]=z;			
	}
	
	//Increment the number of HITS made by 1
	public void setHits()
	{
		this.hits+=1;	
	}
	//Get number of HITS made by AI
	public int getHits()
	{
		return this.hits;	
	}
	
	//Increment number of shots done by 1
	public void setShots()
	{
		this.shots+=1;
	}
	//Get total number of shots for score calculation purposes
	public int getShots()
	{
		return this.shots;	
	}			
	
	//Sees if chosen position by AI is within defined Grid
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
		if ((isValid(x,y))&&(this.getMHF(x,y)==3))
			return true;
		else
			return false;
	}

	//This method produces random x and y coordinates for the AI to attack
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
			Point p;
			p = attackGrid();			
			return p;
		}
	}

	//Updates its internal player grid to determine where to attack next
	public void isHit(boolean didItHit)
	{
		hitOrMiss = didItHit;
		this.setShots();
		if (hitOrMiss)
		{
			this.setHits();
			{
				this.setMHF(point.x,point.y,1);
			}											
		}
		else	
		{	
			this.setMHF(point.x,point.y,0);
		}
	}

	//Function to attack the Player
	public Point aiAttack()
	{
		/* Guess a target that has not been guessed. Start of game or miss on previous turn
		Checks to see state of grid with hits and misses and returns x,y coordinate 
		to attack a location (random)*/
			Point p;
			p = attackGrid();
			return p;
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

	public int[][] aiGrid()
	{
		Random rand = new Random();
		int randXYDir = rand.nextInt(5);
		
		int[] shipLength = {5, 4, 3, 1, 2};

		for(int i=0; i<5; i++)
		{
			int x = shipLength[i];

			switch(x) 
			{
				case 5:
					if(shipFive_dir[randXYDir] == 0)
					{
						for (int j=0; j<shipLength[i]; j++)
						{
							shipMap[shipFive_x[randXYDir]++][shipFive_y[randXYDir]] = 5;
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
						for (int j=0; j<shipLength[i]; j++)
						{
							shipMap[shipFour_x[randXYDir]++][shipFour_y[randXYDir]] = 4;
						}
					}
					else
					{
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
							shipMap[shipThreeTwo_x[randXYDir]++][shipThreeTwo_y[randXYDir]] = 6;
						}
					}
					else
					{
						for (int j=0; j<(shipLength[i]+2); j++)
						{
							shipMap[shipThreeTwo_x[randXYDir]][shipThreeTwo_y[randXYDir]++] = 6;
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
		}	
		return shipMap;
	}	
}
