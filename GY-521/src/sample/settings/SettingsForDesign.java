package sample.settings;

/**
 * Класс SettingsForDesign предоставляет настройки, связанные с дизайном интерфейса, в приложении.
 * Этот класс наследуется от абстрактного класса Settings и предоставляет методы
 * для установки и получения различных параметров настроек для дизайна.
 */
public class SettingsForDesign extends Settings {

    // Поля для хранения параметров настроек дизайна
    private String comPort = "COM3";
    private String path = "";
    private String nameMeasurement = "data";
    private String gyroUnits = "рад/c";
    private String pressureUnits = "Мпа";

    /**
     * Метод для установки параметров настроек.
     * @param parameter Параметр настроек, который нужно установить.
     * @param object Значение параметра, которое нужно установить.
     */
    @Override
    public void set(SettingsParameter parameter, Object object) {
        super.set(parameter, object);
        switch (parameter) {
            case COM_PORT:
                comPort = (String) object;
                break;
            case PATH:
                path = (String) object;
                break;
            case NAME_MEASUREMENT:
                nameMeasurement = (String) object;
                break;
            case GYRO_UNITS:
                gyroUnits = (String) object;
                break;
            case PRESSURE_UNITS:
                pressureUnits = (String) object;
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
            case COM_PORT:
                return comPort;
            case PATH:
                return path;
            case NAME_MEASUREMENT:
                return nameMeasurement;
            case GYRO_UNITS:
                return gyroUnits;
            case PRESSURE_UNITS:
                return pressureUnits;
            default:
                // Обработка неизвестных параметров - возвращает null, но можно рассмотреть бросок исключения.
                return null;
        }
    }
}
