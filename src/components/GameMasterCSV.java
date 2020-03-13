package components;

/**
 * GameMasterCSV
 */
public class GameMasterCSV extends GameMaster{

    public GameMasterCSV(int healthy, int infected, int diseased){
        super(healthy, infected, diseased);
    }

    @Override
    public String toString() {
        return this.turn + "," + this.healthyCount + "," + this.infectedCount + "," + this.diseasedCount;
    }
}