package Lesson7;


public class Test2 {
    @BeforeSuite
    public void bf() {
        System.out.println(getClass().getSimpleName() + " Before suite ");
    }

    @Test(1)
    public void tast0() {
        System.out.println(getClass().getSimpleName() + " task0");
    }

    @Test
    public void task1() {
        System.out.println(getClass().getSimpleName() + " task1");
    }

    @AfterSuite
    public void as0() {
        System.out.println(getClass().getSimpleName() + " After suite");
    }

    @Test(7)
    public void task2() {
        System.out.println(getClass().getSimpleName() + " task2");
    }

    @Test(2)
    public void task3() {
        System.out.println(getClass().getSimpleName() + " task3");
    }
}
