# Завдання 1

### Написати просту консольну програму
![](/misc/Task%201.png)

# Завдання 2

### Визначити двійкове уявлення цілої та дробової частини речового десяткового числа.
````java
import java.util.Scanner;

/**
 * Клас для представлення десяткового числа у двійковій формі
 */
public class BinaryRepresentation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть десяткове число: ");
        double num = scanner.nextDouble();

        int intPart = (int) num;
        double fracPart = num - intPart;

        String binaryIntPart = Integer.toBinaryString(intPart);
        StringBuilder binaryFrac = new StringBuilder();

        /**
         * Метод для отримання двійкового представлення дробової частини числа.
         */
        while (fracPart != 0) {
            fracPart *= 2;
            if (fracPart >= 1) {
                binaryFrac.append(1);
                fracPart -= 1;
            } else {
                binaryFrac.append(0);
            }
        }

        String binaryFracPart = binaryFrac.toString();

        System.out.println("Ціла частина: " + binaryIntPart);
        System.out.println("Дробова частина: " + binaryFracPart);
        scanner.close();
    }
}
````

### Розробити клас, що серіалізується, для зберігання параметрів і результатів обчислень.
````java
import java.io.Serializable;

/**
 * Клас для представлення результатів обчислення у двійковій формі та серіалізації/десеріалізації.
 */
public class BinaryResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private double num;
    private String binaryIntPart;
    private String binaryFracPart;

    /**
     * Ініціалізація результатів обчислення у двійковій формі.
     */
    public BinaryResult(double num, String binaryIntPart, String binaryFracPart) {
        this.num = num;
        this.binaryIntPart = binaryIntPart;
        this.binaryFracPart = binaryFracPart;
    }

    //Гетери й сетери для відповідних значень
    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getBinaryIntPart() {
        return binaryIntPart;
    }

    public void setBinaryIntPart(String binaryIntPart) {
        this.binaryIntPart = binaryIntPart;
    }

    public String getBinaryFracPart() {
        return binaryFracPart;
    }

    public void setBinaryFracPart(String binaryFracPart) {
        this.binaryFracPart = binaryFracPart;
    }
}
````

### Використовуючи агрегування, розробити клас для знаходження рішення задачі.
````java
/**
 * Клас для обчислення та представлення результатів у двійковій формі.
 */
public class BinaryCalculator {
    private BinaryResult binaryResult;

    public void solve(double num, int intPart, double fracPart, String binaryIntPart) {
        StringBuilder binaryFrac = new StringBuilder();
        while (fracPart != 0) {
            fracPart *= 2;
            if (fracPart >= 1) {
                binaryFrac.append(1);
                fracPart -= 1;
            } else {
                binaryFrac.append(0);
            }
        }
        String binaryFracPart = binaryFrac.toString();

        binaryResult = new BinaryResult(num, binaryIntPart, binaryFracPart);
    }

    public BinaryResult getBinaryResult() {
        return binaryResult;
    }
}
````

### Розробити клас для демонстрації в діалоговому режимі збереження та відновлення стану об'єкта, використовуючи серіалізацію. Показати особливості використання transient полів.
````java
import java.io.*;

/**
 * Клас для демонстрації обчислення та серіалізації/десеріалізації результатів в двійковій формі.
 */
public class BinarySerializationDemo {
    public static void main(String[] args) {
        BinaryCalculator calculator = new BinaryCalculator();
        calculator.solve(10, 10, 0, "1010");
        BinaryResult binaryResult = calculator.getBinaryResult();

        serializeObject(binaryResult, "binaryResult.ser");

        BinaryResult restoredResult = (BinaryResult) deserializeObject("binaryResult.ser");
        System.out.println("Відновлений об'єкт:");
        System.out.println("Десяткове число: " + restoredResult.getNum());
        System.out.println("Ціла частина: " + restoredResult.getBinaryIntPart());
        System.out.println("Дробова частина: " + restoredResult.getBinaryFracPart());
    }

