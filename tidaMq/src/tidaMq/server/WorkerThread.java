package tidaMq.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkerThread extends Thread {
	private Socket socket;
	QueueNow queueNow ;
	 
    public WorkerThread(Socket socket,QueueNow q) {
        this.socket = socket;
        this.queueNow = q ;
    }
 
    public void run() {
        System.out.println("Processing: " + socket);
        try {
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            while (true) {
                int ch = is.read(); // Receive data from client
                if (ch == -1) {
                    break;
                }
                System.out.println(ch) ;
                String str= String.valueOf(ch).trim() ;
   	    	 	String[] arg = str.split(" ") ; 
                
   	    	 	ExecutorService executor = Executors.newSingleThreadExecutor();
   	    	 	
                switch (arg[0]) {
	    	 	case "create":
	    	 		queueNow.createNewQueue(arg) ;
	    	 		break ;
	    	 	case "use":
	    	 		queueNow.setQueue(arg[1]) ;
	    	 		break ;
				case "add":
					queueNow.addToQueue(arg, executor);
					break;
				case "pop":
					queueNow.popQueue(arg, executor) ;
					break;
				case "get":
					queueNow.peekQueue() ;
					break;
				case "list":
					queueNow.list();
					break;
				case "listAll":
					for(queue q2 : queueNow.listQueue) {
						System.out.println(q2.name);
					}
					break ;
				case "delete": 
					queueNow.deleteQueue(arg[1],executor);
					break ;
				default:
					System.out.println("Sai cú pháp, mời nhập lại");
					break;
			}
                
                os.write(ch); // Send the results to client
            }
        } catch (IOException e) {
            System.err.println("Request Processing Error: " + e);
        }
        System.out.println("Complete processing: " + socket);
    }
}