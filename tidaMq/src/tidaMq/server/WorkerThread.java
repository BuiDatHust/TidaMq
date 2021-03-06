package tidaMq.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkerThread extends Thread {
	private Socket socket;
	QueueNow queueNow ;
	List<Socket> sockets ;
	 
    public WorkerThread(Socket socket,QueueNow q) {
        this.socket = socket;
        this.queueNow = q ;
        
    }
 
    public void run() {
        System.out.println("Processing: " + socket);
        try {
        	
            while (true) {
            	//Tạo input stream, nối tới Socket
                BufferedReader inFromClient =new BufferedReader(new InputStreamReader(socket.getInputStream())); 
                
                //Tạo outputStream, nối tới socket
                DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
            	 //Đọc thông tin từ socket
                String sentence_from_client = inFromClient.readLine();
                
                String  sentence_to_client = "dcdc";
                
   	    	 	String[] arg = sentence_from_client.split(" ") ; 
   	    	 	System.out.println(arg);
                
   	    	 	ExecutorService executor = Executors.newSingleThreadExecutor();
   	    	 	
                switch (arg[0]) {
	    	 	case "create":
	    	 		sentence_to_client = queueNow.createNewQueue(arg) ;
	    	 		break ;
	    	 	case "use":
	    	 		sentence_to_client = queueNow.setQueue(arg[1]) ;
	    	 		break ;
				case "add":
					sentence_to_client= queueNow.addToQueue(arg, executor);

					System.out.println(sentence_from_client);
					break;
				case "pop":
					sentence_to_client =queueNow.popQueue(arg, executor) ;
					break;
				case "get":
					sentence_to_client =queueNow.peekQueue() ;
					break;
				case "list":
					sentence_to_client = queueNow.listQueue();
					break;
				case "listAll":
					for(queue q2 : queueNow.listQueue) {
						sentence_to_client+= q2.name + " " ;
					}
					break ; 
				case "delete": 
					queueNow.deleteQueue(arg[1],executor);
					break ;
				default:
					sentence_to_client = "Sai cú pháp, mời nhập lại";
					break;
			}
                
              //ghi dữ liệu ra socket
               outToClient.writeBytes(sentence_to_client +'\n'); 
            }
        } catch (IOException e) {
            System.err.println("Request Processing Error: " + e);
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Complete processing: " + socket);
    }
}
