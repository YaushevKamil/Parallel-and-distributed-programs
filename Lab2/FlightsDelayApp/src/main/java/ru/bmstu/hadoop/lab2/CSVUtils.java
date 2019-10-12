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

    static String getAirportId() {
        return airportData.length > 0 ?
                    airportData[AIRPORT_ID_COLUMN] :
                    "";
    }

    static String getAirportName() {
        return airportData.length > 0 ?
                    airportData[AIRPORT_NAME_COLUMN] :
                    "";
    }

    static void parseFlightData(String raw) {
        flightData = raw.split(",");
    }

    static String getOriginAirportId() {
        return flightData.length > 0 ?
                    flightData[ORIGIN_AIRPORT_ID_COLUMN] :
                    "";
    }

    static String getDelayTime() {
        return flightData.length > 0 ?
                    flightData[DELAY_TIME_COLUMN] :
                    "";
    }
}