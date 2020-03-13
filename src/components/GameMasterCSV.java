package components;

import java.io.FileNotFoundException;
import java.io.PrintStream;


/**
 * GameMasterCSV
 */
public class GameMasterCSV extends GameMaster {

    public GameMasterCSV(int healthy, int infected, int diseased) {
        super(healthy, infected, diseased);
        try {
            this.setOut(new PrintStream("data.csv"));
        } catch (FileNotFoundException e) {
            System.err.println("Datei wurde nicht gefunden, das müsste aber überhaupt kein Problem sein, da eine neue Datei erstellt werden sollte...");
        }
        out.println("Runde,Anzahl aller Gesunden, Anzahl aller Kranken, Anzahl aller Toten");
    }

    @Override
    public String toString() {
        return this.getTurn() + "," + this.getHealthyCount() + "," + this.getInfectedCount() + "," + this.getDiseasedCount();
    }
}