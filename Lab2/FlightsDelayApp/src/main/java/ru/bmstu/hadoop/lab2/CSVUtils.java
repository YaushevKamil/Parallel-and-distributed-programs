package ru.bmstu.hadoop.lab2;

class CSVUtils {
    static final int INT_ZERO = 0;
    static final float FLOAT_ZERO = 0.0f;

    static final int TYPE_AIRPORT = 0;
    static final int TYPE_FLIGHT = 1;

    private static final int ORIGIN_AIRPORT_ID_COLUMN = 14;
    private static final int DELAY_TIME_COLUMN = 18;

    private static final int AIRPORT_ID_COLUMN = 0;
    private static final int AIRPORT_NAME_COLUMN = 1;

    private static int strToInt(String numString) {
        return numString.length() > 0 ?
                Integer.parseInt(numString) :
                INT_ZERO;
    }

    static float strToFloat(String numString) {
        return numString.length() > 0 ?
                Float.parseFloat(numString):
                FLOAT_ZERO;
    }

    private String[] data;

    CSVUtils(String raw) {
        data = raw.replaceAll("\"", "").split(",");
    }

    int getAirportId() {
        return strToInt(data[AIRPORT_ID_COLUMN]);
    }

    String getAirportName() {
        return data[AIRPORT_NAME_COLUMN];
    }

    int getOriginAirportId() {
        return strToInt(data[ORIGIN_AIRPORT_ID_COLUMN]);
    }

    String getStringDelayTime() {
        return data[DELAY_TIME_COLUMN];
    }

    float getFloatDelayTime() {
        return strToFloat(getStringDelayTime());
    }
}