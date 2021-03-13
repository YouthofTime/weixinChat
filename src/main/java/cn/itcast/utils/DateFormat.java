package cn.itcast.utils;

import cn.itcast.domain.SingleMsg;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {

    static String[]weeks={"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    public static SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static SimpleDateFormat sdfDate=new SimpleDateFormat("yy/MM/dd");
    public static SimpleDateFormat sdfMinute=new SimpleDateFormat("HH:mm");
    public static Date parse(String str,SimpleDateFormat dateFormat){
        Date date=null;
        try {
             date = dateFormat.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
    public static String format(Date date,SimpleDateFormat dateFormat){
       if(date==null)
           return "";
        return dateFormat.format(date);
    }
    public static String formatNow(Date date){
        /*date和当前时间相比*/

        Date now=new Date();
        Calendar c=Calendar.getInstance();
        c.setTime(now);
        int yearNow=c.get(Calendar.YEAR);
        int monthNow=c.get(Calendar.MONTH)+1;
        int dayNow=c.get(Calendar.DATE);
        c.setTime(date);
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH)+1;
        int day=c.get(Calendar.DATE);
        if(yearNow==year&&monthNow==month){
            // 1.当天返回格式为分钟
            if(dayNow==day)
                return format(date,sdfMinute);
            // 2.昨日返回字符串昨天
            else if(dayNow-day==1)
                return "昨天";
            // 3.一周之内
            // 现在对应的星期
            c.setTime(now);
            int indexNow=c.get(Calendar.DAY_OF_WEEK)-1;
            // date对应的星期
            c.setTime(date);
            int indexDate=c.get(Calendar.DAY_OF_WEEK)-1;
            // 相聚是否是indexNow之内
            if(dayNow-day<=indexNow)
                return weeks[indexDate];
            // 4.一周之外
            else
                return format(date,sdfDate);

        }
        return null;
    }
    /*比较俩个日期的大小*/
    public static boolean less(Date date,Date date1){
        String str=format(date,sdf);
        String str1=format(date1,sdf);
        if(str.compareTo(str1)<0)
            return true;
        else
            return false;
    }

    public static void main(String[] args) {
        Date date=parse("2020/12/26 9:32:24",sdf);
        System.out.println(less(date,new Date()));

    }
}
