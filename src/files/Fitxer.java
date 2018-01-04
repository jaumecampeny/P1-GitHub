package files;

import com.google.gson.Gson;
import memory.Producte;
import org.json.*;
import java.io.*;
import java.util.*;

public class Fitxer extends File {

    public Fitxer(String s){
        super(s);
    }


    //still to implement from warehouse class
    public JSONObject llegeixFitxerMagatzem(Fitxer f) throws FileNotFoundException{
            String info = new Scanner(f).useDelimiter("\\Z").next();
            Gson gson = new Gson();
            return gson.fromJson(info, JSONObject.class);
    }

    public JSONArray llegeixFitxerProductes(){
        try {
            String info = new Scanner(this).useDelimiter("\\Z").next();
            Gson gson = new Gson();
            return new JSONArray(gson.fromJson(info, ArrayList.class));
        }catch(FileNotFoundException e){
            System.out.println("Error: No s'ha trobat l'arxiu productes");
            return new JSONArray();
        }
    }

    public float[][] llegeixFitxerProbabilitats(Producte[] productes){
        float[][] probabilitats = new float[productes.length][productes.length];

        try {
            Scanner sc = new Scanner(this);
            int i, j;
            boolean error = false;

            for(i = 0; sc.hasNextLine() && i < probabilitats.length && !error; i++){
                for(j = 0; sc.hasNextLine() && j < probabilitats[i].length; j++){
                    while(sc.hasNextInt()){
                        sc.nextInt();
                    }
                    probabilitats[i][j] = Float.parseFloat(sc.nextLine());
                }

                if(j != probabilitats[i].length){
                    System.out.println("Error: El fitxer no té informació suficient");
                    error = true;
                }
            }

            if(i != probabilitats.length && !error){
                System.out.println("Error: El fitxer no té informació suficient");
                error = true;
            }

            if(error){
                return new float[0][0];
            }else {
                return probabilitats;
            }
        }catch(FileNotFoundException e){
            System.out.println("Error: No s'ha trobat l'arxiu probabilitats");
            return probabilitats;
        }

    }
}
