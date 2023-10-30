package sample;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.VerticalAlignment;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import sample.data.DataMeasurement;
import sample.data.Measurement;
import sample.settings.Settings;
import sample.settings.SettingsForChart;

import javax.swing.*;
import java.awt.*;

public class Chart {

    public final static int GYRO1_DATA = 0;                         // Константа для серии данных гироскопа 1
    public final static int GYRO2_DATA = 1;                         // Константа для серии данных гироскопа 2
    public final static int PRESSURE_DATA = 2;                      // Константа для серии данных датчика давления

    private final static String GYRO1_NAME = "Гироскоп 1";          // Название серии данных гироскопа 1
    private final static String GYRO2_NAME = "Гироскоп 2";          // Название серии данных гироскопа 2
    private final static String PRESSURE_NAME = "Датчик давления";  // Название серии данных датчика давления
    private final static String CHART_NAME = "GY-521";              // Название графика
    private final static String X_AXIS_NAME = "G&G";                // Название оси X
    private final static String Y_AXIS_NAME = "time";               // Название оси Y

    private final static int CHART_WITH = 890;                      // Ширина графика
    private final static int CHART_HEIGHT = 590;                    // Высота графика

    private JPanel panel;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private XYSeries gyro_1;
    private XYSeries gyro_2;
    private XYSeries pressure;
    private XYSeriesCollection xyDataset;

    public Chart() {
        gyro_1 = new XYSeries(GYRO1_NAME);
        gyro_2 = new XYSeries(GYRO2_NAME);
        pressure = new XYSeries(PRESSURE_NAME);

        xyDataset = new XYSeriesCollection();
        chart = ChartFactory.createXYLineChart(CHART_NAME + "\n    \n     ",
                Y_AXIS_NAME, X_AXIS_NAME, xyDataset);

        chartPanel = new ChartPanel(chart);
        panel = new JPanel();
        panel.add(chartPanel);

        front();
    }

    //Внешний вид графика
    private void front() {
        // Настройка размеров графика
        chartPanel.setPreferredSize(new Dimension(CHART_WITH, CHART_HEIGHT));
        TextTitle textTitle = chart.getTitle();
        textTitle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        textTitle.setVerticalAlignment(VerticalAlignment.BOTTOM);

        // Настройка оси Y для отображения нулевой линии
        XYPlot plot = chart.getXYPlot();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setAutoRangeIncludesZero(true);

        // Добавление нулевой линии для оси Y
        Marker zeroLine = new ValueMarker(0.0);
        zeroLine.setPaint(Color.RED);
        zeroLine.setStroke(new BasicStroke(4));
        plot.addRangeMarker(zeroLine);

    }

    //Настройка внешнего вида графика
    public void setSettings(SettingsForChart settings) {
        XYLineAndShapeRenderer renderer =
                (XYLineAndShapeRenderer) chart.getXYPlot().getRenderer();
        renderer.setSeriesPaint(0,
                (Paint) settings.get(Settings.SettingsParameter.COLOR_GYRO_1)); // Цвет для линии гироскопа 1
        renderer.setSeriesStroke(0,
                new BasicStroke((Float) settings.get(Settings.SettingsParameter.THICKNESS_GYRO_1))); // Толщина линии гироскопа 1
        renderer.setSeriesPaint(1,
                (Paint) settings.get(Settings.SettingsParameter.COLOR_GYRO_2)); // Цвет для линии гироскопа 1
        renderer.setSeriesStroke(1,
                new BasicStroke((Float) settings.get(Settings.SettingsParameter.THICKNESS_GYRO_2))); // Толщина линии гироскопа 1
        renderer.setSeriesPaint(2,
                (Paint) settings.get(Settings.SettingsParameter.COLOR_PRESSURE)); // Цвет для линии гироскопа 1
        renderer.setSeriesStroke(2,
                new BasicStroke((Float) settings.get(Settings.SettingsParameter.THICKNESS_PRESSURE))); // Толщина линии гироскопа 1
    }

    public JPanel getPanel() {
        return panel;
    }

    //Добавление данных
    public void addData(DataMeasurement data) {
        addData(data.gyro_1, data.gyro_2, data.pressure, data.time);
    }

    public void addData(double gyro_1, double gyro_2, double pressure, double time) {
        this.gyro_1.add(time, gyro_1);
        this.gyro_2.add(time, gyro_2);
        this.pressure.add(time, pressure);
    }

    public void addMeasurement(Measurement measurement) {
        for (DataMeasurement data :
                measurement) {
            addData(data);
        }
    }

    public void addData(int serial, double data, double time) {
        switch (serial) {
            case GYRO1_DATA:
                this.gyro_1.add(time, data);
                break;
            case GYRO2_DATA:
                this.gyro_2.add(time, data);
                break;
            case PRESSURE_DATA:
                this.pressure.add(time, data);
                break;
        }
    }

    //Выбор серии данных
    public void addSerial(int serial) {
        switch (serial) {
            case GYRO1_DATA:
                xyDataset.addSeries(gyro_1);
                break;
            case GYRO2_DATA:
                xyDataset.addSeries(gyro_2);
                break;
            case PRESSURE_DATA:
                xyDataset.addSeries(pressure);
                break;
        }
    }

    //Удаление серии данных
    public void deleteSerial(int serial) {
        switch (serial) {
            case GYRO1_DATA:
                xyDataset.removeSeries(gyro_1);
                break;
            case GYRO2_DATA:
                xyDataset.removeSeries(gyro_2);
                break;
            case PRESSURE_DATA:
                xyDataset.removeSeries(pressure);
                break;
        }
    }

    //Очищение графика
    public void clearChart() {
        gyro_1.clear();
        gyro_2.clear();
        pressure.clear();
    }

}
