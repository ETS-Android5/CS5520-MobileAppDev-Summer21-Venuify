package edu.neu.venuify;

import java.util.Comparator;

/*
Input: Two reservation objects, with date fields in this strict format "DD/MM/YYYY"
and times fields in format 4:42am or 12:24pm (12-hour format)
Returns 1 if reservation is more in the future than (or equal to) reservation 2, -1 otherwise
 */
public class ReservationComparator implements Comparator<Reservation> {

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

                if (day1 > day2) {
                    return 1;
                }
                else if (day2 > day1) {
                    return -1;
                }

                //Case where dates are equal, need to look at time
                else {
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
                        int hour1 = Integer.parseInt(r1.time.substring(0, colon_index1));
                        int hour2 = Integer.parseInt(r2.time.substring(0, colon_index2));

                        if (hour1 > hour2) {
                            return 1;
                        } else if (hour2 > hour1) {
                            return -1;
                        }

                        //Case where both am or both pm and both same hour, need to look at min
                        // always assumes two characters for the min
                        else {
                            int min1 = Integer.parseInt(r1.time.substring(colon_index1 + 1, colon_index1 + 3));
                            int min2 = Integer.parseInt(r2.time.substring(colon_index2 + 1, colon_index2 + 3));

                            if (min1 >= min2) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    }
                }
            }
        }
    }
}