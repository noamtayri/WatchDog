package main.java.watchdog_package.layout;

import main.java.watchdog_package.logic.FileHandle;
import main.java.watchdog_package.logic.LocationMethods;
import main.java.watchdog_package.logic.LocationPrediction;
import main.java.watchdog_package.logic.LocationPredictionService;
import main.java.watchdog_package.entities.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocationPredictionUI {

    public static void predictLocationUI(Location lastKnownLocation) throws IOException {
        LocationPrediction.predictLocation(lastKnownLocation);
    }

    //test
    public static void show() throws IOException {
        List<Location> list1 = FileHandle.readFromJSON("C:\\Users\\USER\\Desktop\\Noam\\watchdog\\data\\json.json");
        System.out.println("list1.size() = " + list1.size());

        List<Location> list2 = LocationPredictionService.getSameLocation(list1.get(0));
        System.out.println("list2.size() = " + list2.size());
        /*
        for (Location l: list2
             ) {
            l.print();
        }
        */

        System.out.println();
        System.out.println("list1.get(0) = " + list1.get(0));
        System.out.println("list1.get(10) = " + list1.get(10));
        System.out.println(LocationMethods.timeDiffInSeconds(list1.get(0), list1.get(10)));
        long ms = Math.abs(list1.get(0).time.getTime() - list1.get(10).time.getTime());
        System.out.println(ms);
        System.out.println(list1.get(0).time.getTime() + ms);
        Date d = new Date(list1.get(0).time.getTime() + ms);
        System.out.println(d);

        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        b.add(1);
        b.add(2);
        b.add(3);
        System.out.println("a" + a);
        System.out.println("b" + b);
        List<Integer> c;
        System.out.println();


        /*
        Location l1 = list.get(1300);
        Location l2 = list.get(2);

        System.out.println("l1 = " + l1);
        System.out.println("l2 = " + l2);

        System.out.println("distance(l1,l2) = " + LocationMethods.distance(l1,l2));

        System.out.println("days - " + LocationMethods.timeDiffInDays(l1,l2));
        System.out.println("hour - " + LocationMethods.timeDiffInHours(l1,l2));
        System.out.println("min - " + LocationMethods.timeDiffInMinutes(l1,l2));
        System.out.println("sec - " + LocationMethods.timeDiffInSeconds(l1,l2));
        */

        /* check how many days in max value of long as ms
        long max = Long.MAX_VALUE;
        System.out.println("max = " + max);
        System.out.println(TimeUnit.DAYS.convert(max, TimeUnit.MILLISECONDS));
        */
    }

}