    public static void serializeObject(Object obj, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(obj);
            System.out.println("Об'єкт серіалізовано в файл " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deserializeObject(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
````

### Розробити клас для тестування коректності результатів обчислень та серіалізації/десеріалізації.
````java
import java.io.IOException;

/**
 * Клас для тестування правильності обчислення та серіалізації/десеріалізації результатів в двійковій формі.
 */
import java.io.*;
import java.util.Scanner;

/**
 * Клас для тестування правильності обчислення та серіалізації/десеріалізації результатів в двійковій формі.
 */
public class BinaryTest {
    public static void main(String[] args) {
        BinaryResult expectedResult = calculateBinaryRepresentation();
        testSerialization(expectedResult);
    }

    private static BinaryResult calculateBinaryRepresentation() {
        BinaryCalculator binaryCalculator = new BinaryCalculator();
        binaryCalculator.solve(10, 10, 0, "1010");
        return binaryCalculator.getBinaryResult();
    }

    /**
     * Метод для тестування правильності обчислень.
     * @param expectedResult
     */
    private static void testSerialization(BinaryResult expectedResult) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("testBinaryResult.ser"))) {
            outputStream.writeObject(expectedResult);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("testBinaryResult.ser"))) {
            BinaryResult restoredResult = (BinaryResult) inputStream.readObject();

            if (restoredResult.getNum() == expectedResult.getNum() &&
                    restoredResult.getBinaryIntPart().equals(expectedResult.getBinaryIntPart()) &&
                    restoredResult.getBinaryFracPart().equals(expectedResult.getBinaryFracPart())) {
                System.out.println("Тестування серіалізації та десеріалізації успішно завершено.");
            } else {
                System.out.println("Помилка у тестуванні серіалізації та десеріалізації.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
````

### Результати:
![](/misc/Task%202.1.png)
![](/misc/Task%202.2.png)
![](/misc/Task%202.3.png)

# Завдання 3

### Забезпечити розміщення результатів обчислень у колекції з можливістю збереження/відновлення.
````java
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для представлення результатів обчислення у двійковій формі та серіалізації/десеріалізації.
 */
public class BinaryResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<BinaryResult> results;

    private double num;
    private String binaryIntPart;
    private String binaryFracPart;

    /**
     * Ініціалізація результатів обчислення у двійковій формі.
     */
    public BinaryResult(double num, String binaryIntPart, String binaryFracPart) {
        this.num = num;
        this.binaryIntPart = binaryIntPart;
        this.binaryFracPart = binaryFracPart;
    }

    //Гетери й сетери для відповідних значень
    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getBinaryIntPart() {
        return binaryIntPart;
    }

    public void setBinaryIntPart(String binaryIntPart) {
        this.binaryIntPart = binaryIntPart;
    }

    public String getBinaryFracPart() {
        return binaryFracPart;
    }

    public void setBinaryFracPart(String binaryFracPart) {
        this.binaryFracPart = binaryFracPart;
    }

    public BinaryResult() {
        results = new ArrayList<>();
    }

    public void addResult(BinaryResult result) {
        results.add(result);
    }

    public List<BinaryResult> getResults() {
        return results;
    }
}
````

### Використовуючи шаблон проектування Factory Method (Virtual Constructor), розробити ієрархію, що передбачає розширення рахунок додавання нових відображуваних класів.
````java
public interface ResultFactory {
    BinaryResult createResult(double num, String binaryIntPart, String binaryFracPart);
}
````

````java
public class Factory implements ResultFactory {
    @Override
    public BinaryResult createResult(double num, String binaryIntPart, String binaryFracPart) {
        return new BinaryResult(num, binaryIntPart, binaryFracPart);
    }
}
````

### Розширити ієрархію інтерфейсом "фабрикованих" об'єктів, що представляє набір методів для відображення результатів обчислень.
````java
public interface ResultDisplay {
    void displayResult();
}
````

````java
import java.io.Serializable;

/**
 * Клас для представлення результатів обчислення у двійковій формі та серіалізації/десеріалізації.
 */
public class BinaryResult implements Serializable, ResultDisplay {
    private static final long serialVersionUID = 1L;

    private double num;
    private String binaryIntPart;
    private String binaryFracPart;

    /**
     * Ініціалізація результатів обчислення у двійковій формі.
     */
    public BinaryResult(double num, String binaryIntPart, String binaryFracPart) {
        this.num = num;
        this.binaryIntPart = binaryIntPart;
        this.binaryFracPart = binaryFracPart;
    }

    //Гетери й сетери для відповідних значень
    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getBinaryIntPart() {
        return binaryIntPart;
    }

    public void setBinaryIntPart(String binaryIntPart) {
        this.binaryIntPart = binaryIntPart;
    }

    public String getBinaryFracPart() {
        return binaryFracPart;
    }

    public void setBinaryFracPart(String binaryFracPart) {
        this.binaryFracPart = binaryFracPart;
    }

    @Override
    public void displayResult() {
        System.out.println("Десяткове число: " + num);
        System.out.println("Ціла частина: " + binaryIntPart);
        System.out.println("Дробова частина: " + binaryFracPart);
    }
}
````

### Розробити та реалізувати інтерфейс для "фабрикуючого" методу.
````java
public interface CalculatorFactory {
    BinaryResult calculate(double num);
}
````

````java
/**
 * Клас для обчислення та представлення результатів у двійковій формі.
 */
public class BinaryCalculator implements CalculatorFactory {
    private BinaryResult binaryResult;

    public void solve(double num, int intPart, double fracPart, String binaryIntPart) {
        StringBuilder binaryFrac = new StringBuilder();
        while (fracPart != 0) {
            fracPart *= 2;
            if (fracPart >= 1) {
                binaryFrac.append(1);
                fracPart -= 1;
            } else {
                binaryFrac.append(0);
            }
        }
        String binaryFracPart = binaryFrac.toString();

        binaryResult = new BinaryResult(num, binaryIntPart, binaryFracPart);
    }

    public BinaryResult getBinaryResult() {
        return binaryResult;
    }

    @Override
    public BinaryResult calculate(double num) {
        return binaryResult;
    }
}
````

````java
import java.util.Scanner;

/**
 * Клас для представлення десяткового числа у двійковій формі
 */
public class BinaryRepresentation implements CalculatorFactory {
    private BinaryResult binaryResult;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть десяткове число: ");
        double num = scanner.nextDouble();

        int intPart = (int) num;
        double fracPart = num - intPart;

        String binaryIntPart = Integer.toBinaryString(intPart);
        StringBuilder binaryFrac = new StringBuilder();

        /**
         * Метод для отримання двійкового представлення дробової частини числа.
         */
        while (fracPart != 0) {
            fracPart *= 2;
            if (fracPart >= 1) {
                binaryFrac.append(1);
                fracPart -= 1;
            } else {
                binaryFrac.append(0);
            }
        }

        String binaryFracPart = binaryFrac.toString();

        System.out.println("Ціла частина: " + binaryIntPart);
        System.out.println("Дробова частина: " + binaryFracPart);
        scanner.close();
    }
    @Override
    public BinaryResult calculate(double num) {
        return binaryResult;
    }
}
````

Результат:
![](/misc/Task3.png)

# Завдання 4

### Використовуючи шаблон проектування Factory Method (Virtual Constructor), розширити ієрархію похідними класами, реалізують методи для подання результатів у вигляді текстової таблиці. Параметри відображення таблиці мають визначатися користувачем.

### Продемонструвати заміщення (перевизначення, overriding), поєднання (перевантаження, overloading), динамічне призначення методів (Пізнє зв'язування, поліморфізм, dynamic method dispatch).

### Забезпечити діалоговий інтерфейс із користувачем.

### Розробити клас для тестування основної функціональності.
````java
import java.util.Scanner;

/**
 * Клас для представлення десяткового числа у двійковій формі
 */
public class BinaryRepresentation implements CalculatorFactory {
    private BinaryResult binaryResult;

    /**
     * Метод для запуску програми.
     * @param args Аргументи командного рядка.
     */
    public static void main(String[] args) {
        BinaryRepresentation binaryRepresentation = new BinaryRepresentation();
        binaryRepresentation.displayTable();
    }

    /**
     * Метод для вибору типу відображення результату; введення десяткового числа.
     */
    public void displayTable() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Виберіть тип відображення результату:");
        System.out.println("1. Текст.");
        System.out.println("2. Таблиця.");
        int choice = scanner.nextInt();

        System.out.print("Введіть десяткове число: ");
        double num = scanner.nextDouble();

        ResultFactory factory = null;
        if (choice == 1) {
            factory = new Factory();
        } else if (choice == 2) {
            factory = new TextResultFactory();
        }

        BinaryCalculator calculator = new BinaryCalculator();
        calculator.solve(num, (int) num, num - (int) num, Integer.toBinaryString((int) num));
        BinaryResult binaryResult = calculator.getBinaryResult();

        BinaryResult result = factory.createResult(binaryResult.getNum(), binaryResult.getBinaryIntPart(), binaryResult.getBinaryFracPart());

        if (factory instanceof TextResultFactory) {
            ((TextResultFactory) factory).Table(result);
        } else {
            result.displayResult();
        }

        scanner.close();
    }

    @Override
    public BinaryResult calculate(double num) {
        return binaryResult;
    }

    /**
     * Клас для створення текстового результату.
     */
    class TextResultFactory implements ResultFactory {
        @Override
        public BinaryResult createResult(double num, String binaryIntPart, String binaryFracPart) {
            return new BinaryResult(num, binaryIntPart, binaryFracPart);
        }
    
        /**
         * Метод для відображення результату у вигляді таблиці.
         * @param result Результат обчислень.
         */
        public void Table(BinaryResult result) {
            int numWidth = Math.max(String.valueOf(result.getNum()).length(), 15);
            int intPartWidth = Math.max(result.getBinaryIntPart().length(), 12);
            int fracPartWidth = Math.max(result.getBinaryFracPart().length(), 15);
            
            String formatString = "| %-"+numWidth+"s | %-"+intPartWidth+"s | %-"+fracPartWidth+"s |%n";
            int totalWidth = numWidth + intPartWidth + fracPartWidth + 8;
            
            System.out.println("-".repeat(totalWidth));
            System.out.printf(formatString, "Десяткове число", "Ціла частина", "Дробова частина");
            System.out.println("-".repeat(totalWidth));
            System.out.printf(formatString, result.getNum(), result.getBinaryIntPart(), result.getBinaryFracPart());
            System.out.println("-".repeat(totalWidth));
        }
    }
}
````

### Результат:
![](/misc/Task4.png)

# Завдання 5

### Реалізувати можливість скасування (undo) операцій (команд).
### Продемонструвати поняття "макрокоманда"
### При розробці програми використовувати шаблон Singletone.
### Забезпечити діалоговий інтерфейс із користувачем.
````java
import java.util.Scanner;
import java.util.Stack;

/**
 * Клас для представлення десяткового числа у двійковій формі
 */
public class BinaryRepresentation implements CalculatorFactory {
    private BinaryResult binaryResult;
    private ResultFactory factory;

    /**
     * Метод для запуску програми.
     * 
     * @param args Аргументи командного рядка.
     */
    public static void main(String[] args) {
        BinaryRepresentation binaryRepresentation = new BinaryRepresentation();
        binaryRepresentation.displayTable();
    }

    /**
     * Метод для вибору типу відображення результату; введення десяткового числа.
     */
    public void displayTable() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Виберіть тип відображення результату:");
        System.out.println("1. Текст.");
        System.out.println("2. Таблиця.");
        int choice = scanner.nextInt();

        System.out.print("Введіть десяткове число: ");
        double num = scanner.nextDouble();

        if (choice == 1) {
            factory = new Factory();
        } else if (choice == 2) {
            factory = new TextResultFactory();
        }

        BinaryCalculator calculator = new BinaryCalculator();
        calculator.solve(num, (int) num, num - (int) num, Integer.toBinaryString((int) num));
        BinaryResult binaryResult = calculator.getBinaryResult();

        BinaryResult result = factory.createResult(binaryResult.getNum(), binaryResult.getBinaryIntPart(),
                binaryResult.getBinaryFracPart());

                undoOperation(result, factory);

        scanner.close();
    }

    @Override
    public BinaryResult calculate(double num) {
        return binaryResult;
    }

    /**
     * Клас для створення текстового результату.
     */
    class TextResultFactory implements ResultFactory {
        @Override
        public BinaryResult createResult(double num, String binaryIntPart, String binaryFracPart) {
            return new BinaryResult(num, binaryIntPart, binaryFracPart);
        }

        /**
         * Метод для відображення результату у вигляді таблиці.
         * 
         * @param result Результат обчислень.
         */
        public void Table(BinaryResult result) {
            int numWidth = Math.max(String.valueOf(result.getNum()).length(), 15);
            int intPartWidth = Math.max(result.getBinaryIntPart().length(), 12);
            int fracPartWidth = Math.max(result.getBinaryFracPart().length(), 15);

            String formatString = "| %-" + numWidth + "s | %-" + intPartWidth + "s | %-" + fracPartWidth + "s |%n";
            int totalWidth = numWidth + intPartWidth + fracPartWidth + 8;

            System.out.println("-".repeat(totalWidth));
            System.out.printf(formatString, "Десяткове число", "Ціла частина", "Дробова частина");
            System.out.println("-".repeat(totalWidth));
            System.out.printf(formatString, result.getNum(), result.getBinaryIntPart(), result.getBinaryFracPart());
            System.out.println("-".repeat(totalWidth));
        }
    }

    private Stack<BinaryResult> history = new Stack<>();

    public void executeOperation(BinaryResult result) {
        history.push(result);
    }

    public void undoOperation(BinaryResult result, ResultFactory factory) {
        if (!history.isEmpty()) {
            BinaryResult undoneResult = history.pop();
            System.out.println("Скасовано операцію: " + undoneResult);
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Підтвердження (Y/N): ");
        String confirmation = scanner.next().toUpperCase();
        if (confirmation.equals("Y")) {
            executeOperation(result);
            if (factory instanceof TextResultFactory) {
                ((TextResultFactory) factory).Table(result);
            } else {
                result.displayResult();
            }
        } else if (confirmation.equals("N")) {
            System.out.println("Операцію скасовано.");
        } else {
            displayTable();
        }
        scanner.close();
    }
}
````

````java
import java.util.ArrayList;
import java.util.List;

interface Command {
    void execute();
}

public class MacroCommand implements Command {
    private List<Command> commands = new ArrayList<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }
}
````

### Розробити клас для тестування функціональності програми.
````java
import java.io.*;

/**
 * Клас для тестування правильності обчислення та серіалізації/десеріалізації
 * результатів в двійковій формі.
 */
public class BinaryTest {
    public static void main(String[] args) {
        BinaryResult expectedResult = calculateBinaryRepresentation();
        testSerialization(expectedResult);
        testMacroCommand();
    }

