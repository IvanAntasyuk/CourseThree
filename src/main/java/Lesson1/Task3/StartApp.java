package Lesson1.Task3;

public class StartApp {
    public static void main(String[] args) {

        Box<Apple> boxApple = new Box<>();//ящик яблок
        Box<Orange> boxOrange = new Box<>();//ящик апельсинов

        Apple apple1 = new Apple(); // яблоки
        Orange orange1 = new Orange();// апельсины

        boxApple.addFruit(apple1);
        boxApple.addFruit(apple1);
        boxApple.addFruit(apple1);


        boxOrange.addFruit(orange1);
        boxOrange.addFruit(orange1);
        //boxOrange.addFruit(orange1);


        System.out.println(boxApple.weightFruit());

        System.out.println(boxOrange.weightFruit());

        boxApple.compare(boxOrange);

        Box<Apple> boxAppleTwo = new Box<>(); // второй ящик яблок
        boxApple.fromOneBoxToTwoBox(boxAppleTwo);





    }

}
