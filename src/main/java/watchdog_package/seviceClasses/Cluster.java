package main.java.watchdog_package.seviceClasses;

import main.java.watchdog_package.entities.Position;

public class Cluster{
    //TODO: declare the variables as private and use modifiers!!!
    public int id;
    public int numOfPoints;
    public Position center;

    public Cluster(int id, Position position){
        this.id = id;
        numOfPoints = 0;
        center = position;
    }

    public Position getCenter() {
        return center;
    }
}
