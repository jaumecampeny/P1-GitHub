package memory;

import controller.BoxListener;
import logic.Config;
import view.WarehouseView;

import java.util.ArrayList;

//AQUESTA CLASSE PODRA SER ELIMINADA I JUNTADA AMB LA CLASSE OpcionsMenu
//the object that sets the GUI
public class GuiSet {

    Warehouse warehouse;


    public GuiSet (Warehouse warehouse, ArrayList<Configuracio.Node> xMillor) {

        this.warehouse = warehouse;

        String [] tempString;
        //create warehouse view
        WarehouseView warehouseView = new WarehouseView(warehouse.getWarehouseBooleans(), warehouse.getEntrance_X(), warehouse.getEntrance_Y());

        //create likstener controller
        BoxListener controlador = new BoxListener(warehouseView, xMillor);

        //connect viewer and controller
        warehouseView.setMapMouseListener(controlador);

        //make it visible
        warehouseView.setVisible(true);

    }

}






