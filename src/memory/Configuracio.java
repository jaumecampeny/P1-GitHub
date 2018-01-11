package memory;

import java.util.ArrayList;

public class Configuracio {

    private ArrayList<Node> xMillor = new ArrayList<>();

    public class Prestatge {
        private int prestatges_necessaries;
        private ArrayList<Casella> xMillor;
        private int[] vMillor;


        public Prestatge(int[] prestatges, int prestatges_necessaries, Warehouse wh, ArrayList<Casella> ac) {
            System.out.println("\tBuscant prestatges òptims...");
            this.prestatges_necessaries = prestatges_necessaries;
            this.vMillor = new int[2];
            this.vMillor[0] = this.vMillor[1] = 9999;
            BackTracking(prestatges,0, wh, ac);
            printar();

        }

        private void printar(){
            String s = "\tPrestatges òptims trobats!\n";
            for(int i = 0; i < xMillor.size(); i++){
                s = s + "\t\t" + xMillor.get(i).toString() + "\n";
            }
            System.out.println(s);
        }

        public ArrayList<Casella> getxMillor() { return xMillor;}

        private int[] preparaRecorregut(int[] prestatges, int n) {
            int[] temp = prestatges.clone();
            temp[n] = -1;
            return  temp;
        }

        private boolean hiHaSuccesor(int[] prestatges, int n) { return prestatges[n] < 1; }

        private int[] seguentGerma(int[] prestatges, int n) {
            prestatges[n]++;
            if(prestatges[n] == 1){
                for(int i = n+1; i < prestatges.length; i++){
                    prestatges[i] = 0;
                }
            }
            return prestatges;
        }

        private boolean solucio(int[] prestatges, int n) { return n == prestatges.length - 1; }

        private boolean factible(int[] prestatges){
            int comptador = 0;
            for (int i = 0; i < prestatges.length; i++) {
                if (prestatges[i] == 1) comptador++;
            }
            return (comptador == prestatges_necessaries);
        }

        private boolean completable(int[] prestatges, ArrayList<Casella> ac, Warehouse wh){
            int comptador = 0;
            int[] valor;
            ArrayList<Casella> caselles = new ArrayList<>();
            for (int i = 0; i < prestatges.length; i++) {
                if (prestatges[i] == 1){
                    comptador++;
                    caselles.add(ac.get(i));
                }
            }
            valor = calculaCost(caselles, wh);

            return(comptador <= prestatges_necessaries && (valor[0] + valor[1] < vMillor[0] + vMillor[1] || ((valor[0] + valor[1] == vMillor[0] + vMillor[1]) && (valor[1] < vMillor[1]))));
        }

        private void tractarSolucio(int[] prestatges, ArrayList<Casella> ac, Warehouse wh) {
            ArrayList<Casella> caselles = new ArrayList<>();
            int[] valor;

            for (int i = 0; i < prestatges.length; i++) {
                if (prestatges[i] == 1) {
                    caselles.add(ac.get(i));
                }
            }

            valor = calculaCost(caselles, wh);

            if (valor[0] + valor[1] < vMillor[0] + vMillor[1] || ((valor[0] + valor[1] == vMillor[0] + vMillor[1]) && (valor[1] < vMillor[1]))) {                 //Busquem relacio - costosa i en cas d'empat prioritzem relacio entre ells
                this.vMillor = valor;
                this.xMillor = caselles;
            }


        }

        private int[] calculaCost(ArrayList<Casella> ac, Warehouse wh) {
            int[] cost = new int[2];

            for (int i = 0; i < ac.size(); i++) {
                cost[0] += Math.abs(ac.get(i).getX() - wh.getEntrance_X()) + Math.abs(ac.get(i).getY() - wh.getEntrance_Y());
            }

            for (int i = 0; i < ac.size(); i++) {
                for (int j = i + 1; j < ac.size(); j++) {
                    cost[1] += Math.abs(ac.get(i).getX() - ac.get(j).getX()) + Math.abs(ac.get(i).getY() - ac.get(j).getY()) + Math.abs(ac.get(i).getZ() - ac.get(j).getZ());
                }
            }

            return cost;
        }

        private void BackTracking(int[] prestatges, int n, Warehouse wh, ArrayList<Casella> ac) {
            prestatges = preparaRecorregut(prestatges, n);
            while (hiHaSuccesor(prestatges, n)) {
                prestatges = seguentGerma(prestatges, n);

                if (solucio(prestatges, n)) {
                    if (factible(prestatges)) { tractarSolucio(prestatges, ac, wh);}
                } else {
                    if (completable(prestatges, ac, wh)) {
                        BackTracking(prestatges, n + 1, wh, ac);
                    }
                }

            }
        }
    }