    private static BinaryResult calculateBinaryRepresentation() {
        BinaryCalculator binaryCalculator = new BinaryCalculator();
        binaryCalculator.solve(10, 10, 0, "1010");
        return binaryCalculator.getBinaryResult();
    }

    /**
     * Метод для тестування правильності обчислень.
     * 
     * @param expectedResult
     */
    private static void testSerialization(BinaryResult expectedResult) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("testBinaryResult.ser"))) {
            outputStream.writeObject(expectedResult);
            System.out.println("Об'єкт збережено у файл testBinaryResult.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("testBinaryResult.ser"))) {
            BinaryResult restoredResult = (BinaryResult) inputStream.readObject();

            if (restoredResult.getNum() == expectedResult.getNum() &&
                    restoredResult.getBinaryIntPart().equals(expectedResult.getBinaryIntPart()) &&
                    restoredResult.getBinaryFracPart().equals(expectedResult.getBinaryFracPart())) {
                System.out.println("Тестування серіалізації та десеріалізації успішно завершено.");
            } else {
                System.out.println("Помилка у тестуванні серіалізації та десеріалізації.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void testMacroCommand() {
        BinaryCalculator binaryCalculator = new BinaryCalculator();
        MacroCommand macroCommand = new MacroCommand();
        macroCommand.addCommand(() -> binaryCalculator.solve(10, 10, 0, "1010"));
        macroCommand.addCommand(() -> binaryCalculator.solve(20, 20, 0, "10100"));

        macroCommand.execute();

        if (binaryCalculator.getBinaryResult() != null) {
            System.out.println("Тест макрокоманди пройшов успішно.");
        } else {
            System.out.println("Тест макрокоманди не пройшов.");
        }
    }
}
````

### Результат:
![](/misc/Task5.1.png)
![](/misc/Task5.2.png)

# Завдання 6

### Продемонструвати можливість паралельної обробки елементів колекції (пошук мінімуму, максимуму, обчислення середнього значення, відбір за критерієм, статистична обробка тощо).
````java
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Stack;

/**
 * Клас для представлення десяткового числа у двійковій формі
 */
public class BinaryRepresentation implements CalculatorFactory {
    private BinaryResult binaryResult;
    private ResultFactory factory;

    /**
     * Метод для запуску програми.
     * 
     * @param args Аргументи командного рядка.
     */
    public static void main(String[] args) {
        BinaryRepresentation binaryRepresentation = new BinaryRepresentation();
        binaryRepresentation.displayTable();
    }

    /**
     * Метод для вибору типу відображення результату; введення десяткового числа.
     */
    public void displayTable() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Виберіть тип відображення результату:");
        System.out.println("1. Текст.");
        System.out.println("2. Таблиця.");
        int choice = scanner.nextInt();

        System.out.print("Введіть десяткове число: ");
        double num = scanner.nextDouble();

        if (choice == 1) {
            factory = new Factory();
        } else if (choice == 2) {
            factory = new TextResultFactory();
        }

        BinaryCalculator calculator = new BinaryCalculator();
        calculator.solve(num, (int) num, num - (int) num, Integer.toBinaryString((int) num));
        BinaryResult binaryResult = calculator.getBinaryResult();

        BinaryResult result = factory.createResult(binaryResult.getNum(), binaryResult.getBinaryIntPart(),
                binaryResult.getBinaryFracPart());

                undoOperation(result, factory);

        List<Double> parts = new ArrayList<>();
        parts.add((double) binaryResult.getNum());
        parts.add(Double.parseDouble("0." + binaryResult.getBinaryFracPart()));

        Optional<Double> min = parts.parallelStream().min(Double::compareTo);
        Optional<Double> max = parts.parallelStream().max(Double::compareTo);
        double average = parts.parallelStream().mapToDouble(Double::doubleValue).average().orElse(0);

        System.out.println("Мінімум: " + min.orElse(0.0));
        System.out.println("Максимум: " + max.orElse(0.0));
        System.out.println("Середнє значення: " + average);

        scanner.close();
    }

    @Override
    public BinaryResult calculate(double num) {
        return binaryResult;
    }

    /**
     * Клас для створення текстового результату.
     */
    class TextResultFactory implements ResultFactory {
        @Override
        public BinaryResult createResult(double num, String binaryIntPart, String binaryFracPart) {
            return new BinaryResult(num, binaryIntPart, binaryFracPart);
        }

        /**
         * Метод для відображення результату у вигляді таблиці.
         * 
         * @param result Результат обчислень.
         */
        public void Table(BinaryResult result) {
            int numWidth = Math.max(String.valueOf(result.getNum()).length(), 15);
            int intPartWidth = Math.max(result.getBinaryIntPart().length(), 12);
            int fracPartWidth = Math.max(result.getBinaryFracPart().length(), 15);

            String formatString = "| %-" + numWidth + "s | %-" + intPartWidth + "s | %-" + fracPartWidth + "s |%n";
            int totalWidth = numWidth + intPartWidth + fracPartWidth + 8;

            System.out.println("-".repeat(totalWidth));
            System.out.printf(formatString, "Десяткове число", "Ціла частина", "Дробова частина");
            System.out.println("-".repeat(totalWidth));
            System.out.printf(formatString, result.getNum(), result.getBinaryIntPart(), result.getBinaryFracPart());
            System.out.println("-".repeat(totalWidth));
        }
    }

    private Stack<BinaryResult> history = new Stack<>();

    public void executeOperation(BinaryResult result) {
        history.push(result);
    }

    public void undoOperation(BinaryResult result, ResultFactory factory) {
        if (!history.isEmpty()) {
            BinaryResult undoneResult = history.pop();
            System.out.println("Скасовано операцію: " + undoneResult);
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Підтвердження (Y/N): ");
        String confirmation = scanner.next().toUpperCase();
        if (confirmation.equals("Y")) {
            executeOperation(result);
            if (factory instanceof TextResultFactory) {
                ((TextResultFactory) factory).Table(result);
            } else {
                result.displayResult();
            }
        } else if (confirmation.equals("N")) { // Додано умову для відміни операції
            System.out.println("Операцію скасовано.");
        } else {
            displayTable();
        }
        scanner.close();
    }
}
````

### Управління чергою завдань (команд) реалізувати за допомогою шаблону Worker Thread.
````java
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

interface Task {
    void execute();
}

class Worker extends Thread {
    private final TaskQueue taskQueue;

    public Worker(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (true) {
            Task task = taskQueue.getNextTask();
            task.execute();
        }
    }
}

public class TaskQueue {
    private final BlockingQueue<Task> queue;

    public TaskQueue() {
        queue = new LinkedBlockingQueue<>();
        startWorkers();
    }

    private void startWorkers() {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            Worker worker = new Worker(this);
            worker.start();
        }
    }

    public void addTask(Task task) {
        queue.offer(task);
    }

    public Task getNextTask() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
````

````java
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Клас для представлення десяткового числа у двійковій формі.
 */
public class BinaryRepresentation implements CalculatorFactory {
    private BinaryResult binaryResult;
    private ResultFactory factory;
    private Stack<BinaryResult> history = new Stack<>();
    private BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

    /**
     * Метод для запуску програми.
     * @param args Аргументи командного рядка.
     */
    public static void main(String[] args) {
        BinaryRepresentation binaryRepresentation = new BinaryRepresentation();
        binaryRepresentation.displayMainMenu();
    }

    public void displayMainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Головне меню:");
            System.out.println("1. Запустити програму");
            System.out.println("2. Зберегти файл");
            System.out.println("3. Відновлення файлу");
            System.out.println("4. Тест");
            System.out.println("5. Вихід");

            System.out.print("Оберіть опцію: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayTable();
                    break;
                case 2:
                    BinarySerializationDemo.serializeObject(binaryResult, "binaryResult.ser");
                    break;
                case 3:
                    Object restoredResult = BinarySerializationDemo.deserializeObject("binaryResult.ser");
                    System.out.println("Файл відновлено.");
                    break;
                case 4:
                    BinaryTest.main(null);
                    break;
                case 5:
                    System.out.println("Завершення роботи.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
                    break;
            }
        }
    }

    /**
     * Метод для вибору типу відображення результату; введення десяткового числа.
     */
    public void displayTable() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Виберіть тип відображення результату:");
        System.out.println("1. Текст.");
        System.out.println("2. Таблиця.");
        int choice = scanner.nextInt();

        System.out.print("Введіть десяткове число: ");
        double num = scanner.nextDouble();

        if (choice == 1) {
            factory = new Factory();
        } else if (choice == 2) {
            factory = new TextResultFactory();
        }

        BinaryCalculator calculator = new BinaryCalculator();
        calculator.solve(num, (int) num, num - (int) num, Integer.toBinaryString((int) num));
        BinaryResult binaryResult = calculator.getBinaryResult();

        BinaryResult result = factory.createResult(binaryResult.getNum(), binaryResult.getBinaryIntPart(),
                binaryResult.getBinaryFracPart());

        taskQueue.add(() -> {
            undoOperation(result, factory);
            displayStatistics(binaryResult);
            displayMainMenu();
        });

        executeTasks();
    }

    private void executeTasks() {
        Runnable task;
        while ((task = taskQueue.poll()) != null) {
            task.run();
        }
    }

    /**
     * Відображає статистичні дані про двійкове представлення числа.
     * @param binaryResult Результат двійкового обчислення.
     */
    private void displayStatistics(BinaryResult binaryResult) {
        List<Double> parts = new ArrayList<>();
        parts.add((double) binaryResult.getNum());
        parts.add(Double.parseDouble("0." + binaryResult.getBinaryFracPart()));

        Optional<Double> min = parts.parallelStream().min(Double::compareTo);
        Optional<Double> max = parts.parallelStream().max(Double::compareTo);
        double average = parts.parallelStream().mapToDouble(Double::doubleValue).average().orElse(0);

        System.out.println("Мінімум: " + min.orElse(0.0));
        System.out.println("Максимум: " + max.orElse(0.0));
        System.out.println("Середнє значення: " + average);
    }

    /**
     * Скасовує останню операцію та виконує відповідні дії залежно від підтвердження.
     * @param result Результат операції.
     * @param factory Фабрика для створення результату.
     */
    public void undoOperation(BinaryResult result, ResultFactory factory) {
        if (!history.isEmpty()) {
            BinaryResult undoneResult = history.pop();
            System.out.println("Скасовано операцію: " + undoneResult);
        }
    
        Scanner scanner = new Scanner(System.in);
        System.out.print("Підтвердження (Y/N): ");
        String confirmation = scanner.next().toUpperCase();
        if (confirmation.equals("Y")) {
            executeOperation(result);
            if (factory instanceof TextResultFactory) {
                ((TextResultFactory) factory).Table(result);
            } else {
                result.displayResult();
            }
        } else if (confirmation.equals("N")) {
            System.out.println("Операцію скасовано.");
            displayMainMenu();
        } else {
            displayTable();
        }
    }
    

    public void executeOperation(BinaryResult result) {
        history.push(result);
    }

    @Override
    public BinaryResult calculate(double num) {
        return binaryResult;
    }

    /**
     * Клас для створення текстового результату.
     */
    class TextResultFactory implements ResultFactory {
        @Override
        public BinaryResult createResult(double num, String binaryIntPart, String binaryFracPart) {
            return new BinaryResult(num, binaryIntPart, binaryFracPart);
        }

        /**
         * Метод для відображення результату у вигляді таблиці.
         * 
         * @param result Результат обчислень.
         */
        public void Table(BinaryResult result) {
            int numWidth = Math.max(String.valueOf(result.getNum()).length(), 15);
            int intPartWidth = Math.max(result.getBinaryIntPart().length(), 12);
            int fracPartWidth = Math.max(result.getBinaryFracPart().length(), 15);

            String formatString = "| %-" + numWidth + "s | %-" + intPartWidth + "s | %-" + fracPartWidth + "s |%n";
            int totalWidth = numWidth + intPartWidth + fracPartWidth + 8;

            System.out.println("-".repeat(totalWidth));
            System.out.printf(formatString, "Десяткове число", "Ціла частина", "Дробова частина");
            System.out.println("-".repeat(totalWidth));
            System.out.printf(formatString, result.getNum(), result.getBinaryIntPart(), result.getBinaryFracPart());
            System.out.println("-".repeat(totalWidth));
        }
    }
}
````

