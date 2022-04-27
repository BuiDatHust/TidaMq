package tidaMq.server;

import java.io.IOException;

public class queue {
	 private String[] arr;      
	 private int front;      
	 private int rear;       
	 public int capacity;  
	 public int count;   
	 public String name ;
	 public boolean persistent ;
	
	 queue(int size,String n,String p)
	 { 
		 
	     arr = new String[size];
	     capacity = size;
	     front = 0;
	     rear = -1;
	     count = 0;
	     name= n;
	     persistent = p.equals("0") ? false : true ;
	 
	 }
	 
	 queue(){
		 
	 }

	
	 public synchronized String dequeue() throws InterruptedException
	 {
	     
	     if (isEmpty())
	     {
	         System.out.println("Queue is Empty");
	         wait();
	         return "";
	     }
	     notifyAll();

	     String x = arr[front];

	     System.out.println("Removing " + x);

	     front = (front + 1) % capacity;
	     count--;

	     return x;
	 }

	 
	 public synchronized void enqueue(String item) throws InterruptedException
	 {
	     
	     if (isFull())
	     {
	         System.out.println("Queue is Full");
	         wait();
	         return  ;
	     }

	     System.out.println("Inserting " + item);

	     rear = (rear + 1) % capacity;
	     arr[rear] = item;
	     count++;
	 }

	 
	 public String peek()
	 {
	     if (isEmpty())
	     {
	         System.out.println("Queue is Empty");
	         return "";
	     }
	     return arr[front];
	 }

	 
	 public int size() {
	     return count;
	 }
	 
	 public void list() {
		 for(int i=0 ;i<size(); i++) {
			 System.out.println(arr[i]);
		 }
	 }

	 
	 public boolean isEmpty() {
	     return (size() == 0);
	 }

	 
	 public boolean isFull() {
	     return (size() == capacity);
	 }
}
