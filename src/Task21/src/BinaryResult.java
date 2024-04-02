import java.io.Serializable;

/**
 * Клас для представлення результатів обчислення у двійковій формі та серіалізації/десеріалізації.
 */
public class BinaryResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private double num;
    private String binaryIntPart;
    private String binaryFracPart;

    /**
     * Ініціалізація результатів обчислення у двійковій формі.
     */
    public BinaryResult(double num, String binaryIntPart, String binaryFracPart) {
        this.num = num;
        this.binaryIntPart = binaryIntPart;
        this.binaryFracPart = binaryFracPart;
    }

    //Гетери й сетери для відповідних значень
    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getBinaryIntPart() {
        return binaryIntPart;
    }

    public void setBinaryIntPart(String binaryIntPart) {
        this.binaryIntPart = binaryIntPart;
    }

    public String getBinaryFracPart() {
        return binaryFracPart;
    }

    public void setBinaryFracPart(String binaryFracPart) {
        this.binaryFracPart = binaryFracPart;
    }
}
