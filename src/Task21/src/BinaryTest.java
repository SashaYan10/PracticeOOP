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
