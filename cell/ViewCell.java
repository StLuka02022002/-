package sample.cell;

import javafx.scene.layout.HBox;
import sample.data.Measurement;

/**
 * Базовый класс ViewCell представляет собой абстрактную ячейку, которая может быть использована для отображения данных
 * в пользовательском интерфейсе в виде HBox-контейнера. Этот класс предоставляет общий интерфейс для кастомных ячеек
 * в JavaFX-приложении и хранит объект Measurement для отображения его данных.
 */
public class ViewCell {

    private Measurement measurement;  // Объект Measurement для отображения его данных.

    protected HBox container = null;  // HBox-контейнер для размещения элементов интерфейса в ячейке.

    /**
     * Конструктор класса ViewCell, который принимает объект Measurement для отображения его данных в ячейке.
     *
     * @param measurement Объект Measurement, данные которого нужно отобразить в ячейке.
     */
    public ViewCell(Measurement measurement) {
        this.measurement = measurement;
    }

    /**
     * Метод для получения объекта Measurement, данные которого отображаются в ячейке.
     *
     * @return Объект Measurement.
     */
    public Measurement getMeasurement() {
        return measurement;
    }

    /**
     * Метод для установки объекта Measurement с данными, которые нужно отобразить в ячейке.
     *
     * @param measurement Объект Measurement с данными для отображения.
     */
    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    /**
     * Метод для получения HBox-контейнера, в котором размещаются элементы интерфейса ячейки.
     *
     * @return HBox-контейнер с элементами интерфейса.
     */
    public HBox getContainer() {
        return container;
    }
}
