package facility.schedule;

/**
 * ScheduleNullImpl.java
 * LogisticsApplication
 */
public class ScheduleNullImpl implements Schedule {
    @Override
    public boolean setNumberOfDays(int days) {
        return false;
    }

    @Override
    public boolean setProcessingCapacityPerDay(int processingCapacityPerDay) {
        return false;
    }

    @Override
    public boolean bookSchedule(int day, int numberOfItemsToProcess) {
        return false;
    }

    @Override
    public String getSchedule() {
        return null;
    }

    @Override
    public boolean isDayAvailable(int day) {
        return false;
    }

    @Override
    public int getScheduleForDay(int day) {
        return -1;
    }

    @Override
    public int getFirstOpenDay() {
        return -1;
    }
}
