package app;

import components.PandemicSim_CSVOutput;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");
        PandemicSim_CSVOutput gameMasterCSV = new PandemicSim_CSVOutput();

        System.out.println("Daten werden berechnet. Es dauert möglicherweise etwas.");

        gameMasterCSV.playTurns(300);

        System.out.println("Done!");
    }
}