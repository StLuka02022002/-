package sample.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.*;
import sample.arduino.Arduino;
import sample.arduino.ReadData;
import sample.cell.ListCellMeasurement;
import sample.cell.MeasurementCell;
import sample.cell.ViewCell;
import sample.data.DataMeasurement;
import sample.data.DataTestMeasurement;
import sample.data.DataUtil;
import sample.data.Measurement;
import sample.file.FileUtil;
import sample.settings.Settings;
import sample.settings.SettingsForChart;
import sample.settings.SettingsForDesign;
import sample.settings.SettingsUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    @FXML
    private AnchorPane anchorPaneForChart;

    @FXML
    private AnchorPane allAnchorPane;

    @FXML
    private HBox hBoxForChart;

    @FXML
    private ImageView im1;

    @FXML
    private ImageView im2;

    @FXML
    private VBox vBoxForButton;

    @FXML
    private Button connectionButton;

    @FXML
    private Button startReadButton;

    @FXML
    private Button newMeasurementButton;

    @FXML
    private Button saveButton;

    @FXML
    private ListView listViewForMeasurement;

    @FXML
    private Label labelForProcessing;

    @FXML
    private ProgressBar progressBarProcessing;

    @FXML
    private HBox sensor;

    @FXML
    private Menu menuPR;

    @FXML
    private VBox vBoxForNumber;

    @FXML
    private VBox vBoxForIndicatorTest;

    @FXML
    private ComboBox<String> comboBoxForCOM_port;

    @FXML
    private Label labelForSee;

    @FXML
    private MenuItem menuItemConnect;

    @FXML
    private MenuItem menuItemStartRead;

    @FXML
    private MenuItem menuItemGuide;

    Arduino arduino;
    ReadData arduinoRead;
    Chart chart;
    SwingNode swingNode;
    Timeline colorAnimationForCOM_port;
    Timer timer;

    public void initialize() {
        arduino = new Arduino();
        chart = new Chart();
        arduinoRead = new ReadData(arduino);
        swingNode = new SwingNode();
        colorAnimationForCOM_port = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(comboBoxForCOM_port.styleProperty(), "")),
                new KeyFrame(Duration.seconds(1), new KeyValue(comboBoxForCOM_port.styleProperty(), "-fx-background-color: red;")),
                new KeyFrame(Duration.seconds(2), new KeyValue(comboBoxForCOM_port.styleProperty(), ""))
        );

        setElement();
        addActive();
        setStyle();
        connection();
    }

    public void setElement() {
        SettingsForDesign settingsForDesign = (SettingsForDesign) SettingsUtil.getSettings(SettingsUtil.SETTINGS_FOR_DESIGN);
        if (settingsForDesign == null)
            settingsForDesign = new SettingsForDesign();

        SettingsForChart settingsForChart = (SettingsForChart) SettingsUtil.getSettings(SettingsUtil.SETTINGS_FOR_CHART);
        if (settingsForChart == null)
            settingsForChart = new SettingsForChart();

        ObservableList<String> portList = FXCollections.observableArrayList(Arduino.listAvailablePortNames());
        comboBoxForCOM_port.setItems(portList);
        colorAnimationForCOM_port.setCycleCount(Timeline.INDEFINITE);

        if (portList.size() != 0) {
            comboBoxForCOM_port.setValue(portList.get(0));
            if (portList.size() > 1) {
                for (int i = 0; i < portList.size(); i++)
                    if (settingsForDesign.get(Settings.SettingsParameter.COM_PORT) == portList.get(i)) {
                        comboBoxForCOM_port.setValue(portList.get(i));
                        break;
                    }
            }
        } else {
            comboBoxForCOM_port.setValue("Не найдено!");
            colorAnimationForCOM_port.play();
        }
        String units = (String) settingsForDesign.get(Settings.SettingsParameter.GYRO_UNITS);
        double k = 1;
        if (units.equals(Data.gyroUnits.get(0)))
            k = DataUtil.RAD_TO_SECONDS;
        if (units.equals(Data.gyroUnits.get(1)))
            k = DataUtil.DEGREE_TO_SECONDS;
        if (units.equals(Data.gyroUnits.get(2)))
            k = DataUtil.TURNOVERS_TO_SECONDS;
        if (units.equals(Data.gyroUnits.get(3)))
            k = DataUtil.HERTZ;
        DataUtil.setGyro_K(k);

        units = (String) settingsForDesign.get(Settings.SettingsParameter.PRESSURE_UNITS);
        k = 1;
        if (units.equals(Data.pressureUnits.get(0)))
            k = DataUtil.PASCAL;
        if (units.equals(Data.pressureUnits.get(1)))
            k = DataUtil.MEGAPASCAL;
        if (units.equals(Data.pressureUnits.get(2)))
            k = DataUtil.ATM;
        DataUtil.setPressure_K(k);

        arduino.setComPort(comboBoxForCOM_port.getValue());
        chart.setSettings(settingsForChart);
        chart.addSerial(Chart.GYRO1_DATA);
        chart.addSerial(Chart.GYRO2_DATA);
        chart.addSerial(Chart.PRESSURE_DATA);
        listViewForMeasurement.setCellFactory(param -> new ListCellMeasurement());
        listViewForMeasurement.getItems().add(new MeasurementCell(new Measurement()));
        listViewForMeasurement.getSelectionModel().selectNext();
        swingNode.setContent(chart.getPanel());
        anchorPaneForChart.getChildren().add(0, swingNode);
        AnchorPane.setTopAnchor(swingNode, 0.0);
        AnchorPane.setBottomAnchor(swingNode, 0.0);
        AnchorPane.setLeftAnchor(swingNode, 0.0);
        AnchorPane.setRightAnchor(swingNode, 0.0);
    }

    public void setStyle() {
        sensor.getStylesheets().add(Main.class.getResource("styles/sensor.css").toExternalForm());
        hBoxForChart.getStylesheets().add(Main.class.getResource("styles/chartCheckBox.css").toExternalForm());
        vBoxForButton.getStylesheets().add(Main.class.getResource("styles/buttonStyle.css").toExternalForm());
        listViewForMeasurement.getStylesheets().add(Main.class.getResource("styles/list.css").toExternalForm());
        anchorPaneForChart.getStylesheets().add((Main.class.getResource("styles/chartSwingNode.css").toExternalForm()));
        allAnchorPane.getStylesheets().add((Main.class.getResource("styles/hvBox.css").toExternalForm()));

        im1.setImage(Data.closeImage);
        im2.setImage(Data.startImage);

        int S = 60;
        int R = S / 2;

        Circle clip1 = new Circle(R); // Радиус круга
        clip1.setCenterX(R);
        clip1.setCenterY(R);

        Circle clip2 = new Circle(R); // Радиус круга
        clip2.setCenterX(R);
        clip2.setCenterY(R);

        im1.setClip(clip1);
        im2.setClip(clip2);
        progressBarProcessing.setProgress(0);
    }

    public void addActive() {
        comboBoxForCOM_port.setOnAction(actionEvent -> {
            if (arduinoRead.isRead())
                return;
            colorAnimationForCOM_port.stop();
            comboBoxForCOM_port.setStyle("");
            //Остановка при расчёте
            String com = comboBoxForCOM_port.getValue();
            if (!arduino.isOpen() && com != null && !com.equals(""))
                arduino.setComPort(com);

        });
        comboBoxForCOM_port.setOnMouseClicked(actionEvent -> {
            if (arduinoRead.isRead())
                return;
            ObservableList<String> portList = FXCollections.observableArrayList(Arduino.listAvailablePortNames());
            comboBoxForCOM_port.setItems(portList);
            if (portList.size() == 0) {
                comboBoxForCOM_port.setValue(Data.DO_NOT_FOUND);
                colorAnimationForCOM_port.play();
            }
            if (!(comboBoxForCOM_port.getValue().equals("") ||
                    comboBoxForCOM_port.getValue() == null ||
                    comboBoxForCOM_port.getValue().equals(Data.DO_NOT_FOUND))) {
                colorAnimationForCOM_port.stop();
                comboBoxForCOM_port.setStyle("");
            }

        });

        for (int i = 0; i < hBoxForChart.getChildren().size(); i++) {
            CheckBox checkBox = (CheckBox) hBoxForChart.getChildren().get(i);
            int finalI = i;
            checkBox.setOnAction(event -> {
                switch (finalI) {
                    case 0:
                        if (checkBox.isSelected()) {
                            chart.addSerial(Chart.GYRO1_DATA);
                        } else
                            chart.deleteSerial(Chart.GYRO1_DATA);
                        break;
                    case 1:
                        if (checkBox.isSelected()) {
                            chart.addSerial(Chart.GYRO2_DATA);
                        } else
                            chart.deleteSerial(Chart.GYRO2_DATA);
                        break;
                    case 2:
                        if (checkBox.isSelected()) {
                            chart.addSerial(Chart.PRESSURE_DATA);
                        } else
                            chart.deleteSerial(Chart.PRESSURE_DATA);
                        break;

                }
            });
        }

        menuItemGuide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                openNote();
            }
        });

        listViewForMeasurement.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (arduinoRead.isRead())
                return;
            chart.clearChart();
            chart.addMeasurement(((MeasurementCell) newValue).getMeasurement());

        });
    }

    public void viewTest(DataTestMeasurement testMeasurement) {
        for (int i = 0; i < vBoxForIndicatorTest.getChildren().size(); i++) {
            Circle circle = (Circle) vBoxForIndicatorTest.getChildren().get(i);
            switch (i) {
                case 0:
                    if (testMeasurement.testGyro_1 == 1)
                        circle.setFill(Color.GREEN);
                    else
                        circle.setFill(Color.RED);
                    break;
                case 1:
                    if (testMeasurement.testGyro_2 == 1)
                        circle.setFill(Color.GREEN);
                    else
                        circle.setFill(Color.RED);
                    break;
                case 2:
                    if (testMeasurement.testPressure >= 1)
                        circle.setFill(Color.GREEN);
                    else
                        circle.setFill(Color.RED);
                    break;
            }
        }
    }

    private void viewNumber(DataMeasurement dataMeasurement) {
        for (int i = 0; i < vBoxForNumber.getChildren().size(); i++) {
            Label label = (Label) vBoxForNumber.getChildren().get(i);
            switch (i) {
                case 0:
                    label.setText(String.format("%.4f", dataMeasurement.gyro_1));
                    break;
                case 1:
                    label.setText(String.format("%.4f", dataMeasurement.gyro_2));
                    break;
                case 2:
                    label.setText(String.format("%.4f", dataMeasurement.pressure));
                    break;
            }
        }
    }

    @FXML
    public void connection() {
        if (arduino.isOpen()) {
            if (arduino.close()) {
                connectionButton.setText(Data.CONNECTION);
                menuItemConnect.setText(Data.CONNECTION);
                im1.setImage(Data.closeImage);
                waitConnected(false);
            }
        } else {
            connectionButton.setText(Data.CONNECTION);
            labelForSee.setText(Data.DEVICE_DISCONNECTION);
            if (arduino.open()) {
                waitConnected(true);
                connectionButton.setText(Data.DISCONNECTION);
                menuItemConnect.setText(Data.DISCONNECTION);
                im1.setImage(Data.openImage);
            }
        }
    }

    public void waitConnected(boolean connection) {
        if (connection)
            new Thread(() -> {
                try {
                    Platform.runLater(() -> {
                        labelForSee.setText(Data.DEVICE_COMPOUND);
                    });

                    Thread.sleep(600);
                    Platform.runLater(() -> {
                        labelForSee.setText(Data.DEVICE_DO_CONNECTION);
                    });
                    Thread.sleep(500);
                    Platform.runLater(() -> {
                        labelForSee.setText(Data.DEVICE_CONNECTION);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        else
            new Thread(() -> {
                try {
                    Thread.sleep(600);
                    Platform.runLater(() -> {
                        labelForSee.setText(Data.DEVICE_DO_DISCONNECTION);
                    });
                    Thread.sleep(500);
                    Platform.runLater(() -> {
                        labelForSee.setText(Data.DEVICE_DISCONNECTION);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
    }

    @FXML
    public synchronized void startRead() {
        if (arduino.isOpen())
            if (arduinoRead.isRead()) {
                arduinoRead.stopRead();
                startReadButton.setText(Data.START_READ);
                menuItemStartRead.setText(Data.START_READ);
                im2.setImage(Data.startImage);
                progressBarProcessing.setProgress(0);
                processing(false);
            } else {
                /////////////Возможно без переподклюяения
                //arduino.open();
                arduinoRead.startRead();
                startReadButton.setText(Data.STOP_READ);
                menuItemStartRead.setText(Data.STOP_READ);
                new Thread(() -> {
                    while (arduinoRead.isRead()) {
                        arduinoRead.read();
                        if (arduinoRead.size() > 0) {
                            DataMeasurement dataMeasurement = arduinoRead.getData();
                            chart.addData(dataMeasurement);
                            MeasurementCell measurementCell = (MeasurementCell) listViewForMeasurement.getSelectionModel().getSelectedItem();
                            Measurement measurement = measurementCell.getMeasurement();
                            measurement.add(dataMeasurement);
                            Platform.runLater(() -> {
                                viewNumber(dataMeasurement);
                                viewTest(arduinoRead.getTest());
                            });
                        }
                    }

                }).start();
                im2.setImage(Data.readImage);
                progressBarProcessing.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                processing(true);
            }

    }

    public void processing(boolean start) {
        if (start) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        String text = labelForProcessing.getText();
                        text += '.';
                        if (text.length() > Data.PROCESSING.length() + 3) {
                            text = Data.PROCESSING;
                        }
                        labelForProcessing.setText(text);
                    });
                }
            }, 300, 300);

        } else {
            if (timer != null)
                timer.cancel();
            labelForProcessing.setText(Data.PROCESSING);
        }
    }

    @FXML
    public void newMeasurement() {
        if (arduinoRead.isRead())
            return;
        listViewForMeasurement.getItems().add(new MeasurementCell(new Measurement()));
        listViewForMeasurement.getSelectionModel().selectNext();

    }

    @FXML
    public void save() {
        if (arduinoRead.isRead())
            return;
        ObservableList<ViewCell> saveMeasurement = FXCollections.observableArrayList();
        for (Object measurementCell :
                listViewForMeasurement.getItems()) {
            String isSave = ((MeasurementCell) measurementCell).getSave().getText();
            if (isSave.equals(MeasurementCell.SAVE_NOT)) {
                saveMeasurement.add((ViewCell) measurementCell);
            }
        }
        Data.setMeasurement(saveMeasurement);
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("fxml/save.fxml"));
            stage.setTitle("Сохранить");
            stage.setScene(new Scene(root));
            root.getStylesheets().add(Main.class.getResource("styles/save.css").toExternalForm());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
            List<File> saveFiles = Data.getSaveFiles();
            if (saveFiles != null) {
                for (int i = 0; i < saveFiles.size(); i++) {
                    MeasurementCell measurementCell = (MeasurementCell) listViewForMeasurement.getItems().get(i);
                    measurementCell.getPath().setText(saveFiles.get(i).getAbsolutePath());
                    measurementCell.getSave().setText(MeasurementCell.SAVE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void downloadMeasurement() {
        SettingsForDesign settingsForDesign = (SettingsForDesign) SettingsUtil.getSettings(SettingsUtil.SETTINGS_FOR_DESIGN);
        FileChooser fileChooser = new FileChooser();
        if (settingsForDesign != null) {
            String fileInit = (String) settingsForDesign.get(Settings.SettingsParameter.PATH);
            if (fileInit != null && !fileInit.equals(""))
                fileChooser.setInitialDirectory(new File(fileInit));
        }
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("Файлы Excel (*.xlsx)", "*.xlsx"),
                new FileChooser.ExtensionFilter("Бин файлы (*.bin)", "*.bin")
        );

        File selectedFile = fileChooser.showOpenDialog(listViewForMeasurement.getScene().getWindow());
        if (selectedFile == null)
            return;
        String fileName = selectedFile.getName();
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            //Без расширения
            return;
        }
        String explain = fileName.substring(lastDotIndex);
        Measurement measurement;
        switch (explain) {
            case Data.TXT:
                measurement = FileUtil.readTextFile(selectedFile);
                break;
            case Data.XLSX:
                measurement = FileUtil.readExelFile(selectedFile);
                break;
            case Data.BIN:
                measurement = FileUtil.readBinFile(selectedFile);
                break;
            default:
                //Не верное расширение
                measurement = new Measurement();
        }
        MeasurementCell measurementCell = new MeasurementCell(measurement);
        measurementCell.getPath().setText(selectedFile.getAbsolutePath());
        measurementCell.getSave().setText(MeasurementCell.SAVE);
        listViewForMeasurement.getItems().add(measurementCell);
        listViewForMeasurement.getSelectionModel().select(measurementCell);
    }

    @FXML
    public void openSettings() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("fxml/settings.fxml"));
            stage.setTitle("Настройки");
            stage.setScene(new Scene(root));
            root.getStylesheets().add(Main.class.getResource("styles/settings.css").toExternalForm());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openNote() {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource("fxml/note.fxml"));
            stage.setTitle("Примечание");
            root.getStylesheets().add(Main.class.getResource("styles/note.css").toExternalForm());
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openAbout() {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource("fxml/about.fxml"));
            stage.setTitle("О программе");
            root.getStylesheets().add(Main.class.getResource("styles/about.css").toExternalForm());
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exit() {
        listViewForMeasurement.getScene().getWindow().hide();
        Platform.exit();
    }


}
