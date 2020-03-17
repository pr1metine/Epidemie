package components;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * GameMaster
 */
public class PandemicSim {

    private Person[] people;
    private int turn;
    private int healthyCount;
    private int infectedCount;
    private int diseasedCount;

    public PrintStream out;

    public PandemicSim() {
        this(System.out, 0, 0, 0);
    }

    public PandemicSim(int healthyCount, int infectedCount, int diseasedCount) {
        this(System.out, healthyCount, infectedCount, diseasedCount);
    }

    public PandemicSim(PrintStream out, int healthyCount, int infectedCount, int diseasedCount) {
        this.setPrintStream(out);
        this.setCount(healthyCount, infectedCount, diseasedCount);
    }

    public void compute(int turns) {
        this.compute(turns, (nothing) -> {
        });
    }

    /**
     * Berechnet die angegebene Anzahl an Runden.
     * <p>
     * Pro Runde werden zunächst zufällige Paare gebildet. 
     * <br/>
     * Wenn beide Personen in einem Paar gesund sind, passiert nichts. Wenn einer Gesund, der andere aber krank ist, besteht eine 50:50-Chance, dass der Gesunde krank wird. Wenn es einen Kranken gibt, bestehen für ihn folgende Chancen:
     * </p>
     * <ol>
     * <li>Er stribt (1:6)</li>
     * <li>Er lebt (4:6)</li>
     * <li>Er genest (1:6)</li>
     * </ol>
     * <br/>
     * 
     * @param turns Die Anzahl an Runden, die gespielt werden sollen
     * @param callback Eine sog. Callbackfunktion, die es einem erlaubt, mitten in der Ausführung der compute-Methode beliebige Befehle auszuführen. Benutze dafür <a href="https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html">Lambdafunktionen</a>
     */
    public void compute(int turns, Consumer<PandemicSim> callback) {
        out.println(this);
        callback.accept(this);

        for (int i = 0; i < turns; i++) {
            this.turn++;
            PairGenerator pairGenerator = new PairGenerator(this.getPeople());

            // TODO: implementiere parallele Streams
            Arrays.stream(pairGenerator.getPairs()).forEach((person) -> {
                this.decrementCount(person[0].getStatus());
                person[0].setStatus(HealthStatus.getNewStatus(person[0], person[1]));
                this.incrementCount(person[0].getStatus());
                this.decrementCount(person[1].getStatus());
                person[1].setStatus(HealthStatus.getNewStatus(person[1], person[0]));
                this.incrementCount(person[1].getStatus());
            });

            if (pairGenerator.hasLeftOver()) {
                Person leftOver = pairGenerator.getLeftOver();
                this.decrementCount(leftOver.getStatus());
                leftOver.setStatus(HealthStatus.getNewStatus(leftOver, Person.LEFTOVER_BUDDY));
                incrementCount(leftOver.getStatus());
            }
            out.println(this);
            callback.accept(this);
        }
    }

    /**
     * Erzeugt ein Array mit entsprechenden Leuten, je nachdem, wie viele gesunde,
     * kranke und tote Leute gewünscht werden
     */
    public void initPeople() {
        int total = healthyCount + infectedCount + diseasedCount;
        this.people = new Person[total];
        // Derzeitiger Index des Arrays people
        int globalIndex = 0;

        for (int i = 0; i < healthyCount; i++, globalIndex++) {
            people[globalIndex] = new Person(HealthStatus.HEALTHY);
        }

        for (int i = 0; i < infectedCount; i++, globalIndex++) {
            people[globalIndex] = new Person(HealthStatus.INFECTED);
        }

        for (int i = 0; i < diseasedCount; i++, globalIndex++) {
            people[globalIndex] = new Person(HealthStatus.DISEASED);
        }
    }

    /**
     * Eine einfache Methode, um nachträglich die Anzahl an gesunden, kranken und
     * toten Leuten zu ändern.
     * 
     * @param healthyCount  Die Anzahl an gesunden Leuten.
     * @param infectedCount Die Anzahl an kranken Leuten.
     * @param diseasedCount Die Anzahl an toten Leuten.
     */
    public void setCount(int healthyCount, int infectedCount, int diseasedCount) {
        this.setHealthyCount(healthyCount);
        this.setInfectedCount(infectedCount);
        this.setDiseasedCount(diseasedCount);
        this.initPeople();
    }

    /**
     * @param healthyCount the healthyCount to set
     */
    public void setHealthyCount(int healthyCount) {
        this.healthyCount = healthyCount >= 0 ? healthyCount : 0;
    }

    /**
     * @param diseasedCount the diseasedCount to set
     */
    public void setDiseasedCount(int diseasedCount) {
        this.diseasedCount = diseasedCount >= 0 ? diseasedCount : 0;
    }

    /**
     * @param infectedCount the infectedCount to set
     */
    public void setInfectedCount(int infectedCount) {
        this.infectedCount = infectedCount >= 0 ? infectedCount : 0;
    }

    public void decrementCount(HealthStatus healthState) {
        switch (healthState) {
            case HEALTHY:
                this.setHealthyCount(this.getHealthyCount() - 1);
                break;
            case INFECTED:
                this.setInfectedCount(this.getInfectedCount() - 1);
                break;
            case DISEASED:
                this.setDiseasedCount(this.getDiseasedCount() - 1);
                System.err.println(
                        "Something went wrong because there's a diseased person. Someone has been brought back to life.");
                break;
        }
    }

    public void incrementCount(HealthStatus healthState) {
        switch (healthState) {
            case HEALTHY:
                this.healthyCount++;
                break;
            case INFECTED:
                this.infectedCount++;
                break;
            case DISEASED:
                this.diseasedCount++;
                break;
        }
    }

    /**
     * @param out the out to set
     */
    public void setPrintStream(PrintStream out) {
        this.out = out;
    }

    /**
     * @return the diseasedCount
     */
    public int getDiseasedCount() {
        return diseasedCount;
    }

    /**
     * @return the infectedCount
     */
    public int getInfectedCount() {
        return infectedCount;
    }

    /**
     * @return the healthyCount
     */
    public int getHealthyCount() {
        return healthyCount;
    }

    /**
     * @return the people
     */
    public Person[] getPeople() {

        Person[] notDiseasedPeople = new Person[people.length - diseasedCount];

        for (int i = 0, iDiseased = 0; i < people.length; i++) {
            if (people[i].getStatus() != HealthStatus.DISEASED) {
                notDiseasedPeople[iDiseased] = people[i];
                iDiseased++;
            }
        }

        return notDiseasedPeople;

    }

    /**
     * @return the turn
     */
    public int getTurn() {
        return turn;
    }

    public void println(String string) {
        out.println(string);
    }

    @Override
    public String toString() {
        return "Turn " + this.turn + ": " + this.healthyCount + " HEALTHY, " + this.infectedCount + " INFECTED, "
                + this.diseasedCount + " DISEASED.";
    }

}