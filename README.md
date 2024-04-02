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