package app;

import java.io.PrintStream;

import components.PandemicSim;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");
        PandemicSim pandemicSim = new PandemicSim();
        pandemicSim.setCount(1000000, 10, 0);
        pandemicSim.setPrintStream(new PrintStream("data4.csv"));

        System.out.println("Daten werden berechnet. Es dauert möglicherweise etwas.");
        pandemicSim.compute(300);

        System.out.println("Done!");
    }
}