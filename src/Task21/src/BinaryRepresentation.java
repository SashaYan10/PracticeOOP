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
            System.out.println("------------------------------------------------------");
            System.out.println("| Десяткове число | Ціла частина | Дробова частина |");
            System.out.println("------------------------------------------------------");
            System.out.printf("| %-15.2f | %-12s | %-15s |\n", result.getNum(), result.getBinaryIntPart(), result.getBinaryFracPart());
            System.out.println("------------------------------------------------------");
        }
    }
    
}
