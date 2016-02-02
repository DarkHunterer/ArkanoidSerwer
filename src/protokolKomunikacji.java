/**
 * Created by Daniel and Konrad on 31.01.2016.
 * Klasa odpowiadająca za protokół komunikacji
 * Przetwarza otrzymaną od klienta komende
 * Na jej podstawie zwraca odpowiedź, która steruje zachowaniem serwera
 */
public class protokolKomunikacji {
    /**
     * Pole przechowujące komende klienta
     */
    String inLine;

    /**
     * Konstruktor protokołu. Jedynym zadaniem jest zainicjalizowanie pola inLine domyślną wartością.
     */
    public protokolKomunikacji(){
        inLine="Puste";
    }

    /**
     * Jedyna metoda protokołu komunikacji
     * Przetwarza komendę wejściową
     * Zwraca sterowanie obsługi klienta
     * @param inputLine Komenda do przetworzenia
     * @return Wartość typu int zwracana jako sterowanie. Ta wartość wykorzystana jest w SocketClientHandler
     */
    int processInput(String inputLine){
        System.out.println("Przetwarzam: "+inputLine);
        if(inputLine.equals("Witaj serwerze")) {
            System.out.println("Przetworzyłem przywitanie klienta");
            return 9;
        }
        else if(inputLine.equals("Gimme config")){
            System.out.println("Przetworzylem prosbe o config");
            return 1;
        }
        else if(inputLine.equals("Gimme maps")){
            System.out.println("Przetworzylem prosbe o mapy");
            return 2;
        }
        else if(inputLine.contains("Daj")){
            System.out.println("Przetworzylem prosbe o plik - komenda \"daj\"");
            return 3;
        }
        else if(inputLine.contains("Gimme highscore")){
            System.out.println("Przetworzylem prosbe o highscore");
            return 4;
        }
        else if(inputLine.contains("Wynik")){
            System.out.println("Przetworzylem otrzymany wynik");
            return 5;
        }
        else if(inputLine.contains("Nick")){
            System.out.println("Przetworzylem otrzymany nick");
            return 6;
        }
        else
        System.out.println("Nie znam komendy: "+ inputLine);
        return -1;
    }

}
