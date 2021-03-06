package tidaMq.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Producer {
	public final static String SERVER_IP = "127.0.0.1";
    public final static int SERVER_PORT = 3000;
  
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = null; 
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT); // Connect to server
            System.out.println("Connected: " + socket);
            
            while(true){
            //Tạo OutputStream nối với Socket
            DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
            //Tạo inputStream nối với Socket
            BufferedReader inFromServer =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            System.out.print("Input from client: ");
//            BufferedReader inFromUser =
//                new BufferedReader(new InputStreamReader(System.in));
//            //Lấy chuỗi ký tự nhập từ bàn phím
//            String sentence_to_server = inFromUser.readLine();
            outToServer.writeBytes("use cd" + '\n');
            Thread.sleep(1000);
            for(int i=0 ;i<6 ;i++) {
            	outToServer.writeBytes("add cdcd" + '\n');
            	Thread.sleep(1000);
            }
//             
            //Gửi chuỗi ký tự tới Server thông qua outputStream đã nối với Socket (ở trên)
            
            
            //Đọc tin từ Server thông qua InputSteam đã nối với socket
            String sentence_from_server = inFromServer.readLine();
            
            //print kết qua ra màn hình
            System.out.println("FROM SERVER: " + sentence_from_server);
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
}