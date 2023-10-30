package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.File;
import java.util.List;

import sample.cell.ViewCell;

public class Data {

    public final static String CONNECTION = "Подключить";
    public final static String DISCONNECTION = "Отключить";
    public final static String DO_NOT_FOUND = "Не найдено!";
    public final static String DEVICE_CONNECTION = "Устройство подключено!";
    public final static String DEVICE_DISCONNECTION = "Устройство отключено!";
    public final static String DEVICE_COMPOUND = "Соединение...";
    public final static String DEVICE_DO_CONNECTION = "Подключение...";
    public final static String DEVICE_DO_DISCONNECTION = "Откючение...";
    public final static String START_READ = "Начать запись";
    public final static String STOP_READ = "Остановить запись";
    public final static String TXT = ".txt";
    public final static String XLSX = ".xlsx";
    public final static String BIN = ".bin";
    public final static String PROCESSING = "Processing";

    public final static Image openImage = new Image(Main.class.getResourceAsStream("image/open.png"));
    public final static Image closeImage = new Image(Main.class.getResourceAsStream("image/close.png"));
    public final static Image startImage = new Image(Main.class.getResourceAsStream("image/start.png"));
    public final static Image readImage = new Image(Main.class.getResourceAsStream("image/read.gif"));

    public final static ObservableList<String> gyroUnits =
            FXCollections.observableArrayList(
                    "рад/с",
                    "град/с",
                    "об/мин",
                    "Гц"
            );
    public final static ObservableList<String> pressureUnits =
            FXCollections.observableArrayList(
                    "Па",
                    "Мпа",
                    "атм"
            );
    public final static ObservableList<String> fileNames =
            FXCollections.observableArrayList(
                    "без рессивера и шпильки",
                    "с рессивером и без шпильки",
                    "без рессивера и со шпилькой",
                    "с рессивером и со шпилькой"
            );
    public final static ObservableList<Float> thickness =
            FXCollections.observableArrayList(
                    1.0f,
                    1.5f,
                    2.0f,
                    2.5f
            );


    private static ObservableList<ViewCell> measurement = null;

    private static List<File> saveFiles = null;

    public static ObservableList<ViewCell> getMeasurement() {
        return measurement;
    }

    public static void setMeasurement(ObservableList<ViewCell> measurement) {
        Data.measurement = measurement;
    }

    public static List<File> getSaveFiles() {
        return saveFiles;
    }

    public static void setSaveFiles(List<File> saveFiles) {
        Data.saveFiles = saveFiles;
    }
}
