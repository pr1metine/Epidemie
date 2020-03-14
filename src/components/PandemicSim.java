package components;

import java.io.PrintStream;
import java.util.Scanner;

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
        this.readCount();
        this.initPeople();
    }

    public PandemicSim(int healthyCount, int infectedCount, int diseasedCount) {
        this.setHealthyCount(healthyCount);
        this.setInfectedCount(infectedCount);
        this.setDiseasedCount(diseasedCount);
        this.initPeople();
    }

    public void turn() {
        this.playTurns(1);
    }

    public void playTurns(int turns) {
        out.println(this);
        for (int i = 0; i < turns; i++) {
            this.turn++;
            PairGenerator pairGenerator = new PairGenerator(this.getPeople());

            for (Person[] person : pairGenerator.getPairs()) {
                decrementCount(person[0].getStatus());
                person[0].setStatus(HealthStatus.getNewStatus(person[0], person[1]));
                incrementCount(person[0].getStatus());
                decrementCount(person[1].getStatus());
                person[1].setStatus(HealthStatus.getNewStatus(person[1], person[0]));
                incrementCount(person[1].getStatus());
            }
            if (pairGenerator.hasLeftOver()) {
                Person leftOver = pairGenerator.getLeftOver();
                decrementCount(leftOver.getStatus());
                leftOver.setStatus(HealthStatus.getNewStatus(leftOver, Person.LEFTOVER_BUDDY));
                incrementCount(leftOver.getStatus());
            }
            out.println(this);
        }
    }

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

    public void readCount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Wie viele Gesunde soll es geben?");
        this.setHealthyCount(sc.nextInt());
        System.out.println("Wie viele Kranke soll es geben?");
        this.setInfectedCount(sc.nextInt());
        System.out.println("Wie viele Tote soll es bereits geben?");
        this.setDiseasedCount(sc.nextInt());
        sc.close();
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
    public void setOut(PrintStream out) {
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

    @Override
    public String toString() {
        return "Turn " + this.turn + ": " + this.healthyCount + " HEALTHY, " + this.infectedCount + " INFECTED, "
                + this.diseasedCount + " DISEASED.";
    }

    public static void main(String[] args) {

        PandemicSim gameMaster = new PandemicSim();
        System.out.println(gameMaster);
        // PairGenerator pairGenerator = new PairGenerator(gameMaster.getPeople());

        // Person[][] persons = pairGenerator.getPairs();

        // for (Person[] persons2 : persons) {
        // for (Person persons3 : persons2) {
        // System.out.println(persons3);
        // }
        // System.out.println("---");
        // }
        // if (pairGenerator.hasLeftOver()) {
        // System.out.println("Leftover:");
        // System.out.println(pairGenerator.getLeftOver());
        // }

        gameMaster.playTurns(1000);
    }
}