package com.egov.smartqr.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * Created by onlytree on 15. 12. 8..
 */
public class FlashLightUtil {

    public static Camera cam = null;
    public static Camera.Parameters camParams = null;

    public static void flashLightOn(Context context) {

        try {
            if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                if(cam == null) cam = Camera.open();
                camParams = cam.getParameters();
                camParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                cam.setParameters(camParams);
                cam.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void flashLightOff(Context context) {
        try {
            if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                cam.stopPreview();
                cam.release();
                cam = null;
                camParams = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void flashLightPause() {
        try {
            if(camParams != null) {
                if(camParams.getFlashMode() != Camera.Parameters.FLASH_MODE_OFF) {
                    camParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    cam.setParameters(camParams);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void flashLightResume() {
        try {
            if(camParams != null) {
                if(camParams.getFlashMode() != Camera.Parameters.FLASH_MODE_TORCH) {
                    camParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    cam.setParameters(camParams);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isFlashLightOn() {

        boolean rtn = false;
        try {
            if(cam == null) cam = Camera.open();
            camParams = cam.getParameters();
            if(camParams.getFlashMode() == Camera.Parameters.FLASH_MODE_TORCH) {
                rtn = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rtn;
    }
}
