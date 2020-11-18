package lesson1;

public class Human implements Participant{
    private String name;
    private int maxRun;
    private int maxJump;
    private boolean onDistance;

    public Human(String name, int maxRun, int maxJump) {
        this.name = name;
        this.maxRun = maxRun;
        this.maxJump = maxJump;
        this.onDistance = true;
    }

    @Override
    public void run(int distance) {
        if (maxRun <= distance) {
            System.out.println("Участник " + name + " не мог добежать " + distance);
            onDistance = false;
        } else {
            System.out.println("Участник " + name + " пробежал " + distance);
        }
    }

    @Override
    public void jump(int height) {
        if (maxRun <= height) {
            System.out.println("Участник " + name + " не мог брыгнуть " + height);
            onDistance = false;
        } else {
            System.out.println("Участник " + name + " прыгнул " + height);
        }
    }

    @Override
    public boolean isOnDistance() {
        return onDistance;
    }

    @Override
    public void info() {
        System.out.printf("%s %b \n", name, onDistance);
    }
}
