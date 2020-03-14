package components;


public enum HealthStatus{
    HEALTHY, INFECTED, DISEASED;

    public static HealthStatus getNewStatus(Person p1, Person p2) {
        
        if(p1.getStatus() == HealthStatus.HEALTHY && p2.getStatus() == HealthStatus.HEALTHY){
            return HealthStatus.HEALTHY;
        }

        if (p1.getStatus() == HealthStatus.INFECTED){
            return getInfectedStatus();
        }

        return getsInfected();
    }

    private static HealthStatus getInfectedStatus(){
        double random = Math.random() * 6;

        if(random < 1){
            return HealthStatus.DISEASED;
        } else if(random > 5){
            return HealthStatus.HEALTHY;
        } else {
            return HealthStatus.INFECTED;
        }
    }

    private static HealthStatus getsInfected(){
        double random = Math.random();

        return random >= 0.5 ? HealthStatus.INFECTED : HealthStatus.HEALTHY;
    }

    /** Für Debug, bitte nicht beachten */
    public static void main(String[] args) {
        Person p1 = new Person(HealthStatus.INFECTED), p2 = new Person(HealthStatus.HEALTHY);

        getNewStatus(p1, p2);

        System.out.println(p1.getStatus());
        System.out.println(p2.getStatus());
    }
}