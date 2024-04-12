package oop;

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