    //agrega tots els productes com a nodes vius al principi
    private ArrayList<ArrayList<Node>> agregaNodesVius(Producte[] p, ArrayList<Casella> ac){
        ArrayList<ArrayList<Node>> nodesVius = new ArrayList<>();
        ArrayList<Node> aux;

        for(int i = 0; i < p.length; i++){
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

    //troba el valor(probabilitat) d'un nodeViu
    private float valorNodes(ArrayList<Node> ac, Producte[] p, float[][] probabilitats){

        boolean[] control;
        boolean condicio;
        float valor = 0;
        Casella[] c = new Casella[2];

        for(int i = 0; i < probabilitats.length; i++){

            for(int j = i+1; j < probabilitats[i].length; j++){
                control = new boolean[2];
                condicio = true;
                for(int k = 0; k < ac.size() && condicio; k++){

                    if(ac.get(k).producte.equals(p[i])){

                        control[0] = true;
                        c[0] = ac.get(k).casella;

                    }
                    if(ac.get(k).producte.equals(p[j])){

                        control[1] = true;
                        c[1] = ac.get(k).casella;

                    }
                    if(control[0] && control[1]){
                        valor = valor + (1 - probabilitats[i][j])*(Math.abs(c[0].getX() - c[1].getX()) + Math.abs(c[0].getY() - c[1].getY()) + Math.abs(c[0].getZ() - c[1].getZ()));
                        condicio = false;
                    }
                }
            }
        }
        return valor;

    }

    //tria el node amb el valor + gran
    private ArrayList<Node> seleccionaNodes(ArrayList<ArrayList<Node>> aan, Producte[] p, float[][] probabilitats){
        float costPromig = 9999, valor; int k = 0, sumaFactorial;
        ArrayList<Node> temp;

        for (int i = 0; i < aan.size(); i++) {
            sumaFactorial = 0;
            for (int j = 1; j < aan.get(i).size(); j++) {
                sumaFactorial += j;
            }

            valor = valorNodes(aan.get(i), p, probabilitats);
            if (valor/sumaFactorial < costPromig) {
                    costPromig = valor/sumaFactorial;
                    k = i;
            }
        }

        temp = aan.get(k);
        aan.remove(k);
        return temp;
    }

    private float valorIncial(ArrayList<Casella> ac, Producte[] p, float[][] probabilitats){
        ArrayList<Node> an = new ArrayList<>();
        for(int i = 0; i < p.length; i++){
            an.add(new Node(ac.get(i),p[i]));
        }
        return valorNodes( an, p, probabilitats);

    }

    public Configuracio(Warehouse wh, Producte[] p, float[][] probabilitats) {
        ArrayList<Casella> ac;
        ArrayList<ArrayList<Node>> nodesVius;
        ArrayList<Node> x, fills, aux;
        float vMillor;
        int cont = 0;

        //agreguem caselles
        ac = agregaCasella(wh);

        //Busquem els millors llocs per colocar productes
        Prestatge prestatge = new Prestatge(new int[ac.size()],p.length, wh, ac);
        System.out.print("\tBuscant millor configuracio");
        ////agreguem tots els productes com a nodes vius
        nodesVius = agregaNodesVius(p, prestatge.getxMillor());

        vMillor = valorIncial(prestatge.getxMillor(), p, probabilitats);

        while(!nodesVius.isEmpty()){
            x = seleccionaNodes(nodesVius, p, probabilitats);
            fills = x.get(x.size()-1).expandeix(p, prestatge.getxMillor());

            for(int i = 0; i < fills.size(); i++){
                if(fills.get(i).solucio(x,p)){
                    if(fills.get(i).bona(x)){
                        if(fills.get(i).valor(x, p, probabilitats) < vMillor){
                            cont++;
                            vMillor = fills.get(i).valor(x, p, probabilitats);
                            this.xMillor = new ArrayList<>(x);
                            this.xMillor.add(fills.get(i));
                            System.out.print(".");
                        }
                    }
                }else{
                    if(fills.get(i).bona(x)){
                        if(fills.get(i).valor(x, p, probabilitats) < vMillor){
                            cont++;
                            aux = new ArrayList<>(x);
                            aux.add(fills.get(i));
                            nodesVius.add(aux);
                        }
                    }
                }
            }
        }
        System.out.println("\n\n\tEl millor: " + vMillor + "\n");
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

        public float valor(ArrayList<Node> ac, Producte[] p, float[][] probabilitats){
            boolean[] control; boolean condicio;
            float valor = 0;
            Casella[] c = new Casella[2];

            for(int i = 0; i < probabilitats.length; i++){

                for(int j = i+1; j < probabilitats[i].length; j++){

                    control = new boolean[2];
                    condicio = true;

                    for(int k = 0; k < ac.size() && condicio; k++){

                        if(ac.get(k).producte.equals(p[i]) || this.producte.equals(p[i])){

                            control[0] = true;

                            if(ac.get(k).producte.equals(p[i])) {

                                c[0] = ac.get(k).casella;

                            }else{

                                c[0] = this.casella;

                            }
                        }

                        if(ac.get(k).producte.equals(p[j]) || this.producte.equals(p[j])){

                            control[1] = true;

                            if(ac.get(k).producte.equals(p[j])) {

                                c[1] = ac.get(k).casella;

                            }else{

                                c[1] = this.casella;

                            }

                        }

                        if(control[0] && control[1]){

                            valor = valor + (1 - probabilitats[i][j])*(Math.abs(c[0].getX() - c[1].getX()) + Math.abs(c[0].getY() - c[1].getY()) + Math.abs(c[0].getZ() - c[1].getZ()));

                            condicio = false;

                        }

                    }

                }

            }

            return valor;

        }

    }

}