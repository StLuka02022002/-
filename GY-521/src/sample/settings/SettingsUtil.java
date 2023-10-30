package sample.settings;

import sample.file.SaveSerializable;

import java.io.File;
import java.io.IOException;

/**
 * Класс SettingsUtil предоставляет утилитарные методы для работы с настройками в приложении.
 * Этот класс содержит методы для сохранения и загрузки настроек в файлы, а также определяет
 * константы для файлов настроек.
 */
public class SettingsUtil {

    // Константы для файлов настроек
    public final static File SETTINGS_FOR_CHART = new File("src/sample/settings/SETTINGS_FOR_CHART.bin");
    public final static File SETTINGS_FOR_DESIGN = new File("src/sample/settings/SETTINGS_FOR_DESIGN.bin");

    /**
     * Метод для сохранения настроек в файл.
     * @param settings Настройки, которые нужно сохранить.
     * @param fileName Файл, в который нужно сохранить настройки.
     */
    public static void saveSettings(Settings settings, File fileName) {
        try {
            SaveSerializable.save(settings, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для получения настроек из файла.
     * @param fileName Файл, из которого нужно загрузить настройки.
     * @return Объект настроек, загруженный из файла, или null, если произошла ошибка.
     */
    public static Settings getSettings(File fileName) {
        try {
            return (Settings) SaveSerializable.get(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
