package edu.neu.venuify;

import java.util.Comparator;

/*
Input: Two reservation objects, with time fields in the format such as 4:45pm or 12:22am
Returns 1 if date1 is more in the future than (or equal to) date 2, -1 otherwise
Example: compare("11/22/2022", "10/22/2022") would 1
 */
public class TimeComparator implements Comparator<Reservation> {

    public int compare(Reservation r1, Reservation r2) {
        String slot1 = r1.time.substring(r1.time.length() - 2).toLowerCase();
        String slot2 = r2.time.substring(r2.time.length() - 2).toLowerCase();

        if (slot1.equals("pm") && slot2.equals("am")) {
            return 1;
        }
        else if (slot1.equals("am") && slot2.equals("pm")) {
            return -1;
        }

        //Case where where times are either both am or both pm
        else {

            int colon_index1 = r1.time.indexOf(":");
            int colon_index2 = r2.time.indexOf(":");
            int hour1 = Integer.parseInt(r1.time.substring(0,colon_index1));
            int hour2 = Integer.parseInt(r2.time.substring(0,colon_index2));

            if (hour1 > hour2) {
                return 1;
            }
            else if (hour2 > hour1) {
                return -1;
            }

            //Case where both am or both pm and both same hour, need to look at min
            // always assumes two characters for the min
            else {
                int min1 = Integer.parseInt(r1.time.substring(colon_index1 + 1, colon_index1 + 3));
                int min2 = Integer.parseInt(r2.time.substring(colon_index2 + 1, colon_index2 + 3));

                if (min1 >= min2) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        }
    }
}
