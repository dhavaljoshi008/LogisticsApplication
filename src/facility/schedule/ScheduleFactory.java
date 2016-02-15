package facility.schedule;

/**
 * ScheduleFactory.java
 * LogisticsApplication
 */
public class ScheduleFactory {
    public static Schedule build(String type) {
        switch (type) {
            case "basic":
                return new ScheduleBasicImpl();
            case "null":
            case "NULL":
                return new ScheduleNullImpl();
            default:
                return new ScheduleNullImpl();
        }

    }
}
