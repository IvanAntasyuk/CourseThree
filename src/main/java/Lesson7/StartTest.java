package Lesson7;

import java.lang.reflect.*;
import java.util.*;

public class StartTest {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        start(Test1.class);
        System.out.println();
        start(Test2.class);
        System.out.println();
        start(Test3.class);
        System.out.println();
        start(Test4.class);
        System.out.println();
        start("Lesson7.Test5");
    }

    public static void start(Class c) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Method[] methods = c.getDeclaredMethods();
        int beforeSuiteCount = 0, afterSuiteCount = 0;
        List<Method> tests = new ArrayList<>();

        for (Method o : methods) {
            String type = o.getDeclaredAnnotations()[0].annotationType().getSimpleName();
            if (type.equals("BeforeSuite")) {
                beforeSuiteCount++;
                if (beforeSuiteCount > 1) throw new RuntimeException("Должна быть только одна анотация BeforeSuite");
            } else if (type.equals("AfterSuite")) {
                afterSuiteCount++;
                if (afterSuiteCount > 1) throw new RuntimeException("Должна быть только одна анотация AfterSuite");
            } else if (type.equals("Test")) {
                tests.add(o);
            }
        }

        tests.sort(Comparator.comparingInt(o -> o.getAnnotation(Test.class).value()));

        for (Method o : methods) {
            String type = o.getDeclaredAnnotations()[0].annotationType().getSimpleName();
            if (type.equals("BeforeSuite")) {
                tests.add(0, o);
            }
            if (type.equals("AfterSuite")) {
                tests.add(o);
            }
        }

        for (Method i : tests) {
            try {
                System.out.print("(" + i.getDeclaredAnnotation(Test.class).value() + ") ");
            } catch (NullPointerException e) {

            }
            i.invoke(c.newInstance(), null);
        }
    }


    public static void start(String className) {
        try {
            Class<?> c = Class.forName(className);
            Constructor<?> constructor = c.getConstructor(null);
            start(c);
        } catch (InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}