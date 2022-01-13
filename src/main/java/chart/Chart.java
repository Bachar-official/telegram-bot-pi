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
    private final int WORKDAY = HOUR * 8;

    public Chart(List<Measure> measures) {
        this.measures = measures;
    }

    public DefaultCategoryDataset createDataset() {
        System.out.println(String.format("Request for chart. Measures count: %d", measures.size()));
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String xLabelTemp = "temperature";
        String xLabelHumi = "humidity";
        String xLabelPress = "pressure (x76)";
        double divider = 76.0;

        measures.forEach(measure -> {
            switch (measures.size()) {
                case HOUR: {
                    dataset.addValue(measure.getTemperature(), xLabelTemp, measure.getTime());
                    dataset.addValue(measure.getHumidity(), xLabelHumi, measure.getTime());
                    dataset.addValue(measure.getPressure() / divider, xLabelPress, measure.getTime());
                    break;
                }

                case WORKDAY: {
                    String xValue = measure.getId() % 8 == 0 ? measure.getTime() : " ";
                    dataset.addValue(measure.getTemperature(), xLabelTemp, xValue);
                    dataset.addValue(measure.getHumidity(), xLabelHumi, xValue);
                    dataset.addValue(measure.getPressure() / divider, xLabelPress, xValue);
                    break;
                }

                case DAY: {
                    String xValue = measure.getId() % 10 == 0 ? measure.getTime() : " ";
                    dataset.addValue(measure.getTemperature(), xLabelTemp, xValue);
                    dataset.addValue(measure.getHumidity(), xLabelHumi, xValue);
                    dataset.addValue(measure.getPressure() / divider, xLabelPress, xValue);
                    break;
                }

                case WEEK: {
                    String xValue = measure.getId() % 100 == 0 ? measure.getTime() : " ";
                    dataset.addValue(measure.getTemperature(), xLabelTemp, xValue);
                    dataset.addValue(measure.getHumidity(), xLabelHumi, xValue);
                    dataset.addValue(measure.getPressure() / divider, xLabelPress, xValue);
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
        JFreeChart chart = ChartFactory.createLineChart("@weatherAtHomeBot", // title
                "Time", // x-axis label
                "Measures", // y-axis label
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
