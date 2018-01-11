package logic;

public class Main {

    public static void main(String[] args){
        Menu m = new Menu();
        boolean[] executada = new boolean[3];
        int opcio;

        do {
            opcio = m.executaMenu(executada);
        }while(opcio != 5);
    }
}
