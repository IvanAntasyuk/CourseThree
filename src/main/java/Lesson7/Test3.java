package Lesson7;

public class Test3 {
    @BeforeSuite
    public void bf() {
        System.out.println(getClass().getSimpleName() + " Before suite ");
    }

    @Test
    public void task0() {
        System.out.println(getClass().getSimpleName() + " task0");
    }

    @AfterSuite
    public void as0() {
        System.out.println(getClass().getSimpleName() + " After suite");
    }

    @Test(10)
    public void task1() {
        System.out.println(getClass().getSimpleName() + " task1");
    }
}