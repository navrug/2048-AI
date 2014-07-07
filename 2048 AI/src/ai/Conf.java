package ai;
import java.util.ArrayList;
import java.util.Hashtable;
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
	Dir lastMove;
	ArrayList<Conf> upSons = new ArrayList<Conf>();
	ArrayList<Conf> downSons = new ArrayList<Conf>();
	ArrayList<Conf> leftSons = new ArrayList<Conf>();
	ArrayList<Conf> rightSons = new ArrayList<Conf>();
	Hashtable<Dir,Double> expFitness;

	public Conf(Grid grid)
	{
		this.grid = grid;
		computeFitness();
	}

	Conf(Conf parent, boolean isFour, int k, int l, Dir dir,
			int depthLeft)
			{
		grid = parent.grid.clone();
		this.isFour = isFour;
		if (isFour)
			grid.cells[k][l] = 2;
		else
			grid.cells[k][l] = 1;
		grid.lessEmpty();
		grid.move(dir);
		lastMove = dir;
		computeFitness();
		computeSons(depthLeft);
			}

	/*private*/ public void computeSons(int depthLeft)
	{
		if (depthLeft==0) {
			System.out.println("ExpFitness : "+computeExpFitness());
			return;
		}
		for (int i = 0; i<Grid.size; i++)
			for (int j = 0; j<Grid.size; j++)
				if (grid.cells[i][j]==0) {
					upSons.add(new Conf(this, false, i, j, Dir.UP, depthLeft-1));
					upSons.add(new Conf(this, true, i, j, Dir.UP, depthLeft-1));
					downSons.add(new Conf(this, false, i, j, Dir.DOWN, depthLeft-1));
					downSons.add(new Conf(this, true, i, j, Dir.DOWN, depthLeft-1));
					leftSons.add(new Conf(this, false, i, j, Dir.LEFT, depthLeft-1));
					leftSons.add(new Conf(this, true, i, j, Dir.LEFT, depthLeft-1));
					rightSons.add(new Conf(this, false, i, j, Dir.RIGHT, depthLeft-1));
					rightSons.add(new Conf(this, true, i, j, Dir.RIGHT, depthLeft-1));
				}
		System.out.println("ExpFitness : "+computeExpFitness());
	}


	public Dir bestMove()
	{
		double max = 0;
		double current=0;
		Dir result = null;
		for (Dir dir : expFitness.keySet()) {
			current = expFitness.get(dir);
			if (current>max) {
				max = current;
				result = dir;
			}
		}
		return result;
	}

	public Conf nextConf(Dir dir, int i, int j, boolean isFour)
	{
		byte value;
		if (isFour)
			value = (byte) 2;
		else
			value = (byte) 1;
		for (Conf son : sons(dir))
			if (grid.cells[i][j] == value)
				return son;
		throw new RuntimeException();
	}

	private ArrayList<Conf> sons(Dir dir)
	{
		switch (dir) {
		case UP : return upSons;
		case DOWN : return downSons;
		case LEFT : return leftSons;
		case RIGHT : return rightSons;
		default : return null;
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

	private double computeExpFitness()
	{
		expectedFitness = 0;
//		double temp;
//		if (upSons.size() == 0) {
//			expectedFitness = computeFitness();
//			return expectedFitness;
//		}
//		expFitness = new Hashtable<Dir,Double>();
//		for (Dir dir : Dir.values()) {
//			expFitness.put(dir, (double) 0);
//			for (Conf son : sons(dir))
//				expFitness.put(dir,
//						expFitness.get(dir)+son.expectedFitness);
//			expFitness.put(dir,
//					expFitness.get(dir)*2/sons(dir).size());
//			temp = expFitness.get(dir);
//			if (temp>)
//			expectedFitness += expFitness.get(dir)/4;
//		}
//		System.out.println("computeExpFitness() : "+expectedFitness);
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
