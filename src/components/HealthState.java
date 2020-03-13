package components;


public enum HealthState{
    HEALTHY, INFECTED, DISEASED;

    public static HealthState getNewStatus(Person p1, Person p2) {
        
        if(p1.getStatus() == HealthState.HEALTHY && p2.getStatus() == HealthState.HEALTHY){
            return HealthState.HEALTHY;
        }

        if (p1.getStatus() == HealthState.INFECTED){
            return getInfectedStatus();
        }

        return getsInfected();
    }

    private static HealthState getInfectedStatus(){
        double random = Math.random() * 6;

        if(random < 1){
            return HealthState.DISEASED;
        } else if(random > 5){
            return HealthState.HEALTHY;
        } else {
            return HealthState.INFECTED;
        }
    }

    private static HealthState getsInfected(){
        double random = Math.random();

        return random >= 0.5 ? HealthState.INFECTED : HealthState.HEALTHY;
    }

    /** Für Debug, bitte nicht beachten */
    public static void main(String[] args) {
        Person p1 = new Person(HealthState.INFECTED), p2 = new Person(HealthState.HEALTHY);

        getNewStatus(p1, p2);

        System.out.println(p1.getStatus());
        System.out.println(p2.getStatus());
    }
}