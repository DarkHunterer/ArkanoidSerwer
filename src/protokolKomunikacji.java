/**
 * Created by Daniel on 31.01.2016.
 */
public class protokolKomunikacji {
    String inLine,outLine;
    public protokolKomunikacji(){
        inLine="Puste";
        outLine="Puste";
    }
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
