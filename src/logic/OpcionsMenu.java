package logic;
import files.Fitxer;
import memory.*;
import org.json.JSONArray;

import java.util.*;

public class OpcionsMenu {

    public Warehouse Opcio1(){
        //create a warehouse and display as plain text
        Warehouse warehouse = new Warehouse();

        //check for forceQuit
        if (Config.forceQuit > 0 || Config.error > 0){

            //check for errors
            if (Config.error > 0){

                System.out.println();
                System.out.println("ERROR SOMETHING WENT WRONG");
                System.out.println("System shutdown. Error code "+Config.error);

            }
            return null;
        }else{

            //show warehouse in GUI
            //WarehouseView warehouseView = new WarehouseView(warehouse.getWarehouseBooleans(), warehouse.getEntrance_X(), warehouse.getEntrance_Y());
            return warehouse;
        }
    }

    public Producte[] Opcio2Productes(){
        Scanner sc = new Scanner(System.in);
        Fitxer f;
        JSONArray ja;
        Producte[] p;

        do {
            System.out.print("Localització del fitxer productes: ");
            f = new Fitxer(sc.nextLine());


            if(!f.exists()){
                System.out.println("Error: No existeix el fitxer de productes");
            }

        }while(!f.exists());

        ja = f.llegeixFitxerProductes();
        p = new Producte[ja.length()];
        for(int i = 0; i < ja.length(); i++){
            p[i] = new Producte();
            p[i].setId(ja.getJSONObject(i).getInt("id"));
            p[i].setName(ja.getJSONObject(i).getString("name"));
        }
        return p;
    }

    public float[][] Opcio2Probabilitats(Producte[] p){
        Scanner sc = new Scanner(System.in);
        Fitxer f;

        do{
            System.out.print("Localització del fitxer probabilitats: ");
            f = new Fitxer(sc.nextLine());

            if(!f.exists()){
                System.out.println("Error: No existeix el fitxer de probabilitats");
            }
        }while(!f.exists());
        System.out.println();

        return f.llegeixFitxerProbabilitats(p);
    }

    public ArrayList<Configuracio.Node> Opcio3(Warehouse wh, Producte[] p, float[][] probabilitats){
        return new Configuracio(wh,p,probabilitats).getxMillor();
    }

    public void Opcio4(){

    }
}
