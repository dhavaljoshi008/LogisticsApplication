package orderprocessor;

import java.util.Comparator;

public class FacilityRecordComparator implements Comparator<FacilityRecord> {

    @Override
    public int compare(FacilityRecord o1, FacilityRecord o2) {
        int arrivalDay1 = o1.getArrivalDay();
        int arrivalDay2 = o2.getArrivalDay();

        if (arrivalDay1 > arrivalDay2) {
            return +1;
        } else if (arrivalDay1 < arrivalDay2) {
            return -1;
        } else {
            return 0;
        }
    }
}
