package logic;

import memory.Configuracio;

import java.util.ArrayList;

public class Config {

    //All default parameters
    public static final int MAX_Z = 3;
    public static final int DEFAULT_SHELF_CONFIG = 5;
    public static final int DEFAULT_ENTRANCE_CONFIG = -1;

    //general variable tha will change of state when an error in execution happends
    public static int error = 0;

    //general variable that it will change state when we want to force quit
    public static int forceQuit = 0;

    public static ArrayList<Configuracio.Node> solucio;
}
