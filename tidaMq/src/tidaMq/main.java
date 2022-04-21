package tidaMq;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



class main
{
	public static void WriteToFile(File file,String s) {
		
		try {
			FileWriter fw = new FileWriter(file, true);
		    BufferedWriter bw = new BufferedWriter(fw);
		    bw.write(s);
		    bw.newLine();
		    bw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void DeleteAndUpdateToFile(File file, String s) {
		List<String> list = ReadFromFile(file) ;
		
		try {
		      FileWriter myWriter = new FileWriter(file.getPath());
		      
		      for(String li: list) {
		    	  if( !li.equals(s) ) {
		    		  System.out.println(li);
		    		  myWriter.write(li) ;
		    		  myWriter.write(System.lineSeparator()) ;
		    		 
		    	  }
		      }
		      myWriter.close();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	public static List<String> ReadFromFile(File file) {
		List<String> list = new ArrayList<String>();
		
		try {
		      Scanner myReader = new Scanner(file);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        list.add(data) ;
		        System.out.println(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		return list ;
	}
	
	 public static void main (String[] args) throws IOException
	 {
		 File myfile = new File("/home/buidat/eclipse-workspacjava /tidaMqFile/queue1.txt"); 
		 queue q = new queue(10) ;
		 List<String> list = new ArrayList<String>();
		 
		 if(!myfile.createNewFile()) {
			 list = ReadFromFile(myfile);
			 for(String s: list) {
				 q.enqueue(s) ;
			 }
			 
		 }
		 
		 
	     while(true) {
	    	 String str ;
	    	 Scanner sc = new Scanner(System.in) ;
	    	 str = sc.nextLine() ; 
	    	 
	    	 str= str.trim() ;
	    	 String[] arg = str.split(" ") ; 
	    	 
	    	 switch (arg[0]) {
				case "add":
					q.enqueue(arg[1]);
					WriteToFile(myfile,arg[1]);
					break;
				case "pop":
					String val = q.dequeue();
					System.out.println(val) ;
					DeleteAndUpdateToFile(myfile,val) ;
					break;
				case "get":
					System.out.println(q.peek());
					break;
				case "list":
					q.list();
					break;
				default:
					System.out.println("Sai cú pháp, mời nhập lại");
					break;
			}
	    	 
	    	 
	     }
	 }
}