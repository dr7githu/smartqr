package com.egov.smartqr.util;

import android.app.AlertDialog;
import android.content.Context;

import com.egov.smartqr.R;
import com.egov.smartqr.dialog.LoadingDialog;
import com.egov.smartqr.dialog.MsgDialog;


// 액티비티 띄워주기
public class UIUtil {
	public static AlertDialog mAlertDialog = null;
	public static LoadingDialog mProgressDialog = null;
	
// default UI	
//	public static void alert(Context context, String title, String msg) {
//		if(mAlertDialog != null) {
//			mAlertDialog.dismiss();
//		}
//		mAlertDialog = new AlertDialog.Builder(context).create();
//
//		mAlertDialog.setTitle(title);
//		mAlertDialog.setMessage(msg);
//		mAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				mAlertDialog.dismiss();
//				mAlertDialog = null;
//			}
//		});
//
//		mAlertDialog.show();
//	}
	
	//--- OK버튼 하나 ------------------------
	public static void alert(Context context, String msg) {
		alert(context, "확인", msg, null);
	}
	public static void alert(Context context, String title, String msg) {
		alert(context, title, msg, null);
	}
	public static void alert(Context context, String msg, android.view.View.OnClickListener okClickListener) {
		alert(context, "확인", msg, okClickListener);
	}
	public static void alert(Context context, String title, String msg, android.view.View.OnClickListener okClickListener) {
		MsgDialog dlg = new MsgDialog(context);
		dlg.setMsgType(title, msg, MsgDialog.MB_OK); // ok버튼 하나
		dlg.setOkClickLinstener(okClickListener);
		dlg.show();
	}
	
	//--- OK/Cancel 버튼 두개 ------------------------
	public static void alert(Context context, String msg, android.view.View.OnClickListener okClickListener, android.view.View.OnClickListener cancelClickListener) {
		alert(context, "확인", msg, okClickListener, cancelClickListener);
	}
	public static void alert(Context context, String title, String msg, android.view.View.OnClickListener okClickListener, android.view.View.OnClickListener cancelClickListener) {
		MsgDialog dlg = new MsgDialog(context);
		dlg.setMsgType(title, msg, MsgDialog.MB_OKCANCEL); // ok, cancel
		dlg.setOkClickLinstener(okClickListener);
		dlg.setCancelClickLinstener(cancelClickListener);
		dlg.show();
	}

	//--- Progress ----------------------------------
	public static void showProgress(Context context) {
		showProgress(context, context.getString(R.string.str_loading));
	}
	public static void showProgress(Context context, String msg) {
		if(mProgressDialog == null) {
			mProgressDialog = LoadingDialog.show(context, msg, true, null);
		}
	}
	public static void hideProgress() {
		if(mProgressDialog != null) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
}
