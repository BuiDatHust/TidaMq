package tidaMq.server;

import java.awt.Window.Type;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tidaMq.server.config.GetConfigValue;



class server
{
	public final static int SERVER_PORT = 3000;
	public static final int NUM_OF_THREAD = 4;
	
	 public static void startserver () throws IOException, InterruptedException
	 {
		 QueueNow queueNow =new QueueNow() ;
		 List<Socket> sockets  = new ArrayList<>();
		 
		 GetConfigValue config = new GetConfigValue() ;
		 
		//Creating a File object for directory
	      File directoryPath = new File(config.getPropertyValue("PATHFILE"));
	      //List of all files and directories
	      File filesList[] = directoryPath.listFiles();
	      System.out.println("List of files and directories in the specified directory:");
	      for(File file : filesList) {
	    	  WriteToDiskThread read = new WriteToDiskThread();
	    	  
	    	  List<String> list = read.ReadFromFile(file);
	    	  String fileName = file.getName() ;
	    	  queue nq = new queue(list.size(), fileName.split("\\.")[0], "1");
	    	  
	    	  for(String s:list) {
	    		  nq.enqueue(s);
	    	  }
	    	  queueNow.listQueue.add(nq) ;
	    	  System.out.println(file.getName()) ;
	      }
		
		 

	      ExecutorService executor = Executors.newFixedThreadPool(NUM_OF_THREAD);
	      ServerSocket serverSocket = null;
	        try {
	            System.out.println("Binding to port " + SERVER_PORT + ", please wait  ...");
	            serverSocket = new ServerSocket(SERVER_PORT);
	            System.out.println("Server started: " + serverSocket);
	            System.out.println("Waiting for a client ...");
	            while (true) {
	                try {
	                    Socket socket = serverSocket.accept();
	                    sockets.add(socket) ;
	                    System.out.println("Client accepted: " + socket);
	                    
	                    WorkerThread handler = new WorkerThread(socket,queueNow);
	                    executor.execute(handler);
	                } catch (IOException e) {
	                    System.err.println(" Connection Error: " + e);
	                }
	            }
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        } finally {
	            if (serverSocket != null) {
	                serverSocket.close();
	            }
	        }
	     
	    
	     
	 }
	 
	 public static void main(String[] args)  throws IOException, InterruptedException {
		startserver();
	}
}