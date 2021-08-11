package edu.neu.venuify;

import java.util.Comparator;

/*
Input: Two reservation objects, with date fields in this strict format "DD/MM/YYYY"
Returns 1 if date1 is more in the future than (or equal to) date 2, -1 otherwise
Example: compare("11/22/2022", "10/22/2022") would 1
 */
public class DateComparator implements Comparator<Reservation> {

    public int compare(Reservation r1, Reservation r2) {
        int year1 = Integer.parseInt(r1.date.substring(r1.date.length() - 4));
        int year2 = Integer.parseInt(r2.date.substring(r2.date.length() - 4));

        if (year1 > year2) {
            return 1;
        }
        else if (year2 > year1) {
            return -1;
        }

        //Case where same year, compare the months
        else {
            int month1 = Integer.parseInt(r1.date.substring(0,2));
            int month2 = Integer.parseInt(r2.date.substring(0,2));

            if (month1 > month2) {
                return 1;
            }
            else if (month2 > month1) {
                return -1;
            }

            //Case where same year and same month, compare days
            else {
                int day1 = Integer.parseInt(r1.date.substring(3,5));
                int day2 = Integer.parseInt(r2.date.substring(3,5));

                if (day1 >= day2) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        }
    }
}