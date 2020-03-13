package components;

/**
 * Person
 */
public class Person {

    public final static Person LEFTOVER_BUDDY = new Person(HealthState.HEALTHY);

    public static int amount = 0;
    public int id;
    private HealthState status;

    public Person(HealthState status){
        this.status = status;
        this.id = amount++;
    }

    public HealthState getStatus(){
        return this.status;
    }

    public void setStatus(HealthState status){
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + "; HealthState: " + this.status;
    }
}