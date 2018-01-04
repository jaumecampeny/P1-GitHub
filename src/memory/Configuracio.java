package memory;

import java.util.ArrayList;

public class Configuracio {
    private Casella casella;
    private Producte producte;

    public Configuracio(Casella casella, Producte producte) {
        this.casella = casella;
        this.producte = producte;
    }

    public Casella getCasella() {
        return casella;
    }

    public Producte getProducte() {
        return producte;
    }

    public ArrayList<Configuracio> expandeix(Producte[] p, ArrayList<Casella> acasella){
        ArrayList<Configuracio> aconfiguracio = new ArrayList<>(p.length);
        int posicio = -1;

        for(int j = 0; j < acasella.size(); j++){
            if(this.casella.equals(acasella.get(j))){
                posicio = j+1;
            }
        }

        for(int i = 0; i < p.length; i++){
            aconfiguracio.add(new Configuracio(acasella.get(posicio),p[i]));
        }

        return aconfiguracio;
    }

    public boolean solucio(ArrayList<Configuracio> ac, Producte[] p){
        return(ac.size() + 1 == p.length);
    }

    public boolean bona(ArrayList<Configuracio> ac){
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

    public float valor(ArrayList<Configuracio> ac, Producte[] p, float[][] probabilitats){
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
