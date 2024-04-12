package oop;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphFrame extends JFrame {

    private final XYSeries intPartSeries;

    public GraphFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout());

        XYSeriesCollection dataset = new XYSeriesCollection();
        intPartSeries = new XYSeries("Ratio");
        dataset.addSeries(intPartSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Binary Representation Graph",
                "Fractional Parts",
                "Integer Parts",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        this.add(chartPanel, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    public void updateChart(List<Double> xValues, List<Double> yValues) {
        intPartSeries.clear();

        for (int i = 0; i < xValues.size(); i++) {
            intPartSeries.add(xValues.get(i), yValues.get(i));
        }
    }

    void updateChart(BinaryResult binaryResult) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
