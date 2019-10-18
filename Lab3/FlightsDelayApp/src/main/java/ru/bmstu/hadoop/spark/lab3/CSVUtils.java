package ru.bmstu.hadoop.spark.lab3;

class CSVUtils {
    static final int  INT_ZERO   = 0;
    static final float FLOAT_ZERO = 0.0f;

    static final int TYPE_AIRPORT = 0;
    static final int TYPE_FLIGHT  = 1;

    private static final int ORIGIN_AIRPORT_ID_COLUMN = 11;
    private static final int DEST_AIRPORT_ID_COLUMN   = 14;
    private static final int DELAY_TIME_COLUMN        = 18;
    private static final int IS_CANCELLED_COLUMN      = 19;

    private static final int AIRPORT_ID_COLUMN   = 0;
    private static final int AIRPORT_NAME_COLUMN = 1;

    static final String FLIGHTS_FIRST_COLUMN = "YEAR";
    static final String AIRPORTS_FIRST_COLUMN = "CODE";



    private static String[] airportData;
    private static String[] flightData;

    static int strToInt(String numString) {
        return numString.length() > 0 ?
                Integer.parseInt(numString) :
                INT_ZERO;
    }

    static float strToFloat(String numString) {
        return numString.length() > 0 ?
                Float.parseFloat(numString):
                FLOAT_ZERO;
    }

    static void parseAirportData(String raw) {
        airportData = raw.replaceAll("\"", "").split(",");
    }

    static int getAirportId() {
        return strToInt(airportData[AIRPORT_ID_COLUMN]);
    }

    static String getAirportName() {
        return airportData.length > 0 ?
                airportData[AIRPORT_NAME_COLUMN] :
                "";
    }

    static void parseFlightData(String raw) {
        flightData = raw.split(",");
    }

    static int getOriginAirportId() {
        return strToInt(flightData[ORIGIN_AIRPORT_ID_COLUMN]);
    }

    static int getDestAirportId() {
        return strToInt(flightData[DEST_AIRPORT_ID_COLUMN]);
    }

    static float getFloatDelayTime() {
        return strToFloat(flightData[DELAY_TIME_COLUMN]);
    }

    static String getStringDelayTime() {
        return flightData.length > 0 ?
                flightData[DELAY_TIME_COLUMN] :
                "";
    }

    static boolean getCancelled() {
        return strToInt(flightData[IS_CANCELLED_COLUMN]) != 0;
    }
}