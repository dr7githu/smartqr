package com.egov.smartqr.util;

import android.content.Context;
import android.os.Environment;

/**
 * Created by lss on 2015-08-12.
 */
public class DirUtil {

    //--- 내부 저장소 ---------------------------
    /**
     *  1. [내부] 캐시 저장 영역 : /data/data/[패키지 이름]/cache
     */
    public static String getCacheDir(Context c) {
        return c.getCacheDir().getPath();
    }
    /**
     * 2. [내부] 데이터베이스 파일 : /data/data/[패키지 이름]/databases
     */
    public static String getDatabasePath(Context c, String name) {
        return c.getDatabasePath(name).getPath();
    }
    /**
     * 3. [내부] 일반 파일 저장 영역 : /data/data/[패키지 이름]/files
     */
    public static String getFilesDir(Context c) {
        return c.getFilesDir().getPath();
    }


    //--- 외부 저장소-공용 영역 -------------------
    /**
     * 1. [외부-Common] 최상위 경로 얻기 : /mnt/sdcard
     */
    public static String getExStorageDir() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
        2. [외부-Common] 특정 데이터를 저장하는 영역(여러 애플리케이션에서 공용으로 사용할 수 있는 데이터들을 저장)
        인자명	                            설명 	                                       경로
        Environment.DIRECTORY_ALARMS 	        알람으로 사용할 오디오 파일을 저장합니다.	   /mnt/sdcard/Alarms
        Environment.DIRECTORY_DCIM	        카메라로 촬영한 사진이 저장됩니다.	           /mnt/sdcard/DCIM
        Environment.DIRECTORY_DOWNLOADS	    다운로드한 파일이 저장됩니다.	               /mnt/sdcard/Download
        Environment.DIRECTORY_MUSIC	        음악 파일이 저장됩니다.	                       /mnt/sdcard/Music
        Environment.DIRECTORY_MOVIES	        영상 파일이 저장됩니다.	                       /mnt/sdcard/Movies
        Environment.DIRECTORY_NOTIFICATIONS	알림음으로 사용할 오디오 파일을 저장합니다.	   /mnt/sdcard/Notifications
        Environment.DIRECTORY_PICTURES	    그림 파일이 저장됩니다.	                       /mnt/sdcard/Pictures
        Environment.DIRECTORY_PODCASTS	    팟캐스트(Poacast) 파일이 저장됩니다.	       /mnt/sdcard/Podcasts
     */
    public static String getExStoragePublicDir(String type) {
        return Environment.getExternalStoragePublicDirectory(type).getPath();
    }


    //--- 외부 저장소-애플리케이션 고유 영역 -----------
    /**
     * 1. [외부-My] 특정 데이터를 저장하는 영역
        인자명	경로
        Environment.DIRECTORY_ALARMS	        /mnt/sdcard/Android/data/[패키지 이름]/files/Alarms
        Environment.DIRECTORY_DCIM	        /mnt/sdcard/Android/data/[패키지 이름]/files/DCIM
        Environment.DIRECTORY_DOWNLOADS	    /mnt/sdcard/Android/data/[패키지 이름]/files/Downloads
        Environment.DIRECTORY_MUSIC	        /mnt/sdcard/Android/data/[패키지 이름]/files/Music
        Environment.DIRECTORY_MOVIES	        /mnt/sdcard/Android/data/[패키지 이름]/files/Movies
        Environment.DIRECTORY_NOTIFICATIONS	/mnt/sdcard/Android/data/[패키지 이름]/files/Notifications
        Environment.DIRECTORY_PICTURES	    /mnt/sdcard/Android/data/[패키지 이름]/files/Pictures
        Environment.DIRECTORY_PODCASTS	    /mnt/sdcard/Android/data/[패키지 이름]/files/Podcasts
        null	                                /mnt/sdcard/Android/data/[패키지 이름]/files
     */
    public static String getMyExStorageDir(Context c, String type) {
        return c.getExternalFilesDir(type).getPath();
    }

    /**
     * 2. [외부-My] 캐시 데이터를 저장하는 영역 : /mnt/sdcard/Android/data/[패키지 이름]/cache
     */
    public static String getMyExCacheDir(Context c) {
        return c.getExternalCacheDir().getPath();
    }
}
