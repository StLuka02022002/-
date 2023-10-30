package sample.cell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.Data;
import sample.Main;
import sample.data.Measurement;
import sample.settings.Settings;
import sample.settings.SettingsForDesign;
import sample.settings.SettingsUtil;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

/**
 * Класс SaveCells представляет собой пользовательскую ячейку для выбора параметров сохранения измерения (Measurement).
 * Эта ячейка используется для кастомного отображения данных в списке замеров в JavaFX-приложении, в частности, для
 * выбора имени файла и формата сохранения. Класс наследуется от базового класса ViewCell, чтобы предоставить
 * пользовательский интерфейс и стиль отображения.
 */
public class SaveCells extends ViewCell {

    private ObservableList<String> expansionNames =
            FXCollections.observableArrayList(
                    Data.TXT,
                    Data.XLSX,
                    Data.BIN
            );

    private Label name;
    private ComboBox<String> nameFile;
    private ComboBox<String> expansion;

    /**
     * Конструктор класса SaveCells, который принимает объект Measurement для отображения его данных.
     *
     * @param measurement Объект Measurement, данные которого нужно отобразить в ячейке.
     */
    public SaveCells(Measurement measurement) {
        super(measurement);

        // Загружаем настройки дизайна из Settings и устанавливаем их.
        SettingsForDesign settingsForDesign = (SettingsForDesign) SettingsUtil.getSettings(SettingsUtil.SETTINGS_FOR_DESIGN);
        if (settingsForDesign == null)
            settingsForDesign = new SettingsForDesign();

        // Инициализация элементов интерфейса.
        container = new HBox();
        name = new Label((String) settingsForDesign.get(Settings.SettingsParameter.NAME_MEASUREMENT));
        nameFile = new ComboBox<>(Data.fileNames);
        expansion = new ComboBox<>(expansionNames);

        name.setMinWidth(USE_PREF_SIZE);
        nameFile.setEditable(true);
        nameFile.setMaxWidth(270);
        expansion.setValue(expansion.getItems().get(0));
        expansion.setMinWidth(USE_PREF_SIZE);
        expansion.setPrefWidth(90);

        // Настройка расположения элементов в контейнере.
        container.getChildren().addAll(name, nameFile, expansion);
        container.setSpacing(5);
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(395);
        container.getStylesheets().add(Main.class.getResource("styles/cell.css").toExternalForm());
    }

    /**
     * Метод для получения ComboBox, содержащего имена файлов.
     *
     * @return ComboBox с именами файлов.
     */
    public ComboBox<String> getNameFile() {
        return nameFile;
    }

    /**
     * Метод для получения ComboBox, содержащего доступные расширения файлов для сохранения.
     *
     * @return ComboBox с расширениями файлов.
     */
    public ComboBox<String> getExpansion() {
        return expansion;
    }

    /**
     * Метод для получения Label с именем измерения.
     *
     * @return Label с именем измерения.
     */
    public Label getName() {
        return name;
    }

    /**
     * Метод для получения контейнера HBox, содержащего элементы интерфейса.
     *
     * @return Контейнер HBox с элементами интерфейса.
     */
    public HBox getContainer() {
        return container;
    }
}
