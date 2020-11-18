package lesson1;

public class Main {
    public static void main(String[] args) {
        Participant[] participants = {
                new Human("Sasha", 1200, 120),
                new Cat("Murzik", 2000, 180),
                new Robot("Fedia", 1500, 100),
                new Human("Aleksey", 500, 50)
        };

        Obstacle[] obstacles = {
                new Treadmill(500),
                new Wall(100)
        };

        for (Participant p : participants) {
            for (Obstacle o : obstacles) {
                o.doIt(p);
                if (!p.isOnDistance()) {
                    break;
                }
            }
        }

        for (Participant participant : participants) {
            participant.info();
        }
    }
}
