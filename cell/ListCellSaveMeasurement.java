package sample.cell;

import javafx.scene.control.ListCell;

/**
 * Класс ListCellSaveMeasurement представляет собой настраиваемую ячейку списка для отображения объектов типа SaveCells.
 * Он используется в JavaFX-приложении для кастомного отображения элементов в списке. Этот класс наследуется
 * от ListCell и переопределяет метод updateItem, чтобы установить графический контейнер элемента
 * в ячейке списка при заполнении данных.
 */
public class ListCellSaveMeasurement extends ListCell<SaveCells> {

    /**
     * Конструктор класса ListCellSaveMeasurement.
     * Не требует дополнительных действий в конструкторе.
     */
    public ListCellSaveMeasurement() {
        // Конструктор по умолчанию.
    }

    /**
     * Метод updateItem переопределен для настройки отображения элементов в ячейке списка.
     * Если элемент пустой или null, то в ячейке не отображается графика. В противном случае
     * устанавливается графический контейнер элемента в ячейке.
     *
     * @param item  Элемент типа SaveCells, который нужно отобразить в ячейке.
     * @param empty Флаг, указывающий на пустоту ячейки.
     */
    @Override
    protected void updateItem(SaveCells item, boolean empty) {
        super.updateItem(item, empty);

        // Если элемент пустой или null, не отображаем графику в ячейке.
        if (empty || item == null) {
            setGraphic(null);
        } else {
            setGraphic(item.getContainer());
        }
    }
}
