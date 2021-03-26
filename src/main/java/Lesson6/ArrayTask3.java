package Lesson6;

public class ArrayTask3 {
    public static boolean arrNumber(int[] arr) {
        boolean one = false;
        boolean four = false;
        for (int i : arr) {
            if (i != 1 && i != 4)// throw new RuntimeException("invalid value");
            if (i == 1) one = true;
            if (i == 4) four = true;
        }
        return one && four;
    }
}
