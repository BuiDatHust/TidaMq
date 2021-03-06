package tidaMq.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriteToDiskThread implements Runnable {
	private String task ;
	private File file ;
	private String value ;
	
	public WriteToDiskThread(String s,File file,String val) {
		this.task = s ;
		this.file = file ;
		this.value= val ;
	}
	 
	public WriteToDiskThread() {
		
	}
	
	@Override
	public void run() {
		switch(this.task) {
			case "writedisk":
				WriteToFile(this.file,this.value);
				System.out.println( "thread  " + Thread.currentThread()+ " Success write") ;
				break ;
			case "readDisk":
				ReadFromFile(this.file) ;
				break ;
			case "deleteDisk":
				DeleteAndUpdateToFile(this.file);
				System.out.println( "thread  " + Thread.currentThread()+ " Success pop") ;
				break ;
			case "deleteFile":
				DeleteFile(this.file);
				System.out.println( "thread  " + Thread.currentThread()+ " Success delete") ;
				break; 
			default:
				break; 
				
		}
	}
	
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
	  
	
	
	public static void DeleteAndUpdateToFile(File file) {
		List<String> list = ReadFromFile(file) ;
		
		try {
		      FileWriter myWriter = new FileWriter(file.getPath());
		      
		      for(String li: list) {
		    	  if( !li.equals(list.get(0)) ) {
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
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		return list ;
	}
	
	public static void DeleteFile(File file) {
		try {
            Files.deleteIfExists(
                Paths.get(file.getAbsolutePath()));
        }
        catch (NoSuchFileException e) {
            System.out.println(
                "No such file/directory exists");
        }
        catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        }
        catch (IOException e) {
            System.out.println("Invalid permissions.");
        }
 
        System.out.println("Deletion successful.");
	}
}
