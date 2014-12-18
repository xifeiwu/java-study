package study.java.util.Date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeClass {

    public static void log(String type, String msg){
        System.out.println(type + ": " + msg);
    }
    public static void main(String[] args){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        log("System.currentTimeMillis", String.valueOf(System.currentTimeMillis()));
        log("Date", new Date(System.currentTimeMillis()).toString());
        log("formatter", formatter.format(new Date(System.currentTimeMillis())));
    }
}
