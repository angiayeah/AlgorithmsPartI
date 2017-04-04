import java.util.Iterator;
import java.util.Random;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] store;
	private int size = 0;
	public RandomizedQueue()                 // construct an empty randomized queue
	{
		store = (Item[]) new Object[1]; 
	}
	public boolean isEmpty()                 // is the queue empty?
	{
		return size == 0;
		
	}
	public int size()                        // return the number of items on the queue
	{
		return size;
		
	}
	public void enqueue(Item item)           // add the item
	{
		if (item == null)
		{
			throw new java.lang.NullPointerException("add item is null!");
		}
		store[size] = item;
		size ++;
		if (size >= store.length)
		{
			Item[] temp = (Item[]) new Object[2 * store.length];
			for(int i=0; i<size; i++)
			{
				temp[i] = store[i];
			}
			store = temp;
		}
			
			
	}
	public Item dequeue()                    // remove and return a random item
	{
		if (isEmpty())
		{
			throw new java.util.NoSuchElementException("Queue is empty");
		}
		int random = StdRandom.uniform(0, size);
		Item item = store[random];
		
		for(int i=random; i<size; i++)
		{
			if (store[i] == null)
			{
				break;
			}
			if (i < store.length-1)
			{
				store[i] = store[i+1];
			}
			else if (i== store.length-1)
			{
				store[i] = null;
			}
		}
		size --;
		
		if (size <= store.length/4)
		{
			Item[] temp = (Item[]) new Object[store.length/2];
			for (int i=0; i<size; i++)
			{
				temp[i] = store[i];
			}
			store = temp;
		}
		
		return item;
		
	}
	public Item sample()                     // return (but do not remove) a random item
	{
		if (isEmpty())
		{
			throw new java.util.NoSuchElementException("Queue is empty");
		}
		int random = StdRandom.uniform(0, size);
		Item item = store[random];
		return item;
		
	}
	public Iterator<Item> iterator()         // return an independent iterator over items in random order
	{
		
		return new RandomIterator();
	}
	private class RandomIterator implements Iterator<Item>
	{
		int local_size;
		Item[] temp;
		public RandomIterator() {
			local_size = size;
			temp = (Item[]) new Object[local_size];
			for (int i=0; i<local_size; i++)
			{
				temp[i] = store[i];
			}
			
			StdRandom.shuffle(temp); 	//randomize the array first
		}
		@Override
		public boolean hasNext() {
			return local_size > 0;
		}

		@Override
		public Item next() {
			if (local_size == 0)
			{
				throw new java.util.NoSuchElementException("Queue is empty");
			}
			return temp[--local_size];
		}
		
		@Override
		public void remove()
		{
			throw new java.lang.UnsupportedOperationException("Can't call remove()");
		}
		
	}
	public static void main(String[] args) {
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		rq.enqueue(1);
		rq.enqueue(2);
		rq.enqueue(3);
		System.out.println(rq.dequeue());
		for (int a : rq)
		{
			System.out.print(a + " ");
		}

	}

}
