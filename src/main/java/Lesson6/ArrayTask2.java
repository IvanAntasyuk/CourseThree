package Lesson6;

public class ArrayTask2 {
    public static int[] getArr(int[] arrIn) {
        int[] backArr = new int[2];
        if (arrIn.length == 0) {
            System.out.println(("В массиве нет элементов"));
            return arrIn;
        }
        for (int i = arrIn.length - 3; i >= 0; i--) {
            if (arrIn[i] == 4) {
                System.arraycopy(arrIn, (i + 1), backArr, 0, 2);
                return backArr;
            }
        }
        throw new RuntimeException("В массиве нет ни одной четверки, после которой есть два числа");
    }

}
