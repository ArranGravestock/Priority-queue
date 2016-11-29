/*
 * I have used a insertion sort that builds the linked list. It is less efficient on a large list,
 * but as this is not the case it is not a problem to use here. There are a few advantages to this method.
 * The main advantage to my method is that it is simple to implement and is efficient for small data sets.
 * If this was to be used for large data sets, this method of implementation would be quiet inefficient,
 * as it compares each value to the new value; thus would be in the worst case scenario O(n^2). However,
 * the best case scenario will be where it only needs to compare the newItem once, and then write the new
 * head of the linked list, O(n) or O(1). However, this case is very unlikely if the data set is close in value,
 * for example, if the values are 10, 10, 9, 9, 8, 6, 6, 5, the data will need to be either higher or the same
 * value which is unlikely case. On average, the scenario will be the same as the worst case scenario, O(n^2).
 * Another method which could be used would to insert all the data randomly, and then use a sort method of better
 * efficiency for larger data sets. 
 */

package ci284.ass1.pqueue;

public class PQueue<T> {

	private PQueueItem<T> head;

	public static enum ORDER {
		ASC, DESC;
	}

	public static ORDER DEFAULT_ORDER = ORDER.DESC;
	private ORDER order;
	int itemCount;	//the length of the linked list
	PQueueItem<T> front;	//the front of the linked list, is not the same as the head

	/**
	 * The default constructor for a PQueue, with the default order for
	 * priorities
	 */
	public PQueue() {
		//a new queue is made, clear all variables
		this.head = null;
		this.order = DEFAULT_ORDER;
		this.itemCount = 0;
	}

	/**
	 * The constructor to make a new PQueue with a given order
	 * 
	 * @param order
	 */
	public PQueue(ORDER order) {
		//a new queue is made, clear all variables and set the order
		this.head = null;
		this.order = order;
		this.itemCount = 0;
	}

	/**
	 * Remove and return data from the item at the front of the queue
	 * 
	 * @return
	 */
	public T pop() {
		//remove an item, store the data at the head, and set the new head, return the data
		this.itemCount--;
		T popData = head.getData();
		head = head.getNext();
		return popData;
	}

	/**
	 * Return the data from the item at the front of the queue, without removing
	 * the item itself
	 * 
	 * @return
	 */
	public T peek() {
		return this.head.getData();
	}

	/**
	 * Remove and return the item at the front of the queue
	 * 
	 * @return
	 */
	public PQueueItem<T> popItem() {
		//remove an item, set the next head and return the new head
		this.itemCount--;
		head = head.getNext();
		return head;
	}

	/**
	 * Return the item at the front of the queue without removing it
	 * 
	 * @return
	 */
	public PQueueItem<T> peekItem() {
		return head;
	}

	/**
	 * Insert a new item into the queue, which should be put in the right place
	 * according to its priority. That is, is order == ASC, then the new item
	 * should appear in the queue before all items with a HIGHER priority. If
	 * order == DESC, then the new item should appear in the queue before all
	 * items with a LOWER priority.
	 * 
	 * @param data
	 * @param priority
	 */
	
	public void insert(T data, int priority) {
		PQueueItem<T> newItem = new PQueueItem<T>(data, priority);
		
		boolean check = false;	//boolean used to break while loop
		front = head;	//set the front to the head, which is used to keep two seperate values, the "head" and the "front" of queue
		
		if (this.order == ORDER.DESC) {
			while (!check) {	//loop until the newItem's head has been set and added
				if (front == null) {
					head = newItem;
					check = true;
				} else if (front.getPriority() < newItem.getPriority()) {
					newItem.setNext(head);
					head = newItem;
					check = true;
				} else {	//the newItem is less than the head but greater than the next item, or lower than both
					if (front.getNext() == null) {	//if there is not an item next in the queue then don't check
						front.setNext(newItem);
						check = true;
					} else if (front.getNext().getPriority() < newItem.getPriority()) {	//if the newItem is less than the head but greater than the next item
						newItem.setNext(front.getNext());
						front.setNext(newItem);
						check = true;
					} else {	//otherwise move the head along one and check again
						front = front.getNext();
					}
				}
			}
		} else {	//the order is ASC - just a reversal of signs to rearrange the order
			while (!check) {
				if (front == null) {
					head = newItem;
					check = true;
				} else if (front.getPriority() > newItem.getPriority()) {
					newItem.setNext(head);
					head = newItem;
					check = true;
				} else {
					if (front.getNext() == null) {
						front.setNext(newItem);
						check = true;
					} else if (front.getNext().getPriority() > newItem.getPriority()) {
						newItem.setNext(front.getNext());
						front.setNext(newItem);
						check = true;
					} else {
						front = front.getNext();
					}
				}
			}
		}
		//the item has been set, so increase the length
		this.itemCount++;
	}

	/**
	 * Return the length of the queue
	 * 
	 * @return
	 */
	public int length() {
		return this.itemCount;
	}

	public String toString() {
		int i = length();
		PQueueItem<T> current = head;
		StringBuffer sb = new StringBuffer();
		while (i > 0) {
			sb.append(current.toString());
			if (i > 1)
				sb.append(": ");
			current = current.getNext();
			i--;
		}
		return sb.toString();
	}

}