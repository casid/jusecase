package org.jusecase.builders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateBuilder implements Builder<Date> {
    private Date date;

    public DateBuilder with(String string) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return with(string, dateFormat);
    }

    public DateBuilder with(String string, SimpleDateFormat dateFormat) {
        try {
            date = dateFormat.parse(string);
        } catch (ParseException e) {
            date = null;
        }
        return this;
    }


    public Date build() {
        return date;
    }
}
