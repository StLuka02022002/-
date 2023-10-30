package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * Описание:
 * Этот класс является точкой входа в приложение "ofJava". Он загружает
 * пользовательский интерфейс из FXML файла, устанавливает заголовок окна и
 * иконку приложения, а затем отображает главное окно.
 * <p>
 * Программа разрабатывается с использованием JavaFX для создания
 * графического интерфейса пользователя.
 * Применяется при проведении лабораторной работы по испытанию толкателя.
 * <p>
 * Авторские права (с) 2023 Лукьяненко С.Н.
 */
public class Main extends Application {

    private static final String TITLE = "ofJava"; // Заголовок окна приложения

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));
        primaryStage.setTitle(TITLE);
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("image/icon.jpg").toURI())));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setOnHidden(windowEvent -> {
            Platform.exit();
            primaryStage.hide();
        });
        primaryStage.show();
    }

    static void main(String[] args) {
        launch(args);
    }
}
