package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import sample.Data;
import sample.arduino.Arduino;
import sample.data.DataUtil;
import sample.settings.Settings;
import sample.settings.SettingsForChart;
import sample.settings.SettingsForDesign;
import sample.settings.SettingsUtil;

import java.awt.*;
import java.io.File;

/**
 * Контроллер для управления настройками приложения.
 */
public class SettingsController {

    @FXML
    private ComboBox<String> comboBoxForCOM_port;

    @FXML
    private VBox vBoxForColor;

    @FXML
    private VBox vBoxForThickness;

    @FXML
    private CheckBox checkBox_isFilter;

    @FXML
    private Label labelPath;

    @FXML
    private Button buttonPath;

    @FXML
    private TextField textFieldForMeasurementName;

    @FXML
    private ComboBox<String> comboBoxForUnitsGyro;

    @FXML
    private ComboBox<String> comboBoxForUnitsPressure;

    private ObservableList<String> comPorts =
            FXCollections.observableArrayList(
                    Arduino.listAvailablePortNames()
            );

    /**
     * Инициализация контроллера.
     */
    public void initialize() {
        for (Node comboBox : vBoxForThickness.getChildren()) {
            ((ComboBox<Float>) comboBox).getItems().setAll(Data.thickness);
        }
        comboBoxForCOM_port.setItems(comPorts);
        comboBoxForUnitsGyro.setItems(Data.gyroUnits);
        comboBoxForUnitsPressure.setItems(Data.pressureUnits);
        setSettings();
    }

    /**
     * Загрузка настроек и установка значений интерфейса настроек.
     */
    private void setSettings() {
        SettingsForChart settingsForChart = (SettingsForChart) SettingsUtil.getSettings(SettingsUtil.SETTINGS_FOR_CHART);
        SettingsForDesign settingsForDesign = (SettingsForDesign) SettingsUtil.getSettings(SettingsUtil.SETTINGS_FOR_DESIGN);
        if (settingsForChart != null)
            setSettingsForChart(settingsForChart);
        else setSettingsForChart(new SettingsForChart());
        if (settingsForDesign != null)
            setSettingsForDesign(settingsForDesign);
        else setSettingsForDesign(new SettingsForDesign());
    }

    /**
     * Сохранение настроек в файл.
     *
     * @param settingsForChart  Настройки графиков.
     * @param settingsForDesign Настройки интерфейса.
     */
    private void saveSettings(SettingsForChart settingsForChart, SettingsForDesign settingsForDesign) {
        SettingsUtil.saveSettings(settingsForChart, SettingsUtil.SETTINGS_FOR_CHART);
        SettingsUtil.saveSettings(settingsForDesign, SettingsUtil.SETTINGS_FOR_DESIGN);
        setUnitsForGyro((String) settingsForDesign.get(Settings.SettingsParameter.GYRO_UNITS));
        setUnitsForPressure((String) settingsForDesign.get(Settings.SettingsParameter.PRESSURE_UNITS));
    }

