package components;

/**
 * Person
 */
public class Person {

    public final static Person LEFTOVER_BUDDY = new Person(HealthStatus.HEALTHY);

    public static int amount = 0;
    public int id;
    private HealthStatus status;

    public Person(HealthStatus status){
        this.status = status;
        this.id = amount++;
    }

    public HealthStatus getStatus(){
        return this.status;
    }

    public void setStatus(HealthStatus status){
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + "; HealthState: " + this.status;
    }
}