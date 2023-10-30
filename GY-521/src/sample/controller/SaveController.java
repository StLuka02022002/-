package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import sample.Data;
import sample.Main;
import sample.cell.ListCellSaveMeasurement;
import sample.cell.SaveCells;
import sample.cell.ViewCell;
import sample.data.Measurement;
import sample.file.FileUtil;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для управления сохранением измерений в различных форматах.
 */
public class SaveController {

    @FXML
    private ListView<SaveCells> listViewMeasurement;

    /**
     * Метод инициализации контроллера. Настройка списка и отображение сохраненных измерений.
     */
    public void initialize() {
        listViewMeasurement.setCellFactory(param -> new ListCellSaveMeasurement());
        List<SaveCells> customItemList = new ArrayList<>();
        if (Data.getMeasurement() != null) {
            for (ViewCell viewCell : Data.getMeasurement()) {
                customItemList.add(new SaveCells(viewCell.getMeasurement()));
            }
        }
        listViewMeasurement.getItems().addAll(customItemList);
        listViewMeasurement.getStylesheets().add(Main.class.getResource("styles/cell.css").toExternalForm());
    }

    /**
     * Метод для закрытия окна сохранения.
     */
    @FXML
    void cancel() {
        listViewMeasurement.getScene().getWindow().hide();
    }

    /**
     * Метод для сохранения измерений в выбранной папке и форматах.
     */
    @FXML
    void save() {
        int i = 1;
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите папку");
        List<File> saveFiles = new ArrayList<>();

        // Показываем диалоговое окно выбора папки
        File selectedDirectory = directoryChooser.showDialog(listViewMeasurement.getScene().getWindow());
        if (selectedDirectory == null)
            return;
        for (SaveCells saveCell : listViewMeasurement.getItems()) {
            String nameFile = saveCell.getName().getText() + " " + i++;
            String expansion = saveCell.getExpansion().getValue();
            expansion = expansion.replace("*", "");
            if (saveCell.getNameFile().getValue() != null)
                nameFile = saveCell.getNameFile().getValue();
            File saveFile = new File(Paths.get(selectedDirectory.toString(), nameFile + expansion).toString());

            //Сохранем данные в нужном формате
            switch (expansion) {
                case Data.TXT:
                    FileUtil.saveTextFile(saveCell.getMeasurement(), saveFile);
                    break;
                case Data.XLSX:
                    FileUtil.saveExelFile(saveCell.getMeasurement(), saveFile);
                    break;
                case Data.BIN:
                    FileUtil.saveBinFile(saveCell.getMeasurement(), saveFile);
                    break;
            }
            saveFiles.add(saveFile);
        }
        Data.setSaveFiles(saveFiles);
        listViewMeasurement.getScene().getWindow().hide();
    }
}
