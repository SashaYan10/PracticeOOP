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

        StringBuilder binary = new StringBuilder();
        while (fracPart > 0) {
            fracPart *= 2;
            if (fracPart >= 1) {
                binary.append(1);
                fracPart -= 1;
            } else {
                binary.append(0);
            }
        }
        System.out.println("Дробова частина: " + binary);

        scanner.close();
    }
}
````
