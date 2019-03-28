package com.egov.smartqr.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.egov.smartqr.R;
import com.egov.smartqr.util.UIUtil;


// 메세지박스 다이얼로그
public class LoadingDialog extends AlertDialog {

	TextView textview_msg;
	String m_sMsg = "";

	boolean mCancelable = true;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_loading);

		setCancelable(false);

		textview_msg=(TextView)findViewById(R.id.textview_msg);
		if(m_sMsg != null && m_sMsg.length() > 0)
			textview_msg.setText(m_sMsg);
	}

	// 메세지 지정
	public void setMsg(String sMsg) {
		m_sMsg = sMsg;
	}

	public LoadingDialog(Context context) {
		// 스타일 지정
		super(context, R.style.Dialog);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		UIUtil.hideProgress();
	}

	public static LoadingDialog show(Context context, String message, boolean cancelable, OnCancelListener onCancelListener) {

		try {
			LoadingDialog dialog = new LoadingDialog(context);
			dialog.mCancelable = cancelable;
			dialog.m_sMsg = message;

			dialog.setCanceledOnTouchOutside(true);
			dialog.setCancelable(cancelable);
			dialog.setOnCancelListener(onCancelListener);
			dialog.show();

			return dialog;
		} catch (Exception e) {
//			Log.e(TAG, e);
		}
		return null;
	}
}

