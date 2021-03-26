package Lesson4.Streams;

public class Abc {
   synchronized public void printA(){
        System.out.print("A");
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
   synchronized public void printB(){
        System.out.print("B");
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
   synchronized public void printC(){
        System.out.print("C  ");
        try {
          wait();
          notifyAll();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
