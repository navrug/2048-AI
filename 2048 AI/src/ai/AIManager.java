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
		System.out.println("Told round");
		current.grid.display();
		System.out.println(current.grid.moved);
	}
	
	public Dir askNextMove()
	{
		current.setDepthLeft(2);
		current.computeSons();
		return current.bestMove();
	}
}
