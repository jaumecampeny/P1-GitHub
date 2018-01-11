package logic;

import memory.*;

import java.io.IOException;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Menu {
    private Scanner sc;
    private int menu;
    private boolean error;
    private Warehouse wh;
    private Producte[] p;
    private float[][] probabilitats;
    /**
     * S'encarrega de printar per pantalla el menú
     */
    public void printaMenu() {
        System.out.println("1. Configurar magatzem");
        System.out.println("2. Carregar productes");
        System.out.println("3. Distribuir productes");
        System.out.println("4. Servir comanda");
        System.out.println("5. Sortir");
        System.out.println();
        System.out.print("Sel·lecciona una opció: ");
    }

    /**
     * Demana i comprova que l'opció de l'usuari sigui vàlida
     * @return Correcte/NoCorrecte
     */
    public boolean opcioCorrecte() {
        sc = new Scanner(System.in);
        error = false;
        try {
            menu = parseInt(sc.next());
        } catch (Exception e) {
            error = true;
            System.out.println("Error: S'ha introduit algun caràcter!");
        }

        if (!error && (menu < 1 || menu > 5)) {
            System.out.println("Error: S'ha introduit un número no corresponent al menú!");
        }
        System.out.println();

        error = error || menu < 1 || menu > 5;      //Busca si existeix algun error
        sc.nextLine();
        return error;
    }

    /**
     * Executa el menú, es combina amb els anteriors mètodes
     * @return OpcioDelMenu
     * @throws IOException si hi ha problemes al obrir el arxiu
     */
    public int executaMenu(boolean[] executada){
        OpcionsMenu om = new OpcionsMenu();
        ArrayList<Configuracio.Node> xMillor = null;
        sc = new Scanner(System.in);



        do {
            printaMenu();       //Printa les opcions del menú
            opcioCorrecte();    //Demana opcio i mira si aquesta és correcte
        } while (error);

        switch (menu) {

            case 1:

                wh = om.Opcio1();
                executada[0] = true;
                break;

            case 2:

                p = om.Opcio2Productes();
                probabilitats = om.Opcio2Probabilitats(p);
                executada[1] = true;
                break;

            case 3:

                if(executada[0] && executada[1]) {

                    executada[2] = true;
                    xMillor = om.Opcio3(wh, p, probabilitats);
                    Config.solucio = xMillor;



                }else{

                    System.out.println("Error: no s'han executat la opció 1 o la opció 2 (necessaries per executar aquesta)");

                }
                break;

            case 4:

                if(executada[0] && executada[1] && executada[2]){

                    GuiSet gui = new GuiSet(wh ,Config.solucio);
                    om.Opcio4();

                }else {

                    System.out.println("Error: no s'han executat la opció 1 o la opció 2 o la opció 3 (necessaries per executar aquesta)");

                }

                break;

            case 5:

                System.out.println("Gràcies per utilitzar el nostre programa!");

                break;

        }

        return menu;

    }

}
