package sample.file;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.data.DataMeasurement;
import sample.data.Measurement;

import java.io.*;
import java.util.List;

public class FileUtil {

    public final static String NOTE_FILE = "src/sample/file/Note.txt";
    public final static String ABOUT_FILE = "src/sample/file/About.txt";

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        FileUtil.stage = stage;
    }

    public static void saveTextFile(List<DataMeasurement> measurements) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить файл");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);
        saveTextFile(measurements, file);
    }

    public static void saveTextFile(List<DataMeasurement> measurements, File saveFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            // Запись заголовков столбцов
            writer.write("Gyro1\tGyro2\tPressure\tTime\n");

            for (DataMeasurement measurement : measurements) {
                // Запись данных из объекта DataMeasurement в текстовый файл
                writer.write(measurement.gyro_1 + "\t" + measurement.gyro_2 + "\t" +
                        measurement.pressure + "\t" + measurement.time + "\n");
            }
            //System.out.println("Данные успешно записаны в файл: " + file);
        } catch (IOException e) {
            System.err.println("Ошибка при записи данных в файл: " + e.getMessage());
        }
    }

    public static Measurement readTextFile(File inputFile) {
        Measurement measurements = new Measurement();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");

                if (parts.length == 4) {
                    double gyro1 = Double.parseDouble(parts[0]);
                    double gyro2 = Double.parseDouble(parts[1]);
                    double pressure = Double.parseDouble(parts[2]);
                    long time = Long.parseLong(parts[3]);

                    DataMeasurement measurement = new DataMeasurement(gyro1, gyro2, pressure, time);
                    measurements.add(measurement);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении данных из файла: " + e.getMessage());
        }

        return measurements;
    }

    public void saveExelFile(List<DataMeasurement> measurements) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить файл");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Файлы Excel (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);
        saveExelFile(measurements, file);
    }

    public static void saveExelFile(List<DataMeasurement> measurements, File saveFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            // Запись заголовков столбцов
            writer.write("Gyro1;Gyro2;Pressure;Time\n");

            for (DataMeasurement measurement : measurements) {
                // Запись данных из объекта DataMeasurement в текстовый файл
                writer.write(measurement.gyro_1 + ";" + measurement.gyro_2 + ";" +
                        measurement.pressure + ";" + measurement.time + ";");
            }
            //System.out.println("Данные успешно записаны в файл: " + file);
        } catch (IOException e) {
            System.err.println("Ошибка при записи данных в файл: " + e.getMessage());
        }
    }

    public static Measurement readExelFile(File inputFile) {
        Measurement measurements = new Measurement();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length == 4) {
                    double gyro1 = Double.parseDouble(parts[0]);
                    double gyro2 = Double.parseDouble(parts[1]);
                    double pressure = Double.parseDouble(parts[2]);
                    long time = Long.parseLong(parts[3]);

                    DataMeasurement measurement = new DataMeasurement(gyro1, gyro2, pressure, time);
                    measurements.add(measurement);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении данных из файла: " + e.getMessage());
        }

        return measurements;
    }

    public void saveBinFile(List<DataMeasurement> measurements) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить файл");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Файлы bin (*.bin)", "*.bin");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);
        saveBinFile(measurements, file);
    }

    public static void saveBinFile(List<DataMeasurement> measurements, File saveFile) {
        try {
            SaveSerializable.save(measurements, saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Measurement readBinFile(File inputFile) {
        Measurement measurement = null;
        try {
            measurement = (Measurement) SaveSerializable.get(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return measurement;
    }

    public static String getText(File read) {
        try (FileInputStream fileInputStream = new FileInputStream(read);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
             BufferedReader reader = new BufferedReader(inputStreamReader)) {

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
