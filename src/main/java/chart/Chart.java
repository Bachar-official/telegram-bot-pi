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
    private DefaultCategoryDataset dataset;
    private boolean isPressure;

    private final int HOUR = 12;
    private final int DAY = HOUR * 24;
    private final int WEEK = DAY * 7;
    private final int WORKDAY = HOUR * 8;

    public Chart(List<Measure> measures, boolean pressure) {
        this.measures = measures;
        this.isPressure = pressure;
        this.dataset = new DefaultCategoryDataset();
    }

    private DefaultCategoryDataset createDataset() {
        System.out.println(String.format("Request for chart. Measures count: %d", measures.size()));

        measures.forEach(measure -> {
            switch (measures.size()) {
                case HOUR: {
                    fillPoint(measure, measure.getTime());
                    break;
                }

                case WORKDAY: {
                    String xValue = measure.getId() % 8 == 0 ? measure.getTime() : " ";
                    fillPoint(measure, xValue);
                    break;
                }

                case DAY: {
                    String xValue = measure.getId() % 10 == 0 ? measure.getTime() : " ";
                    fillPoint(measure, xValue);
                    break;
                }

                case WEEK: {
                    String xValue = measure.getId() % 100 == 0 ? measure.getTime() : " ";
                    fillPoint(measure, xValue);
                    break;
                }

                default:
                    break;
            }

        });

        return dataset;
    }

    private void fillPoint(Measure measure, String xValue) {
        String xLabelTemp = "temperature";
        String xLabelHumi = "humidity";
        String xLabelPress = "pressure";
        if (isPressure) {
            this.dataset.addValue(measure.getPressure(), xLabelPress, xValue);
        } else {
            this.dataset.addValue(measure.getTemperature(), xLabelTemp, xValue);
            this.dataset.addValue(measure.getHumidity(), xLabelHumi, xValue);
        }
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
