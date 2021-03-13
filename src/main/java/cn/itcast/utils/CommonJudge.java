package cn.itcast.utils;

import java.util.regex.Pattern;

public class CommonJudge {
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
