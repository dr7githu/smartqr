package com.egov.smartqr.util;

import java.net.URLEncoder;

/**
 * Created by onlytree on 2018. 1. 22..
 */

public class StrUtil {
    public static String hipen(String s) {
        if(s == null || s.length() <= 0) return "-";
        else return s;
    }
    public static String space(String s) {
        if(s == null || s.length() <= 0) return "";
        else return s;
    }
    public static boolean notEmpty(String s) {
        return !(s == null || s.length() <= 0);
    }
    public static boolean isEmpty(String s) {
        return s == null || s.length() <= 0;
    }

    public static String d2s(double d) {
        return d+"";
    }

    //문자비교 & 숫자비교(소수점 있을수도, 없을수도 있음)
    public static boolean exEqual(String s1, String s2) {
        if(s1 == null) s1 = "";
        if(s2 == null) s2 = "";

        if(s1.equals(s2)) return true;

        try {
            double d1 = Double.parseDouble(s1);
            double d2 = Double.parseDouble(s2);
            return d1 == d2;
        } catch (Exception e) {
            return false;
        }
    }

    public static String utf8(String s) {
//아래주석 : utf8적용하지 않기
//        try {
//            return URLEncoder.encode(s, "utf-8");
//        } catch (Exception e) {
//        }
        return s;
    }

    public static boolean isEqual(String s1, String s2) {
//아래주석 : 비교하지 않고 모두 파라메타로 전송
//        if(s1 != null && s2 != null) {
//            if(s1.equals(s2)) return true;
//        }
//        //신규값이 있다면 다름 -> 입력처리
//        if(s2 != null && s2.length() > 0) return false;
//        else return true;
        return false;
    }
    public static boolean isEqual(double d1, String s2) {
//아래주석 : 비교하지 않고 모두 파라메타로 전송
//        String s1 = d1+"";
//        if(s1 != null && s2 != null) {
//            if(s1.equals(s2)) return true;
//        }
//        //신규값이 있다면 다름 -> 입력처리
//        if(s2 != null && s2.length() > 0) return false;
//        else return true;
        return false;
    }
}
