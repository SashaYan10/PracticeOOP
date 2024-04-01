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
    private int intPart;
    private String binaryInt;
    private double fracPart;
    private String binaryFrac;

    /**
     * Конструктор класу для отримання десяткового числа та перетворення його у двійкову форму.
     */
    public BinaryRepresentation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть десяткове число: ");
        double num = scanner.nextDouble();

        this.intPart = (int) num;
        this.binaryInt = Integer.toBinaryString(intPart);
        this.fracPart = num - intPart;
        this.binaryFrac = getFracBinaryRepres(fracPart);

        scanner.close();
    }

    public int getIntPart() {
        return intPart;
    }

    /**
     * Методи для повернення цілої, цілої двійкової, дробової, двійкової дробової частини десяткового числа.
     * @return
     */
    public String getBinaryInt() {
        return binaryInt;
    }

    public double getFracPart() {
        return fracPart;
    }

    public String getBinaryFrac() {
        return binaryFrac;
    }

    /**
     * Метод для отримання двійкового представлення дробової частини числа.
     * @param fraction
     * @return
     */
    private String getFracBinaryRepres(double fraction) {
        StringBuilder binary = new StringBuilder();
        while (fraction > 0) {
            fraction *= 2;
            if (fraction >= 1) {
                binary.append(1);
                fraction -= 1;
            } else {
                binary.append(0);
            }
        }
        return binary.toString();
    }
}
````

### Розробити клас, що серіалізується, для зберігання параметрів і результатів обчислень.
````java
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Клас для представлення результатів обчислення у двійковій формі та серіалізації/десеріалізації.
 */
public class BinaryResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private double num;
    private int intPart;
    private String binaryInt;
    private double fracPart;
    private String binaryFrac;

    /**
     * Ініціалізація результатів обчислення у двійковій формі.
     * @param num Десяткове число.
     * @param intPart Ціла частина числа.
     * @param binaryInt Двійова ціла частина числа.
     * @param fracPart Дробова частина числа.
     * @param binaryFrac Двійкова дробова частина числа.
     */
    public BinaryResult(double num, int intPart, String binaryInt, double fracPart, String binaryFrac) {
        this.num = num;
        this.intPart = intPart;
        this.binaryInt = binaryInt;
        this.fracPart = fracPart;
        this.binaryFrac = binaryFrac;
    }

    //Гетери й сетери для відповідних значень
    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public int getIntPart() {
        return intPart;
    }

    public void setIntPart(int intPart) {
        this.intPart = intPart;
    }

    public String getBinaryInt() {
        return binaryInt;
    }

    public void setBinaryInt(String binaryInt) {
        this.binaryInt = binaryInt;
    }

    public double getFracPart() {
        return fracPart;
    }

    public void setFracPart(double fracPart) {
        this.fracPart = fracPart;
    }

    public String getBinaryFrac() {
        return binaryFrac;
    }

    public void setBinaryFrac(String binaryFrac) {
        this.binaryFrac = binaryFrac;
    }

    public void serialize(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    public static BinaryResult deserialize(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (BinaryResult) in.readObject();
        }
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

    public BinaryCalculator(double num) {
        int intPart = (int) num;
        double fracPart = num - intPart;

        String binaryInt = Integer.toBinaryString(intPart);
        String binaryFrac = getFracBinaryRepres(fracPart);
        
        this.binaryResult = new BinaryResult(num, intPart, binaryInt, fracPart, binaryFrac);
    }

    private String getFracBinaryRepres(double fraction) {
        StringBuilder binary = new StringBuilder();
        while (fraction > 0) {
            fraction *= 2;
            if (fraction >= 1) {
                binary.append(1);
                fraction -= 1;
            } else {
                binary.append(0);
            }
        }
        return binary.toString();
    }

    public BinaryResult getBinaryResult() {
        return binaryResult;
    }

    public void setBinaryResult(BinaryResult binaryResult) {
        this.binaryResult = binaryResult;
    }
}
````

### Розробити клас для демонстрації в діалоговому режимі збереження та відновлення стану об'єкта, використовуючи серіалізацію. Показати особливості використання transient полів.
````java
import java.io.IOException;

/**
 * Клас для демонстрації обчислення та серіалізації/десеріалізації результатів в двійковій формі.
 */
public class BinarySerializationDemo {
    public static void main(String[] args) {
        BinaryRepresentation representation = new BinaryRepresentation();
        int intPart = representation.getIntPart();
        String binaryInt = representation.getBinaryInt();
        double fracPart = representation.getFracPart();
        String binaryFrac = representation.getBinaryFrac();

        BinaryResult result = new BinaryResult(fracPart, intPart, binaryInt, fracPart, binaryFrac);

        try {
            result.serialize("binary_result.ser");
            System.out.println("Дані збережені в файлі binary_result.ser");

            System.out.println("Ціла частина:: " + result.getBinaryInt());
            System.out.println("Дробова частина: " + result.getBinaryFrac());

            BinaryResult restoredResult = BinaryResult.deserialize("binary_result.ser");
            System.out.println("Дані відновлено: " + restoredResult.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
public class BinaryTest {
    public static void main(String[] args) {
        testBinary();
    }

    /**
     * Метод для тестування правильності обчислень.
     */
    public static void testBinary() {
        BinaryRepresentation representation = new BinaryRepresentation();
        int intPart = representation.getIntPart();
        double fracPart = representation.getFracPart();
        double userNum = intPart + fracPart;
        BinaryCalculator calculator = new BinaryCalculator(userNum);
        BinaryResult origResult = calculator.getBinaryResult();

        try {
            origResult.serialize("test_result.ser");

            BinaryResult deserializedResult = BinaryResult.deserialize("test_result.ser");

            if (compareResults(origResult, deserializedResult)) {
                System.out.println("Тест пройдено успішно: результати обчислень та серіалізації/десеріалізації є однаковими.");
            } else {
                System.out.println("Тест не пройдено: результати обчислень та серіалізації/десеріалізації відрізняються.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для порівнювання результатів обчислення.
     * @param original
     * @param deserialized
     * @return
     */
    public static boolean compareResults(BinaryResult original, BinaryResult deserialized) {
        return original.getNum() == deserialized.getNum()
                && original.getIntPart() == deserialized.getIntPart()
                && original.getBinaryInt().equals(deserialized.getBinaryInt())
                && original.getFracPart() == deserialized.getFracPart()
                && original.getBinaryFrac().equals(deserialized.getBinaryFrac());
    }
}
````

### Результати:
![](/misc/Task2.1.png)
![](/misc/Task2.2.png)