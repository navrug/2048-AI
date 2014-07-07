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
	final Grid grid;
	double fitness;
	double expectedFitness;
	boolean isFour;
	Dir lastMove;
	Dir bestNext;
	int depthLeft;
	boolean impossibleMove = false;
	EnumMap<Dir,ArrayList<Conf>> sons = new EnumMap<Dir,ArrayList<Conf>>(Dir.class);
	//	ArrayList<Conf> upSons = new ArrayList<Conf>();
	//	ArrayList<Conf> downSons = new ArrayList<Conf>();
	//	ArrayList<Conf> leftSons = new ArrayList<Conf>();
	//	ArrayList<Conf> rightSons = new ArrayList<Conf>();
	EnumMap<Dir,Double> expFitness;

	public Conf(Grid grid)
	{
		this.grid = grid;
		System.out.println("Constructor grid");
		grid.display();
		computeFitness();
	}

	Conf(Conf parent, boolean isFour, int k, int l,	int depthLeft)
	{
		grid = parent.grid.clone();
		this.isFour = isFour;
		if (isFour)
			grid.cells[k][l] = 2;
		else
			grid.cells[k][l] = 1;
		computeFitness();
		this.depthLeft = depthLeft;
	}

	Conf(Conf parent, Dir dir)
	{
		System.out.println("Trying "+dir+".");
		grid = parent.grid.clone();
		grid.moved = false;
		if (!grid.move(dir)) {
			System.out.println("Imp move : "+dir);
			impossibleMove = true;
			return;
		}
		lastMove = dir;
		computeFitness();
	}
	


	public void setDepthLeft(int depthLeft)
	{
		this.depthLeft= depthLeft; 
	}

	/*private*/ public void computeSons()
	{
		if (depthLeft==0) {
			System.out.println("ExpFitness : "+computeExpFitness());
			return;
		}
		System.out.println("Trying possible moves...");
		EnumMap<Dir, Conf> tempSons = new EnumMap<Dir, Conf>(Dir.class);
		for (Dir dir : Dir.values()) {
			tempSons.put(dir,new Conf(this, dir));
			if (tempSons.get(dir).impossibleMove)
				System.out.println(dir+" impossible");
			else {
				sons.put(dir, new ArrayList<Conf>());
				for (int i = 0; i<Grid.size; i++)
					for (int j = 0; j<Grid.size; j++)
						if (tempSons.get(dir).grid.cells[i][j]==0) {
							sons.get(dir).add(
									new Conf(tempSons.get(dir),false,i,j,depthLeft-1));
							sons.get(dir).add(
									new Conf(tempSons.get(dir),true,i,j,depthLeft-1));
						}
				System.out.println("Number of sons in "+dir+" : "+sons.get(dir).size());
			}
		}
		System.out.println("ExpFitness : "+computeExpFitness());
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
			if (son.grid.cells[i][j] == value)
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
		//Handling impossible moves
		if (impossibleMove) {
			System.out.println("Impossible move");
			return 0;
		}
		//Base case of the recursion
		if (depthLeft == 0) {
			expectedFitness = computeFitness();
			//System.out.println("computeExpFitness() : "+expectedFitness);
			return expectedFitness;
		}
		//Compute expected fitness for each direction
		expFitness = new EnumMap<Dir,Double>(Dir.class);
		double temp;
		for (Dir dir : Dir.values()) {
			if (sons.get(dir)==null) {
				expFitness.put(dir, (double) 0);
				System.out.println("Impossible direction");
			}
			else {
				temp = 0;
				expFitness.put(dir, (double) 0);
				for (Conf son : sons.get(dir))
					expFitness.put(dir,
							expFitness.get(dir)+son.computeExpFitness());
				expFitness.put(dir,
						expFitness.get(dir)*2/sons.get(dir).size());
				temp = expFitness.get(dir);
				System.out.println("temp"+temp);
				if (temp>expectedFitness) {
					expectedFitness = temp;
					bestNext = dir;
				}
			}
		}
		System.out.println("computeExpFitness() : "+expectedFitness);
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
