package controller;

import logic.Config;
import memory.Configuracio;
import view.WarehouseView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


/**
 * Material pràctica 1 Programació Avançada i Estructura de Dades
 * Enginyeria Informàtica
 * © La Salle Campus Barcelona - Departament d'Enginyeria
 *
 * Aquesta classe permet l'actualització de la GUI amb les dades del
 * magatzem donat que escolta els clics realitzats sobre la interfície
 * gràfica. El mètode 'mouseClicked' es crida cada cop que una casella
 * del magatzem que sigui d'una prestatgeria s'hagi clicat.
 *
 * Aquesta classe és modificable, en cap cas ha de suposar una limitació
 * en com ha estat implementada. És a dir, si necessiteu algun paràmetre
 * extra o atribut (o bé us sobren) teniu tot el dret a canviar-ho. L'únic
 * que s'ha de mantenir i completar és el mètode 'mouseClicked'.
 *
 * @author Albert Pernía Vázquez
 */
public class BoxListener implements MouseListener {


    private WarehouseView view;
    private ArrayList<Configuracio.Node> distribucio;


    public BoxListener(WarehouseView view, ArrayList<Configuracio.Node> distribucio ) {
        this.view = view;
        this.distribucio = distribucio;


    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //get x and y mouse position
        Point point = e.getPoint();

        //from the x and y position of the mouse to x and y shelf coordinates of the warehouse matrix
        point = view.getBoxClickedPosition(point);

        //check if exists shelf in that position
        if (point == null) {
            String temp [] = new String[]{

                    "Shelf does not exist",
                    "",
                    ""

            };

            view.setBoxInfo(temp);

        }else {

            boolean ok = false;
            int index = 0;

            while(!ok){

                //check if the box coordinates match with any products coordinates
                if (distribucio.get(index).getCasella().getX() == point.getX() && distribucio.get(index).getCasella().getY() == point.getY()){

                    //create a string to show product information big as amount of products per shelf AKA depth of shelf
                    String []temp = new String[Config.MAX_Z];

                    int index2 = index;

                    //for all amount of products, Z size
                    for (int i = 0; i < Config.MAX_Z; i++) {

                        try {

                            //check if next product in the distribution array is in the same box as the previous, if it is we will add it
                            if (distribucio.get(index).getCasella().getX() == distribucio.get(index2).getCasella().getX() && distribucio.get(index).getCasella().getY() == distribucio.get(index2).getCasella().getY()) {
                                temp[i] = "Producte: " + distribucio.get(index2).getProducte().getId() + " Nom: " + distribucio.get(index2).getProducte().getName();

                            }

                            //increase it to gather information from next Z product
                            index2++;

                        } catch (IndexOutOfBoundsException ee) {

                            temp[i] = "No product";

                        }

                    }

                    view.setBoxInfo(temp);

                    ok = true;

                }else{

                    index++;

                }

                //if no products exist we show
                if (index >= distribucio.size()){

                    ok = true;
                    String temp [] = new String[]{

                            "No Product",
                            "No Product",
                            "No Product"

                    };

                    view.setBoxInfo(temp);

                }

            }

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
