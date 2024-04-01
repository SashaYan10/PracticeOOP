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
