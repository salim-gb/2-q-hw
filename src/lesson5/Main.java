package lesson5;

public class Main {
    private static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;
    static final int QUARTER = SIZE / 4;

    public static void main(String[] args) {
        firstMethod();
        secondMethod();
        thirdMethod();
    }
    static void firstMethod() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                    Math.cos(0.4f + i / 2));
        }
        long b = System.currentTimeMillis();
        System.out.println("Время выполнения первого метода: " + (b - a));
    }

    static void secondMethod() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                        Math.cos(0.4f + i / 2));
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < a2.length; i++) {
                a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                        Math.cos(0.4f + i / 2));
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);

        long b = System.currentTimeMillis();

        System.out.println("Время выполнения второго метода: " + (b - a));
    }

    static void thirdMethod() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        float[] a1 = new float[QUARTER];
        float[] a2 = new float[QUARTER];
        float[] a3 = new float[QUARTER];
        float[] a4 = new float[QUARTER];
        System.arraycopy(arr, 0, a1, 0, QUARTER);
        System.arraycopy(arr, QUARTER, a2, 0, QUARTER);
        System.arraycopy(arr, QUARTER * 2, a3, 0, QUARTER);
        System.arraycopy(arr, QUARTER * 3, a4, 0, QUARTER);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                        Math.cos(0.4f + i / 2));
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < a2.length; i++) {
                a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                        Math.cos(0.4f + i / 2));
            }
        });

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < a3.length; i++) {
                a3[i] = (float) (a3[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                        Math.cos(0.4f + i / 2));
            }
        });

        Thread t4 = new Thread(() -> {
            for (int i = 0; i < a4.length; i++) {
                a4[i] = (float) (a4[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                        Math.cos(0.4f + i / 2));
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, QUARTER);
        System.arraycopy(a2, 0, arr, QUARTER, QUARTER);
        System.arraycopy(a3, 0, arr, QUARTER * 2, QUARTER);
        System.arraycopy(a4, 0, arr, QUARTER * 3, QUARTER);

        long b = System.currentTimeMillis();

        System.out.println("Время выполнения тертего метода: " + (b - a));
    }
}
