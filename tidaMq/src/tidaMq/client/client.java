package tidaMq.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class client {
	public final static String SERVER_IP = "127.0.0.1";
    public final static int SERVER_PORT = 3000;
    
    public static void connect() throws IOException {
    	Socket socket = null;
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT); // Connect to server
            System.out.println("Connected: " + socket);
            
            while(true){
            //Tạo OutputStream nối với Socket
            DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
            //Tạo inputStream nối với Socket
            BufferedReader inFromServer =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
//            String[] li = inFromServer.readLine().split(" ") ;
//            System.out.println(li) ;
            
            System.out.print("127.0.0.1: "+ socket.getLocalPort()+ ">" );
            
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            //Lấy chuỗi ký tự nhập từ bàn phím
            String sentence_to_server = inFromUser.readLine();
//             
            //Gửi chuỗi ký tự tới Server thông qua outputStream đã nối với Socket (ở trên)
            outToServer.writeBytes(sentence_to_server + '\n');
            
            //Đọc tin từ Server thông qua InputSteam đã nối với socket
            String sentence_from_server = inFromServer.readLine();
            
            //print kết qua ra màn hình
            
            String[] list = sentence_from_server.split(" ") ;
            
            if(list.length>1) {
            	for(int i=0 ;i<list.length; i++) {
                	System.out.println(i+1 +". "+ list[i]) ;
                }
            }else {
            	System.out.println(sentence_from_server);
            }
            
            }
//            Thread.sleep(5000);
        } catch (IOException ie) {
            System.out.println("Can't connect to server");
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
// 
//    public static void main(String[] args) throws IOException, InterruptedException {
//        
//    }
}
