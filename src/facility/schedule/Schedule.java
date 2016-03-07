package facility.schedule;

/**
 * Schedule.java
 * LogisticsApplication
 */
public interface Schedule {
    boolean setNumberOfDays(int days);
    boolean setProcessingCapacityPerDay(int processingCapacityPerDay);
    boolean bookSchedule(int orderSubmissionDay, int processQuantity);
    String getSchedule();
    boolean isDayAvailable(int day);
    int getScheduleForDay(int day);
    int getFirstOpenDay();
    int getProcessingEndDay(int orderSubmissionDay, int processQuantity);
}
