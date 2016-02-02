import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Daniel and Konrad on 27.01.2016.
 * Klasa główna odpowiadająca za serwer
 * Klasa tworzy obiekt typu SerwerWorker który otwiera serwerSocket
 * Klasa Serwer odpowiada głównie za GUI serwera
 */
public class Serwer extends JFrame implements ActionListener {
    /**
     * Zmienna przechowująca port na ktorym serwer będzie nasłuchiwał
     */
    private int port;
    /**
     * Pole przechowujące obiekt buttonu start
     */
    private JButton btn_start;
    /**
     * Pole przechowujace obiekt buttonu exit
     */
    private JButton btn_exit;
    /**
     * Pole przechowujące obiekt do wyswietlania statusu serwera
     */
    private JTextField textField_status;
    /**
     * Pole przechowujące obiekt typu SerwerWorker, który odpowiada za połącznie z klientami
     */
    SerwerWorker serwer;

    /**
     * Konstruktor Serwera. Aby stworzyć serwer należy podać numer portu
     * @param portNumb Parametr który jest numerem portu
     */
    public Serwer(int portNumb){
        setDefualtProperties();
        port = portNumb;
        initializeUI();
    }

    /**
     * Metoda odpowiadająca za ustawienia okna GUI serwera, takie jak wielkość, tytuł belki i layout
     */
    private void setDefualtProperties() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300,500));
        setTitle("Serwer gry");
        setLocationRelativeTo(null);
        this.setLayout(new GridLayout(4,4));
    }

    /**
     * Metoda która inicjalizuje interfejs graficzny
     */
    private void initializeUI() {
        textField_status = new JTextField("Status: Wylaczony.");
        btn_start = new JButton("Start");
        btn_exit = new JButton("Exit");
        add(textField_status);
        add(btn_start);
        add(btn_exit);
        btn_start.addActionListener(this);
        btn_exit.addActionListener(this);
        setVisible(true);
        pack();
    }

    /**
     * Metoda main. Tworzy serwer na podstawie numeru portu
     *
     */
    public static void main(String[] args){
        int portNumber = 4455;
        // initializing the Socket Server
        Serwer serv = new Serwer(portNumber);

    }

    /**
     * Metoda start, uruchamiająca wątek serwera.
     * Wywoływana jest przez kliknięcie buttonu start
     */
    void start(){
        boolean listening = true;
        serwer = new SerwerWorker(port);
        Thread thread = serwer;
        thread.start();
        System.out.println("Koniec metody start");
    }

    /**
     * Metoda obsługujące zdarzenia w klasie Serwer.
     * Obsługuje kliknięcie w guzik start i exit
     * @param e Pole typu ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btn_start){
            System.out.println("Start dzia�a");
            textField_status.setText("Status: Wlaczony.");
            start();
        }
        else if(e.getSource()==btn_exit){
            System.exit(1);
        }
    }
}
