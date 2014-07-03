package ai;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import grid.Grid;
import utilities.Dir;
import grid.Cell;

public class Conf
{
	final private Grid grid;
	double fitness;
	double expectedFitness;
	boolean isFour;
	ArrayList<Conf> sons = new ArrayList<Conf>();
	
	public Conf(Grid grid)
	{
		this.grid = grid;
		computeFitness();
	}

	Conf(Conf parent, boolean isFour, int k, int l, Dir dir, int depthLeft)
	{
		grid = parent.grid.clone();
		this.isFour = isFour;
		if (isFour)
			grid.cells[k][l] = 2;
		else
			grid.cells[k][l] = 1;
		grid.lessEmpty();
		grid.move(dir);
		computeFitness();
		computeSons(depthLeft);
		for (Conf son : sons)
			expectedFitness += son.getProbability()*son.expectedFitness;
		expectedFitness = expectedFitness*2/sons.size();
	}

	private void computeSons(int depthLeft) {
		for (int i = 0; i<Grid.size; i++)
			for (int j = 0; j<Grid.size; j++)
				if (grid.cells[i][j]==0) {
					for (Dir d : Dir.values()) {
						sons.add(new Conf(this, false, i, j, d, depthLeft-1));
						sons.add(new Conf(this, true, i, j, d, depthLeft-1));
					}
				}
	}



	public double getFitness()
	{
		return fitness;
	}
	private double getProbability()
	{
		if (isFour)
			return 0.25;
		else
			return 0.75;
	}

	private double getExpFitness()
	{
		return expectedFitness;
	}

	private double computeFitness()
	{
		PriorityQueue<Cell> queue = new PriorityQueue<Cell>();
		for (int i = 0; i<Grid.size; i++)
			for (int j = 0; j<Grid.size; j++)
				queue.add(new Cell(i,j,grid.cells[i][j]));
		Cell first = queue.remove();
		Cell next;
		while (!queue.isEmpty()) {
			next = queue.remove();
			if ((first.i == next.i && (first.j==next.j+1 || first.j==next.j-1))
					||(first.i == next.i-1 && first.j==next.j))
				fitness += first.b;
			first = next;
		}
		fitness += grid.getEmpty()*0.5;
		return fitness;
	}

}
