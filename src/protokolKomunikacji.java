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
        else
        return -1;
    }

}
