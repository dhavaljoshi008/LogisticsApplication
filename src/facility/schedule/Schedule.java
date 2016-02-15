package facility.schedule;

/**
 * Schedule.java
 * LogisticsApplication
 */
public interface Schedule {
    boolean setNumberOfDays(int days);
    boolean setProcessingCapacityPerDay(int processingCapacityPerDay);
    boolean bookSchedule(int day, int numberOfItemsToProcess);
    String getSchedule();
    boolean isDayAvailable(int day);
    int getScheduleForDay(int day);
    int getFirstOpenDay();

}