### Результат
![](/misc/Task6.1.png)
![](/misc/Task6.2.png)
![](/misc/Task6.3.png)
![](/misc/Task6.4.png)

# Завдання 7

### Розробити ієрархію класів відповідно до шаблону Observer (java) та продемонструвати можливість обслуговування розробленої раніше колекції (об'єкт, що спостерігається, Observable) різними (не менше двох) спостерігачами (Observers) – відстеження змін, упорядкування, висновок, відображення і т.д.
### При реалізації ієрархії класів використати інструкції (Annotation). Відзначити особливості різних політик утримання анотацій (annotation retention policies). Продемонструвати підтримку класів концепції рефлексії (Reflection).
### Використовуючи раніше створені класи, розробити додаток, що відображає результати обробки колекції об'єктів у графічному вигляді
### Забезпечити діалоговий інтерфейс з користувачем та перемальовування графіка під час зміни значень елементів колекції.
````java
package oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Random;


/**
 * Клас для представлення десяткового числа у двійковій формі.
 */
public class BinaryRepresentation implements CalculatorFactory {

    private BinaryResult binaryResult;
    private ResultFactory factory;
    private Stack<BinaryResult> history = new Stack<>();
    private BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private Scanner scanner = new Scanner(System.in);

    /**
     * Метод для запуску програми.
     *
     * @param args Аргументи командного рядка.
     */
    public static void main(String[] args) {
        BinaryRepresentation binaryRepresentation = new BinaryRepresentation();
        binaryRepresentation.displayMainMenu();
    }

