package cn.factory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static String DateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);
        return time ;
    }
}
