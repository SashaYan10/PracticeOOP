package oop;

/**
 * Клас для обчислення та представлення результатів у двійковій формі.
 */
public class BinaryCalculator implements CalculatorFactory {
    private BinaryResult binaryResult;

    public void solve(double num, int intPart, double fracPart, String binaryIntPart) {
        StringBuilder binaryFrac = new StringBuilder();
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

        binaryResult = new BinaryResult(num, binaryIntPart, binaryFracPart);
    }

    public BinaryResult getBinaryResult() {
        return binaryResult;
    }

    @Override
    public BinaryResult calculate(double num) {
        return binaryResult;
    }
}