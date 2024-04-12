import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для представлення результатів обчислення у двійковій формі та серіалізації/десеріалізації.
 */
public class BinaryResult implements Serializable, ResultDisplay {
    private static final long serialVersionUID = 1L;
    private List<BinaryResult> results;

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

    @Override
    public void displayResult() {
        System.out.println("Десяткове число: " + num);
        System.out.println("Ціла частина: " + binaryIntPart);
        System.out.println("Дробова частина: " + binaryFracPart);
    }

    public BinaryResult() {
        results = new ArrayList<>();
    }

    public void addResult(BinaryResult result) {
        results.add(result);
    }

    public List<BinaryResult> getResults() {
        return results;
    }
}
