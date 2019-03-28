package com.egov.smartqr.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egov.smartqr.R;

// 메세지박스 다이얼로그
public class MsgDialog extends AlertDialog implements OnClickListener {
	
	// 메세지박스 타입
	public static int MB_OK = 0, MB_OKCANCEL = 1;
	
	// ok, cancel 리스너
	//android.view.View.OnClickListener m_clickListenerOk=null, m_clickListenerCancel=null;
	View.OnClickListener m_clickListenerOk=null, m_clickListenerCancel=null;
	
	LinearLayout linearlayout_ok=null;
	LinearLayout linearlayout_okcancel=null;
	TextView textview_title=null;
	TextView textview_msg=null;
	Button btn_ok=null, btn_ok2=null;
	Button btn_cancel=null;
	
	// 메세지
	int m_nType = MB_OK;
	String m_sTitle = "", m_sMsg = "";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		setContentView(R.layout.dialog_msg);
		
		setCancelable(false);
		
		linearlayout_ok=(LinearLayout)findViewById(R.id.linearlayout_ok);
		linearlayout_okcancel=(LinearLayout)findViewById(R.id.linearlayout_okcancel);
		textview_title=(TextView)findViewById(R.id.textview_title);
		textview_msg=(TextView)findViewById(R.id.textview_msg);
		btn_ok=(Button)findViewById(R.id.btn_ok);
		btn_ok2=(Button)findViewById(R.id.btn_ok2);
		btn_cancel=(Button)findViewById(R.id.btn_cancel);

		btn_ok.setOnClickListener(this);
		btn_ok2.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

		if(m_sTitle.length() > 0) textview_title.setText(m_sTitle);
		else textview_title.setVisibility(View.GONE);
		
		textview_msg.setText(m_sMsg);
		
		if(m_nType==MB_OKCANCEL) {
			linearlayout_ok.setVisibility(View.GONE);
			linearlayout_okcancel.setVisibility(View.VISIBLE);
		}
	}
	
	// 메세지 지정 / 메세지박스 타입 지정
	public void setMsgType(String sTitle, String sMsg, int nType) {
		m_sTitle = sTitle;
		m_sMsg = sMsg;
		m_nType = nType;
	}

	public MsgDialog(Context context) {
		// 스타일 지정
		super(context, R.style.Dialog);
	}
	
	public void setOkClickLinstener(View.OnClickListener listener) {
		m_clickListenerOk = listener;
	}
	public void setCancelClickLinstener(View.OnClickListener listener) {
		m_clickListenerCancel = listener;
	}

	public void onClick(View v) {
		if(v == btn_ok) {
			if(m_clickListenerOk != null) m_clickListenerOk.onClick(btn_ok);
			dismiss();
		}
		else if(v == btn_ok2) {
			if(m_clickListenerOk != null) m_clickListenerOk.onClick(btn_ok2);
			dismiss();
		}
		else if(v == btn_cancel) {
			if(m_clickListenerCancel != null) m_clickListenerCancel.onClick(btn_cancel);
			dismiss();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		dismiss();
	}
}

