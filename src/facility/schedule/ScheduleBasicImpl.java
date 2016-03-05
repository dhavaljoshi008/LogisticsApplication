package facility.schedule;


import java.util.Map;
import java.util.TreeMap;

/**
 * ScheduleBasicImpl.java
 * LogisticsApplication
 */
public class ScheduleBasicImpl implements Schedule {
    // Day and Processing Capacity map.
    private Map<Integer, Integer> schedule;
    private int processingCapacityPerDay;
    private int scheduleDays;

    @Override
    public boolean setNumberOfDays(int days) {
        if (days > 0 && days <= 30) {
            scheduleDays = days;
            schedule = new TreeMap<>();
            return true;
        }
        return false;
    }


    @Override
    public boolean setProcessingCapacityPerDay(int processingCapacityPerDay) {
        if (processingCapacityPerDay > 0 && processingCapacityPerDay <= 25) {
            this.processingCapacityPerDay = processingCapacityPerDay;
            for (int i = 1; i <= scheduleDays; i++) {
                schedule.put(i, this.processingCapacityPerDay);
            }
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean bookSchedule(int day, int numberOfItemsToProcess) {
        return false;
    }

    @Override
    public String getSchedule() {
        StringBuilder scheduleBuilder = new StringBuilder();
        scheduleBuilder.append("Day:       ");
        for(Integer day: schedule.keySet()) {
            scheduleBuilder.append(String.format("%2d",day) + " ");
        }
        scheduleBuilder.append("\n");
        scheduleBuilder.append("Available: ");
        for(Integer day: schedule.keySet()) {
            scheduleBuilder.append(String.format("%2d", schedule.get(day)) + " ");
        }
        scheduleBuilder.append("\n");
        return scheduleBuilder.toString();
    }

    @Override
    public boolean isDayAvailable(int day) {
        return schedule.get(day) > 0;
    }

    @Override
    public int getScheduleForDay(int day) {
        return schedule.get(day);
    }

    @Override
    public int getFirstOpenDay() {
        // Default available day = -1. No day available for processing.
        int availableDay = -1;
        for(Integer day: schedule.keySet()) {
            if(schedule.get(day) > 0) {
                availableDay = day;
                break;
            }
        }
        return availableDay;
    }

}
