package sample.settings;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Абстрактный класс Settings предоставляет основную структуру для хранения настроек приложения.
 */
public abstract class Settings implements Serializable {

    /**
     * Перечисление, определяющее доступные параметры настроек.
     */
    public enum SettingsParameter {
        COM_PORT,           // Порт COM
        COLOR_GYRO_1,       // Цвет для графика гироскопа 1
        COLOR_GYRO_2,       // Цвет для графика гироскопа 2
        COLOR_PRESSURE,     // Цвет для графика давления
        THICKNESS_GYRO_1,   // Толщина линии графика гироскопа 1
        THICKNESS_GYRO_2,   // Толщина линии графика гироскопа 2
        THICKNESS_PRESSURE, // Толщина линии графика давления
        IS_FILTER,          // Включен ли фильтр
        PATH,               // Путь для сохранения данных
        NAME_MEASUREMENT,   // Название измерения
        GYRO_UNITS,         // Единицы измерения для гироскопа
        PRESSURE_UNITS      // Единицы измерения для давления
    }


    /**
     * Устанавливает значение для заданного параметра настроек.
     *
     * @param parameter Параметр настроек, для которого нужно установить значение
     * @param object    Значение, которое нужно установить для параметра
     */
    public void set(SettingsParameter parameter, Object object) {
    }

    /**
     * Получает значение для заданного параметра настроек.
     *
     * @param parameter Параметр настроек, для которого нужно получить значение
     * @return Значение параметра настроек
     */
    public Object get(SettingsParameter parameter) {
        return null;
    }
}
