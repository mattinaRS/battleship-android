package org.game.Battleship;

import android.graphics.Point;

public abstract class Aiplayer 
{	
	public abstract int[][] aiGrid();
	public abstract Point attackCell();
	public abstract void isHit(boolean hit);
	public abstract void setPlayerGrid(int[][] g);
}
