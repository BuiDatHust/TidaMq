package tidaMq.server;

import java.io.IOException;

import tidaMq.server.config.GetConfigValue;

public class queue {
	 private String[] arr;      
	 private int front;      
	 private int rear;       
	 public int capacity;  
	 public int count;   
	 public String name ;
	 public boolean persistent ;
	 public GetConfigValue config = new GetConfigValue() ;
	
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
	         System.out.println(config.getPropertyValue("FULLQUEUE"));
//	         wait();
	         return config.getPropertyValue("FULLQUEUE") ;
	     }
//	     notifyAll();

	     String x = arr[front];

	     System.out.println("Removing " + x);

	     front = (front + 1) % capacity;
	     count--;

	     return x;
	 }

	 
	 public synchronized String enqueue(String item) throws InterruptedException
	 {
	     
	     if (isFull())
	     {
	         System.out.println(config.getPropertyValue("FULLQUEUE") );
//	         wait();
	         return config.getPropertyValue("FULLQUEUE") ; 
	     }

	     System.out.println("Inserting " + item);

	     rear = (rear + 1) % capacity;
	     arr[rear] = item;
	     count++;
	     return item ;
	 }

	 
	 public String peek()
	 {
	     if (isEmpty())
	     {
	         System.out.println(config.getPropertyValue("EMPTYQUEUE") );
	         return "";
	     }
	     return arr[front];
	 }

	 
	 public int size() {
	     return count;
	 }
	 
	 public String list() {
		 String res = "";
		 for(int i=front ;i<=rear; i++) {
			 res+= arr[i]+ " ";
		 }
		 
		 return res; 
	 }

	 
	 public boolean isEmpty() {
	     return (size() == 0);
	 }

	 
	 public boolean isFull() {
	     return (size() == capacity);
	 }
}
