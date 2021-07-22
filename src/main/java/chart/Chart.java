package chart;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
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

    public Chart(List<Measure> measures) {
        this.measures = measures;
    }

    public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    public DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        measures.forEach(measure -> {
            dataset.addValue(measure.getTemperature(), "temperature", measure.getTime());
            dataset.addValue(measure.getHumidity(), "humidity", measure.getTime());
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
