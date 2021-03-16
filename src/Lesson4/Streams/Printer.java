package Lesson4.Streams;

public class Printer {
    public static void main(String[] args) throws InterruptedException {

        Abc abc = new Abc();
        for (int i = 0; i <5 ; i++) {

            Thread a = new Thread(new Runnable() {
                @Override
                public void run() {
                    abc.printA();
                }
            });

            Thread c = new Thread(new Runnable() {
                @Override
                public void run() {
                    abc.printC();
                }
            });
            Thread b = new Thread(new Runnable() {
                @Override
                public void run() {
                    abc.printB();
                }
            });
            a.start();
            b.start();
            c.start();

        }
    }
}
