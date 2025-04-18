package com.example.appxemphim.Utilities;

import com.google.firebase.Timestamp;
import java.util.Calendar;

public class Utils {

    public static int getYearFromTimestamp(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp.toDate());
        return calendar.get(Calendar.YEAR);
    }

}
