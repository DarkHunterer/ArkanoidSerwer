import sun.misc.IOUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by Daniel on 30.01.2016.
 */
public class SocketClientHandler implements Runnable {

    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;
    private Thread thread;

    public SocketClientHandler(Socket client) {
        this.clientSocket = client;
        try {
            System.out.println(Thread.currentThread().getName()+" Klient podlaczony");
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           // out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            thread = new Thread(this);
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try{
            String inputLine, outputLine;
            protokolKomunikacji pk = new protokolKomunikacji();
            //out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            OutputStream out = clientSocket.getOutputStream();

            inputLine = in.readLine();
            int action_code=pk.processInput(inputLine);
            switch (action_code){
                case 1:{
                    BufferedWriter outbuff = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    outbuff.write("Konfiguracja_z_serwera.json");
                    outbuff.newLine();
                    outbuff.flush();
                    //clientSocket.shutdownOutput();
                    out = clientSocket.getOutputStream();
                    File file = new File("domyslny_config.json");
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    byte[] contents;
                    long fileLength = file.length();
                    long current = 0;
                    while(current!=fileLength){
                        int size = 10000;
                        if(fileLength - current >= size)
                            current += size;
                        else{
                            size = (int)(fileLength - current);
                            current = fileLength;
                        }
                        contents = new byte[size];
                        bis.read(contents, 0, size);
                        out.write(contents);
                        System.out.print("Sending file ... "+(current*100)/fileLength+"% complete!");
                    }
                    break;
                }
                case 2:{
                    BufferedWriter outbuff = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    outbuff.write("Mapa_z_serwera.json");
                    outbuff.newLine();
                    outbuff.flush();
                    //clientSocket.shutdownOutput();
                    out = clientSocket.getOutputStream();
                    File file = new File("mapa.json");
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    byte[] contents;
                    long fileLength = file.length();
                    long current = 0;
                    while(current!=fileLength){
                        int size = 10000;
                        if(fileLength - current >= size)
                            current += size;
                        else{
                            size = (int)(fileLength - current);
                            current = fileLength;
                        }
                        contents = new byte[size];
                        bis.read(contents, 0, size);
                        out.write(contents);
                        System.out.print("Sending file ... "+(current*100)/fileLength+"% complete!");
                    }
                    break;
                }
                default: {
                    outputLine = "Nie znam komendy";
                    //out.write(outputLine);
                    break;
                }
            }

            out.flush();
            clientSocket.shutdownOutput();
            System.out.println("Zamykam socket");
            clientSocket.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}

/*    private void sendTime() throws IOException, InterruptedException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        writer.write(new Date().toString());
        writer.flush();
        writer.close();
    }*/
