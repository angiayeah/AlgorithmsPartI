import edu.princeton.cs.algs4.Average;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats
{
	private double[] results = null;
	public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n gri
	{
		if (n<=0 || trials <=0)
		{
			throw new IllegalArgumentException("Illegal argument in Stats");
		}
		
		results = new double[trials];
		
		for (int i=0; i<trials; i++)
		{
			Percolation percolation = new Percolation(n);
			int num = 0;
			while (!percolation.percolates())
			{
				int row = StdRandom.uniform(1,n+1);		//[0, n)
				int col = StdRandom.uniform(1,n+1);
				if (!percolation.isOpen(row, col))
				{
					percolation.open(row, col);
				}
				num++;
			}
			int num_open = percolation.numberOfOpenSites();
			results[i] = (double)num_open/(double)(n*n);
			
		}
	}
	public double mean()                          // sample mean of percolation threshold
	{
		double sum = 0;
		for (int i=0; i<results.length; i++)
		{
			sum += results[i];
		}
		
		return sum/(double)(results.length);
		
	}
	public double stddev()                        // sample standard deviation of percolation threshold
	{
		double avg = this.mean();
		double sum = 0;
		for (int i=0; i<results.length; i++)
		{
			sum += (results[i] - avg) * (results[i] - avg);
		}
		return Math.sqrt(sum/(double)(results.length-1));
		
	}
	public double confidenceLo()                  // low  endpoint of 95% confidence interval
	{
		double avg = this.mean();
		double std = this.stddev();
		return avg-1.96*std/Math.sqrt(results.length);
		
	}
	public double confidenceHi()                  // high endpoint of 95% confidence interval
	{
		double avg = this.mean();
		double std = this.stddev();
		return avg+1.96*std/Math.sqrt(results.length);
		
	}
	public static void main(String[] args) throws Exception        // test client (described below)
	{
		int n;
		int trials;
		if (args.length != 2)
		{
			throw new Exception("number of arguments error");
		}
		n = Integer.parseInt(args[0]);
		trials = Integer.parseInt(args[1]);
		
		PercolationStats stats = new PercolationStats(n, trials);
		System.out.println(stats.mean());
		System.out.println(stats.stddev());
		System.out.println(stats.confidenceLo() + " " +stats.confidenceHi());
		
		
	}
}