import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Daniel on 31.01.2016.
 */
public class SerwerWorker extends Thread{
    int port;
    ServerSocket serverSocket;
    public SerwerWorker(int portNumber){
        port = portNumber;
        try{
            serverSocket = new ServerSocket(port);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            boolean listening = true;
            while (listening) {
                new SocketClientHandler(serverSocket.accept());
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            //e.getMessage();
            System.exit(-1);
        }
    }

}