    public void displayMainMenu() {
        while (true) {
            System.out.println("Головне меню:");
            System.out.println("1. Запустити програму");
            System.out.println("2. Зберегти файл");
            System.out.println("3. Відновлення файлу");
            System.out.println("4. Тест");
            System.out.println("5. Вихід");

            System.out.print("Оберіть опцію: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayTable();
                    break;
                case 2:
                    BinarySerializationDemo.serializeObject(binaryResult, "binaryResult.ser");
                    break;
                case 3:
                    Object restoredResult = BinarySerializationDemo.deserializeObject("binaryResult.ser");
                    System.out.println("Файл відновлено.");
                    break;
                case 4:
                    BinaryTest.main(null);
                    break;
                case 5:
                    System.out.println("Завершення роботи.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
                    break;
            }
        }
    }

    /**
     * Метод для вибору типу відображення результату; автоматична генерація
     * десяткових чисел.
     */
    public List<BinaryResult> displayTable() {
        factory = new TextResultFactory();
        List<BinaryResult> results = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            double num = 1 + random.nextDouble() * 100;
            BinaryCalculator calculator = new BinaryCalculator();
            calculator.solve(num, (int) num, num - (int) num, Integer.toBinaryString((int) num));
            BinaryResult binaryResult = calculator.getBinaryResult();

            BinaryResult result = factory.createResult(binaryResult.getNum(), binaryResult.getBinaryIntPart(),
                    binaryResult.getBinaryFracPart());
            results.add(result);
        }

        return results;
    }

