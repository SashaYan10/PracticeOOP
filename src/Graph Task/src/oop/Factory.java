package oop;

public class Factory implements ResultFactory {
    @Override
    public BinaryResult createResult(double num, String binaryIntPart, String binaryFracPart) {
        return new BinaryResult(num, binaryIntPart, binaryFracPart);
    }
}