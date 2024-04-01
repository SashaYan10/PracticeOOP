import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Клас для представлення результатів обчислення у двійковій формі та серіалізації/десеріалізації.
 */
public class BinaryResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private double num;
    private int intPart;
    private String binaryInt;
    private double fracPart;
    private String binaryFrac;

    /**
     * Ініціалізація результатів обчислення у двійковій формі.
     * @param num Десяткове число.
     * @param intPart Ціла частина числа.
     * @param binaryInt Двійова ціла частина числа.
     * @param fracPart Дробова частина числа.
     * @param binaryFrac Двійкова дробова частина числа.
     */
    public BinaryResult(double num, int intPart, String binaryInt, double fracPart, String binaryFrac) {
        this.num = num;
        this.intPart = intPart;
        this.binaryInt = binaryInt;
        this.fracPart = fracPart;
        this.binaryFrac = binaryFrac;
    }

    //Гетери й сетери для відповідних значень
    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public int getIntPart() {
        return intPart;
    }

    public void setIntPart(int intPart) {
        this.intPart = intPart;
    }

    public String getBinaryInt() {
        return binaryInt;
    }

    public void setBinaryInt(String binaryInt) {
        this.binaryInt = binaryInt;
    }

    public double getFracPart() {
        return fracPart;
    }

    public void setFracPart(double fracPart) {
        this.fracPart = fracPart;
    }

    public String getBinaryFrac() {
        return binaryFrac;
    }

    public void setBinaryFrac(String binaryFrac) {
        this.binaryFrac = binaryFrac;
    }

    public void serialize(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    public static BinaryResult deserialize(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (BinaryResult) in.readObject();
        }
    }
}