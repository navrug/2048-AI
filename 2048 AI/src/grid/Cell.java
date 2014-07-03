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
