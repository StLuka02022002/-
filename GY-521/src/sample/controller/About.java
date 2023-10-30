package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import sample.file.FileUtil;

import java.io.File;

/**
 * Контроллер для отображения информации "О программе".
 */
public class About {

    @FXML
    private TextArea textFieldShow;

    /**
     * Метод инициализации контроллера. Загружает текст из файла "about.txt" и отображает его в textFieldShow.
     */
    public void initialize() {
        File aboutFile = new File(FileUtil.ABOUT_FILE);
        if (aboutFile.exists()) {
            String aboutText = FileUtil.getText(aboutFile);
            textFieldShow.setText(aboutText);
        } else {
            // Файл "about.txt" не существует
        }
        textFieldShow.setEditable(false);
    }

    /**
     * Метод для закрытия окна "О программе".
     */
    @FXML
    void cancel() {
        textFieldShow.getScene().getWindow().hide();
    }
}
