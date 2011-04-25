package org.game.Battleship;

import android.graphics.Point;

public abstract class AbstractAI 
{	
	public abstract int[][] aiGrid();
	public abstract Point aiAttack();
	public abstract void isHit(boolean hit);
}
