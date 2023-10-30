package sample.data;

import java.io.Serializable;

/**
 * Класс DataMeasurement представляет данные измерения, включая значения гироскопов, давления и время.
 */
public class DataMeasurement implements Serializable {

    public double gyro_1 = 0;
    public double gyro_2 = 0;
    public double pressure = 0;
    public long time = 0;

    /**
     * Конструктор для создания объекта DataMeasurement с заданными значениями.
     *
     * @param gyro_1   Значение гироскопа 1.
     * @param gyro_2   Значение гироскопа 2.
     * @param pressure Значение давления.
     * @param time     Время измерения.
     */
    public DataMeasurement(double gyro_1, double gyro_2, double pressure, long time) {
        this.gyro_1 = gyro_1;
        this.gyro_2 = gyro_2;
        this.pressure = pressure;
        this.time = time;
    }

    /**
     * Устанавливает новые значения для объекта DataMeasurement.
     *
     * @param gyro_1   Значение гироскопа 1.
     * @param gyro_2   Значение гироскопа 2.
     * @param pressure Значение давления.
     * @param time     Время измерения.
     */
    public void set(double gyro_1, double gyro_2, double pressure, long time) {
        this.gyro_1 = gyro_1;
        this.gyro_2 = gyro_2;
        this.pressure = pressure;
        this.time = time;
    }
}
