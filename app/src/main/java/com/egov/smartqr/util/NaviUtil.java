package com.egov.smartqr.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.egov.smartqr.LoginActivity;
import com.egov.smartqr.MainActivity;
import com.egov.smartqr.ZxingActivity;
import com.egov.smartqr.qrcode.DecoderActivity;
import com.google.zxing.integration.android.IntentIntegrator;


// 액티비티 띄워주기
public class NaviUtil {
	
	public static final int ReqMainActivity = 1;
	public static final int ReqQrActivity = 1;

	public static void gotoMain(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
	}

	public static void gotoLogin(Context context) {
		Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);
	}

	public static void gotoQr(Fragment fragment) {
//		Intent intent = new Intent(fragment.getContext(), DecoderActivity.class);
//		fragment.startActivityForResult(intent, ReqQrActivity);
		IntentIntegrator integrator = new IntentIntegrator(fragment.getActivity());
		integrator.setCaptureActivity( ZxingActivity.class );
		integrator.setOrientationLocked(false);
		integrator.initiateScan();
	}
	public static void gotoQr(Activity activity) {
//		Intent intent = new Intent(fragment.getContext(), DecoderActivity.class);
//		fragment.startActivityForResult(intent, ReqQrActivity);
		IntentIntegrator integrator = new IntentIntegrator(activity);
		integrator.setCaptureActivity( ZxingActivity.class );
		integrator.setOrientationLocked(false);
		integrator.initiateScan();
	}

}
