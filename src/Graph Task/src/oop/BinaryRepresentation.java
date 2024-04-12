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
