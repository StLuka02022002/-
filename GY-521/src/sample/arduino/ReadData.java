package sample.arduino;

import sample.data.DataMeasurement;
import sample.data.DataTestMeasurement;
import sample.data.DataUtil;
import sample.data.Measurement;

import java.util.LinkedList;
import java.util.Queue;

public class ReadData {

    //Команда посылаемые arduino
    public final static byte WRITE_COMMAND = 0x6;
    public final static byte STOP_COMMAND = 0x8;

    public final static String SEPARATOR = " ";
    public final static int SIZE_DATA = 7;

    private Arduino arduino;
    private Queue<DataMeasurement> measurement = new LinkedList<>();
    private Queue<DataTestMeasurement> testMeasurement = new LinkedList<>();
    private boolean isRead = false;

    public Arduino getArduino() {
        return arduino;
    }

    public void setArduino(Arduino arduino) {
        this.arduino = arduino;
    }

    public ReadData(Arduino arduino) {
        this.arduino = arduino;
    }

    public void startRead() {
        arduino.write(WRITE_COMMAND);                               //Отправление команды на начало запись
        if (arduino.isOpen()) {
            measurement = new LinkedList<>();
            testMeasurement = new LinkedList<>();
            isRead = true;
            //new Thread(new ReadRunnable()).start();
        }
    }

    //Чтение данных
    public void read() {
        String s;
        String[] str;
        if (arduino.isRead()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            s = arduino.readString();
            if (!s.equals("")) {
                str = s.split(SEPARATOR);
                if (str.length == SIZE_DATA) {
                    try {
                        DataMeasurement data = new DataMeasurement(
                                Integer.parseInt(str[0]),
                                Integer.parseInt(str[1]),
                                Integer.parseInt(str[2]),
                                Integer.parseInt(str[3])
                        );
                        measurement.offer(data);
                        DataTestMeasurement test = new DataTestMeasurement(
                                Integer.parseInt(str[4]),
                                Integer.parseInt(str[5]),
                                Integer.parseInt(str[6])
                        );
                        testMeasurement.offer(test);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class ReadRunnable implements Runnable {

        @Override
        public void run() {
            String s;
            String[] str;
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (isRead) {
                if (arduino.isRead()) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    s = arduino.readString();
                    if (!s.equals("")) {
                        str = s.split(SEPARATOR);
                        if (str.length == SIZE_DATA) {
                            try {
                                DataMeasurement data = new DataMeasurement(
                                        Integer.parseInt(str[0]),
                                        Integer.parseInt(str[1]),
                                        Integer.parseInt(str[2]),
                                        Integer.parseInt(str[3])
                                );
                                measurement.offer(data);
                                DataTestMeasurement test = new DataTestMeasurement(
                                        Integer.parseInt(str[4]),
                                        Integer.parseInt(str[5]),
                                        Integer.parseInt(str[6])
                                );
                                testMeasurement.offer(test);
                                System.out.println(measurement);
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                }

            }
        }
    }

    //Возвращение считаннных данных
    public DataMeasurement getData() {
        if (measurement.isEmpty())
            return null;
        return DataUtil.createDataMeasurement(measurement.poll());
    }

    //Возвращение состояния датчиков
    public DataTestMeasurement getTest() {
        if (testMeasurement.isEmpty())
            return null;
        return testMeasurement.poll();
    }

    public int size() {
        return Math.min(measurement.size(), testMeasurement.size());
    }

    public int stopRead() {
        isRead = false;
        arduino.write(STOP_COMMAND);                                    //Отправление команды на остановки записи
        return Math.max(measurement.size(), testMeasurement.size());
    }

    public boolean isRead() {
        return isRead;
    }
}
