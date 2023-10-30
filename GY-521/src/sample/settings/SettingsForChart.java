package sample.settings;

import java.awt.Color;

/**
 * Класс SettingsForChart предоставляет настройки, связанные с графиками, в приложении.
 * Этот класс наследуется от абстрактного класса Settings и предоставляет методы
 * для установки и получения различных параметров настроек для графиков.
 */
public class SettingsForChart extends Settings {

    // Поля для хранения параметров настроек
    private Color colorGyro_1 = Color.BLUE;
    private Color colorGyro_2 = Color.GREEN;
    private Color colorPressure = Color.MAGENTA;
    private float thicknessGyro_1 = 1.0f;
    private float thicknessGyro_2 = 1.0f;
    private float thicknessPressure = 1.0f;
    private boolean isFilter = false;

    /**
     * Метод для установки параметров настроек.
     * @param parameter Параметр настроек, который нужно установить.
     * @param object Значение параметра, которое нужно установить.
     */
    @Override
    public void set(SettingsParameter parameter, Object object) {
        switch (parameter) {
            case COLOR_GYRO_1:
                colorGyro_1 = (Color) object;
                break;
            case COLOR_GYRO_2:
                colorGyro_2 = (Color) object;
                break;
            case COLOR_PRESSURE:
                colorPressure = (Color) object;
                break;
            case THICKNESS_GYRO_1:
                thicknessGyro_1 = (float) object;
                break;
            case THICKNESS_GYRO_2:
                thicknessGyro_2 = (float) object;
                break;
            case THICKNESS_PRESSURE:
                thicknessPressure = (float) object;
                break;
            case IS_FILTER:
                isFilter = (boolean) object;
                break;
            default:
                // Обработка неизвестных параметров - возможно, стоит бросить исключение.
                break;
        }
    }

    /**
     * Метод для получения параметров настроек.
     * @param parameter Параметр настроек, который нужно получить.
     * @return Значение параметра настроек.
     */
    @Override
    public Object get(SettingsParameter parameter) {
        switch (parameter) {
            case COLOR_GYRO_1:
                return colorGyro_1;
            case COLOR_GYRO_2:
                return colorGyro_2;
            case COLOR_PRESSURE:
                return colorPressure;
            case THICKNESS_GYRO_1:
                return thicknessGyro_1;
            case THICKNESS_GYRO_2:
                return thicknessGyro_2;
            case THICKNESS_PRESSURE:
                return thicknessPressure;
            case IS_FILTER:
                return isFilter;
            default:
                // Обработка неизвестных параметров - возвращает null, но можно рассмотреть бросок исключения.
                return null;
        }
    }
}
