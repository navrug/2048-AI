package ai;

import utilities.Dir;
import grid.Grid;

public class AIManager
{
	Conf current;
	
	public AIManager(Grid g)
	{
		current = new Conf(g);
	}
	
	public void tellRound(Dir dir, int i, int j, boolean isFour)
	{
		current = current.nextConf(dir, i, j, isFour);
	}
	
	public Dir askNextMove()
	{
		current.computeSons(1);
		return current.bestMove();
	}
}
