package app;

import components.PandemicSim_CSVOutput;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");
        PandemicSim_CSVOutput pandemicSim_CSVOutput = new PandemicSim_CSVOutput(1000000,10,0);

        System.out.println("Daten werden berechnet. Es dauert möglicherweise etwas.");

        pandemicSim_CSVOutput.playTurns(300);

        System.out.println("Done!");
    }
}