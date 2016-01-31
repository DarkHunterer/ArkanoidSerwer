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
 * Created by Daniel on 27.01.2016.
 */
public class Serwer extends JFrame implements ActionListener {

    private int port;

    private JButton btn_start;
    private JButton btn_exit;
    private JTextField textField_status;
    SerwerWorker serwer;
    public Serwer(int portNumb){
        setDefualtProperties();
        port = portNumb;
        initializeUI();
    }

    private void sendWelcomeMessage(Socket client) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        writer.write("Hello. You are connected to a Simple Socket Server. What is your name?");
        writer.flush();
        writer.close();
    }
    private void setDefualtProperties() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300,500));
        setTitle("Serwer gry");
        setLocationRelativeTo(null);
        this.setLayout(new GridLayout(4,4));
    }

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
    public static void main(String[] args){
        int portNumber = 4455;
        // initializing the Socket Server
        Serwer serv = new Serwer(portNumber);

    }

    void start(){
        boolean listening = true;
        serwer = new SerwerWorker(port);
        Thread thread = serwer;
        thread.start();
        System.out.println("Koniec metody start");
    }
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
