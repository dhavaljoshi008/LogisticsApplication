package facility.schedule;

/**
 * ScheduleBasicImpl.java
 * LogisticsApplication
 */
public class ScheduleBasicImpl implements Schedule {
    boolean[] bookedScheduleStatus;
    private int processingCapacityPerDay;
    private int[] schedule;

    @Override
    public boolean setNumberOfDays(int days) {
        if (days > 0 && days <= 30) {
            schedule = new int[days];
            bookedScheduleStatus = new boolean[days];
            return true;
        } else {
            System.out.println("Please enter number of days within the range 1 to 30");
        }
        return false;
    }


    @Override
    public boolean setProcessingCapacityPerDay(int processingCapacityPerDay) {
        if (processingCapacityPerDay > 0 && processingCapacityPerDay <= 25) {
            this.processingCapacityPerDay = processingCapacityPerDay;
            for (int i = 0; i < schedule.length; i++)
                schedule[i] = this.processingCapacityPerDay;
            return true;
        } else {
            System.out.println("Please enter a number within 1 to 25");
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
        int scheduleLength = schedule.length;
        scheduleBuilder.append("Day:       ");
        for(int i = 0; i < scheduleLength; i++) {
            scheduleBuilder.append(i + 1 + " ");
        }
        scheduleBuilder.append("\n");
        scheduleBuilder.append("Available: ");
        for(int i = 0; i < scheduleLength; i++) {
            scheduleBuilder.append(schedule[i] + " ");
        }
        scheduleBuilder.append("\n");
        return scheduleBuilder.toString();
    }

    @Override
    public boolean isDayAvailable(int day) {

        boolean empty = false;
        int scheduleLength = schedule.length;
        for (int i = 0; i < scheduleLength; i++) {
            if (schedule[i] > 0) {
                empty = true;
                break;
            }
        }
        return empty;
    }

    @Override
    public int getScheduleForDay(int day) {
        return schedule[day];
    }

    @Override
    public int getFirstOpenDay() {
        int availableDay = -1;
        int scheduleLength = schedule.length;
        for (int i = 0; i < scheduleLength; i++) {
            if (schedule[i] > 0) {
                availableDay = schedule[i];
                break;
            }
        }
        return availableDay;
    }

}
