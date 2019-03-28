package com.egov.smartqr;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.egov.smartqr.common.Common;
import com.egov.smartqr.util.NaviUtil;
import com.egov.smartqr.util.PreferenceUtil;
import com.egov.smartqr.util.StrUtil;
import com.egov.smartqr.util.UIUtil;
import com.egov.smartqr.vo.DetailViewVO;
import com.egov.smartqr.vo.MemberVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sds.BizAppLauncher.gov.aidl.GovController;
import com.sds.mobile.servicebrokerLib.ServiceBrokerLib;
import com.sds.mobile.servicebrokerLib.event.ResponseEvent;
import com.sds.mobile.servicebrokerLib.event.ResponseListener;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.go.mobile.mobp.mff.lib.plugins.basic.MDHBasic;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.edittext_id) EditText edittext_id;
    @BindView(R.id.edittext_pass) EditText edittext_pass;
    @BindView(R.id.btn_login) Button btn_login;

    // 공통기반 서비스 초기화 요청 식별 코드
    private static final int GOV_INIT = 1; // 인증 데이터
    String dn, cn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //다음지도api에 적용할 키해시 가져오기
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.gov.smartqr", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }



        btn_login.setOnClickListener(this);

        String idBk = PreferenceUtil.instance(this).get(PreferenceUtil.ID);
        edittext_id.setText(StrUtil.space(idBk));

edittext_id.setText("testuser");
edittext_pass.setText("1q2w3e4r!");//임시

        // 공통기반 서비스 초기화
        GovController.getInstance(this).bindService();
        GovController.startGovActivityForResult(this, GOV_INIT);
    }

    @Override
    public void onClick(View view) {
        if(view == btn_login) {
            String id = edittext_id.getText().toString();
            String pass = edittext_pass.getText().toString();

            if(StrUtil.isEmpty(id)) {
                UIUtil.alert(this, "아이디를 입력해주세요.");
                return;
            }
            if(StrUtil.isEmpty(pass)) {
                UIUtil.alert(this, "비밀번호를 입력해주세요.");
                return;
            }

            reqLogin(id, pass);
        }
    }

    void reqLogin(final String id, final String pass) {
        UIUtil.showProgress(this);
        ServiceBrokerLib lib = new ServiceBrokerLib(this,
                new ResponseListener() {
                    @Override
                    public void receive(ResponseEvent re) {
                        UIUtil.hideProgress();

                        // 중계서버 연계 API 요청 결과 확인
                        int resultCode = re.getResultCode();
                        String resultData = re.getResultData();//{"gid":0,"ins_org":"5580000","ins_id":"testuser"}

                        if(resultCode == 0) {
                            try {
                                Common.memberVO = new Gson().fromJson(re.getResultData(), MemberVO.class);
                            } catch (Exception e) {
                            }

                            //정상 로그인 성공
                            if(Common.memberVO != null && Common.memberVO.ins_id != null) {
                                PreferenceUtil.instance(LoginActivity.this).set(PreferenceUtil.ID, id);

                                Toast.makeText(LoginActivity.this, "로그인을 성공햇습니다.", Toast.LENGTH_SHORT).show();
                                NaviUtil.gotoMain(LoginActivity.this);
                                finish();
                            } else {//로그인실패
                                UIUtil.alert(LoginActivity.this, "로그인을 실패했습니다.\n아이디, 비밀번호를 확인해 주세요.");
                            }
                        } else {
                            UIUtil.alert(LoginActivity.this, "통신오류가 발생하였습니다.", re.getResultData() + "(" + re.getResultCode() + ")");
                        }
                    }
        });
        // 파라미터 설정
        Intent intent = new Intent();
        intent.putExtra("sCode", Common.sCode);
        intent.putExtra("parameter", String.format("method=loginCheck&id=%s&pw=%s", id, pass));
        lib.request(intent);


    }

    // 공통기반 서비스 초기화 요청 결과 수신
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent != null) {
            switch (requestCode) {
                case GOV_INIT:
                if (resultCode == Activity.RESULT_OK) {
                    // 공통기반 서비스 초기화 성공
                    // dn데이터 추출 및 파싱
                    dn = intent.getStringExtra("dn");
                    StringTokenizer st = new StringTokenizer(dn, ",");
                    while (st.hasMoreTokens()) {
                        String token = st.nextToken();
                        // cn추출
                        if (token.startsWith("cn")) {
                            cn = token.substring(3);
                        }
                    }
                } else {
                    // 공통기반 서비스 초기화 실패
                    // 애플리케이션을 종료합니다.
                    finish();
                }
                break;
                default:
                    // 예상하지 못한 결과
                    // 애플리케이션을 종료합니다.
                    finish();
                    break;
            }
        }
    }
}
