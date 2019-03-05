package main.java.watchdog_package.logic;

import main.java.watchdog_package.entities.Movement;
import main.java.watchdog_package.entities.Stay;
import main.java.watchdog_package.seviceClasses.*;

import java.util.List;
import java.util.Map;

public class TimeLineService {

    private TimeLine timeLine;

    public TimeLineService(){
        timeLine = new TimeLine();
    }

    public TimeLine getTimeLine() {
        return timeLine;
    }

    public void createTimeLine(List<Stay> stayList, List<LabeledMovement> labeledMovements){
        int stayIndex = 0;
        int movementIndex = 0;
        while(stayIndex < stayList.size() && movementIndex < labeledMovements.size()){
            Stay currentStay = stayList.get(stayIndex);
            LabeledMovement currentMovement = labeledMovements.get(movementIndex);

            Log currentLog;

            if(currentStay.getStartTime().compareTo(currentMovement.getStartTime()) < 0){
                currentLog = new Log(currentStay.getStartTime(), currentStay.getEndTime(), ActivityType.REST);
                stayIndex++;
            }
            else{
                currentLog = new Log(currentMovement.getStartTime(), currentMovement.getEndTime(), currentMovement.getActivityType());
                movementIndex++;
            }
            timeLine.addLog(currentLog);
        }

        while(stayIndex < stayList.size()){
            Stay currentStay = stayList.get(stayIndex);
            Log currentLog = new Log(currentStay.getStartTime(), currentStay.getEndTime(), ActivityType.REST);
            timeLine.addLog(currentLog);
            stayIndex++;
        }

        while(movementIndex < labeledMovements.size()){
            LabeledMovement currentMovement = labeledMovements.get(movementIndex);
            Log currentLog = new Log(currentMovement.getStartTime(), currentMovement.getEndTime(), currentMovement.getActivityType());
            timeLine.addLog(currentLog);
            movementIndex++;
        }
    }
}
