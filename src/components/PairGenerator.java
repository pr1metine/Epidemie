package components;

/**
 * PairGenerator
 */
public class PairGenerator {

    private Person[][] pairs;
    private Person leftOver;
    private boolean hasLeftOver;

    public PairGenerator(Person[] pairs){
        this.generate(pairs);
    }

    private void generate(Person[] persons){
        this.hasLeftOver = persons.length % 2 == 1;
        Person[] res1 = new Person[this.hasLeftOver ? persons.length -1 : persons.length];
        this.pairs = new Person[res1.length/2][2];
        boolean[] taken = new boolean[persons.length];
        for (int i = 0; i < taken.length; i++) {
            taken[i] = false;
        }

        for (int i = 0; i < res1.length; i++) {
            int randomIndex = (int) (Math.random() * persons.length);
            if(!taken[randomIndex]){
                res1[i] = persons[randomIndex];
                taken[randomIndex] = true;
            } else {
                i--;
            }
        }

        for (int i = 0; i < res1.length; i++) {
            this.pairs[i/2][i%2] = res1[i];
        }

        if(this.hasLeftOver){
            for (int i = 0; i < taken.length; i++) {
                if(!taken[i]){
                    this.leftOver = persons[i];
                    break;
                }
            }
        }
    }

    /**
     * @return the pairs
     */
    public Person[][] getPairs() {
        return pairs;
    }

    /**
     * @return the leftOver
     */
    public Person getLeftOver() {
        return leftOver;
    }

    public boolean hasLeftOver(){
        return hasLeftOver;
    }

    public static void main(String[] args) {
        Person[] persons = {new Person(HealthStatus.INFECTED), new Person(HealthStatus.HEALTHY), new Person(HealthStatus.DISEASED)};
        PairGenerator pairGenerator = new PairGenerator(persons);
        Person[][] persons2 = pairGenerator.pairs;
        System.out.println("Hello World");
        for (Person[] persons3 : persons2) {
            for (Person persons4 : persons3) {
                System.out.println(persons4.getStatus());
            }
            System.out.println("---");
        }
        System.out.println(pairGenerator.leftOver.getStatus());
    }
}