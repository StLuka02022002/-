package sample.arduino;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Scanner;

/////////Протестировать
public class Arduino {

    //Констатнты скорости передачи данных
    public final static int SPEED_300 = 300;
    public final static int SPEED_1200 = 1200;
    public final static int SPEED_2400 = 2400;
    public final static int SPEED_4800 = 4800;
    public final static int SPEED_9600 = 9600;
    public final static int SPEED_14400 = 14400;
    public final static int SPEED_19200 = 19200;
    public final static int SPEED_28800 = 28800;
    public final static int SPEED_38400 = 38400;
    public final static int SPEED_57600 = 57600;
    public final static int SPEED_115200 = 115200;

    private SerialPort arduinoPort;
    private String comPort;
    private int serialSpeed;

    private Scanner scanner;

    public Arduino() {
        this.comPort = "COM3";
        this.serialSpeed = SPEED_9600;
        this.arduinoPort = SerialPort.getCommPort(comPort); //Добавить реакцию на отсутствие порта
        arduinoPort.setComPortParameters(serialSpeed, 8,
                SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        arduinoPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
    }

    public Arduino(String comPort, int serialSpeed) {
        this.comPort = comPort;
        this.serialSpeed = serialSpeed;
        this.arduinoPort = SerialPort.getCommPort(comPort); //Добавить реакцию на отсутствие порта
        arduinoPort.setComPortParameters(serialSpeed, 8,
                SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        arduinoPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
    }

    public Arduino(SerialPort arduinoPort) {
        this.arduinoPort = arduinoPort;
    }

    public SerialPort getArduinoPort() {
        return arduinoPort;
    }

    public void setArduinoPort(SerialPort arduinoPort) {
        this.arduinoPort = arduinoPort;
    }

    public String getComPort() {
        return comPort;
    }

    public void setComPort(String comPort) {
        this.comPort = comPort;
        //Добавить реакцию на отсутствие порта
        this.arduinoPort = SerialPort.getCommPort(comPort);
    }

    public int getSerialSpeed() {
        return serialSpeed;
    }

    public void setSerialSpeed(int serialSpeed) {
        this.serialSpeed = serialSpeed;
        arduinoPort.setComPortParameters(serialSpeed, 8,
                SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
    }

    //Вернуть все доступные и видимые порты
    public static String[] listAvailablePortNames() {
        SerialPort[] ports = SerialPort.getCommPorts();
        String[] portNames = new String[ports.length];

        for (int i = 0; i < ports.length; i++) {
            portNames[i] = ports[i].getSystemPortName();
        }

        return portNames;
    }

    public boolean open() {
        boolean connection = arduinoPort.openPort();
        if (connection)
            scanner = new Scanner(arduinoPort.getInputStream());
        return connection;
    }

    public boolean close() {
        boolean disconnection = arduinoPort.closePort();
        if (disconnection)
            scanner = null;
        return disconnection;
    }

    public boolean isOpen() {
        return arduinoPort.isOpen();
    }

    public boolean isRead() {
        try {
            return arduinoPort.getInputStream().available() > 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Чтение данных
    public Integer readInt() {
        Integer i = null;
        if (scanner != null && scanner.hasNextInt()) {
            i = scanner.nextInt();
        }
        return i;
    }

    public Integer readInteger() {
        Integer i = null;
        if (scanner != null && scanner.hasNextInt())
            i = scanner.nextInt();
        return i;
    }

    public String readString() {

        String s = "";
        try {
            InputStream inputStream = arduinoPort.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            char c;

            while ((c = (char) inputStream.read()) != '\n') {
                stringBuilder.append(c);
            }

            s = stringBuilder.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s;
    }

    public byte read() {
        byte b = -1;
        if (scanner != null && scanner.hasNextByte())
            b = scanner.nextByte();
        return b;
    }

    //Запись данных
    public void write(String send) {
        byte[] sendBytes = send.getBytes();//проверить на кодировку
        arduinoPort.writeBytes(sendBytes, sendBytes.length);
    }

    public void writeInt(int send) {
        byte[] sendBytes = ByteBuffer.allocate(4).putInt(send).array();
        arduinoPort.writeBytes(sendBytes, sendBytes.length);
    }

    public void write(byte send) {
        byte[] sendBytes = {send};
        arduinoPort.writeBytes(sendBytes, 1);
    }

    public void writeDouble(double send) {
        byte[] sendBytes = ByteBuffer.allocate(8).putDouble(send).array();
        arduinoPort.writeBytes(sendBytes, sendBytes.length);
    }


}