    /**
     * Установка настроек для графиков.
     *
     * @param settings Настройки графиков.
     */
    private void setSettingsForChart(SettingsForChart settings) {
        Color colorGyro_1 = (Color) settings.get(Settings.SettingsParameter.COLOR_GYRO_1);
        Color colorGyro_2 = (Color) settings.get(Settings.SettingsParameter.COLOR_GYRO_2);
        Color colorPressure = (Color) settings.get(Settings.SettingsParameter.COLOR_PRESSURE);
        float thicknessGyro_1 = (float) settings.get(Settings.SettingsParameter.THICKNESS_GYRO_1);
        float thicknessGyro_2 = (float) settings.get(Settings.SettingsParameter.THICKNESS_GYRO_2);
        float thicknessPressure = (float) settings.get(Settings.SettingsParameter.THICKNESS_PRESSURE);
        boolean isFilter = (boolean) settings.get(Settings.SettingsParameter.IS_FILTER);
        ColorPicker colorPicker;
        colorPicker = (ColorPicker) vBoxForColor.getChildren().get(0);
        colorPicker.setValue(awtColorConvert(colorGyro_1));
        colorPicker = (ColorPicker) vBoxForColor.getChildren().get(1);
        colorPicker.setValue(awtColorConvert(colorGyro_2));
        colorPicker = (ColorPicker) vBoxForColor.getChildren().get(2);
        colorPicker.setValue(awtColorConvert(colorPressure));
        ComboBox<Float> comboBox;
        comboBox = (ComboBox<Float>) vBoxForThickness.getChildren().get(0);
        comboBox.setValue(thicknessGyro_1);
        comboBox = (ComboBox<Float>) vBoxForThickness.getChildren().get(1);
        comboBox.setValue(thicknessGyro_2);
        comboBox = (ComboBox<Float>) vBoxForThickness.getChildren().get(2);
        comboBox.setValue(thicknessPressure);
        checkBox_isFilter.setSelected(isFilter);
    }

    /**
     * Установка настроек для интерфейса.
     *
     * @param settings Настройки интерфейса.
     */
    private void setSettingsForDesign(SettingsForDesign settings) {
        comboBoxForCOM_port.setValue((String) settings.get(Settings.SettingsParameter.COM_PORT));
        labelPath.setText((String) settings.get(Settings.SettingsParameter.PATH));
        textFieldForMeasurementName.setPromptText(
                (String) settings.get(Settings.SettingsParameter.NAME_MEASUREMENT));
        comboBoxForUnitsGyro.setValue((String) settings.get(Settings.SettingsParameter.GYRO_UNITS));
        comboBoxForUnitsPressure.setValue((String) settings.get(Settings.SettingsParameter.PRESSURE_UNITS));
    }

    /**
     * Получение настроек для графиков.
     *
     * @return Настройки графиков.
     */
    private SettingsForChart getSettingsForChart() {
        SettingsForChart settings = new SettingsForChart();
        ColorPicker colorPicker;
        colorPicker = (ColorPicker) vBoxForColor.getChildren().get(0);
        settings.set(Settings.SettingsParameter.COLOR_GYRO_1,
                javafxColorConvert(colorPicker.getValue()));
        colorPicker = (ColorPicker) vBoxForColor.getChildren().get(1);
        settings.set(Settings.SettingsParameter.COLOR_GYRO_2,
                javafxColorConvert(colorPicker.getValue()));
        colorPicker = (ColorPicker) vBoxForColor.getChildren().get(2);
        settings.set(Settings.SettingsParameter.COLOR_PRESSURE,
                javafxColorConvert(colorPicker.getValue()));
        ComboBox<Float> comboBox;
        comboBox = (ComboBox<Float>) vBoxForThickness.getChildren().get(0);
        settings.set(Settings.SettingsParameter.THICKNESS_GYRO_1,
                comboBox.getValue());
        comboBox = (ComboBox<Float>) vBoxForThickness.getChildren().get(1);
        settings.set(Settings.SettingsParameter.THICKNESS_GYRO_2,
                comboBox.getValue());
        comboBox = (ComboBox<Float>) vBoxForThickness.getChildren().get(2);
        settings.set(Settings.SettingsParameter.THICKNESS_PRESSURE,
                comboBox.getValue());
        settings.set(Settings.SettingsParameter.IS_FILTER,
                checkBox_isFilter.isSelected());
        return settings;
    }

