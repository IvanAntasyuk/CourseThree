package Lesson1.Task1;

public class MoveArrays {
    static public <T> void move(T arr[], int arrIndexOne, int arrIndexTwo) {
        T temp = arr[arrIndexOne];
        arr[arrIndexOne] = arr[arrIndexTwo];
        arr[arrIndexTwo] = temp;

    }
    public static void main(String[] args) {
String word [] = {"Animal","Dog","Cat","Bird"};
for(String listWord : word){
    System.out.print(listWord + " ");
}
move(word,2,1);
move(word,0,3);
        System.out.println("");
        for(String listWord : word){
            System.out.print(listWord + " ");
        }

    }
}
