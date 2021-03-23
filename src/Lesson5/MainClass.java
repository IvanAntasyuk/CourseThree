package Lesson5;

import java.util.concurrent.CyclicBarrier;

public class MainClass {

    public static final int CARS_COUNT = 4;
    public static Thread[] threadCar = new Thread[CARS_COUNT];

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

        Car.setBarrier(new CyclicBarrier(CARS_COUNT, new StartMessage()));

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            threadCar[i] = new Thread(cars[i]);
            threadCar[i].start();
        }



        for (int i = 0; i < cars.length; i++) {
            try {
                threadCar[i].join();
            } catch (InterruptedException ignore) {
            }
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
