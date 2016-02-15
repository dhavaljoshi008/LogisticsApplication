package facility.schedule;

/**
 * ScheduleService.java
 * LogisticsApplication
 */
final public class ScheduleService implements Schedule  {
    private static ScheduleService scheduleServiceInstance;
    private Schedule scheduleDelegate;

    private ScheduleService(String type) {
        scheduleDelegate = ScheduleFactory.build(type);
    }

    public static ScheduleService getScheduleServiceInstance() {
        if(scheduleServiceInstance == null) {
            scheduleServiceInstance = new ScheduleService("basic");
        }
        return scheduleServiceInstance;
    }

    @Override
    public boolean setNumberOfDays(int days) {
        return scheduleDelegate.setNumberOfDays(days);
    }

    @Override
    public boolean setProcessingCapacityPerDay(int processingCapacityPerDay) {
        return scheduleDelegate.setProcessingCapacityPerDay(processingCapacityPerDay);
    }

    @Override
    public boolean bookSchedule(int day, int numberOfItemsToProcess) {
        return scheduleDelegate.bookSchedule(day, numberOfItemsToProcess);
    }

    public String getSchedule() {
        return scheduleDelegate.getSchedule();
    }

    @Override
    public boolean isDayAvailable(int day) {
        return scheduleDelegate.isDayAvailable(day);
    }

    @Override
    public int getScheduleForDay(int day) {
        return scheduleDelegate.getScheduleForDay(day);
    }

    @Override
    public int getFirstOpenDay() {
        return scheduleDelegate.getFirstOpenDay();
    }

    public void changeScheduleType(String type) {
        scheduleDelegate = ScheduleFactory.build(type);
    }
}
