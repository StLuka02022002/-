package sample.file;

import java.io.*;

/**
 * Утилита SaveSerializable предоставляет методы для сериализации (сохранения в бинарный файл) и десериализации (восстановления из бинарного файла) объектов.
 */
public class SaveSerializable {

    /**
     * Сохраняет объект в бинарный файл.
     *
     * @param object Объект для сериализации
     * @param file   Файл, в который нужно сохранить объект
     * @throws IOException Исключение, возникающее при ошибках ввода-вывода
     */
    public static void save(Object object, File file) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        outputStream.writeObject(object);
    }

    /**
     * Восстанавливает объект из бинарного файла.
     *
     * @param file Файл, из которого нужно восстановить объект
     * @return Восстановленный объект
     * @throws IOException            Исключение, возникающее при ошибках ввода-вывода
     * @throws ClassNotFoundException Исключение, возникающее, если класс объекта не найден
     */
    public static Object get(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        return inputStream.readObject();
    }
}
