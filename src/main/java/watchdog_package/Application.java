package main.java.watchdog_package;

import main.java.watchdog_package.logic.FileHandle;
import main.java.watchdog_package.logic.entities.Location;
import main.java.watchdog_package.logic.LocationMethods;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException, ParseException {
        List<Location> list = FileHandle.readFromJSON("C:\\Users\\USER\\Desktop\\Noam\\watchdog\\data\\json.json");

        Location l1 = list.get(0);
        Location l2 = list.get(2);

        System.out.println("l1 = " + l1);
        System.out.println("l2 = " + l2);

        System.out.println("distance(l1,l2) = " + LocationMethods.distance(l1,l2));

        System.out.println("days - " + LocationMethods.timeDiffInDays(l1,l2));
        System.out.println("hour - " + LocationMethods.timeDiffInHours(l1,l2));
        System.out.println("min - " + LocationMethods.timeDiffInMinutes(l1,l2));
        System.out.println("sec - " + LocationMethods.timeDiffInSeconds(l1,l2));
    }
}
