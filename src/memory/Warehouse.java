/*
    Class Warehouse stores the earehouse itself

    NOTE!:

    Id and configuration mean the same thing. Id is the number that relates to that configuration.



 */

package memory;
import logic.Config;
import com.google.gson.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Warehouse {

    //warehouse creator
    public Warehouse(){

        System.out.println("Gathering information and saving");

        //retrieve warehouse information
        fromJsonToWarehouse();

        //if quit
        if (Config.forceQuit > 0 || Config.error > 1){

        }else{

            //create warehouse
            System.out.println("Finished gathering information");
            System.out.println("Creating warehouse");
            createWarehouse();
            printWarehouse();
            System.out.println("Warehouse created");
            System.out.println();
        }


    }

    //Dimensions of warehouse
    private int max_x;
    private int max_y;
    private int max_z = Config.MAX_Z;

    //Entrance points of warehouse
    private int entrance_X;
    private int entrance_Y;

    public int getEntrance_X() {
        return entrance_X;
    }

    public void setEntrance_X(int entrance_X) {
        this.entrance_X = entrance_X;
    }

    public int getEntrance_Y() {
        return entrance_Y;
    }

    public void setEntrance_Y(int entrance_Y) {
        this.entrance_Y = entrance_Y;
    }



    //WarehouseArray contains all shelves of the warehouse in the correct order
    WarehouseShelf warehouseArray [][];

    //warehouse map array booleans for warehouseView
    boolean warehouseBooleans[][];

    public boolean[][] getWarehouseBooleans() {
        return warehouseBooleans;
    }

    public void setWarehouseBooleans(boolean[][] warehouseBooleans) {
        this.warehouseBooleans = warehouseBooleans;
    }


    //getters setters for warehouse array

    public WarehouseShelf[][] getWarehouseArray() {
        return warehouseArray;
    }

    //Getters/setters for dimension attributes
    private int getMax_x() {
        return max_x;
    }

    private void setMax_x(int max_x) {
        this.max_x = max_x;
    }

    private int getMax_y() {
        return max_y;
    }

    private void setMax_y(int max_y) {
        this.max_y = max_y;
    }

    //Contains all configurations
    ArrayList<ShelfConfiguration> shelves_config = new ArrayList<ShelfConfiguration>();

    //Contains all shelves
    ArrayList <Shelf> shelves = new ArrayList<Shelf>();

    //Class that represents shelf configurations
    private class ShelfConfiguration {

        int id;
        int lenght;

        //constructors, getters and setters
        public ShelfConfiguration(int id, int lenght) {
            this.id = id;
            this.lenght = lenght;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLenght() {
            return lenght;
        }

        public void setLenght(int lenght) {
            this.lenght = lenght;
        }

    }

    //Class that represent a shelf
    private class Shelf{

        int config;
        int x_start;
        int y_start;
        char orientation;

        //Getters, setters and constructors of shelf

        public Shelf(int config, int x_start, int y_start, char orientation) {
            this.config = config;
            this.x_start = x_start;
            this.y_start = y_start;
            this.orientation = orientation;
        }

        public int getConfig() {
            return config;
        }

        public void setConfig(int config) {
            this.config = config;
        }

        public int getX_start() {
            return x_start;
        }

        public void setX_start(int x_start) {
            this.x_start = x_start;
        }

        public int getY_start() {
            return y_start;
        }

        public void setY_start(int y_start) {
            this.y_start = y_start;
        }

        public char getOrientation() {
            return orientation;
        }

        public void setOrientation(char orientation) {
            this.orientation = orientation;
        }

    }

    private class WarehouseShelf {

        int configuration = Config.DEFAULT_SHELF_CONFIG;
        int length;
        char orientation;

        public WarehouseShelf(int configuration, int length, char orientation) {
            this.configuration = configuration;
            this.length = length;
            this.orientation = orientation;
        }

        public int getConfiguration() {
            return configuration;
        }

        public void setConfiguration(int configuration) {
            this.configuration = configuration;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public char getOrientation() {
            return orientation;
        }

        public void setOrientation(char orientation) {
            this.orientation = orientation;
        }
    }

    //save json info for later creating the warehouse
    private void fromJsonToWarehouse (){

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject tempobject;
        JsonArray tempArray;
        String path;

        try {

            /*
            System.out.println("Introdueix path al arxiu");

            */

            path = createValidPath();

            if (path.equals("")) {

                //should return something to quit
                Config.forceQuit = 1;

            } else {

                tempobject = (JsonObject) parser.parse(new FileReader(path));

                //we read and set the
                max_x = tempobject.getAsJsonObject("dim").get("max_x").getAsInt();
                max_y = tempobject.getAsJsonObject("dim").get("max_y").getAsInt();

                //we read and set entrance points
                entrance_X = tempobject.getAsJsonObject("entrance").get("x").getAsInt();
                entrance_Y = tempobject.getAsJsonObject("entrance").get("y").getAsInt();

                //we read the shelves_configuration array
                tempArray = tempobject.get("shelves_config").getAsJsonArray();

                //we will read and add all elements from the array
                for (int i = 0; i < tempArray.size(); i++) {

                    //get the item we need from the array
                    JsonObject tempShelfConfObject = tempArray.get(i).getAsJsonObject();

                    //create new object with the information read from the json file
                    ShelfConfiguration tempShelfConfiguration = new ShelfConfiguration(tempShelfConfObject.get("id").getAsInt(), tempShelfConfObject.get("length").getAsInt());

                    //add configuration to our configuration arraylist
                    shelves_config.add(tempShelfConfiguration);

                }

                //we read the shelves array
                tempArray = tempobject.get("shelves").getAsJsonArray();

                //we will read and add all elements from the array
                for (int s = 0; s < tempArray.size(); s++) {

                    //get the item we need from the array
                    JsonObject tempShelfJObject = tempArray.get(s).getAsJsonObject();

                    //create new object with the information we read from json file
                    Shelf tempShelf = new Shelf(tempShelfJObject.get("config").getAsInt(), tempShelfJObject.get("x_start").getAsInt(), tempShelfJObject.get("y_start").getAsInt(), tempShelfJObject.get("orientation").getAsCharacter());

                    //add shelf to our shelves array
                    shelves.add(tempShelf);

                }

            }

        }catch(IOException e){

            System.out.println(e);

        }

    }

    //creates the warehouse with the previously gathered information
    private void createWarehouse (){

        try {
            warehouseArray = new WarehouseShelf[max_x][max_y];

            warehouseBooleans = new boolean[max_x][max_y];

            for (int i = 0; i < shelves.size(); i++) {

                Shelf tempShelf = shelves.get(i);

                ShelfConfiguration tempShelfConfiguration = findConfiguration(tempShelf.getConfig());

                //we create a shelf to add to the warehouse array
                WarehouseShelf tempWarehouseShelf = new WarehouseShelf(tempShelf.getConfig(), tempShelfConfiguration.getLenght(), tempShelf.getOrientation());

                //if it is horizontal we will fill horizontally
                if (shelves.get(i).getOrientation() == 'H') {

                    for (int x = tempShelf.getX_start(); x < tempWarehouseShelf.getLength() + tempShelf.getX_start(); x++) {

                        warehouseArray[x][tempShelf.getY_start()] = tempWarehouseShelf;
                        warehouseBooleans[x][tempShelf.getY_start()] = true;

                    }

                    //if it is vertical we will fill vertically
                } else if (shelves.get(i).getOrientation() == 'V') {

                    for (int y = tempShelf.getY_start(); y < tempWarehouseShelf.getLength() + tempShelf.getY_start(); y++) {

                        warehouseArray[tempShelf.getX_start()][y] = tempWarehouseShelf;
                        warehouseBooleans[tempShelf.getX_start()][y] = true;

                    }

                }

            }

            //fill warehouse entrance with default configuration
            warehouseArray[entrance_X][entrance_Y] = new WarehouseShelf(Config.DEFAULT_ENTRANCE_CONFIG, 0, '0');
        }catch(ArrayIndexOutOfBoundsException e){

            Config.error = 1;
            Config.forceQuit = 1;

        }
    }

    //finds configuration with that id
    private ShelfConfiguration findConfiguration (int id){

        boolean found = false;
        ShelfConfiguration tempShelfConfiguration;
        int index = 0;

        try {
            do {

                tempShelfConfiguration = shelves_config.get(index);

                //if that id is equal to the desired one
                if (tempShelfConfiguration.getId() == id) {

                    found = true;

                } else {

                    //if we havent found it we will search the next configuration
                    index++;


                }

                if (index > shelves_config.size()) {

                    found = true;
                    System.out.println("Error finding the configuration");

                }

            } while (found == false);

            //we return the desired configuration
            return tempShelfConfiguration;

        //if fails finding configuration returns null
        }catch (IndexOutOfBoundsException e){

            System.out.println("Error finding configuration");
            return null;

        }

    }

    //prints the warehouse for test purposes
    private void printWarehouse (){

        for (int y = 0; y < max_y; y++){

            for (int x = 0; x < max_x; x++){

                try{

                    System.out.print("Id: "+warehouseArray[x][y].getConfiguration());
                    System.out.print("     ");

                }catch(NullPointerException e){

                    System.out.print("Id: 0");
                    System.out.print("     ");

                }

            }

            System.out.println();

        }

    }

    //ask user path and prepare it for use
    private String createValidPath (){

        Scanner sc = new Scanner(System.in);
        JsonParser parser = new JsonParser();
        JsonObject tempobject;
        boolean ok = false, ok2 = false;
        String option = null;
        String path = null;


         do {

             ok2 = false;
             System.out.println("Insert path");

             //read users input
             path = sc.nextLine();

             //make sure user will use correct slashed for separating path
             path = path.replaceAll("\\\\", "\\\\\\\\" );

             //try and find the file
             try {

                 tempobject = (JsonObject) parser.parse(new FileReader(path));
                 ok = true;

             } catch (FileNotFoundException e) {

                 //if file cannot be find we will tell the user
                 System.out.println("Error no file found with that path. Try again?");


                 do {

                     option = sc.nextLine();

                     //let user decide if close program or choose different path
                     if (option.compareToIgnoreCase("no") == 0) {

                         System.out.println("Bye");
                         ok2 = true;
                         ok = true;
                         path = "";

                     } else if (option.compareToIgnoreCase("yes") == 0) {

                         ok2 = true;

                     } else {

                         System.out.println("Wrong option. Answer yes or no pls");

                     }

                 }while(!ok2);

             }

         }while(!ok);

        return path;

    }

}