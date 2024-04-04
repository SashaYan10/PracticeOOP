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
import java.util.Scanner;

/**
 * Клас для демонстрації обчислення та серіалізації/десеріалізації результатів в двійковій формі.
 */
public class BinarySerializationDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть десяткове число: ");
        double num = scanner.nextDouble();

        int intPart = (int) num;
        double fracPart = num - intPart;
        String binaryIntPart = Integer.toBinaryString(intPart);

        BinaryCalculator calculator = new BinaryCalculator();
        calculator.solve(num, intPart, fracPart, binaryIntPart);
        BinaryResult binaryResult = calculator.getBinaryResult();

        serializeObject(binaryResult, "binaryResult.ser");

        BinaryResult restoredResult = (BinaryResult) deserializeObject("binaryResult.ser");
        System.out.println("Відновлений об'єкт:");
        System.out.println("Десяткове число: " + restoredResult.getNum());
        System.out.println("Ціла частина: " + restoredResult.getBinaryIntPart());
        System.out.println("Дробова частина: " + restoredResult.getBinaryFracPart());

        scanner.close();
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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть десяткове число: ");
        double num = scanner.nextDouble();
        scanner.close();

        BinaryCalculator binaryCalculator = new BinaryCalculator();
        binaryCalculator.solve(num, (int) num, num - (int) num, Integer.toBinaryString((int) num));
        return binaryCalculator.getBinaryResult();
    }

    /**
     * Метод для тестування правильності обчислень.
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
        } else {
            System.out.println("Немає операцій для скасування.");
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
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть десяткове число: ");
        double num = scanner.nextDouble();
        scanner.close();

        BinaryCalculator binaryCalculator = new BinaryCalculator();
        binaryCalculator.solve(num, (int) num, num - (int) num, Integer.toBinaryString((int) num));
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