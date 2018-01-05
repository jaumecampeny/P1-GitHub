package memory;

import java.util.ArrayList;

public class Configuracio {

    private ArrayList<Node> xMillor = new ArrayList<>();

    //agrega tots els productes com a nodes vius al principi
    private ArrayList<ArrayList<Node>> agregaNodesVius(Producte[] p, ArrayList<Casella> ac){

        ArrayList<ArrayList<Node>> nodesVius = new ArrayList<>();

        for(int i = 0; i < p.length; i++){

            ArrayList<Node> x, fills, aux;
            aux = new ArrayList<>();
            aux.add(new Node(ac.get(0),p[i]));
            nodesVius.add(aux);

        }

        return nodesVius;

    }

    //agrega caselles
    private ArrayList<Casella> agregaCasella (Warehouse wh){

        ArrayList<Casella> ac = new ArrayList<>();

        for(int i = 0; i < wh.getWarehouseBooleans().length; i++){

            for(int j = 0; j < wh.getWarehouseBooleans()[i].length; j++){

                if(wh.getWarehouseBooleans()[i][j]){

                    ac.add(new Casella(i,j,0));
                    ac.add(new Casella(i,j,1));
                    ac.add(new Casella(i,j,2));

                }

            }

        }


        return ac;
    }

    public Configuracio(Warehouse wh, Producte[] p, float[][] probabilitats) {

        ArrayList<Casella> ac;
        ArrayList<ArrayList<Node>> nodesVius;
        ArrayList<Node> x, fills, aux;
        float vMillor = 9999999;
        int comptador = 0, comptador2 = 0;

        //agreguem caselles
        ac = agregaCasella(wh);

        ////agreguem tots els productes com a nodes vius
        nodesVius = agregaNodesVius(p, ac);


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
    }

    public ArrayList<Node> getxMillor() {
        return xMillor;
    }

    public class Node{
        private Casella casella;
        private Producte producte;

        public Node(Casella casella, Producte producte) {
            this.casella = casella;
            this.producte = producte;
        }

        public Casella getCasella() {
            return casella;
        }

        public Producte getProducte() {
            return producte;
        }

        public ArrayList<Node> expandeix(Producte[] p, ArrayList<Casella> acasella){

            ArrayList<Node> aconfiguracio = new ArrayList<>(p.length);
            int posicio = -1;

            for(int j = 0; j < acasella.size(); j++){

                if(this.casella.equals(acasella.get(j))){

                    posicio = j+1;

                }

            }

            for(int i = 0; i < p.length; i++){

                aconfiguracio.add(new Node(acasella.get(posicio),p[i]));

            }

            return aconfiguracio;
        }

        public boolean solucio(ArrayList<Node> ac, Producte[] p){
            return(ac.size() + 1 == p.length);
        }

        public boolean bona(ArrayList<Node> ac){

            boolean b = true;

            for(int i = 0; i < ac.size(); i++){

                if(this.producte.equals(ac.get(i).producte)){

                    b = false;

                }

            }

            return b;

        }

        //public float valorParcial(ArrayList<Configuracio> ac, Producte[] p, float[][] probabilitats){
        //    int[] posicio = new int[2];

        //    if(ac.size() > 1) {
        //        for (int i = 0; i < p.length; i++) {
        //            if (this.producte.equals(p[i])) {
        //                posicio[0] = i;
        //            }
        //            if (ac.get(ac.size()-1).producte.equals(p[i])) {
        //                posicio[1] = i;
        //            }
        //        }
        //        return probabilitats[posicio[0]][posicio[1]];
        //    }else{
        //        return 0;
        //    }
        //}

        public float valor(ArrayList<Node> ac, Producte[] p, float[][] probabilitats){

            boolean[] control;
            float valor = 0;
            Casella[] c = new Casella[2];

            for(int i = 0; i < probabilitats.length; i++){

                for(int j = i; j < probabilitats[i].length; j++){

                    control = new boolean[2];

                    for(int k = 0; k < ac.size(); k++){

                        if(ac.get(k).producte.equals(p[i]) || this.producte.equals(p[i])){

                            control[0] = true;
                            c[0] = ac.get(k).casella;

                        }
                        if(ac.get(k).producte.equals(p[j]) || this.producte.equals(p[j])){

                            control[1] = true;
                            c[1] = ac.get(k).casella;

                        }
                        if(control[0] && control[1]){

                            valor = valor + probabilitats[i][j]*(Math.abs(c[0].getX() - c[1].getX()) + Math.abs(c[0].getY() - c[1].getY()) + Math.abs(c[0].getZ() - c[1].getZ()));

                        }

                    }

                }

            }

            return valor;

        }

    }

}
