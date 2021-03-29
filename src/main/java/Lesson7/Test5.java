package Lesson7;

public class Test5 {
    @BeforeSuite
    public void bf(){
        System.out.println(getClass().getSimpleName() + " Before suite ");
    }
    @Test(9)
    public void task0() {
        System.out.println(getClass().getName() + " task 0 ");
    }

    @Test(1)
    public void task1() {
        System.out.println(getClass().getSimpleName() + " task 1 ");
    }
    @AfterSuite
    public void as0(){
        System.out.println(getClass().getSimpleName() + " After suite");
    }
    @Test(1)
    public void task2() {
        System.out.println(getClass().getSimpleName() + " task 2 ");
    }

    @AfterSuite
    public void as(){
        System.out.println(getClass().getSimpleName() + " After suite");
    }
}
