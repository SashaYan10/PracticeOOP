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
