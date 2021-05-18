# CourseThree

--Task1 

1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
2. Написать метод, который преобразует массив в ArrayList;
3. Большая задача:
a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
c. Для хранения фруктов внутри коробки можете использовать ArrayList;
d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут в compare в качестве параметра, true - если их веса равны, false в противном случае(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку(помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами), соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
g. Не забываем про метод добавления фрукта в коробку.


--Task2

Добавить в сетевой чат авторизацию через базу данных SQLite.
2.*Добавить в сетевой чат возможность смены ника.


--Task3

1. Добавить в сетевой чат запись локальной истории в текстовый файл на клиенте.
2. После загрузки клиента показывать ему последние 100 строк чата.



--Task4

1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС). Используйте wait/notify/notifyAll.
2. На серверной стороне сетевого чата реализовать управление потоками через ExecutorService.


--Task5

1. Приведённый код перенести в новый проект.

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
    }
}
public abstract class Stage {
    protected int length;
    protected String description;
    public String getDescription() {
        return description;
    }
    public abstract void go(Car c);
}
public class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
public class Tunnel extends Stage {
    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
public class Race {
    private ArrayList<Stage> stages;
    public ArrayList<Stage> getStages() { return stages; }
    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}


Организуем гонки:
Все участники должны стартовать одновременно, несмотря на то, что на подготовку у каждого из них уходит разное время.
В туннель не может заехать одновременно больше половины участников (условность).
Попробуйте всё это синхронизировать.
Только после того как все завершат гонку, нужно выдать объявление об окончании.
Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent.
Пример выполнения кода до корректировки:



ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!

Участник #2 готовится

ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!

ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!

Участник #1 готовится

Участник #4 готовится

Участник #3 готовится

Участник #3 готов

Участник #3 начал этап: Дорога 60 метров

Участник #2 готов

Участник #2 начал этап: Дорога 60 метров

Участник #1 готов

Участник #1 начал этап: Дорога 60 метров

Участник #4 готов

Участник #4 начал этап: Дорога 60 метров

Участник #3 закончил этап: Дорога 60 метров

Участник #3 готовится к этапу(ждет): Тоннель 80 метров

Участник #3 начал этап: Тоннель 80 метров

Участник #2 закончил этап: Дорога 60 метров

Участник #2 готовится к этапу(ждет): Тоннель 80 метров

Участник #2 начал этап: Тоннель 80 метров

Участник #1 закончил этап: Дорога 60 метров

Участник #1 готовится к этапу(ждет): Тоннель 80 метров

Участник #1 начал этап: Тоннель 80 метров

Участник #4 закончил этап: Дорога 60 метров

Участник #4 готовится к этапу(ждет): Тоннель 80 метров

Участник #4 начал этап: Тоннель 80 метров

Участник #2 закончил этап: Тоннель 80 метров

Участник #2 начал этап: Дорога 40 метров

Участник #3 закончил этап: Тоннель 80 метров

Участник #3 начал этап: Дорога 40 метров

Участник #2 закончил этап: Дорога 40 метров

Участник #1 закончил этап: Тоннель 80 метров

Участник #1 начал этап: Дорога 40 метров

Участник #4 закончил этап: Тоннель 80 метров

Участник #4 начал этап: Дорога 40 метров

Участник #3 закончил этап: Дорога 40 метров

Участник #1 закончил этап: Дорога 40 метров

Участник #4 закончил этап: Дорога 40 метров


Что примерно должно получиться:

ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!

Участник #2 готовится

Участник #1 готовится

Участник #4 готовится

Участник #3 готовится

Участник #2 готов

Участник #4 готов

Участник #1 готов

Участник #3 готов

ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!

Участник #2 начал этап: Дорога 60 метров

Участник #4 начал этап: Дорога 60 метров

Участник #3 начал этап: Дорога 60 метров

Участник #1 начал этап: Дорога 60 метров

Участник #1 закончил этап: Дорога 60 метров

Участник #3 закончил этап: Дорога 60 метров

Участник #3 готовится к этапу(ждет): Тоннель 80 метров

Участник #1 готовится к этапу(ждет): Тоннель 80 метров

Участник #1 начал этап: Тоннель 80 метров

Участник #3 начал этап: Тоннель 80 метров

Участник #4 закончил этап: Дорога 60 метров

Участник #4 готовится к этапу(ждет): Тоннель 80 метров

Участник #2 закончил этап: Дорога 60 метров

Участник #2 готовится к этапу(ждет): Тоннель 80 метров

Участник #3 закончил этап: Тоннель 80 метров

Участник #1 закончил этап: Тоннель 80 метров

Участник #2 начал этап: Тоннель 80 метров

Участник #4 начал этап: Тоннель 80 метров

Участник #3 начал этап: Дорога 40 метров

Участник #1 начал этап: Дорога 40 метров

Участник #3 закончил этап: Дорога 40 метров

Участник #3 - WIN

Участник #1 закончил этап: Дорога 40 метров

Участник #4 закончил этап: Тоннель 80 метров

Участник #4 начал этап: Дорога 40 метров

Участник #2 закончил этап: Тоннель 80 метров

Участник #2 начал этап: Дорога 40 метров

Участник #2 закончил этап: Дорога 40 метров

Участник #4 закончил этап: Дорога 40 метров

ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!



--Task6

1. Добавить на серверную сторону чата логирование, с выводом информации о действиях на сервере (запущен, произошла ошибка, клиент подключился, клиент прислал сообщение/команду).
2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив. Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов, идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, иначе в методе необходимо выбросить RuntimeException. Написать набор тестов для этого метода (по 3-4 варианта входных данных). Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
3. Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы, то метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).
4. *Добавить на серверную сторону сетевого чата логирование событий.


--Task7

1. Создать класс, который может выполнять «тесты», в качестве тестов выступают классы с наборами методов с аннотациями @Test. Для этого у него должен быть статический метод start(), которому в качестве параметра передается или объект типа Class, или имя класса. Из «класса-теста» вначале должен быть запущен метод с аннотацией @BeforeSuite, если такой имеется, далее запущены методы с аннотациями @Test, а по завершению всех тестов – метод с аннотацией @AfterSuite. К каждому тесту необходимо также добавить приоритеты (int числа от 1 до 10), в соответствии с которыми будет выбираться порядок их выполнения, если приоритет одинаковый, то порядок не имеет значения. Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре, иначе необходимо бросить RuntimeException при запуске «тестирования».
Это домашнее задание никак не связано с темой тестирования через JUnit и использованием этой библиотеки, то есть проект пишется с нуля.