    public List<BinaryResult> generateResults() {
        List<BinaryResult> results = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            double num = 1 + random.nextDouble() * 100;
            BinaryCalculator calculator = new BinaryCalculator();
            calculator.solve(num, (int) num, num - (int) num, Integer.toBinaryString((int) num));
            BinaryResult binaryResult = calculator.getBinaryResult();

            BinaryResult result = new BinaryResult(binaryResult.getNum(), binaryResult.getBinaryIntPart(),
                    binaryResult.getBinaryFracPart());
            results.add(result);
        }

        return results;
    }

    private void executeTasks() {
        Runnable task;
        while ((task = taskQueue.poll()) != null) {
            task.run();
        }
    }

    /**
     * Скасовує останню операцію та виконує відповідні дії залежно від
     * підтвердження.
     *
     * @param result Результат операції.
     * @param factory Фабрика для створення результату.
     */
    public void undoOperation(BinaryResult result, ResultFactory factory) {
        if (!history.isEmpty()) {
            BinaryResult undoneResult = history.pop();
            System.out.println("Скасовано операцію: " + undoneResult);
        }

        System.out.print("Підтвердження (Y/N): ");
        String confirmation = scanner.next().toUpperCase();
        if (confirmation.equals("Y")) {
            executeOperation(result);
            if (factory instanceof TextResultFactory) {
                ((TextResultFactory) factory).Table(result);
            } else {
                result.displayResult();
            }
        } else if (confirmation.equals("N")) {
            System.out.println("Операцію скасовано.");
            displayMainMenu();
        } else {
            displayTable();
        }
    }

    public void executeOperation(BinaryResult result) {
        history.push(result);
    }

    @Override
    public BinaryResult calculate(double num) {
        return binaryResult;
    }

    /**
     * Клас для створення текстового результату.
     */
    class TextResultFactory implements ResultFactory {

        @Override
        public BinaryResult createResult(double num, String binaryIntPart, String binaryFracPart) {
            return new BinaryResult(num, binaryIntPart, binaryFracPart);
        }

        /**
         * Метод для відображення результату у вигляді таблиці.
         *
         * @param result Результат обчислень.
         */
        public void Table(BinaryResult result) {
            int numWidth = Math.max(String.valueOf(result.getNum()).length(), 15);
            int intPartWidth = Math.max(result.getBinaryIntPart().length(), 12);
            int fracPartWidth = Math.max(result.getBinaryFracPart().length(), 15);

            String formatString = "| %-" + numWidth + "s | %-" + intPartWidth + "s | %-" + fracPartWidth + "s |%n";
            int totalWidth = numWidth + intPartWidth + fracPartWidth + 8;

            System.out.println("-".repeat(totalWidth));
            System.out.printf(formatString, "Десяткове число", "Ціла частина", "Дробова частина");
            System.out.println("-".repeat(totalWidth));
            System.out.printf(formatString, result.getNum(), result.getBinaryIntPart(), result.getBinaryFracPart());
            System.out.println("-".repeat(totalWidth));
        }
    }
}
````

