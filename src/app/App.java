package app;

import components.GameMasterCSV;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");
        GameMasterCSV gameMasterCSV = new GameMasterCSV(1000000, 10, 0);

        gameMasterCSV.playTurns(1000);
    }
}