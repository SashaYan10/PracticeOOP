package oop;

public class ObserverChanges implements Observer {
    @Override
    public void update(BinaryResult binaryResult) {
        System.out.println("Відбулися зміни.");
    }
}