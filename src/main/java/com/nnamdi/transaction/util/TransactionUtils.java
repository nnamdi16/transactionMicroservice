package com.nnamdi.transaction.util;

import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.time.DateUtils;

public class TransactionUtils {

    public static Date getEndOfDay(Date date){
        return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
    }

    public static Date getStartOfDay(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }
}
