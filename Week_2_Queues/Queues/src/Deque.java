import java.util.Iterator;


public class Deque<Item> implements Iterable<Item> {
	private Node head = null;
	private Node tail = null;
	private int size = 0;
	public Deque()                           // construct an empty deque
	{
		
	}
	private class Node
	{
		Item item = null;
		Node next = null;
		Node prev = null;
	}
	public int size()                        // return the number of items on the deque
	{
		return size;
	}
	public boolean isEmpty()                 // is the deque empty?
	{
		return this.size() == 0;
	}
	public void addFirst(Item item)          // add the item to the front
	{
		if (item == null)
		{
			throw new java.lang.NullPointerException("add item is null!");
		}
		
		Node newNode = new Node();
		newNode.item = item;
		size ++;
		if (head != null)
		{
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		else
		{
			head = newNode;
		}
		if (tail == null)
		{
			tail = newNode;
		}
	}
	public void addLast(Item item)           // add the item to the end
	{
		if (item == null)
		{
			throw new java.lang.NullPointerException("add item is null!");
		}
		Node newNode = new Node();
		newNode.item = item;
		size ++;
		if (tail != null)
		{
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		else
		{
			tail = newNode;
		}
		if (head == null)
		{
			head = newNode;
		}
	}
	public Item removeFirst()                // remove and return the item from the front
	{
		if (isEmpty())
		{
			throw new java.util.NoSuchElementException("Queue is empty");
		}
		size --;
		Item item = head.item;
			
		if (isEmpty())
		{
			head = null;
			tail = null;
		}
		else
		{
			head = head.next;
			head.prev = null;
		}
		return item;
		
	}
	public Item removeLast()                 // remove and return the item from the end
	{
		if (isEmpty())
		{
			throw new java.util.NoSuchElementException("Queue is empty");
			
		}
		size --;
		Item item = tail.item;
		
		
		if (isEmpty())
		{
			head = null;
			tail = null;
		}
		else
		{
			tail = tail.prev;
			//System.out.println("move previous: " +tail.item);
			tail.next = null;
		}
		return item;
		
	}
	
	@Override
	public Iterator<Item> iterator() // return an iterator over items in order from front to end
	{
		return new DequeIterator();
	}
	
	
	private class DequeIterator implements Iterator<Item>
	{
		Node current = head;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext())
			{
				throw new java.util.NoSuchElementException("Already empty");
			}
			Item item = current.item;
			current = current.next;
			return item;
		}
		
		@Override
		public void remove()
		{
			throw new java.lang.UnsupportedOperationException("Can't call remove()");
		}
		
	}
	
	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<Integer>();
		deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        System.out.println(deque.removeLast());

		
		for (int each : deque)
		{
			System.out.print(each+" ");
		}
		System.out.println();
		System.out.println(deque.removeLast());
		System.out.println(deque.removeLast());
			
	}
}
