package components;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * GameMasterCSV
 */
public class PandemicSim_CSVOutput extends PandemicSim {

    public PandemicSim_CSVOutput(int healthy, int infected, int diseased) {
        super(healthy, infected, diseased);
        this.setupStream();
    }

    public PandemicSim_CSVOutput(){
        super();
        this.setupStream();
    }

    public void setupStream() {
        try {
            this.setPrintStream(new PrintStream("data.csv"));
        } catch (FileNotFoundException e) {
            System.err.println(
                    "Datei wurde nicht gefunden, das müsste aber überhaupt kein Problem sein, da eine neue Datei erstellt werden sollte...");
        }
        out.println("Runde\tAnzahl aller Gesunden\tAnzahl aller Kranken\tAnzahl aller Toten");

    }

    @Override
    public String toString() {
        return this.getTurn() + "\t" + this.getHealthyCount() + "\t" + this.getInfectedCount() + "\t"
                + this.getDiseasedCount();
    }

    public static void csv(PandemicSim pSim) {
        System.out.println(pSim.getTurn() + "," + pSim.getHealthyCount() + "," + pSim.getInfectedCount() + "," + pSim.getDiseasedCount());
    }
}