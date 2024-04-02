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
