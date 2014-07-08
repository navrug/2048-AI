package grid;

public class Cell implements Comparable {
	public int i;
	public int j;
	public Byte b;
	
	public Cell(int i, int j, byte b)
	{
		this.i = i;
		this.j = j;
		this.b = b;
	}
	
	public boolean upSnakeCompare(Cell c)
	{
		return i<c.i ||  (i % 2 == 0 && j<c.j) || (i % 2 == 1 && j>c.j);
	}
	
	public boolean upIsNext(Cell c)
	{
		return i==c.i &&  (i % 2 == 0 && j+1 == c.j) || (i % 2 == 1 && j-1 == c.j);
	}
	
	/*
	 * Invert the natural ordering so that the head is the greatest
	 * in a PriorityQueue.
	 */
	
	@Override
	public int compareTo(Object o) {
		if (!(o instanceof Cell))
			throw new RuntimeException();
		return -b.compareTo(((Cell)o).b);
	}
}
