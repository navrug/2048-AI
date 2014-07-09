package ai;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import grid.Grid;
import utilities.Dir;
import grid.Cell;

public class Conf
{
	//static int numberOfComputed = 0;
	final Grid grid;
	double fitness;
	double expectedFitness;
	boolean isFour;
	Dir bestNext;
	int depthLeft;
	boolean impossibleMove = false;
	EnumMap<Dir,ArrayList<Conf>> sons =
			new EnumMap<Dir,ArrayList<Conf>>(Dir.class);
	EnumMap<Dir,Double> expFitness;

	//Super for HypConf constructor
	Conf(Conf parent) {
		grid = parent.grid;
//		grid = parent.grid.clone();
	}

	public Conf(Grid grid)
	{
		this.grid = grid;
		System.out.println("Constructor grid");
		grid.display();
		//computeFitness();
	}

	Conf(Conf parent, boolean isFour, int k, int l,	int depthLeft)
	{
		grid = parent.grid.clone();
		this.isFour = isFour;
		if (isFour)
			grid.cells[k][l] = 2;
		else
			grid.cells[k][l] = 1;
		//fitness = Fitness.compute(this);
		this.depthLeft = depthLeft;
		computeSons();
	}

	Conf(Conf parent, Dir dir)
	{
		grid = parent.grid.clone();
		if (parent instanceof HypConf)
			((HypConf) parent).normalize(grid);
		grid.moved = false;
		if (!grid.move(dir)) {
			impossibleMove = true;
			return;
		}
//		lastMove = dir;
		//computeFitness();
	}

	public byte getByte(int i, int j)
	{
		return grid.cells[i][j];
	}

	public void setDepthLeft(int depthLeft)
	{
		this.depthLeft= depthLeft; 
	}

	public int numberOfEmpties()
	{
		return grid.getEmpty();
	}

	/*private*/ public void computeSons()
	{
		if (depthLeft==0) {
			computeExpFitness();
			return;
		}
		EnumMap<Dir, Conf> tempSons = new EnumMap<Dir, Conf>(Dir.class);
		for (Dir dir : Dir.values()) {
			tempSons.put(dir,new Conf(this, dir));
			if (tempSons.get(dir).impossibleMove);
			else {
				sons.put(dir, new ArrayList<Conf>());
				for (int i = 0; i<Grid.size; i++)
					for (int j = 0; j<Grid.size; j++)
						if (tempSons.get(dir).grid.cells[i][j]==0) {
							sons.get(dir).add(
									new HypConf(tempSons.get(dir),
											false,i,j,depthLeft-1));
							sons.get(dir).add(
									new HypConf(tempSons.get(dir),
											true,i,j,depthLeft-1));
						}
			}
		}
		computeExpFitness();
	}



	public Dir bestMove()
	{
		double max = 0;
		double current=0;
		Dir result = null;
		for (Dir dir : Dir.values()) {
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
		for (Conf son : sons.get(dir))
			if (son.getByte(i,j) == value)
				return son;
		throw new RuntimeException();
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
		//Base case of the recursion
		if (depthLeft == 0) {
			expectedFitness = Fitness.compute(this);
			//System.out.println("computeExpFitness() : "+expectedFitness);
			return expectedFitness;
		}
		//Compute expected fitness for each direction
		expFitness = new EnumMap<Dir,Double>(Dir.class);
		double temp;
		for (Dir dir : Dir.values()) {
			if (sons.get(dir)==null) {
				expFitness.put(dir, (double) 0);
			}
			else {
				temp = 0;
				expFitness.put(dir, (double) 0);
				for (Conf son : sons.get(dir))
					expFitness.put(dir,
							expFitness.get(dir)+son.computeExpFitness()
							*son.getProbability());
				expFitness.put(dir,
						expFitness.get(dir)*2/sons.get(dir).size());
				temp = expFitness.get(dir);
				if (temp>expectedFitness) {
					expectedFitness = temp;
					bestNext = dir;
				}
			}
		}
		return expectedFitness;
	}


}
