package org.game.Battleship;

public class ComputeScore {

	int compute(int turns, int enemydestroyed, int owndestroyed)
	{
		if(enemydestroyed <5)
			return (turns + (enemydestroyed)*10 - (owndestroyed)*5);
		else
			return (170 - turns + (enemydestroyed)*10 - (owndestroyed)*5);
	}
}
