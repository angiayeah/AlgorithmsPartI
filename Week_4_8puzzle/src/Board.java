import java.awt.print.Printable;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class Board {
	private int[][] game;
	public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
	{
		game = new int[blocks.length][blocks.length];
		for (int i=0; i<blocks.length; i++)
			for (int j=0; j<blocks[0].length; j++)
			{
				game[i][j] = blocks[i][j];
			}
	}
    // (where blocks[i][j] = block in row i, column j)
	
	public int dimension()                 // board dimension n
	{
		return game.length;
		
	}
	public int hamming()                   // number of blocks out of place
	{
		int ham = 0;
		for (int i=0; i<game.length; i++)
		{
			for (int j=0; j<game[0].length; j++)
			{
				if (game[i][j] != 0 && game[i][j] != i*game.length+j+1 )
				{
					ham ++;
				}
			}
		}
		return ham;
	}
	public int manhattan()                 // sum of Manhattan distances between blocks and goal{
	{
		int sum = 0;
		for (int i=0; i<game.length; i++)
		{
			for (int j=0; j<game[0].length; j++)
			{
				if (game[i][j] == 0) continue;
				int real_i = (game[i][j]-1)/game.length;
				int real_j = game[i][j]-real_i*game.length-1;
				//System.out.println(game[i][j] + " (" + real_i+ ", " +real_j +")");
				sum += Math.abs(real_i-i) + Math.abs(real_j-j);
			}
		}
		return sum;
		
	}
	public boolean isGoal()                // is this board the goal board?
	{
		boolean goal = true;
		for (int i=0; i<game.length; i++)
		{
			for (int j=0; j<game[0].length; j++)
			{
				if (game[i][j] == 0) continue;
				if (game[i][j] != i*game.length+j+1) return false;
				//System.out.println(game[i][j] + " (" + real_i+ ", " +real_j +")");
			}
		}
		return true;
		
	}
	public Board twin()                    // a board that is obtained by exchanging any pair of blocks
	{
		int[][] temp_array = new int[game.length][game.length];
		for (int i=0; i<temp_array.length; i++)
			for (int j=0; j<temp_array.length; j++)
			{
				temp_array[i][j] = game[i][j];
				
			}
		for (int i=0; i<temp_array.length; i++)
			for (int j=0; j<temp_array.length; j++)
			{
				if (temp_array[i][j] == 0)
				{
					int i1 = i;
					int i2 = i;
					int j1 = j;
					int j2 = j;
					if (i1 -1 >= 0) i1--;
					else if (j1 -1 >= 0) j1--;
					else if (i1 +1 < temp_array.length) i1++;
					else if (j1 +1 < temp_array.length) j1++;
					
					if (j2 +1 < temp_array.length) j2++;
					else if (i2 +1 < temp_array.length) i2++;
					else if (j2 -1 >= 0) j2--;
					else if (i2 -1 >= 0) i2--;
					//System.out.println(this.toString());
					int temp = temp_array[i1][j1];
					temp_array[i1][j1] = temp_array[i2][j2];
					temp_array[i2][j2] = temp;
					
					break;
				}
			}
		
		Board twinBoard = new Board(temp_array);
		return twinBoard;
		
	}
	public boolean equals(Object y)        // does this board equal y?
	{
		if (y == null) return false;
		if (!y.getClass().equals(this.getClass())) return this.toString().equals(y.toString());
		if (((Board)y).game.length != game.length) return false;
		for (int i=0; i<game.length; i++)
			for (int j=0; j<game.length; j++)
			{
				if (game[i][j] != ((Board)y).game[i][j]) return false;
			}
		
		return true;
	}
	public Iterable<Board> neighbors()     // all neighboring boards
	{
		Board[] neighbors = new Board[4];
		int index_i = 0;
		int index_j = 0;
		int index = 0;
		for (int i=0; i<game.length; i++)
			for (int j=0; j<game.length; j++)
			{
				if (game[i][j] == 0)
				{
					index_i = i;
					index_j = j;
					break;
				}
			}
		
		int[][] temp_array = new int[game.length][game.length];
		for (int i=0; i<game.length; i++)
			for (int j=0; j<game.length; j++)
			{
				temp_array[i][j] = game[i][j];
			}
		if (index_i-1 >=0){
			//swap with blank block
			int temp = temp_array[index_i-1][index_j];
			temp_array[index_i-1][index_j] = temp_array[index_i][index_j];
			temp_array[index_i][index_j] = temp;
			neighbors[index++] = new Board(temp_array);		//put into array
			
			//swap back
			temp = temp_array[index_i-1][index_j];
			temp_array[index_i-1][index_j] = temp_array[index_i][index_j];
			temp_array[index_i][index_j] = temp;
		}
		if (index_i+1 < game.length){
			//swap with blank block
			int temp = temp_array[index_i+1][index_j];
			temp_array[index_i+1][index_j] = temp_array[index_i][index_j];
			temp_array[index_i][index_j] = temp;
			neighbors[index++] = new Board(temp_array);		//put into array
			
			//swap back
			temp = temp_array[index_i+1][index_j];
			temp_array[index_i+1][index_j] = temp_array[index_i][index_j];
			temp_array[index_i][index_j] = temp;
		}
		if (index_j-1 >=0){
			//swap with blank block
			int temp = temp_array[index_i][index_j-1];
			temp_array[index_i][index_j-1] = temp_array[index_i][index_j];
			temp_array[index_i][index_j] = temp;
			neighbors[index++] = new Board(temp_array);		//put into array
			
			//swap back
			temp = temp_array[index_i][index_j-1];
			temp_array[index_i][index_j-1] = temp_array[index_i][index_j];
			temp_array[index_i][index_j] = temp;
		}
		if (index_j+1 < game.length){
			//swap with blank block
			int temp = temp_array[index_i][index_j+1];
			temp_array[index_i][index_j+1] = temp_array[index_i][index_j];
			temp_array[index_i][index_j] = temp;
			neighbors[index++] = new Board(temp_array);		//put into array
			
			//swap back
			temp = temp_array[index_i][index_j+1];
			temp_array[index_i][index_j+1] = temp_array[index_i][index_j];
			temp_array[index_i][index_j] = temp;
		}
		
		return new Iterable<Board>() {
			@Override
			public Iterator<Board> iterator() {
				// TODO Auto-generated method stub
				return new nbIterator(neighbors);
			}
		};
		
	}
	
	private class nbIterator implements Iterator<Board>
	{
		Board[] neighbors;
		int index = 0;
		public nbIterator(Board[] boards) {
			neighbors = boards;
		}
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return index < neighbors.length && neighbors[index] != null;
		}

		@Override
		public Board next() {
			// TODO Auto-generated method stub
			return neighbors[index++];
		}
		
	}

	public String toString()               // string representation of this board (in the output format specified below)
	{
		String result = game.length+"\n";
		for (int i=0; i<game.length; i++)
		{
			for (int j=0; j<game[0].length; j++)
			{
				result += String.format("%2d ", game[i][j]);
			}
			result += "\n";
		}
		
		return result;
		
	}

	public static void main(String[] args) // unit tests (not graded)
	{
		
	}
}
