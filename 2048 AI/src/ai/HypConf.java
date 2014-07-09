package ai;

import grid.Cell;
import grid.Grid;

public class HypConf extends Conf
{
	Cell newCell;
	
	HypConf(Conf parent, boolean isFour, int k, int l,	int depthLeft)
	{
		super(parent);
		this.isFour = isFour;
		this.depthLeft = depthLeft;
		if (isFour)
			newCell = new Cell(k, l, (byte) 2);
		else
			newCell = new Cell(k, l, (byte) 1);
		computeSons();
	}
	
	public byte getByte(int i, int j)
	{
		if (i==newCell.i && j==newCell.j)
			return newCell.b;
		else
			return grid.cells[i][j];
	}
	
	public void normalize(Grid grid)
	{
		 grid.cells[newCell.i][newCell.j] = newCell.b;

	}
	
	public int numberOfEmpties()
	{
		return grid.getEmpty()-1;
	}
}
