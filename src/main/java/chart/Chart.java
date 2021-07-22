package chart;

import java.io.File;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartUtilities;

import measure.Measure;

public class Chart {
    public List<Measure> measures;

    private final int HOUR = 12;
    private final int DAY = HOUR * 24;
    private final int WEEK = DAY * 7;

    public Chart(List<Measure> measures) {
        this.measures = measures;
    }

    public DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String xLabelTemp = "temperature";
        String xLabelHumi = "humidity";

        measures.forEach(measure -> {
            switch (measures.size()) {
                case HOUR: {
                    dataset.addValue(measure.getTemperature(), xLabelTemp, measure.getTime());
                    dataset.addValue(measure.getHumidity(), xLabelHumi, measure.getTime());
                    break;
                }

                case DAY: {
                    String xValue = measure.getId() % 10 == 0 ? measure.getTime() : " ";
                    dataset.addValue(measure.getTemperature(), xLabelTemp, xValue);
                    dataset.addValue(measure.getHumidity(), xLabelHumi, xValue);
                    break;
                }

                case WEEK: {
                    String xValue = measure.getId() % 100 == 0 ? measure.getTime() : " ";
                    dataset.addValue(measure.getTemperature(), xLabelTemp, xValue);
                    dataset.addValue(measure.getHumidity(), xLabelHumi, xValue);
                    break;
                }

                default:
                    break;
            }

        });

        return dataset;
    }

    public void createChart() {
        DefaultCategoryDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createLineChart("Погода в доме", // title
                "Время", // x-axis label
                "Показания", // y-axis label
                dataset);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        CategoryAxis axis = plot.getDomainAxis();
        axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        File fileChart = new File("chart.jpg");

        try {
            ChartUtilities.saveChartAsJPEG(fileChart, chart, 800, 600);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
