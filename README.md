# Завдання 1

### Написати просту консольну програму
![](/misc/Task%201.png)

# Завдання 2

### Визначити двійкове уявлення цілої та дробової частини речового десяткового числа.
````java
import java.util.Scanner;

public class BinaryRepresentation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть десяткове число: ");
        double num = scanner.nextDouble();

        int intPart = (int) num;
        double fracPart = num - intPart;

        System.out.println("Ціла частина: " + Integer.toBinaryString(intPart));
        System.out.println("Дробова частина: " + getBinaryRepresentation(fracPart));

        scanner.close();
    }

    public static String getBinaryRepresentation(double fraction) {
        StringBuilder binary = new StringBuilder();
        while (fraction>0) {
            fraction *= 2;
            if (fraction >=1) {
                binary.append(1);
                fraction -= 1;
            } else {
                binary.append(0);
            }
        }
        return binary.toString();
    }
}
````
