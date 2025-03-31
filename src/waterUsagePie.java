import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.Map;

public class waterUsagePie {

    public static JPanel createPieChart(Map<String, Double> waterData) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (Map.Entry<String, Double> entry : waterData.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Water Usage Per Plant",
                dataset,
                true,   // show legend
                true,   // tooltips
                false   // URLs
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{0}: {1} L"));

        return new ChartPanel(chart); // This is a JPanel
    }
}