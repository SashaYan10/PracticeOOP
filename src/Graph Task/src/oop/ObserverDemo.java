package oop;

public class ObserverDemo implements Observer {
    private GraphFrame frame;
    
    public ObserverDemo(GraphFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public void update(BinaryResult binaryResult) {
        frame.updateChart(binaryResult);
    }
}
