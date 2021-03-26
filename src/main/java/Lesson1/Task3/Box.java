package Lesson1.Task3;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit>  {
    private List<T> fruitOfBox = new ArrayList<>();

    public void addFruit(T fruit) {
        fruitOfBox.add(fruit);
    }

    public double weightFruit() {
        double weight = 0;
        for (T fruitWeight : fruitOfBox) {
            weight += fruitWeight.getWeight();
        }
        return weight;
    }

    public void fromOneBoxToTwoBox(Box<T> one) {
        for (T fruit : fruitOfBox) {
            one.addFruit(fruit);
        }
fruitOfBox.clear();
    }


    public void compare(Box <?> two) {
        double sum;
       sum = this.weightFruit()-two.weightFruit();
       if (sum ==0){
           System.out.println(true);
       }else if (sum!=0){
           System.out.println(false);
       }
    }


}
