package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;

import java.io.IOException;
import java.util.List;

public class ActivityTracker {
    public static void extractActivity() throws IOException {
        List<Location> locationList = FileHandle.readFromJSON("C:\\Users\\Nyxoah\\IdeaProjects\\WatchDog\\json.json");

        ActivityTrackerService service = new ActivityTrackerService();
        service.ExtractActivityPalces(locationList);
    }
}