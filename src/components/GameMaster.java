package components;

/**
 * GameMaster
 */
public class GameMaster {

    public Person[] people;
    public int turn;
    public int healthyCount;
    public int infectedCount;
    public int diseasedCount;

    public GameMaster(int healthyCount, int infectedCount, int diseasedCount) {
        this.healthyCount = healthyCount;
        this.infectedCount = infectedCount;
        this.diseasedCount = diseasedCount;
        int total = healthyCount + infectedCount + diseasedCount;

        this.people = new Person[total];

        // Derzeitiger Index des Arrays people
        int globalIndex = 0;

        for (int i = 0; i < healthyCount; i++, globalIndex++) {
            people[globalIndex] = new Person(HealthState.HEALTHY);
        }

        for (int i = 0; i < infectedCount; i++, globalIndex++) {
            people[globalIndex] = new Person(HealthState.INFECTED);
        }

        for (int i = 0; i < diseasedCount; i++, globalIndex++) {
            people[globalIndex] = new Person(HealthState.DISEASED);
        }
    }

    public void turn() {

    }

    public void playTurns(int turns) {
        for (int i = 0; i < turns; i++) {
            this.turn++;
            PairGenerator pairGenerator = new PairGenerator(this.getPeople());

            for (Person[] person : pairGenerator.getPairs()) {
                decrementCount(person[0].getStatus());
                person[0].setStatus(HealthState.getNewStatus(person[0], person[1]));
                incrementCount(person[0].getStatus());
                decrementCount(person[1].getStatus());
                person[1].setStatus(HealthState.getNewStatus(person[1], person[0]));
                incrementCount(person[1].getStatus());
            }
            System.out.println(this);
        }
    }

    public void decrementCount(HealthState healthState) {
        switch (healthState) {
            case HEALTHY:
                this.healthyCount--;
                break;
            case INFECTED:
                this.infectedCount--;
                break;
            case DISEASED:
                this.diseasedCount--;
                System.err.println("Something went wrong because there's a diseased person. Someone has been brought back to life.");
                break;
        }
    }

    public void incrementCount(HealthState healthState){
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
            if (people[i].getStatus() != HealthState.DISEASED) {
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
        GameMaster gameMaster = new GameMaster(1000, 5, 0);
        System.out.println(gameMaster);
        PairGenerator pairGenerator = new PairGenerator(gameMaster.getPeople());

        Person[][] persons = pairGenerator.getPairs();

        for (Person[] persons2 : persons) {
            for (Person persons3 : persons2) {
                System.out.println(persons3);
            }
            System.out.println("---");
        }
        if (pairGenerator.hasLeftOver()) {
            System.out.println("Leftover:");
            System.out.println(pairGenerator.getLeftOver());
        }

        gameMaster.playTurns(1000);
    }
}