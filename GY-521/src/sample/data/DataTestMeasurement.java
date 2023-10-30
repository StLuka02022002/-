package sample.data;

/**
 * Класс DataTestMeasurement представляет тестовые данные измерения, включая значения гироскопов и давления.
 */
public class DataTestMeasurement {

    public int testGyro_1 = 0;
    public int testGyro_2 = 0;
    public int testPressure = 0;

    /**
     * Конструктор для создания объекта DataTestMeasurement с заданными значениями.
     *
     * @param testGyro_1   Значение тестового гироскопа 1.
     * @param testGyro_2   Значение тестового гироскопа 2.
     * @param testPressure Значение тестового давления.
     */
    public DataTestMeasurement(int testGyro_1, int testGyro_2, int testPressure) {
        this.testGyro_1 = testGyro_1;
        this.testGyro_2 = testGyro_2;
        this.testPressure = testPressure;
    }

    /**
     * Устанавливает новые значения для объекта DataTestMeasurement.
     *
     * @param testGyro_1   Значение тестового гироскопа 1.
     * @param testGyro_2   Значение тестового гироскопа 2.
     * @param testPressure Значение тестового давления.
     */
    public void set(int testGyro_1, int testGyro_2, int testPressure) {
        this.testGyro_1 = testGyro_1;
        this.testGyro_2 = testGyro_2;
        this.testPressure = testPressure;
    }
}
