package app;

import components.GameMasterCSV;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");
        GameMasterCSV gameMasterCSV = new GameMasterCSV();

        System.out.println("Daten werden berechnet. Es dauert möglicherweise etwas.");

        gameMasterCSV.playTurns(300);

        System.out.println("Done!");
    }
}