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

        displayConfirmation(result, factory);

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

    public void undoOperation() {
        if (!history.isEmpty()) {
            BinaryResult undoneResult = history.pop();
            System.out.println("Скасовано операцію: " + undoneResult);
        } else {
            System.out.println("Немає операцій для скасування.");
        }
    }

    public void displayConfirmation(BinaryResult result, ResultFactory factory) {
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
        } else {
            displayTable();
        }
        scanner.close();
    }
}
