import edu.princeton.cs.algs4.WeightedQuickUnionUF;;

public class Percolation {
	private int n;
	private WeightedQuickUnionUF qf = null;
	private WeightedQuickUnionUF qf_roof = null;
	private int num_open = 0;
	private boolean[] isOpens = null;
	
	public Percolation(int n)                // create n-by-n grid, with all sites blocked
	{
		if (n <= 0)
		{
			throw new IllegalArgumentException("Illegal argument");
		}
		this.n = n;
		
		isOpens = new boolean[n*n+2];
		isOpens[0] = true;	//top virtual point
		isOpens[n*n+1] = true;	//bottom virtual point
		qf = new WeightedQuickUnionUF(n*n+2);
		qf_roof = new WeightedQuickUnionUF(n*n+1);
		
		for (int i=1; i<=n; i++)
		{
			qf.union(0, i);
			qf_roof.union(0, i);
		}
		for (int i=n*n-n+1; i<=n*n; i++)
		{
			qf.union(n*n+1, i);
		}
	
		
	}
	public void open(int row, int col)    // open site (row, col) if it is not open already
	{
		if (row <1 || row > n || col <1 || col >n)
		{
			throw new IndexOutOfBoundsException("Index out of Bound.");
		}
		//to count the number, row has to do -1
		if (!isOpens[(row-1)*n+col])
		{
			isOpens[(row-1)*n+col] = true;
			num_open ++;
			//connect with neighbours
			//up
			if (row-1 > 0)
			{
				int up = (row-2)*n+col;
				if (isOpens[up])
				{
					qf.union((row-1)*n+col, up);
					qf_roof.union((row-1)*n+col, up);
				}
				
			}
			//down
			if (row+1 < n+1)
			{
				int down = (row)*n+col;
				if (isOpens[down])
				{
					qf.union((row-1)*n+col, down);
					qf_roof.union((row-1)*n+col, down);
				}
				
			}
			//left
			if (col-1 > 0)
			{
				int left = (row-1)*n+col-1;
				if (isOpens[left])
				{
					qf.union((row-1)*n+col, left);
					qf_roof.union((row-1)*n+col, left);
				}
				
			}
			//right
			if (col+1 < n+1)
			{
				int right = (row-1)*n+col+1;
				if (isOpens[right])
				{
					qf.union((row-1)*n+col, right);
					qf_roof.union((row-1)*n+col, right);
				}
				
			}
			
		}
		
	}
	public boolean isOpen(int row, int col)  // is site (row, col) open?
	{
		if (row <1 || row > n || col <1 || col >n)
		{
			throw new IndexOutOfBoundsException("Index out of Bound.");
		}
		
		return isOpens[(row-1)*n + col];
		
	}
	public boolean isFull(int row, int col)  // is site (row, col) full?
	{

		if (row <1 || row > n || col <1 || col >n)
		{
			throw new IndexOutOfBoundsException("Index out of Bound.");
		}
		if (isOpens[(row-1)*n+col])
		{
			return qf_roof.connected((row-1)*n+col, 0);
		}
		else
		{
			return false;
		}
		
		
	}
	public int numberOfOpenSites()       // number of open sites
	{
		return num_open;
		
	}
	public boolean percolates()              // does the system percolate?
	{
		boolean isPercolate = qf.connected(0, n*n+1);
		
		if (isPercolate)
		{
			for (int i=1; i<=n; i++)
			{
				if (this.isOpen(1, i))
				{
					return true;
				}
			}
		}
		
		return false;
		
	}
//	public static void main(String[] args) 
//	{
//		Percolation p = new Percolation(20);
//		//p.printPercolation();
//		p.open(1, 1);
//		p.open(1, 2);
//		System.out.println(p.qf.connected(1, 22));
//		
//	}
}

