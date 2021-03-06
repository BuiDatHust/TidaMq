package tidaMq.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

import tidaMq.server.config.GetConfigValue;

public class QueueNow extends queue {
	public static queue q ;
	public static List<queue> listQueue =new ArrayList<>()  ;
	public static GetConfigValue config = new GetConfigValue() ;
	
	public static String createNewQueue(String[] arg) {
		queue newque = new queue(Integer.parseInt(arg[1]), arg[2], arg[3]) ;
		
 		if( newque.persistent ) {
 			File myfile0 = new File(config.getPropertyValue("PATHFILE")+ arg[2] + ".txt"); 
 		} 
 		listQueue.add(newque) ;
 		setQueue(arg[2]);
 		return "Ok"; 
	} 
	
	public static String setQueue(String arg){ 
		boolean found = false;
		String res = "";
 		for(queue qu: listQueue) {
 			System.out.println(qu.name) ;
 			if(qu.name.equals(arg)) {
 				q=qu ; 
 				System.out.println("use queue "+ q.name) ;
 				found=true;
 				res += q.name ;
 				break;
 			}
 		}
 		if(!found) return config.getPropertyValue("QUEUENOTFOUND") ;
 		return res;
	}
	
	public static String addToQueue(String[] arg, ExecutorService executor) throws InterruptedException {
		File myfile = new File(config.getPropertyValue("PATHFILE")+ q.name + ".txt");
		String res= "" ;
		
		if(!q.isFull()) {
			if( q.persistent ) {
				Runnable writeDisk = new WriteToDiskThread(config.getPropertyValue("WRITE"), myfile,arg[1]);
				executor.execute(writeDisk) ;	
			}
			
			q.enqueue(arg[1]);
			res= arg[1]  ;
		}else {
			return config.getPropertyValue("FULLQUEUE") ;
		}
		return res; 
	}
	
	public static String popQueue(String[] arg, ExecutorService executor) throws InterruptedException {
		String val = q.dequeue();
		if(!q.isEmpty()) {
			File myfile1 = new File("/home/buidat/eclipse-workspacjava /tidaMqFile/"+ q.name + ".txt");
			if( q.persistent ) {
				Runnable popDisk = new WriteToDiskThread(config.getPropertyValue("DELETE"), myfile1,"");
				executor.execute(popDisk) ;
			}
			
			System.out.println(val) ;
			return val ; 
		}else {
			System.out.println(config.getPropertyValue("EMPTYQUEUE")) ;
		}
		
		return val ; 
	}
	
	public String peekQueue() {
		String res = "" ;
		if(!q.isEmpty()) {
			System.out.println(q.peek());
			res= q.peek() ;
		}else {
			res= config.getPropertyValue("EMPTYQUEUE");
		}
		
		return res; 
	}
	
	public static String listQueue() {
		return q.list();
	}
	
	public static void deleteQueue(String s,ExecutorService executor) {
		File myfile2 = new File("/home/buidat/eclipse-workspacjava /tidaMqFile/"+ s + ".txt");
		
		if(q.persistent) {
			Runnable deleteFile = new WriteToDiskThread(config.getPropertyValue("DELETEFILE"), myfile2,"");
			executor.execute(deleteFile) ;
		}
		
		for(queue q3: listQueue) {
			if( q3.name.equals(s) ) {
				listQueue.remove(q3) ;
			}
		}
		
	}
}
