package sample.cell;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

import sample.Main;
import sample.data.Measurement;
import sample.settings.Settings;
import sample.settings.SettingsForDesign;
import sample.settings.SettingsUtil;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

/**
 * Класс MeasurementCell представляет собой пользовательскую ячейку для отображения информации о измерениях (Measurement).
 * Эта ячейка используется для кастомного отображения данных в списке замеров в JavaFX-приложении. Класс наследуется от
 * базового класса ViewCell, чтобы предоставить пользовательский интерфейс и стиль отображения.
 */
public class MeasurementCell extends ViewCell {

    public final static String SAVE = "Save";
    public final static String SAVE_NOT = "Don't Save";

    private Label name;
    private Label path;
    private Label save;

    /**
     * Конструктор класса MeasurementCell, который принимает объект Measurement для отображения его данных.
     *
     * @param measurement Объект Measurement, данные которого нужно отобразить в ячейке.
     */
    public MeasurementCell(Measurement measurement) {
        super(measurement);

        // Загружаем настройки дизайна из Settings и устанавливаем их.
        Settings settings = SettingsUtil.getSettings(SettingsUtil.SETTINGS_FOR_DESIGN);
        if (settings == null)
            settings = new SettingsForDesign();

        name = new Label((String) settings.get(Settings.SettingsParameter.NAME_MEASUREMENT));
        path = new Label();
        save = new Label(SAVE_NOT);

        // Создаем контейнер HBox и добавляем в него элементы для отображения.
        container = new HBox(name, new Separator(Orientation.VERTICAL), path,
                new Separator(Orientation.VERTICAL), save);

        name.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        path.setPrefWidth(225);
        path.setMaxWidth(USE_PREF_SIZE);
        save.setMinSize(37, USE_COMPUTED_SIZE);

        container.setSpacing(0);
        container.setAlignment(Pos.CENTER_LEFT);
        container.getStylesheets().add(Main.class.getResource("styles/cell.css").toExternalForm());
    }

    /**
     * Метод для получения Label, содержащего имя измерения.
     *
     * @return Label с именем измерения.
     */
    public Label getName() {
        return name;
    }

    /**
     * Метод для получения Label, содержащего путь к файлу измерения.
     *
     * @return Label с путем к файлу измерения.
     */
    public Label getPath() {
        return path;
    }

    /**
     * Метод для получения Label, указывающего на сохранение измерения.
     *
     * @return Label, где "Сохранено" или "Не сохранено".
     */
    public Label getSave() {
        return save;
    }
}
