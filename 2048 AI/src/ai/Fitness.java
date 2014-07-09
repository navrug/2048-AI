package ai;

import grid.Cell;
import grid.Grid;

import java.util.PriorityQueue;

public class Fitness {
	static int numberOfComputed = 0;

	
	public static double compute(Conf c)
	{
		numberOfComputed++;
		//return computeSnake(c);
		return computePlaced(c);
	}

	static private double computeSnake(Conf c)
	{
		double fitness = 0;
		PriorityQueue<Cell> queue = new PriorityQueue<Cell>();
		for (int i = 0; i<Grid.size; i++)
			for (int j = 0; j<Grid.size; j++)
				queue.add(new Cell(i,j,c.getByte(i,j)));
		Cell first = queue.remove();
		Cell next;
		while (!queue.isEmpty()) {
			next = queue.remove();
			if (first.i == next.i && (first.j==next.j+1 || first.j==next.j-1))
					fitness += 2*first.b;
			if (first.i == next.i-1 && first.j==next.j)
				fitness += first.b;
			first = next;
		}
		fitness += 2*c.numberOfEmpties() ;
		return fitness;
	}
	

	
	static private double computePlaced(Conf c)
	{
		double fitness = 0;
		PriorityQueue<Cell> queue = new PriorityQueue<Cell>();
		for (int i = 0; i<Grid.size; i++)
			if (i%2 == 0)
			for (int j = 0; j<Grid.size; j++)
				queue.add(new Cell(i,j,c.getByte(i,j)));
			else
				for (int j = Grid.size-1 ; j>=0; j--)
					queue.add(new Cell(i,j,c.getByte(i,j)));
		Cell current = queue.remove();
		if (current.i == 0 && current.j == 0)
			fitness += 10*current.b;
		byte highest = current.b;
		Cell next;
		while (!queue.isEmpty()) {
			next = queue.remove();
			if (current.upIsNext(next))
				fitness += current.b;
			current = next;
		}
		fitness += highest*c.numberOfEmpties() ;
		return fitness;
	}

}
