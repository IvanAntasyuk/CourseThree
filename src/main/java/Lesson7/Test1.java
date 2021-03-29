package Lesson7;

public class Test1 {
    @BeforeSuite
    public void bf() {
        System.out.println(getClass().getSimpleName() + " Before suite ");
    }

    @Test(10)
    public void task1() {
        System.out.println(getClass().getSimpleName() + " task1");
    }

    @Test(7)
    public void task2() {
        System.out.println(getClass().getSimpleName() + " task2");
    }

    @Test(1)
    public void task3() {
        System.out.println(getClass().getSimpleName() + " task3");
    }

    @Test(3)
    public void task4() {
        System.out.println(getClass().getSimpleName() + " task3");
    }

    @AfterSuite
    public void taskAfter() {
        System.out.println(getClass().getSimpleName() + " after");
    }
}