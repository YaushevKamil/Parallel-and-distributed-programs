package ru.bmstu.hadoop.spark.lab3;

class CSVUtils {
    static final String FLIGHTS_CSV  = "664600583_T_ONTIME_sample.csv";
    static final String AIRPORTS_CSV = "L_AIRPORT_ID.csv";
    static final String OUTPUT_FILE  = "output";

    static final String FLIGHTS_FIRST_COLUMN  = "YEAR";
    static final String AIRPORTS_FIRST_COLUMN = "Code";

    static final int  INT_ZERO   = 0;
    static final float FLOAT_ZERO = 0.0f;

    private static final int ORIGIN_AIRPORT_ID_COLUMN = 11;
    private static final int DEST_AIRPORT_ID_COLUMN   = 14;
    private static final int DELAY_TIME_COLUMN        = 18;
    private static final int IS_CANCELLED_COLUMN      = 19;

    private static final int AIRPORT_ID_COLUMN   = 0;
    private static final int AIRPORT_NAME_COLUMN = 1;

    private static String[] airportData;
    private static String[] flightData;

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

    static int getAirportId(String raw) {
        String[] airportData = raw.replaceAll("\"", "").split(",");
        return strToInt(airportData[AIRPORT_ID_COLUMN]);
    }

    static String getAirportName(String raw) {
        String[] airportData = raw.replaceAll("\"", "").split(",");
        return airportData.length > 0 ?
                airportData[AIRPORT_NAME_COLUMN] :
                "";
    }

    static int getOriginAirportId(String raw) {
        String[] flightData = raw.split(",");
        return strToInt(flightData[ORIGIN_AIRPORT_ID_COLUMN]);
    }

    static int getDestAirportId(String raw) {
        String[] flightData = raw.split(",");
        return strToInt(flightData[DEST_AIRPORT_ID_COLUMN]);
    }

    static float getFloatDelayTime(String raw) {
        String[] flightData = raw.split(",");
        return strToFloat(flightData[DELAY_TIME_COLUMN]);
    }

    static boolean getCancelled(String raw) {
        String[] flightData = raw.split(",");
        return strToInt(flightData[IS_CANCELLED_COLUMN]) != 0;
    }
}