    /**
     * Получение настроек для интерфейса.
     *
     * @return Настройки интерфейса.
     */
    private SettingsForDesign getSettingsForDesign() {
        SettingsForDesign settings = new SettingsForDesign();
        settings.set(Settings.SettingsParameter.COM_PORT, comboBoxForCOM_port.getValue());
        settings.set(Settings.SettingsParameter.PATH, labelPath.getText());
        if (textFieldForMeasurementName.getText() != null && !textFieldForMeasurementName.getText().equals(""))
            settings.set(Settings.SettingsParameter.NAME_MEASUREMENT, textFieldForMeasurementName.getText());
        else settings.set(Settings.SettingsParameter.NAME_MEASUREMENT, textFieldForMeasurementName.getPromptText());
        settings.set(Settings.SettingsParameter.GYRO_UNITS, comboBoxForUnitsGyro.getValue());
        settings.set(Settings.SettingsParameter.PRESSURE_UNITS, comboBoxForUnitsPressure.getValue());
        return settings;
    }

    /**
     * Установка коэффициента для единиц измерения гироскопа.
     *
     * @param units Единицы измерения.
     */
    private void setUnitsForGyro(String units) {
        double k = 1;
        if (units.equals(Data.gyroUnits.get(0)))
            k = DataUtil.RAD_TO_SECONDS;
        if (units.equals(Data.gyroUnits.get(1)))
            k = DataUtil.DEGREE_TO_SECONDS;
        if (units.equals(Data.gyroUnits.get(2)))
            k = DataUtil.TURNOVERS_TO_SECONDS;
        if (units.equals(Data.gyroUnits.get(3)))
            k = DataUtil.HERTZ;
        DataUtil.setGyro_K(k);
    }

    /**
     * Установка коэффициента для единиц измерения давления.
     *
     * @param units Единицы измерения.
     */
    private void setUnitsForPressure(String units) {
        double k = 1;
        if (units.equals(Data.pressureUnits.get(0)))
            k = DataUtil.PASCAL;
        if (units.equals(Data.pressureUnits.get(1)))
            k = DataUtil.MEGAPASCAL;
        if (units.equals(Data.pressureUnits.get(2)))
            k = DataUtil.ATM;
        DataUtil.setPressure_K(k);
    }

    /**
     * Конвертация цвета из JavaFX в AWT.
     *
     * @param color Цвет JavaFX.
     * @return Цвет AWT.
     */
    private javafx.scene.paint.Color awtColorConvert(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int alpha = color.getAlpha();

        double javafxRed = red / 255.0;
        double javafxGreen = green / 255.0;
        double javafxBlue = blue / 255.0;
        double javafxAlpha = alpha / 255.0;

        return new javafx.scene.paint.Color(javafxRed, javafxGreen, javafxBlue, javafxAlpha);
    }

    /**
     * Конвертация цвета из AWT в JavaFX.
     *
     * @param color Цвет AWT.
     * @return Цвет JavaFX.
     */
    private Color javafxColorConvert(javafx.scene.paint.Color color) {
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);
        int alpha = (int) (color.getOpacity() * 255);
        return new Color(red, green, blue, alpha);
    }

    /**
     * Метод для закрытия окна настроек.
     *
     * @param event Событие закрытия.
     */
    @FXML
    void cancel(ActionEvent event) {
        comboBoxForCOM_port.getScene().getWindow().hide();
    }

    /**
     * Метод для выбора пути к папке.
     *
     * @param event Событие выбора пути.
     */
    @FXML
    void choosePath(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите папку");
        File selectedDirectory = directoryChooser.showDialog(labelPath.getScene().getWindow());
        labelPath.setText(selectedDirectory.getAbsolutePath());
    }

    /**
     * Метод для сохранения настроек.
     *
     * @param event Событие сохранения.
     */
    @FXML
    void save(ActionEvent event) {
        SettingsForChart settingsForChart = getSettingsForChart();
        SettingsForDesign settingsForDesign = getSettingsForDesign();
        saveSettings(settingsForChart, settingsForDesign);
        comboBoxForCOM_port.getScene().getWindow().hide();
    }

    /**
     * Метод для сброса настроек на значения по умолчанию.
     *
     * @param event Событие сброса.
     */
    @FXML
    void saveDefault(ActionEvent event) {
        saveSettings(new SettingsForChart(), new SettingsForDesign());
        setSettings();
    }
}
