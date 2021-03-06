package main.java.watchdog_package;

import main.java.watchdog_package.layout.LocationPredictionUI;
import main.java.watchdog_package.entities.Location;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Application {
    public static void main(String[] args) throws IOException, ParseException {
        String timeStr = 2018 + "-" + 10 + "-" + 31 + "-" + 20 + "-" + 10 + "-" + 03;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd-H-m-s");
        Date time = fmt.parse(timeStr);

        Location l = new Location(31.556319, 34.600448, time);

        System.out.println(l);

        LocationPredictionUI.predictLocationUI(l);

        //FileHandle.handleData();

    }
}
