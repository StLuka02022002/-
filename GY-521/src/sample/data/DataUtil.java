package sample.data;

public class DataUtil {

    public final static int DEL_MPU = 32768;
    public final static int SCALE_GYRO_RANGE = 250;
    public final static double RAD_TO_SECONDS = (double) SCALE_GYRO_RANGE * Math.PI / 180;
    public final static double DEGREE_TO_SECONDS = SCALE_GYRO_RANGE;
    public final static double TURNOVERS_TO_SECONDS = (double) SCALE_GYRO_RANGE / 60;
    public final static double HERTZ = (double) SCALE_GYRO_RANGE / 360;
    public final static double PASCAL = 1;
    public final static double MEGAPASCAL = PASCAL * 10E-6;
    public final static double ATM = MEGAPASCAL * 9.86923;

    private static double gyro_K = 1;
    private static double pressure_K = 1;

    public static DataMeasurement createDataMeasurement(DataMeasurement data) {
        data.gyro_1 *= gyro_K / DEL_MPU;
        data.gyro_2 *= gyro_K / DEL_MPU;
        data.pressure *= pressure_K;
        return data;
    }

    public static double getGyro_K() {
        return gyro_K;
    }

    public static void setGyro_K(double gyro_K) {
        DataUtil.gyro_K = gyro_K;
    }

    public static double getPressure_K() {
        return pressure_K;
    }

    public static void setPressure_K(double pressure_K) {
        DataUtil.pressure_K = pressure_K;
    }
}
