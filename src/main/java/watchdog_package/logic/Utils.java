package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Location;
import main.java.watchdog_package.entities.Stay;
import main.java.watchdog_package.entities.Movement;
import main.java.watchdog_package.seviceClasses.ActivityType;
import main.java.watchdog_package.seviceClasses.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Utils {
    public static void printLocationList(List<Location> locationList){
        for(Location location : locationList){
            System.out.println(location);
        }
    }

    public static void printStayList(List<Stay> stayList){
        for(Stay stay : stayList){
            System.out.println(stay);
        }
    }

    public static void printMovementList(List<Movement> movementList){
        for(Movement movement : movementList){
            System.out.println(movement);
        }
    }

    public static void printMatrix(long [][] matrix){
        long diagonalSum = 0;
        long totalSum = 0;
        System.out.print("\t\t\t\t");
        for(int colIndex = 0; colIndex < matrix.length; colIndex++){
            if(colIndex == 2 || colIndex == 3){
                System.out.print(ActivityType.values()[colIndex] + "\t\t\t");
            }
            else{
                System.out.print(ActivityType.values()[colIndex] + "\t\t");
            }
        }
        System.out.println();
        for(int rowIndex = 0; rowIndex < matrix.length; rowIndex++){
            if(rowIndex == 2 || rowIndex == 3){
                System.out.print(ActivityType.values()[rowIndex] + "\t\t\t");
            }
            else{
                System.out.print(ActivityType.values()[rowIndex] + "\t\t");
            }
            for(int colIndex = 0; colIndex < matrix[rowIndex].length; colIndex++){
                if(matrix[rowIndex][colIndex] < 1000){
                    System.out.print(matrix[rowIndex][colIndex] + "\t\t\t\t");
                }
                else{
                    System.out.print(matrix[rowIndex][colIndex] + "\t\t\t");
                }
                totalSum += matrix[rowIndex][colIndex];
                if(rowIndex == colIndex){
                    diagonalSum += matrix[rowIndex][colIndex];
                }
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Total Matrix Sum = "+ totalSum);
        System.out.println("Diagonal Sum = " + diagonalSum);
        System.out.println();
        System.out.println(String.format( "Success Rate = %.3f%s", ((100*(double)diagonalSum)/totalSum),"%" ));
    }

    public static void writeSegmentsToFile(List<Stay> stayList, List<Movement> movementList) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("segments.txt"));

        int stayIndex = 0;
        int movementIndex = 0;
        while(stayIndex < stayList.size() && movementIndex < movementList.size()){
            Stay currentStay = stayList.get(stayIndex);
            Movement currentMovement = movementList.get(movementIndex);
            String str;
            if(currentStay.getStartTime().compareTo(currentMovement.getStartTime()) < 0){
                str = currentStay.toString();
                stayIndex++;
            }
            else{
                str = currentMovement.toString();
                movementIndex++;
            }
            writer.write(str);
        }

        while(stayIndex < stayList.size()){
            String str = stayList.get(stayIndex).toString();
            writer.write(str);
            stayIndex++;
        }

        while(movementIndex < movementList.size()){
            String str = movementList.get(movementIndex).toString();
            writer.write(str);
            movementIndex++;
        }

        writer.close();
    }

    public static void printLogList(List<Log> logList){
        for(Log log : logList){
            System.out.println(log);
        }
    }
}
