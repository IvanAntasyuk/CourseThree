package Lesson1.Task2;

import java.util.ArrayList;
import java.util.List;

public class ArraysToArraysList {
    static public <T> List arrToList(T arr[], List<T> tList) {
        for (int i = 0; i < arr.length; i++) {
            tList.add(arr[i]);
        }
        return tList;
    }

    public static void main(String[] args) {
        Integer arrNumbers [] = {1,2,3,4,5,6,7,8,9};
        List listNumbers = new ArrayList();
        arrToList(arrNumbers,listNumbers);
        System.out.println(listNumbers);
    }
}
