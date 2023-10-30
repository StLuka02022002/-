package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import sample.file.FileUtil;

import java.io.File;

/**
 * Контроллер для работы с "Примечанием".
 */
public class Note {

    @FXML
    private TextArea textFieldShow;

    /**
     * Метод инициализации контроллера. Загружает текст из файла "note.txt" и отображает его в textFieldShow.
     */
    public void initialize() {
        File noteFile = new File(FileUtil.NOTE_FILE);
        if (noteFile.exists()) {
            String noteText = FileUtil.getText(noteFile);
            textFieldShow.setText(noteText);
        } else {
            // Файл "note.txt" не существует
        }
        textFieldShow.setEditable(false);
    }

    /**
     * Метод для закрытия окна с заметками.
     */
    @FXML
    void cancel() {
        textFieldShow.getScene().getWindow().hide();
    }
}