````java
package oop;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.*;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.Optional;

public class Graph extends javax.swing.JFrame {

    GraphFrame frame = new GraphFrame("Calculations Graph");
    private BinaryResult binaryResult;

    /**
     * Creates new form Graph
     */
    public Graph() {
        initComponents();
        frame.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        ButtonGraph = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ButtonGraph.setText("Згенерувати графік");
        ButtonGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonGraphActionPerformed(evt);
            }
        });

        jButton1.setText("Зберегти файл");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Відновити файл");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Тест");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Вихід");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setForeground(new java.awt.Color(51, 51, 51));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Термінал");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 181, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonGraph, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(199, 199, 199))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(284, 284, 284)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void createChart(List<BinaryResult> results) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<Double> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();

        for (BinaryResult result : results) {
            xValues.add(result.getNum());
            yValues.add(Double.parseDouble(result.getBinaryIntPart()));
        }

        frame.updateChart(xValues, yValues);

        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");

        sb.append("<table border='1'>");
        sb.append("<tr><th>Десяткове число</th><th>Ціла частина</th><th>Дробова частина</th></tr>");

        for (BinaryResult result : results) {
            sb.append("<tr>");
            sb.append("<td>").append(result.getNum()).append("</td>");
            sb.append("<td>").append(result.getBinaryIntPart()).append("</td>");
            sb.append("<td>").append(result.getBinaryFracPart()).append("</td>");
            sb.append("</tr>");
        }

        sb.append("</table>");

        List<Double> numList = results.stream().map(BinaryResult::getNum).collect(Collectors.toList());
        Optional<Double> min = numList.stream().min(Double::compareTo);
        Optional<Double> max = numList.stream().max(Double::compareTo);
        double average = numList.stream().mapToDouble(Double::doubleValue).average().orElse(0);

        sb.append("<br>");
        sb.append("Мінімум: ").append(min.orElse(0.0)).append("<br>");
        sb.append("Максимум: ").append(max.orElse(0.0)).append("<br>");
        sb.append("Середнє значення: ").append(average).append("<br>");

        sb.append("</body></html>");
        jLabel1.setText(sb.toString());

        frame.setVisible(true);
    }


    private void ButtonGraphActionPerformed(java.awt.event.ActionEvent evt) {                                            
        BinaryRepresentation binaryRepresentation = new BinaryRepresentation();
        List<BinaryResult> results = binaryRepresentation.displayTable();
        createChart(results);
        frame.setVisible(true);
    }                                           

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        BinaryTest.main(null);
        jLabel1.setText("Тестування серіалізації та десеріалізації успішно завершено. Тест макрокоманди пройшов успішно.");
    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        System.exit(0);
    }                                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        BinarySerializationDemo.serializeObject(binaryResult, "binaryResult.ser");
        jLabel1.setText("Об'єкт серіалізовано в файл binaryResult.ser");
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        Object restoredResult = BinarySerializationDemo.deserializeObject("binaryResult.ser");
        jLabel1.setText("Файл відновлено.");
    }                                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Graph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Graph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Graph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Graph.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Graph().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton ButtonGraph;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration                   
}
````

