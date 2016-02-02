import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Daniel and Konrad on 31.01.2016.
 * Klasa SerwerWorker odpowiada za przyjmowanie połączeń od klienta.
 * Działa na zasadzie 1 klient - 1 wątek
 */
public class SerwerWorker extends Thread{
    /**
     * Zmienna przechowująca port komunikacji sieciowej
     */
    int port;
    /**
     * Pole typu ServerSocket. Odpowiada za wywołanie metody accept
     */
    ServerSocket serverSocket;

    /**
     * Konstruktor klasy SerwerWorker. Uruchamia serverSocket i inicjalizuje go numerer portu
     * @param portNumber Numer portu
     */
    public SerwerWorker(int portNumber){
        port = portNumber;
        try{
            serverSocket = new ServerSocket(port);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda run, dziedziczona po klasie Thread
     * Cały czas nasłuchuje czy klient nie chce się połączyć
     * Jeśli tak, tworzy nowy obiekt SocketClientHandler, który odpowiada za obsluge polaczenia
     * Tutaj wywoływana jest metoda accept() serverSocket'u
     */
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
