import org.json.simple.JSONObject;
import sun.misc.IOUtils;

import java.io.*;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 30.01.2016.
 */
public class SocketClientHandler implements Runnable {

    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;
    private Thread thread;
    private String nick;
    public SocketClientHandler(Socket client) {
        this.clientSocket = client;
        try {
            System.out.println(Thread.currentThread().getName()+" Klient podlaczony");
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            nick =" ";
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

            inputLine = in.readLine();
            int action_code=pk.processInput(inputLine);
            switch (action_code){
                case 1:
                    send_file("domyslny_config.json");
                    System.out.println("Koniec case 1 - wyslanie configu");
                    break;
                case 2: {
                    File folder = new File("mapy/");
                    File[] listOfFiles = folder.listFiles();

                    BufferedWriter outbuff = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    outbuff.write(listOfFiles.length + "\n");
                    outbuff.flush();

                    for (int i = 0; i < listOfFiles.length; i++) {
                        if (listOfFiles[i].isFile()) {
                            System.out.println("File " + listOfFiles[i].getName());
                            outbuff.write(listOfFiles[i].getName() + "\n");
                            outbuff.flush();
                        }
                    }
                    System.out.println("Koniec case 2 - wyslanie ilosci map i ich nazw");
                }
                    break;
                case 3:{
                    String file = inputLine.replaceAll("Daj ","");
                    System.out.println("Nazwa pliku to "+file);
                    send_file("mapy/"+file);
                    System.out.println("Koniec case 3 - wysylanie plikow map");
                }
                break;
                case 4: {
                    send_file("HighScore.txt");
                    System.out.println("Koniec case 4 - wyslanie highscore");
                }
                break;
                case 5:{
                    BufferedWriter outbuff = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    String input = inputLine.replaceAll("Wynik: ","");
                    int score = Integer.valueOf(input);
                    System.out.println("Otrzymany wynik to: "+score);

                    FileReader fileReader = new FileReader(new File("Nick.txt"));
                    BufferedReader br = new BufferedReader(fileReader);
                    nick = br.readLine();
                    br.close();
                    fileReader.close();
                    File f = new File("Nick.txt");
                    f.delete();
                    Map<String, Long> highScore = new HashMap<String, Long>();
                    org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
                    Object obj = parser.parse(new FileReader("HighScore.txt"));
                    JSONObject jsonObjMain = (JSONObject) obj;
                    highScore.putAll((Map) jsonObjMain.get("HighScore"));
                    Boolean czyRekord=false;
                    Map.Entry<String, Long> tempEntry = new AbstractMap.SimpleEntry<String, Long>(nick,(long)score);
                    for (Map.Entry<String, Long> tempMap : highScore.entrySet()) {
                        if ((long) score > tempMap.getValue()) {
                            czyRekord = true;
                            //tempMap = tempEntry;
                            highScore.remove(tempMap.getKey(),tempMap.getValue());
                            highScore.put(tempEntry.getKey(),tempEntry.getValue());
                            break;
                        }
                    }

                    if (czyRekord) {
                        outbuff.write("true\n");
                        outbuff.flush();
                        try {

                            FileWriter writer2 = new FileWriter("HighScore.txt");
                            StringWriter out = new StringWriter();
                            JSONObject objMain = new JSONObject();
                            objMain.put("HighScore", highScore);

                            objMain.writeJSONString(out);
                            writer2.write(out.toString());
                            writer2.close();
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                        System.out.println("Koniec case 5 - obrabianie wyniku");
                }
                break;
                case 6:{
                    String input = inputLine.replaceAll("Nick: ","");
                    nick = new String(input);
                    System.out.println("Nick to: "+nick);
                    FileWriter writer = new FileWriter("Nick.txt");
                    StringWriter out = new StringWriter();
                    out.write(nick);
                    writer.write(nick);
                    writer.close();
                    System.out.println("Koniec case 6 - obrabianie nicku");
                }
                break;
                default: {
                    outputLine = "Nie znam komendy";
                    //out.write(outputLine);
                    System.out.println("Nie znam komendy");
                    break;
                }
            }
        clientSocket.shutdownOutput();
        System.out.println("Zamykam socket");
        clientSocket.close();
    }catch (Exception ex){
        System.out.println(ex.toString());
    }
    }
    void send_file(String name){
        try {
            OutputStream out = clientSocket.getOutputStream();
            BufferedWriter outbuff = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            outbuff.write(name);
            outbuff.newLine();
            outbuff.flush();
            //clientSocket.shutdownOutput();
            out = clientSocket.getOutputStream();
            File file = new File(name);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] contents;
            long fileLength = file.length();
            long current = 0;
            while (current != fileLength) {
                int size = 10000;
                if (fileLength - current >= size)
                    current += size;
                else {
                    size = (int) (fileLength - current);
                    current = fileLength;
                }
                contents = new byte[size];
                bis.read(contents, 0, size);
                out.write(contents);
                System.out.print("Sending file ... "+name+" " + (current * 100) / fileLength + "% complete!");
                out.flush();
                System.out.println("Zamykam strumien wyjsciowy");
            }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}

/*
 Map.Entry<String, Long> tempEntry = new AbstractMap.SimpleEntry<String, Long>("Nick", 0l);
            for (Map.Entry<String, Long> tempMap : oknoGlowneUchwyt.highScore.entrySet()) {
                if ((long) wynik > tempMap.getValue()) {
                    czyRekord = true;
                    tempEntry = tempMap;
                    System.out.println("Dziala");
                    break;
                }
            }


            oknoGlowneUchwyt.highScore.remove(tempEntry.getKey());
                oknoGlowneUchwyt.highScore.put(nick, (long) wynik);
                OknoGlowne.BestScoreFrame bestscore;
                bestscore = oknoGlowneUchwyt.getScoreFrame();
                bestscore.zapiszDoPliku();
 */