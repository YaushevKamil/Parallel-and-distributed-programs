package ru.bmstu.hadoop.spark.lab3;

class CSVUtils {
    static final String FLIGHTS_CSV  = "664600583_T_ONTIME_sample.csv";
    static final String AIRPORTS_CSV = "L_AIRPORT_ID.csv";
    static final String OUTPUT_FILE  = "output";

    static final String FLIGHTS_FIRST_COLUMN  = "YEAR";
    static final String AIRPORTS_FIRST_COLUMN = "Code";

    static final int  INT_ZERO = 0;
    static final int  INT_COUNT_ZERO = 0;
    static final int  INT_COUNT_ONE = 1;
    static final float FLOAT_ZERO = 0.0f;
    static final float FLOAT_HUNDRED_PERCENT = 100.0f;

    private static final int ORIGIN_AIRPORT_ID_COLUMN = 11;
    private static final int DEST_AIRPORT_ID_COLUMN   = 14;
    private static final int DELAY_TIME_COLUMN        = 18;
    private static final int IS_CANCELLED_COLUMN      = 19;

    private static final int AIRPORT_ID_COLUMN   = 0;
    private static final int AIRPORT_NAME_COLUMN = 1;

    private static int strToInt(String numString) {
        return numString.length() > 0 ?
                Integer.parseInt(numString) :
                INT_ZERO;
    }

    private static float strToFloat(String numString) {
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

    int getDestAirportId() {
        return strToInt(data[DEST_AIRPORT_ID_COLUMN]);
    }

    float getDelayTime() {
        return strToFloat(data[DELAY_TIME_COLUMN]);
    }

    boolean getCancelled() {
        return strToFloat(data[IS_CANCELLED_COLUMN]) != FLOAT_ZERO;
    }
}