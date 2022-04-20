package tidaMq;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
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
			// TODO: handle exception
		}
	}
	
	 public static void main (String[] args)
	 {
		 queue q = new queue(10) ;
		 File myfile = new File("/home/buidat/eclipse-workspacjava /tidaMqFile/queue1.txt"); 
		 
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
					q.dequeue();
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