package com.egov.smartqr.util;

import android.content.Context;

/**
 * Created by onlytree on 16. 3. 18..
 */
public class UnitUtil {
    public static int dp2pxl(Context context, int dp) {
        float d = context.getResources().getDisplayMetrics().density;
        int result = (int)(dp * d);
        return result;
    }
}
