package ai;

import utilities.Dir;
import grid.Grid;

public class AIManager
{
	Conf current;
	long ti;
	
	public AIManager(Grid g)
	{
		current = new Conf(g);
	}
	
	public void tellRound(Dir dir, int i, int j, boolean isFour)
	{
		current = current.nextConf(dir, i, j, isFour);
		System.out.println("Told round");
		current.grid.display();
	}
	
	public double speed()
	{
		return current.numberOfComputed()
				/(System.currentTimeMillis()-ti);
	}
	
	public Dir askNextMove()
	{
		ti = System.currentTimeMillis();
		current.setDepthLeft(3);
		current.computeSons();
		return current.bestMove();
	}
}
