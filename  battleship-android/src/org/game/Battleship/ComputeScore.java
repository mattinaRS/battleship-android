package org.game.Battleship;

public class ComputeScore {

	int compute(int turns, int enemydestroyed, int owndestroyed)
	{
		if(enemydestroyed <5)
			return (turns + (enemydestroyed/5)*4 - (owndestroyed/5)*4);
		else
			return (100 - turns + (enemydestroyed/5)*4 - (owndestroyed/5)*4);
	}
}