````java
package oop;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphFrame extends JFrame {

    private final XYSeries intPartSeries;

    public GraphFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout());

        XYSeriesCollection dataset = new XYSeriesCollection();
        intPartSeries = new XYSeries("Ratio");
        dataset.addSeries(intPartSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Binary Representation Graph",
                "Fractional Parts",
                "Integer Parts",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        this.add(chartPanel, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    public void updateChart(List<Double> xValues, List<Double> yValues) {
        intPartSeries.clear();

        for (int i = 0; i < xValues.size(); i++) {
            intPartSeries.add(xValues.get(i), yValues.get(i));
        }
    }

    void updateChart(BinaryResult binaryResult) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
````

````java
package oop;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(BinaryResult binaryResult);
}
````

````java
package oop;

import java.util.ArrayList;
import java.util.List;

public class ObservableControl implements Observable {

    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(BinaryResult binaryResult) {
        for (Observer observer : observers) {
            observer.update(binaryResult);
        }
    }
}
````

````java
package oop;

public interface Observer {
    void update(BinaryResult binaryResult);
}
````

````java
package oop;

public class ObserverChanges implements Observer {
    @Override
    public void update(BinaryResult binaryResult) {
        System.out.println("Відбулися зміни.");
    }
}
````

````java
package oop;

public class ObserverDemo implements Observer {
    private GraphFrame frame;
    
    public ObserverDemo(GraphFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public void update(BinaryResult binaryResult) {
        frame.updateChart(binaryResult);
    }
}
````

#### Результати
![](/misc/Task7.1.png)
![](/misc/Task7.2.png)
