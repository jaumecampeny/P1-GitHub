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

<<<<<<< HEAD
    public ArrayList<Configuracio> Opcio3(Warehouse wh, Producte[] p, float[][] probabilitats){

        ArrayList<Casella> ac = new ArrayList<>();
        ArrayList<ArrayList<Configuracio>> nodesVius = new ArrayList<>();
        ArrayList<Configuracio> x, xMillor = new ArrayList<>(), fills, aux;
        float vMillor = 9999999;
        int comptador = 0, comptador2 = 0;

        for(int i = 0; i < wh.getWarehouseBooleans().length; i++){

            for(int j = 0; j < wh.getWarehouseBooleans()[i].length; j++){

                if(wh.getWarehouseBooleans()[i][j]){

                    ac.add(new Casella(i,j,0));
                    ac.add(new Casella(i,j,1));
                    ac.add(new Casella(i,j,2));

                }

            }

        }

        for(int i = 0; i < p.length; i++){

            aux = new ArrayList<>();
            aux.add(new Configuracio(ac.get(0),p[i]));
            nodesVius.add(aux);

        }

        while(!nodesVius.isEmpty()){
            x = nodesVius.get(0);
            nodesVius.remove(0);
            fills = x.get(x.size()-1).expandeix(p, ac);

            for(int i = 0; i < fills.size(); i++){
                if(fills.get(i).solucio(x,p)){
                    if(fills.get(i).bona(x)){
                        comptador++;
                        if(fills.get(i).valor(x, p, probabilitats) < vMillor){
                            comptador2++;
                            vMillor = fills.get(i).valor(x, p, probabilitats);
                            xMillor = new ArrayList<>(x);
                            xMillor.add(fills.get(i));
                        }
                    }
                }else{
                    if(fills.get(i).bona(x)){
                        comptador++;
                        if(fills.get(i).valor(x, p, probabilitats) < vMillor){
                            aux = new ArrayList<>(x);
                            aux.add(fills.get(i));
                            nodesVius.add(aux);
                        }
                    }
                }
            }
            //ordena nodesVius

        }

        System.out.println(xMillor.size() + "   " + comptador + "   " + comptador2);

        for(int i = 0; i < xMillor.size(); i++){
            System.out.print(xMillor.get(i).getCasella().toString() + "   ");
            System.out.println(xMillor.get(i).getProducte().toString());
        }

        return xMillor;
=======
    public ArrayList<Configuracio.Node> Opcio3(Warehouse wh, Producte[] p, float[][] probabilitats){
        return new Configuracio(wh,p,probabilitats).getxMillor();
>>>>>>> b3f94dad8e8c7844f530daa78d99327e48252ff1
    }

    public void Opcio4(){

    }
}
