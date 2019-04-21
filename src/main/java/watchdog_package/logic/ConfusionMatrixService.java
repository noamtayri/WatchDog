package main.java.watchdog_package.logic;

import main.java.watchdog_package.seviceClasses.ActivityType;
import main.java.watchdog_package.seviceClasses.TimeLine;

import java.util.Date;

//This class is used for testing only
public class ConfusionMatrixService {
    private long [][] confusionMatrix;

    public ConfusionMatrixService(int matrixSize){
        confusionMatrix = new long[matrixSize][matrixSize];
    }

    private void clearMatrix(){
        for(int rowIndex = 0; rowIndex < confusionMatrix.length; rowIndex++){
            for(int colIndex = 0; colIndex < confusionMatrix[rowIndex].length; colIndex++){
                confusionMatrix[rowIndex][colIndex] = 0;
            }
        }
    }

    private Date getNextTimeStamp(TimeLine actualTimeLine, TimeLine analyzedTimeLine, Date currentDate){
        Date ret;
        Date nextActualTime = actualTimeLine.getNextTimeStamp(currentDate);
        Date nextAnalyzedTime = analyzedTimeLine.getNextTimeStamp(currentDate);
        if(nextActualTime.compareTo(nextAnalyzedTime) < 0){
            ret = nextActualTime;
        }
        else{
            ret = nextAnalyzedTime;
        }
        return ret;
    }

    private void setActivityPeriod(TimeLine actualTimeLine, TimeLine predictedTimeLine, Date startTime, Date endTime){
        ActivityType actualActivity = actualTimeLine.getActivityAtTime(startTime);
        ActivityType predictedActivity = predictedTimeLine.getActivityAtTime(startTime);
        long durationInSeconds = LocationMethods.timeDiffInSeconds(startTime,endTime);
        confusionMatrix[actualActivity.ordinal()][predictedActivity.ordinal()] += durationInSeconds;
    }


    public long [][] getConfusionMatrix(TimeLine actualTimeLine, TimeLine analyzedTimeLine){
        clearMatrix();
        Date startTime = analyzedTimeLine.getStartTime();
        while(startTime.compareTo(actualTimeLine.getEndTime()) < 0){
            System.out.println(startTime);
            Date endTime = getNextTimeStamp(actualTimeLine, analyzedTimeLine, startTime);
            setActivityPeriod(actualTimeLine, analyzedTimeLine, startTime, endTime);
            startTime = getNextTimeStamp(actualTimeLine, analyzedTimeLine, endTime);
        }
        return confusionMatrix;
    }
}
