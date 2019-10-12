package ru.bmstu.hadoop.lab2;

class CSVUtils {
    static final int   INT_ZERO = 0;
    static final float FLOAT_ZERO = 0.0f;

    static final int TYPE_AIRPORT = 0;
    static final int TYPE_FLIGHT = 1;

    static final int ORIGIN_AIRPORT_ID_COLUMN = 14;
    static final int DELAY_TIME_COLUMN = 18;

    static final int AIRPORT_ID_COLUMN = 0;
    static final int AIRPORT_NAME_COLUMN = 1;

     static String[] airportData; // ????
     static String[] flightData;  // ????

    static int strToInt(String numString) {
        int number = INT_ZERO;
        if (numString.length() > 0) {
            number = Integer.parseInt(numString);
        }
        return number;
    }

    static float strToFloat(String numString) {
        float number = FLOAT_ZERO;
        if (numString.length() > 0) {
            number = Float.parseFloat(numString);
        }
        return number;
    }

    static String[] parseAirportData(String raw) {
//        airportData = raw.replaceAll("\"", "").split(",");
        return raw.replaceAll("\"", "").split(",");
    }

    static String[] parseFlightData(String raw) {
//        flightData = raw.toString().split(",");
        return raw.toString().split(",");
    }
}