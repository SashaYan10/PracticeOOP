/**
 * Клас для обчислення та представлення результатів у двійковій формі.
 */
public class BinaryCalculator {
    private BinaryResult binaryResult;

    public BinaryCalculator(double num) {
        int intPart = (int) num;
        double fracPart = num - intPart;

        String binaryInt = Integer.toBinaryString(intPart);
        String binaryFrac = getFracBinaryRepres(fracPart);
        
        this.binaryResult = new BinaryResult(num, intPart, binaryInt, fracPart, binaryFrac);
    }

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

    public BinaryResult getBinaryResult() {
        return binaryResult;
    }

    public void setBinaryResult(BinaryResult binaryResult) {
        this.binaryResult = binaryResult;
    }
}
