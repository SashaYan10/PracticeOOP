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
