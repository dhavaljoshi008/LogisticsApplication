package facility.schedule;


import java.util.*;

/**
 * ScheduleBasicImpl.java
 * LogisticsApplication
 */
public class ScheduleBasicImpl implements Schedule {
    // Day and Processing Capacity map.
    private Map<Integer, Integer> schedule;
    private int processingCapacityPerDay;
    private int scheduleDays;
    private int firstAvailableDay = 1;

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
    public boolean bookSchedule(int orderSubmissionDay, int processQuantity) {
       // System.out.println("First Open Day = " + firstAvailableDay + " PR: " + processingCapacityPerDay);
        if(orderSubmissionDay <= getFirstOpenDay()) {
            if (processQuantity > 0) {
                int firstOpenDay = getFirstOpenDay();
                int firstOpenDaySchedule = getScheduleForDay(firstOpenDay);

                if (processQuantity < firstOpenDaySchedule) {
                    firstOpenDaySchedule = firstOpenDaySchedule - processQuantity;
                    schedule.put(firstOpenDay, firstOpenDaySchedule);
                    return true;
                } else if (processQuantity == firstOpenDaySchedule) {
                    schedule.put(firstOpenDay, 0);
                    firstAvailableDay = getFirstOpenDay() + 1;
                    return true;
                } else {
                    int remainingQuantity = processQuantity - firstOpenDaySchedule;
                    int firstOpenDayProcessQuantity = firstOpenDaySchedule;
                    schedule.put(firstOpenDay, 0);
                    int completeProcessingDays = (int) Math.floor(remainingQuantity / (double) processingCapacityPerDay);
                    int partialDayProcessQuantity = processQuantity - (firstOpenDayProcessQuantity + (completeProcessingDays * processingCapacityPerDay));
                    // If there are no completeProcessingDays i.e. completeProcessingDays = 0, lastCompleteDay will be the old firstOpenDay.
                    int lastCompleteDay = firstOpenDay;
                    for (int i = 1; i <= completeProcessingDays; i++) {
                        schedule.put(firstOpenDay + i, 0);
                        lastCompleteDay = firstOpenDay + i;
                    }
                    schedule.put(lastCompleteDay + 1, processingCapacityPerDay - partialDayProcessQuantity);
                    // Even if the last day is a partial day, it will be the first available day.
                    firstAvailableDay = lastCompleteDay + 1;
                    return true;
                }
            }
        }
        else {
            if (processQuantity > 0) {
                int startDay = orderSubmissionDay;
                if(!schedule.containsKey(startDay)) {
                    schedule.put(startDay, processingCapacityPerDay);
                }
                int startDaySchedule = getScheduleForDay(startDay);

                if (processQuantity < startDaySchedule) {
                    startDaySchedule = startDaySchedule - processQuantity;
                    schedule.put(startDay, startDaySchedule);
                    return true;
                } else if (processQuantity == startDaySchedule) {
                    schedule.put(startDay, 0);
                    return true;
                } else {
                    int remainingQuantity = processQuantity - startDaySchedule;
                    int startDayProcessQuantity = startDaySchedule;
                    schedule.put(startDay, 0);
                    int completeProcessingDays = (int) Math.floor(remainingQuantity / (double) processingCapacityPerDay);
                    int partialDayProcessQuantity = processQuantity - (startDayProcessQuantity + (completeProcessingDays * processingCapacityPerDay));
                    // If there are no completeProcessingDays i.e. completeProcessingDays = 0, lastCompleteDay will be the old startDay.
                    int lastCompleteDay = startDay;
                    for (int i = 1; i <= completeProcessingDays; i++) {
                        schedule.put(startDay + i, 0);
                        lastCompleteDay = startDay + i;
                    }
                    schedule.put(lastCompleteDay + 1, processingCapacityPerDay - partialDayProcessQuantity);
                    // Even if the last day is a partial day, it will be the first available day.
                    firstAvailableDay = lastCompleteDay + 1;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getSchedule() {
        StringBuilder scheduleBuilder = new StringBuilder();
        scheduleBuilder.append("Day:       ");
        for (Integer day : schedule.keySet()) {
            scheduleBuilder.append(String.format("%3d", day) + " ");
        }
        scheduleBuilder.append("\n");
        scheduleBuilder.append("Available: ");
        for (Integer day : schedule.keySet()) {
            scheduleBuilder.append(String.format("%3d", schedule.get(day)) + " ");
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
           return firstAvailableDay;
    }

    public int getProcessingEndDay(int orderSubmissionDay, int processQuantity) {
        if (orderSubmissionDay <= getFirstOpenDay()) {
            if (processQuantity > 0) {
                int firstOpenDay = getFirstOpenDay();
                int firstOpenDaySchedule = getScheduleForDay(firstOpenDay);

                if (processQuantity < firstOpenDaySchedule) {
                    // firstOpenDaySchedule = firstOpenDaySchedule - processQuantity;
                    return firstOpenDay;
                } else if (processQuantity == firstOpenDaySchedule) {
                    return firstOpenDay;
                } else {
                    int remainingQuantity = processQuantity - firstOpenDaySchedule;
                    int startDayProcessQuantity = firstOpenDaySchedule;
                    int completeProcessingDays = (int) Math.floor(remainingQuantity / (double) processingCapacityPerDay);
                    int partialDayProcessQuantity = processQuantity - (startDayProcessQuantity + (completeProcessingDays * processingCapacityPerDay));
                    // If there are no completeProcessingDays i.e. completeProcessingDays = 0, lastCompleteDay will just be the old firstOpenDay.
                    int lastCompleteDay = firstOpenDay;
                    if (completeProcessingDays > 0) {
                        lastCompleteDay = firstOpenDay + completeProcessingDays;
                    }
                    // If there is some quantity which requires partial day to process, add one more day.
                    if (partialDayProcessQuantity > 0) {
                        return lastCompleteDay + 1;
                    } else {
                        return lastCompleteDay;
                    }
                }
            }
        }
        else {
            if (processQuantity > 0) {
                int startDay = orderSubmissionDay;
                if(!schedule.containsKey(startDay)) {
                    schedule.put(startDay, processingCapacityPerDay);
                }
                int startDaySchedule = getScheduleForDay(startDay);

                if (processQuantity < startDaySchedule) {
                    // startDaySchedule = startDaySchedule - processQuantity;
                    return startDay;
                } else if (processQuantity == startDaySchedule) {
                    return startDay;
                } else {
                    int remainingQuantity = processQuantity - startDaySchedule;
                    int startDayProcessQuantity = startDaySchedule;
                    int completeProcessingDays = (int) Math.floor(remainingQuantity / (double) processingCapacityPerDay);
                    int partialDayProcessQuantity = processQuantity - (startDayProcessQuantity + (completeProcessingDays * processingCapacityPerDay));
                    // If there are no completeProcessingDays i.e. completeProcessingDays = 0, lastCompleteDay will just be the old startDay.
                    int lastCompleteDay = startDay;
                    if (completeProcessingDays > 0) {
                        lastCompleteDay = startDay + completeProcessingDays;
                    }
                    // If there is some quantity which requires partial day to process, add one more day.
                    if (partialDayProcessQuantity > 0) {
                        return lastCompleteDay + 1;
                    } else {
                        return lastCompleteDay;
                    }
                }
            }
        }
        return -1;
    }
}
