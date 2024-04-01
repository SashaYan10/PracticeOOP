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
